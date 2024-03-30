package com.CareBuddy.stock.trading.application.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trade_details")
public class TradeDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime tradeDateTime;
    private String stockSymbol;
    private String stockName;
    private BigDecimal listingPrice;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private Type type; //enum type (BUY/SELL)
    private BigDecimal pricePerUnit;

    @OneToMany(mappedBy = "tradeDetails",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<OrderMaster>orderMasterList;

}
