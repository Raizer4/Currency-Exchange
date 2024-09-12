package dao;

import utils.ConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO {
    public static List<Currency> findAll() throws SQLException {
        List<Currency> result = new ArrayList<>();

        try (Connection con = ConnectionManager.open()) {
            var statement =
                    con.prepareStatement("""
                            SELECT * 
                            FROM currencies
                            """);

            var rs = statement.executeQuery();

            while (rs.next()) {
                int id = (int) rs.getLong("id");
                String code = rs.getString("code");
                String full_name = rs.getString("fullname");
                String sign = rs.getString("sign");
                Currency currency = new Currency(id, code, full_name, sign);
                result.add(currency);
            }

        } catch (SQLException ex) {
            throw new RuntimeException();
        }

        return result;
    }

    public static Currency findCurrencyByCode(String code) throws SQLException {

        try (var con = ConnectionManager.open()) {
            PreparedStatement stmt = con.prepareStatement("""
                    SELECT *
                    FROM currencies
                    WHERE code = ?
                    """);
            stmt.setString(1, code);

            var rs = stmt.executeQuery();

            while (rs.next()) {
                return new Currency((int) rs.getLong("id"), rs.getString("code"), rs.getString("fullname"), rs.getString("sign"));
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void addCurrency(String name, String code, String sign) throws SQLException {

        try (var con = ConnectionManager.open()) {
            PreparedStatement statement = con.prepareStatement("INSERT INTO currencies(code, fullname, sign) VALUES (?,?,?)");

            statement.setString(1, code);
            statement.setString(2, name);
            statement.setString(3, sign);

            statement.executeUpdate();
        }

    }



}