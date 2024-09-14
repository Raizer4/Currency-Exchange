package servlets;

import dao.CurrencyDAO;
import dao.CurrencyRateDAO;
import dao.CurrencyRateDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CurrencyRate;
import service.CurrencyRateService;
import utils.Validator;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;

@WebServlet("/exchangeRate/new")
public class AddExchangeRatesServlet extends HttpServlet {

    private final CurrencyRateService currencyRateService = CurrencyRateService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String baseCode = req.getParameter("basecurrencycode");
        String targetCode = req.getParameter("targetcurrencycode");
        String rate = req.getParameter("rate");

        if (!Validator.isNotNull(baseCode,targetCode,rate)){
            resp.sendError(400,"One of parameters is missed");
            return;
        }

        try {
            if (currencyRateService.getByCodePair(baseCode + targetCode) != null){
                resp.sendError(409,"CurrencyRate with such codes is already exists");
                return;
            }

            currencyRateService.create(baseCode,targetCode,rate);
            resp.sendRedirect("/exchangeRate/" + baseCode + targetCode);
        } catch (SQLException e) {
            resp.sendError(500,"Unable to connect to database");
        }

    }


}

