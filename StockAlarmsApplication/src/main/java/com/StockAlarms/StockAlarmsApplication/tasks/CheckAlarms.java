package com.StockAlarms.StockAlarmsApplication.tasks;


import com.StockAlarms.StockAlarmsApplication.domain.Alarm;
import com.StockAlarms.StockAlarmsApplication.domain.enums.AlarmStatus;
import com.StockAlarms.StockAlarmsApplication.services.AlarmService;
import com.StockAlarms.StockAlarmsApplication.services.UserService;
import com.StockAlarms.StockAlarmsApplication.services.mailservice.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Turian Ovidiu.
 * This class represent Scheduled task for updating the prices and evaluate the alarms.
 */

@Component
public class CheckAlarms {

    @Autowired
    private UserService userService;

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private MailService mailService;

    private List<String> symbolsList;

    private List<Alarm> alarmsList;

    private Logger LOGGER = LoggerFactory.getLogger("CheckAlarms");

    /**
     * This is a scheduled method with a fixedRate 20000(20sec).
     * Every 20sec it will run.
     * 1.Prepare the symbols.
     * 2.Update the prices.
     * 3.Evaluate the alarms.
     */
    @Scheduled(fixedRate = 20000)
    public void checkAlarms() {
        prepareSymbols();
        updatePrices();
        evaluateAlarms();
    }


    /**
     * This method prepare the symbols for the update.
     * 1.See if the alarms are active.
     * 2.Make sure we do not have duplicates.
     */
    private void prepareSymbols() {
        List<Alarm> alarms = (List<Alarm>) alarmService.listAll();
        this.alarmsList = alarms;

        List<String> alarmsSymbols = new LinkedList<>();
        this.symbolsList = new LinkedList<>();

        for (Alarm alarm : alarms) {
            if (alarm.getAlarmStatus().equals(AlarmStatus.ACTIVE)) {
                alarmsSymbols.add(alarm.getSymbol());
            }
        }

        for (String symbol : alarmsSymbols) {
            if (!symbolsList.contains(symbol)) {
                symbolsList.add(symbol);
            }
        }
    }


    /**
     * This method make a single API request for all the active alarms.
     */
    private void updatePrices() {
        String[] arrayStocks = this.symbolsList.toArray(new String[0]);
        if (arrayStocks.length > 0) {
            try {
                Map<String, Stock> updateStocks = YahooFinance.get(arrayStocks);
                for (Map.Entry<String, Stock> entry : updateStocks.entrySet()) {
                    for (Alarm alarm : this.alarmsList) {
                        if (alarm.getSymbol().equals(entry.getValue().getQuote().getSymbol())) {
                            alarm.setCurrentPrice(entry.getValue().getQuote().getPrice());
                            alarmService.saveOrUpdate(alarm);
                        }
                    }
                }
            } catch (IOException e) {
                LOGGER.error("Error updating prices");
                e.printStackTrace();
            }
        }
    }


    /**
     * This method will evaluate the alarms and check to see if the alarms are triggered.
     * If an alarm it`s triggered will set the alarm INACTIVE and send an email to the user.
     */
    private void evaluateAlarms() {
        List<Alarm> alarms = (List<Alarm>) alarmService.listAll();

        for (Alarm alarm : alarms) {
            if (alarm.getAlarmStatus().equals(AlarmStatus.ACTIVE)) {
                if (alarm.getPercentChange().compareTo(BigDecimal.ZERO) == 1) {
                    if (alarm.getCurrentPrice().compareTo(alarm.getPriceTarget()) == 1 || alarm.getCurrentPrice().compareTo(alarm.getPriceTarget()) == 0) {
                        alarm.setAlarmStatus(AlarmStatus.INACTIVE);
                        try {
                            mailService.sendAlarm(userService.getById(alarm.getUser().getId()), alarm.getSymbol());
                        } catch (MailException e) {
                            LOGGER.error("Error sending email!");
                            e.printStackTrace();
                        }
                    }
                }

                if (alarm.getPercentChange().compareTo(BigDecimal.ZERO) == -1) {
                    if (alarm.getCurrentPrice().compareTo(alarm.getPriceTarget()) == -1 || alarm.getCurrentPrice().compareTo(alarm.getPriceTarget()) == 0) {
                        alarm.setAlarmStatus(AlarmStatus.INACTIVE);
                        try {
                            mailService.sendAlarm(userService.getById(alarm.getUser().getId()), alarm.getSymbol());
                        } catch (MailException e) {
                            LOGGER.error("Error sending email!");
                            e.printStackTrace();
                        }
                    }
                }
                alarmService.saveOrUpdate(alarm);
            }
        }
    }


}







































