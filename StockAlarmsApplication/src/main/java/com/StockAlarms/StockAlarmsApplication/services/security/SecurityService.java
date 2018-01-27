package com.StockAlarms.StockAlarmsApplication.services.security;

/**
 * Created by Turian Ovidiu.
 * This is the interface for SecurityService implementation.
 */

public interface SecurityService {

    String findLoggedInUsername();

    void autologin(String username, String password);
}
