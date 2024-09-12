package servlets;

import dao.CurrencyDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.JsonMapper;
import model.Currency;
import utils.Validator;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


@WebServlet("/currency/*")
public class GetCurrencyByIdServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String code = req.getPathInfo().substring(1);

        if (!Validator.isCodeValid(code)){
            resp.sendError(400,"You haven't put currency code in path");
            return;
        }

        try {
            Currency currencyCode = CurrencyDAO.findCurrencyByCode(code);

            if (currencyCode == null){
                resp.sendError(404,"Currency not found");
            }else {
                String mapper = JsonMapper.getMapper(currencyCode);
                resp.setStatus(200);
                PrintWriter writer = resp.getWriter();
                writer.print(mapper);
                writer.flush();
            }
        }catch (SQLException e){
            resp.sendError(500,e.getMessage());
        }


    }
    
}
