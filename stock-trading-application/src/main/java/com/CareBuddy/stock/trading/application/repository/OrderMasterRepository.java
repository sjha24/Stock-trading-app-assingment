package com.CareBuddy.stock.trading.application.repository;

import com.CareBuddy.stock.trading.application.model.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  OrderMasterRepository extends JpaRepository<OrderMaster,Integer> {

}
