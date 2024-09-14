package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CurrencyService;
import utils.Validator;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/currencies/new")
public class AddCurrencyServlet extends HttpServlet {

    CurrencyService currencyService = CurrencyService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("fullName");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");

        if (!Validator.isNotNull(name,code,sign)){
            resp.sendError(400,"You missed one or more arguments");
            return;
        }

        try {
            if (currencyService.getByCode(code) != null){
                resp.sendError(409,"Currency with such code is already exists");
                return;
            }
            currencyService.create(name,code,sign);
            resp.sendRedirect("/currency/" + code);
        } catch (SQLException e) {
            resp.sendError(500,e.getMessage());
        }
    }

}
