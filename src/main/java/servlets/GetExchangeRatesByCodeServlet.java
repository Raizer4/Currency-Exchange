package servlets;

import dao.CurrencyRateDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.JsonMapper;
import model.CurrencyRate;
import service.CurrencyRateService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/exchangeRate/*")
public class GetExchangeRatesByCodeServlet extends HttpServlet {
   private final CurrencyRateService currencyRateService = CurrencyRateService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String code = req.getPathInfo().substring(1);

        if (code.length() != 6){
            resp.sendError(400,"You entered incorrect currency pair");
            return;
        }

        try {
            CurrencyRate cr = currencyRateService.getByCodePair(code);
            if (cr == null){
                resp.sendError(404,"Currency rate with such codes doesn't exists");
                return;
            }
            String json = JsonMapper.toJson(cr);
            PrintWriter writer = resp.getWriter();
            resp.setStatus(200);
            writer.print(json);
            writer.flush();
        } catch (SQLException e) {
           resp.sendError(500,"Unable to connect to database");
        }

    }

}
