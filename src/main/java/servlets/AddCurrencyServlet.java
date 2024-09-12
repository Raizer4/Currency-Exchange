package servlets;

import dao.CurrencyDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.Validator;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/currencies/new")
public class AddCurrencyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");

        if (!Validator.isNotNull(name,code,sign)){
            resp.sendError(400,"You missed one or more arguments");
            return;
        }

        try {
            if (CurrencyDAO.findCurrencyByCode(code) != null){
                resp.sendError(409,"Currency with such code is already exists");
                return;
            }
            CurrencyDAO.addCurrency(name,code,sign);
            resp.sendRedirect("/currency/" + code);
        } catch (SQLException e) {
            resp.sendError(500,e.getMessage());
        }
    }

}
