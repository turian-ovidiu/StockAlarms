package com.StockAlarms.StockAlarmsApplication.controllers;

import com.StockAlarms.StockAlarmsApplication.domain.User;
import com.StockAlarms.StockAlarmsApplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Turian Ovidiu.
 * This class represent Index Controller.
 */

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByEmail(username);

        model.addAttribute("username", username);
        return "index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexSlash(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByEmail(username);

        model.addAttribute("username", username);
        return "index";
    }
}
