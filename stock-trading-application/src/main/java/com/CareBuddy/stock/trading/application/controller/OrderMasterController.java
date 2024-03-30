package com.CareBuddy.stock.trading.application.controller;
import com.CareBuddy.stock.trading.application.exception.InvalidTokenException;
import com.CareBuddy.stock.trading.application.exception.ResourceNotFoundException;
import com.CareBuddy.stock.trading.application.exception.StockQuantityNotEnoughException;
import com.CareBuddy.stock.trading.application.model.OrderMaster;
import com.CareBuddy.stock.trading.application.service.IOrderMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("*")
@RequestMapping("order")
@RestController
public class OrderMasterController {
    @Autowired
    IOrderMasterService orderMasterService;

    @PostMapping("/create")
    public ResponseEntity<OrderMaster> createOrderFromTrade(@RequestParam Integer tradeDetailsId,
                                                       @RequestParam Integer orderQuantity,
                                                       @RequestParam LocalDateTime orderDateTime
                                                       ) throws ResourceNotFoundException, StockQuantityNotEnoughException, InvalidTokenException {
        return orderMasterService.createOrder(tradeDetailsId,orderDateTime,orderQuantity);
    }


    @GetMapping("/list")
    public ResponseEntity<List<OrderMaster>> listOrders(){
        List<OrderMaster> orders = orderMasterService.listAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PatchMapping("update/{id}")
    public ResponseEntity<?>updateOrderById(@PathVariable Integer id,
                                            @RequestParam(required = false) Integer quantity,
                                            @RequestParam(required = false) LocalDateTime updateDateTime) throws ResourceNotFoundException {
        return orderMasterService.updateOrderById(id,quantity,updateDateTime);
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?>cancelOrderById(@PathVariable Integer id) throws ResourceNotFoundException {
        return orderMasterService.cancelOrderById(id);
    }

}
