package com.niafikra.inaya.money.exchange;

import java.time.LocalDate;
import java.util.HashMap;

public class MockExchangeService {
    public Object findCurrencies() {
        HashMap<String,String> currencies = new HashMap<>();

        currencies.put("USD","United States Dollar");
        currencies.put("TZS","Tanzania Shillings");

        return currencies;
    }

    public Object calculateRates(LocalDate date, String baseCurrency) {
        HashMap<String,Double> rates = new HashMap<>();

        rates.put("USD",1.00);
        rates.put("TZS",2230.00);

        return rates;
    }
}
