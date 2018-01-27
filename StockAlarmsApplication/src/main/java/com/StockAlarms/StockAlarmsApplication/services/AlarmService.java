package com.StockAlarms.StockAlarmsApplication.services;

import com.StockAlarms.StockAlarmsApplication.domain.Alarm;

import java.util.List;

/**
 * Created by Turian Ovidiu.
 * This is the interface for AlarmService implementation.
 */

public interface AlarmService extends CRUDService<Alarm> {

    Alarm findBySymbol (String symbol);

    List<Alarm> findAlarmsByUser_Id(int id);
}
