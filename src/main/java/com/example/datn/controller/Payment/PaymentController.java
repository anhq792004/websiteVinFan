package com.example.datn.controller.Payment;

import com.example.datn.entity.HoaDon.HoaDon;
import com.example.datn.entity.MomoTransaction;
import com.example.datn.service.HoaDonService.HoaDonService;
import com.example.datn.service.MomoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    
    private final MomoService momoService;
    private final HoaDonService hoaDonService;
    
    /**
     * Endpoint để nhận IPN callback từ Momo
     */
    @PostMapping("/momo-notify")
    @ResponseBody
    public ResponseEntity<String> momoNotify(@RequestBody Map<String, Object> payload) {
        logger.info("Received Momo IPN notification: {}", payload);
        
        try {
            // Xử lý thông báo từ Momo
            if (payload.containsKey("orderId") && payload.containsKey("resultCode")) {
                String orderId = payload.get("orderId").toString();
                int resultCode = Integer.parseInt(payload.get("resultCode").toString());
                
                // Kiểm tra kết quả giao dịch
                if (resultCode == 0) {
                    // Giao dịch thành công
                    // Tìm giao dịch Momo tương ứng
                    MomoTransaction transaction = momoService.getTransactionByOrderId(orderId);
                    if (transaction != null) {
                        // Cập nhật trạng thái giao dịch
                        momoService.confirmTransaction(transaction.getHoaDon().getId());
                        return ResponseEntity.ok("Transaction processed successfully");
                    }
                }
            }
            
            return ResponseEntity.ok("Transaction processing failed");
        } catch (Exception e) {
            logger.error("Error processing Momo IPN: {}", e.getMessage());
            return ResponseEntity.ok("Error processing transaction");
        }
    }
    
    /**
     * Endpoint khi người dùng được redirect từ Momo về
     */
    @GetMapping("/momo-return")
    public String momoReturn(@RequestParam Map<String, String> params, RedirectAttributes redirectAttributes) {
        logger.info("User returned from Momo payment: {}", params);
        
        try {
            if (params.containsKey("orderId") && params.containsKey("resultCode")) {
                String orderId = params.get("orderId");
                int resultCode = Integer.parseInt(params.get("resultCode"));
                
                // Kiểm tra kết quả giao dịch
                if (resultCode == 0) {
                    // Giao dịch thành công
                    // Tìm giao dịch Momo tương ứng
                    MomoTransaction transaction = momoService.getTransactionByOrderId(orderId);
                    if (transaction != null) {
                        // Cập nhật trạng thái giao dịch
                        momoService.confirmTransaction(transaction.getHoaDon().getId());
                        redirectAttributes.addFlashAttribute("paymentStatus", "success");
                        redirectAttributes.addFlashAttribute("paymentMessage", "Thanh toán thành công!");
                        return "redirect:/sale/index";
                    }
                } else {
                    // Giao dịch thất bại
                    String message = params.getOrDefault("message", "Giao dịch thất bại");
                    redirectAttributes.addFlashAttribute("paymentStatus", "failed");
                    redirectAttributes.addFlashAttribute("paymentMessage", message);
                    
                    // Nếu có orderId, lấy ID hóa đơn để redirect về trang chi tiết hóa đơn đó
                    if (orderId != null && !orderId.isEmpty()) {
                        MomoTransaction transaction = momoService.getTransactionByOrderId(orderId);
                        if (transaction != null) {
                            Long hoaDonId = transaction.getHoaDon().getId();
                            return "redirect:/sale/hdct?idHD=" + hoaDonId;
                        }
                    }
                    
                    return "redirect:/sale/index";
                }
            }
            
            redirectAttributes.addFlashAttribute("paymentStatus", "unknown");
            redirectAttributes.addFlashAttribute("paymentMessage", "Không thể xác định kết quả thanh toán");
            return "redirect:/sale/index";
        } catch (Exception e) {
            logger.error("Error handling Momo return: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("paymentStatus", "error");
            redirectAttributes.addFlashAttribute("paymentMessage", "Lỗi xử lý thanh toán: " + e.getMessage());
            return "redirect:/sale/index";
        }
    }
} 