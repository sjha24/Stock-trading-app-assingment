package com.CareBuddy.stock.trading.application.service;
import com.CareBuddy.stock.trading.application.exception.InvalidTokenException;
import com.CareBuddy.stock.trading.application.exception.ResourceNotFoundException;
import com.CareBuddy.stock.trading.application.exception.StockQuantityNotEnoughException;
import com.CareBuddy.stock.trading.application.model.OrderMaster;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface IOrderMasterService {
    List<OrderMaster> listAllOrders();

    ResponseEntity<?> updateOrderById(Integer id, Integer quantity, LocalDateTime updateDateTime) throws ResourceNotFoundException;

    ResponseEntity<?>cancelOrderById(Integer id) throws ResourceNotFoundException;

    ResponseEntity<OrderMaster> createOrder(Integer tradeDetailsId, LocalDateTime orderDateTime, Integer orderQuantity) throws ResourceNotFoundException, StockQuantityNotEnoughException, InvalidTokenException;

}
