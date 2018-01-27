package com.StockAlarms.StockAlarmsApplication.services.servicesimpl;

import com.StockAlarms.StockAlarmsApplication.domain.User;
import com.StockAlarms.StockAlarmsApplication.repositories.UserRepository;
import com.StockAlarms.StockAlarmsApplication.services.UserService;
import com.StockAlarms.StockAlarmsApplication.services.security.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Turian Ovidiu.
 * This class represent the implementation of UserService interface.
 */

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EncryptionService encryptionService;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<?> listAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public User saveOrUpdate(User domainObject) {
        if(domainObject.getPassword() != null){
            domainObject.setEncryptedPassword(encryptionService.encryptString(domainObject.getPassword()));

        }
        return userRepository.save(domainObject);
    }

    @Override
    public void delete(Integer id) {
        userRepository.delete(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
