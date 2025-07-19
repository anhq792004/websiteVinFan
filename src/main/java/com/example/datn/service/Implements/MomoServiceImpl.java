package com.example.datn.service.Implements;

import com.example.datn.entity.HoaDon.HoaDon;
import com.example.datn.entity.HoaDon.LichSuHoaDon;
import com.example.datn.entity.MomoTransaction;
import com.example.datn.repository.HoaDonRepo.LichSuHoaDonRepo;
import com.example.datn.repository.MomoTransactionRepository;
import com.example.datn.service.BanHang.BanHangService;
import com.example.datn.service.HoaDonService.HoaDonService;
import com.example.datn.service.MomoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MomoServiceImpl implements MomoService {
    private static final Logger logger = LoggerFactory.getLogger(MomoServiceImpl.class);

    private final MomoTransactionRepository momoTransactionRepository;
    private final HoaDonService hoaDonService;
    private final BanHangService banHangService;
    private final LichSuHoaDonRepo lichSuHoaDonRepo;
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Value("${momo.partner-code}")
    private String partnerCode;
    
    @Value("${momo.access-key}")
    private String accessKey;
    
    @Value("${momo.secret-key}")
    private String secretKey;
    
    @Value("${momo.api-endpoint}")
    private String apiEndpoint;
    
    @Value("${momo.return-url}")
    private String returnUrl;
    
    @Value("${momo.notify-url}")
    private String notifyUrl;
    
    @Override
    public MomoTransaction createTransaction(HoaDon hoaDon) {
        return createTransaction(hoaDon, "admin");
    }
    
    @Override
    public MomoTransaction createTransaction(HoaDon hoaDon, String context) {
        try {
            // Kiểm tra xem đã có giao dịch cho hóa đơn này chưa
            Optional<MomoTransaction> existingTransaction = momoTransactionRepository.findByHoaDonId(hoaDon.getId());
            if (existingTransaction.isPresent()) {
                return existingTransaction.get();
            }
            
            String orderId = "HD" + hoaDon.getMa() + "_" + System.currentTimeMillis();
            String requestId = "RQ" + hoaDon.getMa() + "_" + System.currentTimeMillis();
            
            // Tạo giao dịch mới
            MomoTransaction transaction = new MomoTransaction();
            transaction.setHoaDon(hoaDon);
            transaction.setPartnerCode(partnerCode);
            transaction.setOrderId(orderId);
            transaction.setRequestId(requestId);
            
            BigDecimal amount = hoaDon.getTongTienSauGiamGia() != null ?
                    hoaDon.getTongTienSauGiamGia() : hoaDon.getTongTien();
            transaction.setAmount(amount);
            
            transaction.setOrderInfo("Thanh toán đơn hàng " + hoaDon.getMa());
            transaction.setOrderType("momo_wallet");
            
            // Xác định URLs dựa trên context
            String actualReturnUrl;
            String actualNotifyUrl;
            if ("user".equals(context)) {
                actualReturnUrl = returnUrl.replace("/payment/", "/checkout/");
                actualNotifyUrl = notifyUrl.replace("/payment/", "/checkout/");
            } else {
                actualReturnUrl = returnUrl;
                actualNotifyUrl = notifyUrl;
            }
            
            transaction.setRedirectUrl(actualReturnUrl);
            transaction.setIpnUrl(actualNotifyUrl);
            transaction.setRequestType("captureWallet");
            transaction.setExtraData("");
            transaction.setNgayTao(LocalDateTime.now());
            transaction.setTrangThai(0); // 0: Chờ thanh toán
            
            // Tạo payload cho API MoMo
            Map<String, Object> requestBody = new LinkedHashMap<>();
            requestBody.put("partnerCode", partnerCode);
            requestBody.put("requestId", requestId);
            requestBody.put("amount", amount.longValue());
            requestBody.put("orderId", orderId);
            requestBody.put("orderInfo", transaction.getOrderInfo());
            requestBody.put("redirectUrl", actualReturnUrl);
            requestBody.put("ipnUrl", actualNotifyUrl);
            requestBody.put("requestType", "captureWallet");
            requestBody.put("extraData", "");
            requestBody.put("lang", "vi");
            
            // Tạo chữ ký
            StringBuilder rawSignature = new StringBuilder();
            rawSignature.append("accessKey=").append(accessKey)
                    .append("&amount=").append(amount.longValue())
                    .append("&extraData=")
                    .append("&ipnUrl=").append(actualNotifyUrl)
                    .append("&orderId=").append(orderId)
                    .append("&orderInfo=").append(transaction.getOrderInfo())
                    .append("&partnerCode=").append(partnerCode)
                    .append("&redirectUrl=").append(actualReturnUrl)
                    .append("&requestId=").append(requestId)
                    .append("&requestType=captureWallet");
            
            logger.debug("Raw signature: {}", rawSignature.toString());
            
            String signature;
            try {
                signature = generateSignature(rawSignature.toString(), secretKey);
            } catch (Exception e) {
                logger.error("Lỗi khi tạo chữ ký: {}", e.getMessage());
                throw new RuntimeException("Không thể tạo chữ ký cho giao dịch MoMo");
            }
            
            requestBody.put("signature", signature);
            transaction.setSignature(signature);
            
            // Gọi API MoMo
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            
            try {
                logger.debug("Calling MoMo API: {}", apiEndpoint);
                Map<String, Object> response = restTemplate.postForObject(
                    apiEndpoint, request, Map.class);
                
                logger.debug("MoMo API response: {}", response);
                
                if (response != null) {
                    if (response.containsKey("resultCode")) {
                        Integer resultCode = Integer.parseInt(response.get("resultCode").toString());
                        transaction.setResultCode(resultCode);
                        
                        if (resultCode != 0) {
                            String message = response.containsKey("message") ? 
                                response.get("message").toString() : "Unknown error";
                            transaction.setMessage(message);
                            momoTransactionRepository.save(transaction);
                            throw new RuntimeException("Lỗi từ MoMo: " + message);
                        }
                    }
                    
                    if (response.containsKey("payUrl")) {
                        String payUrl = response.get("payUrl").toString();
                        transaction.setPayUrl(payUrl);
                        transaction.setMessage(response.containsKey("message") ? 
                            response.get("message").toString() : "Success");
                        
                        // Cập nhật trạng thái đơn hàng
                        hoaDon.setPhuongThucThanhToan("MOMO");
                        hoaDonService.saveHoaDon(hoaDon);
                        
                        // Lưu thông tin giao dịch
                        return momoTransactionRepository.save(transaction);
                    }
                }
                
                throw new RuntimeException("Không nhận được payUrl từ MoMo");
            } catch (Exception e) {
                logger.error("Lỗi khi gọi API MoMo: {}", e.getMessage());
                transaction.setMessage("Lỗi kết nối MoMo: " + e.getMessage());
                transaction.setTrangThai(2); // 2: Lỗi
                momoTransactionRepository.save(transaction);
                
                // Trong môi trường test, vẫn trả về transaction để có thể hiển thị QR code giả lập
                return transaction;
            }
        } catch (Exception e) {
            logger.error("Lỗi khi tạo giao dịch MoMo: {}", e.getMessage());
            throw new RuntimeException("Không thể tạo giao dịch MoMo: " + e.getMessage());
        }
    }

    @Override
    public boolean confirmTransaction(Long hoaDonId) {
        Optional<MomoTransaction> transactionOpt = momoTransactionRepository.findByHoaDonId(hoaDonId);
        if (!transactionOpt.isPresent()) {
            return false;
        }
        
        MomoTransaction transaction = transactionOpt.get();
        transaction.setTrangThai(1); // 1: Đã thanh toán
        transaction.setTransId(System.currentTimeMillis()); // Giả lập mã giao dịch
        transaction.setMessage("Giao dịch thành công");
        transaction.setResponseTime(System.currentTimeMillis());
        
        momoTransactionRepository.save(transaction);
        
        // Cập nhật trạng thái hóa đơn
        try {
            // Kiểm tra loại hóa đơn để xử lý phù hợp
            Optional<HoaDon> hoaDonOpt = hoaDonService.findHoaDonById(hoaDonId);
            if (hoaDonOpt.isPresent()) {
                HoaDon hoaDon = hoaDonOpt.get();
                
                if (hoaDon.getLoaiHoaDon() != null && !hoaDon.getLoaiHoaDon()) {
                    // Đây là hóa đơn online (false), xử lý riêng
                    confirmOnlineOrder(hoaDon);
                } else {
                    // Đây là hóa đơn admin (true), dùng logic cũ
                    banHangService.thanhToan(hoaDonId);
                }
            }
            return true;
        } catch (Exception e) {
            // Nếu có lỗi khi hoàn tất hóa đơn
            transaction.setTrangThai(2); // 2: Lỗi
            transaction.setMessage("Lỗi khi hoàn tất hóa đơn: " + e.getMessage());
            momoTransactionRepository.save(transaction);
            return false;
        }
    }

    @Override
    public boolean cancelTransaction(Long hoaDonId) {
        Optional<MomoTransaction> transactionOpt = momoTransactionRepository.findByHoaDonId(hoaDonId);
        if (!transactionOpt.isPresent()) {
            return false;
        }
        
        MomoTransaction transaction = transactionOpt.get();
        transaction.setTrangThai(3); // 3: Đã hủy
        transaction.setMessage("Giao dịch đã hủy");
        
        momoTransactionRepository.save(transaction);
        
        // Cập nhật lại trạng thái hóa đơn
        try {
            Optional<HoaDon> hoaDonOpt = hoaDonService.findHoaDonById(hoaDonId);
            if (hoaDonOpt.isPresent()) {
                HoaDon hoaDon = hoaDonOpt.get();
                hoaDon.setTrangThai(hoaDonService.getTrangThaiHoaDon().getHoaDonCho());
                hoaDon.setPhuongThucThanhToan(null);
                hoaDonService.saveHoaDon(hoaDon);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public MomoTransaction getTransactionByHoaDonId(Long hoaDonId) {
        return momoTransactionRepository.findByHoaDonId(hoaDonId).orElse(null);
    }
    
    @Override
    public MomoTransaction getTransactionByOrderId(String orderId) {
        return momoTransactionRepository.findByOrderId(orderId).orElse(null);
    }
    
    /**
     * Xử lý hoàn tất đơn hàng online mà không thay đổi loại hóa đơn
     */
    private void confirmOnlineOrder(HoaDon hoaDon) {
        // Cập nhật trạng thái hóa đơn thành hoàn thành nhưng giữ nguyên loaiHoaDon = false
        hoaDon.setNgaySua(LocalDateTime.now());
        hoaDon.setTrangThai(hoaDonService.getTrangThaiHoaDon().getHoanThanh());
        // Không thay đổi loaiHoaDon - giữ nguyên false (Online)
        
        // Tạo lịch sử hóa đơn
        LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
        lichSuHoaDon.setHoaDon(hoaDon);
        lichSuHoaDon.setTrangThai(hoaDonService.getTrangThaiHoaDon().getHoanThanh());
        lichSuHoaDon.setNgayTao(LocalDateTime.now());
        lichSuHoaDon.setNguoiTao(hoaDon.getKhachHang() != null ? hoaDon.getKhachHang().getTen() : "Online Customer");
        lichSuHoaDon.setMoTa("Thanh toán MoMo thành công - Đơn hàng online");
        
        // Lưu vào database
        hoaDonService.saveHoaDon(hoaDon);
        lichSuHoaDonRepo.save(lichSuHoaDon);
    }
    
    private String generateSignature(String message, String key) throws Exception {
        Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        hmacSHA256.init(secretKey);
        byte[] hash = hmacSHA256.doFinal(message.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
} 