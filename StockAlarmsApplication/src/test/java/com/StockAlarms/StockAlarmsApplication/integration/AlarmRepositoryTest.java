package com.StockAlarms.StockAlarmsApplication.integration;


import com.StockAlarms.StockAlarmsApplication.configuration.RepositoryConfig;
import com.StockAlarms.StockAlarmsApplication.domain.Alarm;
import com.StockAlarms.StockAlarmsApplication.domain.enums.AlarmStatus;
import com.StockAlarms.StockAlarmsApplication.repositories.AlarmRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Turian Ovidiu.
 * This class represent the alarm repository test.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryConfig.class})
public class AlarmRepositoryTest {

    private AlarmRepository alarmRepository;

    @Autowired
    public void setAlarmRepository(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }


    @Test
    public void alarmTest(){

        Alarm alarm = new Alarm();
        alarm.setSymbol("AAPL");
        alarm.setName("AAPL Inc.");
        alarm.setCurrency("US");
        alarm.setStockExchange("NYSC");
        alarm.setPrice(new BigDecimal(172.63).setScale(2,BigDecimal.ROUND_HALF_EVEN));
        alarm.setPercentChange(new BigDecimal(3));
        alarm.calculatePriceTarget();
        alarm.setAlarmStatus(AlarmStatus.ACTIVE);

        assertNull(alarm.getId());
        alarmRepository.save(alarm);
        assertNotNull(alarm.getId());

        Alarm fetchedAlarm = alarmRepository.findOne(alarm.getId());
        assertNotNull(fetchedAlarm);

        assertEquals(alarm.getId(),fetchedAlarm.getId());
        assertEquals(alarm.getPrice(),fetchedAlarm.getPrice());

        //Update
        fetchedAlarm.setPrice(new BigDecimal(181.23).setScale(2,BigDecimal.ROUND_HALF_EVEN));
        alarmRepository.save(fetchedAlarm);

        Alarm updateAlarm = alarmRepository.findOne(fetchedAlarm.getId());
        assertEquals(fetchedAlarm.getPrice(),updateAlarm.getPrice());


        //Count
        long alarmCount = alarmRepository.count();
        assertEquals(alarmCount,1);

        Iterable<Alarm> alarms = alarmRepository.findAll();

        int count = 0;

        for (Alarm p: alarms){
            count++;
        }

        assertEquals(count,1);

        alarmRepository.delete(updateAlarm.getId());
        assertEquals(0,alarmRepository.count());
    }

}































