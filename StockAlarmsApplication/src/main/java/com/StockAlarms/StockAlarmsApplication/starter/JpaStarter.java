package com.StockAlarms.StockAlarmsApplication.starter;

import com.StockAlarms.StockAlarmsApplication.domain.Alarm;
import com.StockAlarms.StockAlarmsApplication.domain.User;
import com.StockAlarms.StockAlarmsApplication.domain.enums.AlarmStatus;
import com.StockAlarms.StockAlarmsApplication.services.AlarmService;
import com.StockAlarms.StockAlarmsApplication.services.UserService;
import com.StockAlarms.StockAlarmsApplication.tasks.CheckAlarms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Turian Ovidiu.
 * This class represent a starter class that implements ApplicationListener.
 * When the application starts, this class will load some users and alarms for demonstration purpose.
 */

@Component
public class JpaStarter implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private AlarmService alarmService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadUsers();
        loadAlarms();
    }


    private void loadUsers() {
        User user1 = new User();
        user1.setFirstName("Jack");
        user1.setLastName("Sparrow");
        user1.setEmail("realjack@gmail.com");
        user1.setPassword("user");
        userService.saveOrUpdate(user1);

        User user2 = new User();
        user2.setFirstName("John");
        user2.setLastName("Lastjohn");
        user2.setEmail("user2@gmail.com");
        user2.setPassword("user2");
        userService.saveOrUpdate(user2);
    }


    private void loadAlarms() {
        List<User> users = (List<User>) userService.listAll();
        users.forEach(user -> {
            Alarm alarm1 = new Alarm();
            alarm1.setSymbol("AAPL");
            alarm1.setName("AAPL Inc.");
            alarm1.setCurrency("USD");
            alarm1.setStockExchange("NYSE");
            alarm1.setPrice(new BigDecimal(172.63));
            alarm1.setPercentChange(new BigDecimal(3.65));
            alarm1.calculatePriceTarget();
            alarm1.setAlarmStatus(AlarmStatus.ACTIVE);
            alarm1.setUser(user);
            alarm1.setCurrentPrice(new BigDecimal(172.63));
            alarm1.setCurrentPercentChange(alarm1.getCurrentPercentChange());


            Alarm alarm2 = new Alarm();
            alarm2.setSymbol("NVDA");
            alarm2.setName("NVDA Inc.");
            alarm2.setCurrency("USD");
            alarm2.setStockExchange("NYSE");
            alarm2.setPrice(new BigDecimal(237.61));
            alarm2.setPercentChange(new BigDecimal(8));
            alarm2.calculatePriceTarget();
            alarm2.setAlarmStatus(AlarmStatus.ACTIVE);
            alarm2.setUser(user);
            alarm2.setCurrentPrice(new BigDecimal(237.61));
            alarm2.setCurrentPercentChange(alarm2.getCurrentPercentChange());

            Alarm alarm3 = new Alarm();
            alarm3.setSymbol("AMD");
            alarm3.setName("AMD Inc.");
            alarm3.setCurrency("USD");
            alarm3.setStockExchange("NYSE");
            alarm3.setPrice(new BigDecimal(12.62));
            alarm3.setPercentChange(new BigDecimal(-12.78));
            alarm3.calculatePriceTarget();
            alarm3.setAlarmStatus(AlarmStatus.INACTIVE);
            alarm3.setUser(user);
            alarm3.setCurrentPrice(new BigDecimal(12.62));
            alarm3.setCurrentPercentChange(alarm3.getCurrentPercentChange());


            alarmService.saveOrUpdate(alarm1);
            alarmService.saveOrUpdate(alarm2);
            alarmService.saveOrUpdate(alarm3);
        });
    }
}
