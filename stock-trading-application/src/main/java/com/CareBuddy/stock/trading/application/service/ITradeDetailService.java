package com.CareBuddy.stock.trading.application.service;

import com.CareBuddy.stock.trading.application.exception.InternalServerException;
import com.CareBuddy.stock.trading.application.exception.UnauthorisedException;
import com.CareBuddy.stock.trading.application.model.TradeDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ITradeDetailService {
    TradeDetails updateTradeDetails(Integer id, String photoBytes, LocalDateTime tradeDateTime, String stockName, BigDecimal listingPrice, Integer quantity, String type, BigDecimal pricePerUnit) throws InternalServerException, UnauthorisedException;

    boolean deleteTradeDetail(Integer id) throws UnauthorisedException;

    List<TradeDetails> findAllTradeDetails() throws UnauthorisedException;
    TradeDetails addTradeDetails(LocalDateTime tradeDateTime, String stockSymbol, String stockName, BigDecimal listingPrice, Integer quantity, String type, BigDecimal pricePerUnit);
}
