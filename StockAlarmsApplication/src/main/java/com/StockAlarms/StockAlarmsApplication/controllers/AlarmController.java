package com.StockAlarms.StockAlarmsApplication.controllers;


import com.StockAlarms.StockAlarmsApplication.domain.Alarm;
import com.StockAlarms.StockAlarmsApplication.domain.User;
import com.StockAlarms.StockAlarmsApplication.domain.enums.AlarmStatus;
import com.StockAlarms.StockAlarmsApplication.services.AlarmService;
import com.StockAlarms.StockAlarmsApplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by Turian Ovidiu.
 * This class represent Alarm Controller.
 */

@Controller
public class AlarmController {

    @Autowired
    private UserService userService;

    @Autowired
    private AlarmService alarmService;


    @RequestMapping(value = "/alarm/list", method = RequestMethod.GET)
    public String listAlarms(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByEmail(username);

        model.addAttribute("username", username);
        model.addAttribute("alarms", alarmService.findAlarmsByUser_Id(user.getId()));
        return "alarm/list";
    }


    @RequestMapping(value = "/alarm/show/{id}", method = RequestMethod.GET)
    public String getAlarm(@PathVariable Integer id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        // User user = userService.findByEmail(username);

        model.addAttribute("username", username);
        model.addAttribute("alarm", alarmService.getById(id));
        return "alarm/show";
    }

    @RequestMapping(value = "/alarm/edit/{id}", method = RequestMethod.GET)
    public String editAlarm(@PathVariable Integer id, Model model) {
        model.addAttribute("alarm", alarmService.getById(id));
        return "alarm/alarmeditform";
    }

    @RequestMapping(value = "/alarm/new/{symbol}", method = RequestMethod.GET)
    public String newAlarm(@PathVariable String symbol, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByEmail(username);
        List<Alarm> alarmList = user.getAlarmList();

        for (Alarm alrm: alarmList) {
            if (alrm.getSymbol().equals(symbol)){
                return "alarm/errorAlarm";
            }
        }
        Alarm alarm = new Alarm();
        try {
            Stock stock = YahooFinance.get(symbol);
            alarm.setSymbol(stock.getQuote().getSymbol());
            alarm.setName(stock.getName());
            alarm.setCurrency(stock.getCurrency());
            alarm.setStockExchange(stock.getStockExchange());
            alarm.setPrice(stock.getQuote().getPrice());
            alarm.setCurrentPrice(stock.getQuote().getPrice());
            alarm.setUser(user);
            alarm.setAlarmStatus(AlarmStatus.ACTIVE);

            model.addAttribute("alarm", alarm);
            model.addAttribute("stock", stock);
        } catch (IOException e) {
            e.printStackTrace();
            return "alarm/errorStock";
        }

        return "alarm/alarmform";
    }

    @RequestMapping(value = "/alarm", method = RequestMethod.POST)
    public String saveOrUpdateAlarm(@Valid Alarm alarm, BindingResult bindingResult) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByEmail(username);
        alarm.setUser(user);
        alarm.calculatePriceTarget();

        if (bindingResult.hasErrors()){
            return "alarm/alarmeditform";
        }

        alarmService.saveOrUpdate(alarm);
        return "redirect:/alarm/list";
    }


    @RequestMapping(value = "/alarm/delete/{id}", method = RequestMethod.GET)
    public String deleteAlarm(@PathVariable Integer id, HttpServletRequest httpServletRequest) {
        alarmService.delete(id);
        String refered = httpServletRequest.getHeader("Referer");
        return "redirect:" + refered;
    }


    @RequestMapping(value = "/alarm/listID/{id}", method = RequestMethod.GET)
    public String listUserAlarms(@PathVariable Integer id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByEmail(username);

        model.addAttribute("username", username);
        model.addAttribute("alarms", alarmService.findAlarmsByUser_Id(id));
        return "alarm/list";
    }

}
