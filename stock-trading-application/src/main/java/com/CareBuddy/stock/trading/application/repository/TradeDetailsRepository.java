package com.CareBuddy.stock.trading.application.repository;

import com.CareBuddy.stock.trading.application.model.TradeDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeDetailsRepository extends JpaRepository<TradeDetails,Integer> {
}
