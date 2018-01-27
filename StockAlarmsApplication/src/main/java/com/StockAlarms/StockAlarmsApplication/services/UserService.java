package com.StockAlarms.StockAlarmsApplication.services;

import com.StockAlarms.StockAlarmsApplication.domain.User;

/**
 * Created by Turian Ovidiu.
 * This is the interface for UserService implementation.
 */

public interface UserService extends CRUDService<User>{

    User findByEmail(String email);
}
