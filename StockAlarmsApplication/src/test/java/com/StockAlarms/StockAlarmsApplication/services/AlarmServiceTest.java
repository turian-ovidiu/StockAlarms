package com.StockAlarms.StockAlarmsApplication.services;

import com.StockAlarms.StockAlarmsApplication.domain.Alarm;
import com.StockAlarms.StockAlarmsApplication.repositories.AlarmRepository;
import com.StockAlarms.StockAlarmsApplication.services.servicesimpl.AlarmServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by Turian Ovidiu.
 * This class represent the Alarm service unit tests.
 */

public class AlarmServiceTest {

    private AlarmServiceImpl alarmServiceImpl;

    @Mock
    private AlarmRepository alarmRepository;

    @Mock
    private Alarm alarm;

    private List<Alarm> alarms;

    @Before
    public void setupMock(){
        MockitoAnnotations.initMocks(this);
        alarmServiceImpl = new AlarmServiceImpl();
        alarmServiceImpl.setAlarmRepository(alarmRepository);
        alarms = new ArrayList<>();
    }

    @Test
    public void shouldReturnAlarm_whenGetByIdIsCalled() throws Exception{
        when(alarmRepository.findOne(1)).thenReturn(alarm);
        Alarm retrievedAlarm = alarmServiceImpl.getById(1);
        assertThat(retrievedAlarm,is(equalTo(alarm)));
    }

    @Test
    public void shouldReturnAlarm_whenSaveOrUpdateIsCalled() throws Exception{
        when(alarmRepository.save(alarm)).thenReturn(alarm);
        Alarm savedAlarm = alarmServiceImpl.saveOrUpdate(alarm);
        assertThat(savedAlarm,is(equalTo(alarm)));
    }

    @Test
    public void shouldCallDeleteMethodOfAlarmRepository_whenDeleteIsCalled() throws Exception{
        doNothing().when(alarmRepository).delete(1);
        AlarmRepository alarmrepo = Mockito.mock(AlarmRepository.class);
        alarmServiceImpl.delete(1);
        verify(alarmRepository,times(1)).delete(1);
    }

    @Test
    public void shouldReturnListAlarms_whenListAllIsCalled(){
        when(alarmRepository.findAll()).thenReturn(alarms);
        List<Alarm> alarmList = (List<Alarm>) alarmServiceImpl.listAll();
        assertThat(alarmList,is(equalTo(alarms)));
    }


}



























