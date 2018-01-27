package com.StockAlarms.StockAlarmsApplication.repositories;

import com.StockAlarms.StockAlarmsApplication.domain.Alarm;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Turian Ovidiu.
 * This is the interface for Alarm Repository.
 */

public interface AlarmRepository extends CrudRepository<Alarm,Integer> {

    Alarm findBySymbol (String symbol);
    List<Alarm> findAlarmsByUser_Id(int id);
}
