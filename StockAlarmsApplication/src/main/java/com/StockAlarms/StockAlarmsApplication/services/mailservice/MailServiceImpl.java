package com.StockAlarms.StockAlarmsApplication.services.mailservice;

import com.StockAlarms.StockAlarmsApplication.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public void sendAlarm(User user,String symbol) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(user.getEmail());
        mail.setFrom("trovvi@gmail.com");
        mail.setSubject("Alert for a stock !");
        mail.setText("Your alert for the stock "+symbol+" was triggered .");

        javaMailSender.send(mail);

    }
}
