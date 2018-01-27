package com.StockAlarms.StockAlarmsApplication.services;

import com.StockAlarms.StockAlarmsApplication.domain.User;
import com.StockAlarms.StockAlarmsApplication.repositories.UserRepository;
import com.StockAlarms.StockAlarmsApplication.services.servicesimpl.UserServiceImpl;
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
 * This class represent the User service unit tests.
 */

public class UserServiceTest {

    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private User user;

    @Mock
    private List<User> users;

    @Before
    public void setupMock(){
        MockitoAnnotations.initMocks(this);
        userServiceImpl = new UserServiceImpl();
        userServiceImpl.setUserRepository(userRepository);
        users = new ArrayList<>();
    }


    @Test
    public void shouldReturnUser_whenGetByIdIsCalled(){
        when(userRepository.findOne(3)).thenReturn(user);
        User retrievedUser = userServiceImpl.getById(3);
        assertThat(retrievedUser,is(equalTo(user)));
    }


    @Test
    public void shouldReturnUser_whenSaveOrUpdateIsCalled() throws Exception{
        when(userRepository.save(user)).thenReturn(user);
        User savedUser = userServiceImpl.saveOrUpdate(user);
        assertThat(savedUser,is(equalTo(user)));
    }

    @Test
    public void shouldCallDeleteMethodOfUserRepository_whenDeleteIsCalled() throws  Exception{
        doNothing().when(userRepository).delete(3);
        UserRepository urepo = Mockito.mock(UserRepository.class);
        userServiceImpl.delete(3);
        verify(userRepository,times(1)).delete(3);
    }

    @Test
    public void shouldReturnUser_whenFindByNameIsCalled(){
        when(userRepository.findByEmail("jack@gmail.com")).thenReturn(user);
        User savedUser = userServiceImpl.findByEmail("jack@gmail.com");
        assertThat(savedUser,is(equalTo(user)));
    }

    @Test
    public void shouldReturnListUsers_whenListAllIsCalled(){
        when(userRepository.findAll()).thenReturn(users);
        List<User> userList = (List<User>) userServiceImpl.listAll();
        assertThat(userList,is(equalTo(users)));

    }

}



























