package com.StockAlarms.StockAlarmsApplication.repositories;

import com.StockAlarms.StockAlarmsApplication.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Turian Ovidiu.
 * This is the interface for User Repository.
 */

public interface UserRepository extends CrudRepository<User,Integer> {

    User findByEmail(String email);

}
