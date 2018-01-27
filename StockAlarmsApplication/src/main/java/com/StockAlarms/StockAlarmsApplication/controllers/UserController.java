package com.StockAlarms.StockAlarmsApplication.controllers;

import com.StockAlarms.StockAlarmsApplication.domain.User;
import com.StockAlarms.StockAlarmsApplication.services.UserService;
import com.StockAlarms.StockAlarmsApplication.services.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Turian Ovidiu.
 * This class represent User Controller.
 */

@Controller
public class UserController {


    private UserService userService;

    @Autowired
    private SecurityService securityService;


    private Logger LOGGER = LoggerFactory.getLogger("UserController");

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public String listUsers(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        model.addAttribute("username", username);
        model.addAttribute("users", userService.listAll());
        return "user/list";
    }

    @RequestMapping(value = "/user/show/{id}", method = RequestMethod.GET)
    public String getUser(@PathVariable Integer id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("username", username);

        model.addAttribute("user", userService.getById(id));
        return "user/show";
    }

    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getById(id);

        model.addAttribute("username", username);
        model.addAttribute("user", user);
        return "user/userform";
    }

    @RequestMapping(value = "/user/new", method = RequestMethod.GET)
    public String newUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user/userform";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String saveOrUpdate(@Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "user/userform";
        }

        userService.saveOrUpdate(user);
        return "redirect:/index";
    }

    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Integer id, HttpServletRequest httpServletRequest) {
        LOGGER.info("DELETE ID " + id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByEmail(username);

        userService.delete(id);
        String refered = httpServletRequest.getHeader("Referer");
        return "redirect:" + refered;
    }



    @RequestMapping(value = "/user/show-account", method = RequestMethod.GET)
    public String getUserAccount(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByEmail(username);

        model.addAttribute("user", user);
        return "user/show";
    }
}
