package com.StockAlarms.StockAlarmsApplication.controllers;


import com.StockAlarms.StockAlarmsApplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

/**
 * Created by Turian Ovidiu.
 * This class represent Stock Controller.
 */

@Controller
public class StockController {


    @Autowired
    UserService userService;

    @RequestMapping(value ="stock/search", method = RequestMethod.POST)
    public String searchProduct(@RequestParam("stockSymbol") String stockSymbol){

        return "redirect:/stock/searchResult/"+stockSymbol;
    }



    @RequestMapping(value = "/stock/searchResult/{stockSymbol}",method = RequestMethod.GET)
    public String listProductsSearch(@PathVariable String stockSymbol, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("username",username);


        Stock stock = null;
        try {
            stock = YahooFinance.get(stockSymbol);
        } catch (IOException e) {
            e.printStackTrace();
            return "stock/searchError";
        }

        model.addAttribute("stock",stock);
        return "stock/searchResult";
    }



}
