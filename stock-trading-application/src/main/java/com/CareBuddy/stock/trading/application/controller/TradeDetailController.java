package com.CareBuddy.stock.trading.application.controller;

import com.CareBuddy.stock.trading.application.exception.InternalServerException;
import com.CareBuddy.stock.trading.application.exception.TradeDetailsNotFound;
import com.CareBuddy.stock.trading.application.exception.UnauthorisedException;
import com.CareBuddy.stock.trading.application.model.TradeDetails;
import com.CareBuddy.stock.trading.application.service.ITradeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("*")
@RequestMapping("tradeDetails")
@RestController
public class TradeDetailController {
    @Autowired
    private ITradeDetailService tradeDetailService;

    @PostMapping("/add")
    public  ResponseEntity<?>addTradeDetails(
                                                @RequestParam("tradeDateTime")LocalDateTime tradeDateTime,
                                                @RequestParam("stockSymbol") String stockSymbol,
                                                @RequestParam("stockName") String stockName,
                                                @RequestParam("listingPrice")BigDecimal listingPrice,
                                                @RequestParam("quantity")Integer quantity,
                                                @RequestParam("type")String type,
                                                @RequestParam("pricePerUnit")BigDecimal pricePerUnit
                                                ) throws IOException, SQLException, UnauthorisedException {

        TradeDetails tradeDetails = tradeDetailService.addTradeDetails(tradeDateTime,stockSymbol,stockName,listingPrice,quantity,type,pricePerUnit);


        return new ResponseEntity<>(tradeDetails,HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public  ResponseEntity<?>updateTradeDetails(@PathVariable Integer id,
                                                @RequestParam(required = false)LocalDateTime tradeDateTime,
                                                @RequestParam(required = false)String stockSymbol,
                                                @RequestParam(required = false)String stockName,
                                                @RequestParam(required = false)BigDecimal listingPrice,
                                                @RequestParam(required = false)Integer quantity,
                                                @RequestParam(required = false)String type,
                                                @RequestParam(required = false)BigDecimal pricePerUnit
                                                ) throws InternalServerException, UnauthorisedException {

        TradeDetails tradeDetails = tradeDetailService.updateTradeDetails(id,stockSymbol,tradeDateTime,stockName,listingPrice,quantity,type,pricePerUnit);

        return ResponseEntity.ok(tradeDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrade(@PathVariable Integer id) throws TradeDetailsNotFound, UnauthorisedException {

        boolean status = tradeDetailService.deleteTradeDetail(id);
        if(status) {
            return new ResponseEntity<>("TradeDetails Remove successfully !!", HttpStatus.OK);
        }
        throw new TradeDetailsNotFound("Please Enter Valid Details");
    }

    @GetMapping("/all")
    public List<TradeDetails> listTrades() throws UnauthorisedException {
        return tradeDetailService.findAllTradeDetails();
    }
}
