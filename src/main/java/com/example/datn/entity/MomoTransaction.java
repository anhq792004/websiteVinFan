package com.example.datn.entity;

import com.example.datn.entity.HoaDon.HoaDon;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Momo_transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MomoTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hoa_don")
    HoaDon hoaDon;

    @Column(name = "partner_code")
    String partnerCode;

    @Column(name = "order_id")
    String orderId;
    
    @Column(name = "request_id")
    String requestId;

    @Column(name = "amount")
    BigDecimal amount;

    @Column(name = "order_info")
    String orderInfo;

    @Column(name = "order_type")
    String orderType;
    
    @Column(name = "trans_id")
    Long transId;
    
    @Column(name = "result_code")
    Integer resultCode;
    
    @Column(name = "redirect_url")
    String redirectUrl;
    
    @Column(name = "ipn_url")
    String ipnUrl;
    
    @Column(name = "request_type")
    String requestType;
    
    @Column(name = "extra_data")
    String extraData;

    @Column(name = "response_time")
    Long responseTime;

    @Column(name = "message")
    String message;

    @Column(name = "pay_type")
    String payType;
    
    @Column(name = "pay_url")
    String payUrl;

    @Column(name = "signature")
    String signature;

    @Column(name = "ngay_tao")
    LocalDateTime ngayTao;

    @Column(name = "trang_thai")
    Integer trangThai;
} 