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
    public String momoReturn(@RequestParam Map<String, String> params) {
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
                        return "redirect:/sale/index?payment=success";
                    }
                } else {
                    // Giao dịch thất bại
                    return "redirect:/sale/index?payment=failed&reason=" + params.getOrDefault("message", "unknown");
                }
            }
            
            return "redirect:/sale/index?payment=unknown";
        } catch (Exception e) {
            logger.error("Error handling Momo return: {}", e.getMessage());
            return "redirect:/sale/index?payment=error";
        }
    }
} 