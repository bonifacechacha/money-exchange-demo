package com.niafikra.inaya.money.exchange;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@WebServlet("/money-exchange/*")
public class MoneyExchangeServlet extends HttpServlet {

    private Gson gson;
    private MockExchangeService exchangeService;


    public static final String DEFAULT_DATE_FORMAT = "dd-MM-yyyy";
    public static final String DEFAULT_BASE_CURRENCY = "USD";

    public MoneyExchangeServlet() {
        super();

        gson = new Gson();
        exchangeService = new MockExchangeService();
    }

    private void sendAsJson(
            HttpServletResponse response,
            Object obj) throws IOException {

        response.setContentType("application/json");

        String res = gson.toJson(obj);

        PrintWriter out = response.getWriter();

        out.print(res);
        out.flush();
    }

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String baseCurrency = DEFAULT_BASE_CURRENCY;
        String dateFormat = DEFAULT_DATE_FORMAT;

        String pathInfo = request.getPathInfo();

        if (StringUtils.isNotEmpty(pathInfo)) {
            String[] splits = pathInfo.split("/");

            if (splits.length == 2) {

                String path = splits[1];
                if (path.equalsIgnoreCase("currency")) {
                    showCurrencies(response);
                    return;
                }

            } else if (splits.length == 3) {
                String path = splits[1];

                if (path.equalsIgnoreCase("rate")) {

                    String dateFormatParam = request.getParameter("DATE_FORMAT");
                    String baseCurrencyParam = request.getParameter("BASE_CURRENCY");

                    if (StringUtils.isNotEmpty(dateFormatParam))
                        dateFormat = dateFormatParam;
                    if (StringUtils.isNotEmpty(baseCurrencyParam))
                        baseCurrency = baseCurrencyParam;

                    String dateString = splits[2];
                    LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(dateFormat));

                    showRates(response, date, baseCurrency);
                    return;

                }

            }

        }

        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return;
    }

    private void showCurrencies(HttpServletResponse response) throws IOException {
        sendAsJson(response, exchangeService.findCurrencies());
    }

    private void showRates(HttpServletResponse response, LocalDate date, String baseCurrency) throws IOException {
        sendAsJson(response, exchangeService.calculateRates(date, baseCurrency));
    }
}