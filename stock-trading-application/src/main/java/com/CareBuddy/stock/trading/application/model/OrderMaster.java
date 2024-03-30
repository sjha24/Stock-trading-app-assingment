package com.CareBuddy.stock.trading.application.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_master")
public class OrderMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    private Integer orderQuantity;
    private String stockName;
    private BigDecimal perUnitPrice;
    private String type;
    private String stockSymbol;
    private LocalDateTime orderDateTime;
    private BigDecimal totalPrice;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tradeDetails_id")
    @JsonBackReference
    private TradeDetails tradeDetails;
}
