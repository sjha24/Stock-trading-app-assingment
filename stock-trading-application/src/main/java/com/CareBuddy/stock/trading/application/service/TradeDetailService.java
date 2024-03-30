package com.CareBuddy.stock.trading.application.service;

import com.CareBuddy.stock.trading.application.exception.InternalServerException;
import com.CareBuddy.stock.trading.application.exception.UnauthorisedException;
import com.CareBuddy.stock.trading.application.model.TradeDetails;
import com.CareBuddy.stock.trading.application.model.Type;
import com.CareBuddy.stock.trading.application.repository.TradeDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TradeDetailService implements ITradeDetailService{
    @Autowired
    private TradeDetailsRepository tradeDetailsRepository;
    @Override
    public TradeDetails addTradeDetails(LocalDateTime tradeDateTime, String stockSymbol, String stockName, BigDecimal listingPrice, Integer quantity, String type, BigDecimal pricePerUnit) {
        TradeDetails tradeDetails = new TradeDetails();
        tradeDetails.setTradeDateTime(tradeDateTime);
        tradeDetails.setType(Type.valueOf(type));
        tradeDetails.setQuantity(quantity);
        tradeDetails.setListingPrice(listingPrice);
        tradeDetails.setPricePerUnit(pricePerUnit);
        tradeDetails.setStockSymbol(stockSymbol);
        tradeDetails.setStockName(stockName);

        return tradeDetailsRepository.save(tradeDetails);
    }
    @Override
    public TradeDetails updateTradeDetails(Integer id, String stockSymbol, LocalDateTime tradeDateTime, String stockName, BigDecimal listingPrice, Integer quantity, String type, BigDecimal pricePerUnit) throws InternalServerException, UnauthorisedException {


        TradeDetails tradeDetails = tradeDetailsRepository.findById(id).get();

        if (tradeDateTime != null) tradeDetails.setTradeDateTime(tradeDateTime);
        if (listingPrice != null) tradeDetails.setListingPrice(listingPrice);
        if (quantity != null) tradeDetails.setQuantity(quantity);
        if (type != null) tradeDetails.setType(Type.valueOf(type));
        if (pricePerUnit != null) tradeDetails.setPricePerUnit(pricePerUnit);
        if (stockSymbol != null) tradeDetails.setStockSymbol(stockSymbol);
        if(stockName != null)tradeDetails.setStockName(stockName);

        return tradeDetailsRepository.save(tradeDetails);
    }

    @Override
    public boolean deleteTradeDetail(Integer id){
            Optional<TradeDetails> tradeDetails = tradeDetailsRepository.findById(id);

            if (tradeDetails.isPresent()) {
                tradeDetailsRepository.deleteById(id);
                return true;
            }
        return false;
    }

    @Override
    public List<TradeDetails> findAllTradeDetails() {
        return tradeDetailsRepository.findAll();
    }


}
