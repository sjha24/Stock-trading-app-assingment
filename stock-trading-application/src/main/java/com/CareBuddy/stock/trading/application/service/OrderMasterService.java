package com.CareBuddy.stock.trading.application.service;
import com.CareBuddy.stock.trading.application.exception.ResourceNotFoundException;
import com.CareBuddy.stock.trading.application.exception.StockQuantityNotEnoughException;
import com.CareBuddy.stock.trading.application.model.OrderMaster;
import com.CareBuddy.stock.trading.application.model.TradeDetails;
import com.CareBuddy.stock.trading.application.repository.OrderMasterRepository;
import com.CareBuddy.stock.trading.application.repository.TradeDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderMasterService implements IOrderMasterService{
    @Autowired
    OrderMasterRepository orderMasterRepository;
    @Autowired
    TradeDetailsRepository tradeDetailsRepository;


    @Override
    public List<OrderMaster> listAllOrders() {
        return orderMasterRepository.findAll();
    }

    @Override
    public ResponseEntity<OrderMaster> createOrder(Integer tradeDetailsId, LocalDateTime orderDateTime, Integer orderQuantity) throws ResourceNotFoundException, StockQuantityNotEnoughException {
        Optional<TradeDetails> tradeDetailOptional = tradeDetailsRepository.findById(tradeDetailsId);

        if (tradeDetailOptional.isEmpty()) {
            throw new ResourceNotFoundException("TradeDetail not found with id " +tradeDetailsId);
        }


        TradeDetails tradeDetail = tradeDetailOptional.get();

        if(orderQuantity>tradeDetail.getQuantity()){
            throw new StockQuantityNotEnoughException("Your Order Quantity is Over from or Stock Quantity plz order "+tradeDetail.getQuantity()+" or Less than this Quantity");
        }else{

            OrderMaster order = getOrderMaster(orderQuantity, tradeDetail);
            order.setOrderDateTime(orderDateTime);
            order.setStockName(tradeDetail.getStockName());
            tradeDetail.setQuantity(tradeDetail.getQuantity()-orderQuantity);
            tradeDetailsRepository.save(tradeDetail);

            orderMasterRepository.save(order);

            return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
        }
    }

    @Override
    public ResponseEntity<?> updateOrderById(Integer id, Integer quantity, LocalDateTime updateDateTime) throws ResourceNotFoundException {

        OrderMaster orderMaster = orderMasterRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Order Is not found with this Id "+id));
        orderMaster.setOrderQuantity(quantity);
        orderMaster.setOrderDateTime(updateDateTime);

        BigDecimal pricePerUnit = orderMaster.getPerUnitPrice();

        BigDecimal totalPrice = pricePerUnit.multiply(BigDecimal.valueOf(quantity));
        orderMaster.setTotalPrice(totalPrice);

        orderMasterRepository.save(orderMaster);

        return new ResponseEntity<>(orderMaster,HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<?> cancelOrderById(Integer id) throws ResourceNotFoundException {
        OrderMaster orderMaster = orderMasterRepository.findById(id).
                        orElseThrow(()-> new ResourceNotFoundException("Order Is not found with this Id "+id));
        BigDecimal refundedAmount = orderMaster.getTotalPrice();
        orderMasterRepository.delete(orderMaster);
        return new ResponseEntity<>("Order Cancel Successfully your refunded Amount - "+refundedAmount,HttpStatus.ACCEPTED);
    }

    private static OrderMaster getOrderMaster(Integer orderQuantity, TradeDetails tradeDetail) {
        OrderMaster order = new OrderMaster();
        order.setOrderQuantity(orderQuantity);
        order.setPerUnitPrice(tradeDetail.getPricePerUnit());
        order.setType(String.valueOf(tradeDetail.getType()));
        order.setStockSymbol(tradeDetail.getStockSymbol());
        order.setStatus("created"); // Initial status is always "created"

        BigDecimal pricePerUnit = tradeDetail.getPricePerUnit();

        BigDecimal totalPrice = pricePerUnit.multiply(BigDecimal.valueOf(orderQuantity));

        order.setTotalPrice(totalPrice);
        return order;
    }
}
