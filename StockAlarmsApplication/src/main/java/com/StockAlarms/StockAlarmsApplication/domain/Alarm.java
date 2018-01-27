package com.StockAlarms.StockAlarmsApplication.domain;

import com.StockAlarms.StockAlarmsApplication.domain.enums.AlarmStatus;
import org.hibernate.validator.constraints.NotEmpty;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import javax.persistence.*;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by Turian Ovidiu.
 * This class represent Alarm Entity.
 */

@Entity
public class Alarm implements DomainObject {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Version
    private Integer version;

    @Enumerated(EnumType.STRING)
    private AlarmStatus alarmStatus;


    private String symbol;

    private String name;

    private String currency;

    private String stockExchange;

    private BigDecimal price;

    private BigDecimal priceTarget;

    private BigDecimal percentChange;

    private BigDecimal currentPrice = new BigDecimal(0.0000);

    private BigDecimal currentPercentChange;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public AlarmStatus getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(AlarmStatus alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(String stockExchange) {
        this.stockExchange = stockExchange;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceTarget() {
        return priceTarget;
    }

    public void setPriceTarget(BigDecimal priceTarget) {
        this.priceTarget = priceTarget;
    }

    public BigDecimal getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(BigDecimal percentChange) {
        this.percentChange = percentChange;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void calculatePriceTarget() {
        BigDecimal temp = (getPrice().multiply(getPercentChange())).divide(new BigDecimal(100));
        this.priceTarget = getPrice().add(temp);
    }

    public void setCurrentPercentChange(BigDecimal currentPercentChange) {
        this.currentPercentChange = currentPercentChange;
    }

    public BigDecimal getCurrentPercentChange() {
        if (!getCurrentPrice().equals(0.0000)) {
            BigDecimal temp = getCurrentPrice().subtract(this.price);
            this.currentPercentChange = temp.divide(this.price, 4, BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100));
        }
        return currentPercentChange;
    }
}
