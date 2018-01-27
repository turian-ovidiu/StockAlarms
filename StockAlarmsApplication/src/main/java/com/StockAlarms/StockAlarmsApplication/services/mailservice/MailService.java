package com.StockAlarms.StockAlarmsApplication.services.mailservice;

import com.StockAlarms.StockAlarmsApplication.domain.User;

public interface MailService {

    void sendAlarm(User user,String symbol);
}
