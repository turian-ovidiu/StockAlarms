package com.StockAlarms.StockAlarmsApplication.services.servicesimpl;

import com.StockAlarms.StockAlarmsApplication.domain.Alarm;
import com.StockAlarms.StockAlarmsApplication.repositories.AlarmRepository;
import com.StockAlarms.StockAlarmsApplication.services.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Turian Ovidiu.
 * This class represent the implementation of AlarmService interface.
 */

@Service
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    private AlarmRepository alarmRepository;

    public void setAlarmRepository(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    @Override
    public Alarm findBySymbol(String symbol) {
        return alarmRepository.findBySymbol(symbol);
    }

    @Override
    public List<Alarm> findAlarmsByUser_Id(int id) {
        List<Alarm> alarms = new ArrayList<>();
        alarmRepository.findAlarmsByUser_Id(id).forEach(alarms::add);
        return alarms;
    }

    @Override
    public List<?> listAll() {
        List<Alarm> alarms = new ArrayList<>();
        alarmRepository.findAll().forEach(alarms::add);
        return alarms;
    }

    @Override
    public Alarm getById(Integer id) {
        return alarmRepository.findOne(id);
    }

    @Override
    public Alarm saveOrUpdate(Alarm domainObject) {
        return alarmRepository.save(domainObject);
    }

    @Override
    public void delete(Integer id) {
        alarmRepository.delete(id);
    }


}
