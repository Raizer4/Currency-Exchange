package mappers;

import dao.CurrencyDAO;
import model.Currency;
import model.CurrencyRate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetMapper {
    public static Currency mapToCurrency(ResultSet rs) throws SQLException {
        return new Currency(
                rs.getInt("id"),
                rs.getString("code"),
                rs.getString("fullname"),
                rs.getString("sign")
        );
    }

    public static CurrencyRate mapToCurrencyRate(ResultSet rs) throws SQLException{
        CurrencyDAO currencyDAO = CurrencyDAO.INSTANCE;

        return new CurrencyRate(
                rs.getInt("id"),
                currencyDAO.findById(rs.getInt("basecurrencyid")),
                currencyDAO.findById(rs.getInt("targetcurrencyid")),
                rs.getBigDecimal("rate")
        );
    }

}
