package com.StockAlarms.StockAlarmsApplication.integration;


import com.StockAlarms.StockAlarmsApplication.configuration.RepositoryConfig;
import com.StockAlarms.StockAlarmsApplication.domain.User;
import com.StockAlarms.StockAlarmsApplication.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Turian Ovidiu.
 * This class represent the user repository test.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryConfig.class})
public class UserRepositoryTest {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    public void userTest(){

        User user = new User();
        user.setFirstName("Jack");
        user.setLastName("Sparrow");
        user.setEmail("jack@gmail.com");


        assertNull(user.getId());
        userRepository.save(user);
        assertNotNull(user.getId());

        User fetchedUser = userRepository.findOne(user.getId());
        assertNotNull(fetchedUser);

        assertEquals(user.getId(),fetchedUser.getId());
        assertEquals(user.getEmail(),fetchedUser.getEmail());

        fetchedUser.setEmail("jackSp@gmail.com");
        userRepository.save(fetchedUser);

        User updateUser = userRepository.findOne(fetchedUser.getId());
        assertEquals(fetchedUser.getEmail(),updateUser.getEmail());

        long userCount = userRepository.count();
        assertEquals(userCount,1);
        Iterable<User> users = userRepository.findAll();

        int count = 0;

        for (User u: users){
            count++;
        }

        assertEquals(count,1);

        userRepository.delete(updateUser.getId());
        assertEquals(0,userRepository.count());
    }


}
