package com.example.datn.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "dia_chi")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiaChi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Column(name = "xa")
    String xa;

    @Column(name = "huyen")
    String huyen;

    @Column(name = "tinh")
    String tinh;
    @Column(name = "so_nha_ngo_duong")
    String soNhaNgoDuong;

}
