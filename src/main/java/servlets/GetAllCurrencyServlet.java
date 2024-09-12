package servlets;

import dao.CurrencyDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.JsonMapper;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/currencies")
public class GetAllCurrencyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String mapper = JsonMapper.getMapper(CurrencyDAO.findAll());
            resp.setStatus(200);
            var writer = resp.getWriter();
            writer.print(mapper);
            writer.flush();
        }catch (SQLException e){
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка базы данных");
        }
    }

}
