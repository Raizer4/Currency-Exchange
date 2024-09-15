package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.JsonMapper;
import model.Exchange;
import service.ExchangeService;
import utils.Validator;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;


@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {

    private final ExchangeService exchangeService = ExchangeService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        String amount = req.getParameter("amount");

        if (!Validator.isNotNull(from,to,amount)){
            resp.sendError(400,"Missing one or more required parameters");
            return;
        }

        BigDecimal amountDecimal = BigDecimal.valueOf(Integer.parseInt(amount));

        try {
            Exchange exchange = exchangeService.exchangeCurrency(from, to, amountDecimal);

            if (exchange == null){
              resp.sendError(404,"We have no currency rates for your currencies");
                return;
            }

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            String json = JsonMapper.toJson(exchange);
            PrintWriter out = resp.getWriter();
            resp.setStatus(200);
            out.print(json);
            out.flush();
        }catch (SQLException e){
            resp.sendError(500,"Unable to connect to database");
        }



    }


}
