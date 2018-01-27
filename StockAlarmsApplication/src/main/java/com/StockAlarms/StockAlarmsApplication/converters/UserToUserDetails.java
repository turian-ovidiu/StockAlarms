package com.StockAlarms.StockAlarmsApplication.converters;

import com.StockAlarms.StockAlarmsApplication.domain.User;
import com.StockAlarms.StockAlarmsApplication.services.security.UserDetailsImp;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Created by Turian Ovidiu.
 * This class represent the implementation of Converter interface.
 * This class will convert a User to UserDetails.
 */

@Component
public class UserToUserDetails implements Converter<User,UserDetails> {


    @Override
    public UserDetails convert(User user) {
        UserDetailsImp userDetails = new UserDetailsImp();

        if (user != null){
            userDetails.setUsername(user.getEmail());
            userDetails.setPassword(user.getEncryptedPassword());
        }

        return userDetails;
    }
}
