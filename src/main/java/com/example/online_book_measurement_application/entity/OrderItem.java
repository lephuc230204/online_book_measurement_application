package com.example.online_book_measurement_application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book bbook;

    private int quantity;

    private Double price;

    @ManyToOne
    @JoinColumn( name = "order_id")
    private Order order;


}
