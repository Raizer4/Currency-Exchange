package dao;

import mappers.ResultSetMapper;
import model.Currency;
import model.CurrencyRate;
import utils.ConnectionManager;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyRateDAO implements BaseDAO<CurrencyRate>{

    public static final CurrencyRateDAO INSTANCE = new CurrencyRateDAO();
    private final CurrencyDAO currencyDAO = CurrencyDAO.INSTANCE;

    @Override
    public List<CurrencyRate> findAll() throws SQLException {
        try (var con = ConnectionManager.open()) {
            List<CurrencyRate> currencyRates = new ArrayList<>();
            var stmt = con.prepareStatement("SELECT * FROM exchangerates");
            var rs = stmt.executeQuery();

            while (rs.next()) {
                currencyRates.add(ResultSetMapper.mapToCurrencyRate(rs));
            }

            return currencyRates;
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    @Override
    public CurrencyRate findByCode(String code) throws SQLException {
        Currency baseCurr = currencyDAO.findByCode(code.substring(0, 3));
        Currency target = currencyDAO.findByCode(code.substring(3));

        if (baseCurr == null || target == null){
            return null;
        }

        try(var con = ConnectionManager.open()) {
            var stmt = con.prepareStatement("SELECT * " +
                    "FROM exchangerates " +
                    "WHERE basecurrencyid = ? " +
                    "AND targetcurrencyid = ?");
            stmt.setInt(1,baseCurr.id());
            stmt.setInt(2,target.id());
            var rs = stmt.executeQuery();

            if (rs.next()){
                return ResultSetMapper.mapToCurrencyRate(rs);
            }else {
                return null;
            }

        }
    }

    @Override
    public void create(CurrencyRate currencyRate) throws SQLException {
        try (var con = ConnectionManager.open()) {
            var stmt = con.prepareStatement("INSERT INTO exchangerates(basecurrencyid, targetcurrencyid, rate) VALUES (?,?,?)");
            stmt.setInt(1,currencyRate.baseCurrency().id());
            stmt.setInt(2,currencyRate.targetCurrency().id());
            stmt.setBigDecimal(3, currencyRate.rate());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
