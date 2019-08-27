package com.niafikra.inaya.money.exchange;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class ExchangeService {
    public Object findCurrencies() {

        //TODO CORRECT THIS METHOD
        HashMap<String,String> currencies = new HashMap<>();

        currencies.put("USD","United States Dollar");
        currencies.put("TZS","Tanzania Shillings");

        return currencies;
    }

    public Object calculateRates(LocalDate date, String baseCurrency) {

        //TODO CORRECT THIS METHOD
        HashMap<String,Double> rates = new HashMap<>();

        rates.put("USD",1.00);
        rates.put("TZS",2230.00);

        HashMap result = new HashMap();
        result.put("date",date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        result.put("timestamp", System.currentTimeMillis());
        result.put("base",baseCurrency);
        result.put("rates",rates);

        return result;
    }
}
