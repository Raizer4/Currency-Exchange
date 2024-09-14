package dao;

import mappers.ResultSetMapper;
import utils.ConnectionManager;

import java.sql.*;

import model.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO implements BaseDAO<Currency>{

    public static final CurrencyDAO INSTANCE = new CurrencyDAO();

    @Override
    public List<Currency> findAll() throws SQLException {
        try (Connection con = ConnectionManager.open()) {
            List<Currency> currencies = new ArrayList<>();
            var statement = con.prepareStatement("SELECT * FROM currencies");
            var rs = statement.executeQuery();

            while (rs.next()) {
                currencies.add(ResultSetMapper.mapToCurrency(rs));
            }

            return currencies;
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }

    }

    @Override
    public Currency findByCode(String code) throws SQLException {
        try (var con = ConnectionManager.open()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM currencies where code = ?");
            stmt.setString(1, code);
            var rs = stmt.executeQuery();

            if (rs.next()) {
                return ResultSetMapper.mapToCurrency(rs);
            }else {
                return null;
            }

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void create(Currency currency) throws SQLException {
        try (var con = ConnectionManager.open()) {
            PreparedStatement statement = con.prepareStatement("INSERT INTO currencies(code, fullname, sign) VALUES (?,?,?)");
            statement.setString(1, currency.code());
            statement.setString(2, currency.fullName());
            statement.setString(3, currency.sign());
            statement.executeUpdate();
        }catch (SQLException e){
            throw new SQLException(e);
        }
    }

    public static Currency findById(int id) throws SQLException{

        try(var con = ConnectionManager.open()){
           var stmt = con.prepareStatement("select * from currencies where id = ?");
           stmt.setInt(1,id);
           var rs = stmt.executeQuery();

           if(rs.next()){
               return ResultSetMapper.mapToCurrency(rs);
           }else {
               return null;
           }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

    }





}