package servlets;

import dao.CurrencyRateDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.JsonMapper;
import service.CurrencyRateService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/exchangeRates")
public class GetAllExchangeRatesServlet extends HttpServlet {

   private final CurrencyRateService currencyRateService = CurrencyRateService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String json = JsonMapper.toJson(currencyRateService.getAll());
            PrintWriter out = resp.getWriter();
            resp.setStatus(200);
            out.print(json);
            out.flush();
        } catch (SQLException e) {
            resp.sendError(500, "Unable to connect to database");
        }


    }

}
