package com.example.online_book_measurement_application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "orders")
public class Order {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private com.example.online_book_measurement_application.entity.User user;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    private String shippingUnit;

    private LocalDate orderDate;

    private Double totalPrice;

    private String phone;

    @Column(nullable = false)
    private String note; // Ghi chú của khách hàng

    @Column(nullable = false)
    private double shippingFee; // Phí vận chuyển

    @Enumerated(EnumType.STRING)
    private Status status;
    public enum Status {
        PENDING, CONFIRMED, SHIPPED, CANCELED
    }
    @Enumerated(EnumType.STRING)
    private Payment paymentMethod;
    public enum Payment {
        COD,ONLINE_PAYMENT,MOMO
    }
}
