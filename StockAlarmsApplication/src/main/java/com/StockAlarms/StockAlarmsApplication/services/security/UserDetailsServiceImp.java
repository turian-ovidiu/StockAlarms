package com.StockAlarms.StockAlarmsApplication.services.security;

import com.StockAlarms.StockAlarmsApplication.domain.User;
import com.StockAlarms.StockAlarmsApplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Turian Ovidiu.
 * This class represent the implementation of UserDetailsService interface.
 */

@Service("userDetailsService")
public class UserDetailsServiceImp implements UserDetailsService{

    private UserService userService;
    private Converter<User,UserDetails> userUserDetailsConverter;


    public UserDetailsServiceImp(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    @Qualifier(value = "userToUserDetails")
    public void setUserUserDetailsConverter(Converter<User, UserDetails> userUserDetailsConverter) {
        this.userUserDetailsConverter = userUserDetailsConverter;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userUserDetailsConverter.convert(userService.findByEmail(username));
    }
}
