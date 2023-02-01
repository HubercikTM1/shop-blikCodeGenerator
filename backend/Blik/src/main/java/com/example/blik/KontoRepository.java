package com.example.blik;


import com.example.blik.models.BlikParametrs;
import com.example.blik.models.Konto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Random;

@Repository
public class KontoRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Konto getAllProducts(){
        return jdbcTemplate.queryForObject("Select * From konto LIMIT 1", BeanPropertyRowMapper.newInstance(Konto.class));}




    public BlikParametrs payBlikCheck(Integer blik){
        return  jdbcTemplate.queryForObject("SELECT blik.kod, konto.waluta, konto.id FROM konto JOIN blik on konto.id = blik.id_konto WHERE kod =?", BeanPropertyRowMapper.newInstance(BlikParametrs.class), blik);
    }


    public void payment(Integer IdKonto, double newMoneyAmount){
        jdbcTemplate.update("UPDATE konto set waluta = ? WHERE id = ?", newMoneyAmount, IdKonto);
    }


    public void removeBlik(int kod){
        jdbcTemplate.update("DELETE FROM `blik` WHERE kod = ?", kod);
    }







    public String getKontoId(String login, String haslo) {
        try {
            MInfo i = jdbcTemplate.queryForObject(
                    "SELECT konto.id FROM konto JOIN users on konto.id_user = users.id WHERE name = ? AND password = ?",
                    BeanPropertyRowMapper.newInstance(MInfo.class),
                    login, haslo);
            String id = i.id;
            return id;
        } catch (EmptyResultDataAccessException e) {
            return "0";
        }
    }


    public String genarateBlik() {
        int a = new Random().nextInt(900000) + 100000;
        return Integer.toString(a);
    }


    public Boolean isBlikUnique(String blik){

        try {
            BlikParametrs BlikOgParameters = jdbcTemplate.queryForObject("SELECT blik.kod, konto.waluta FROM konto JOIN blik on konto.id = blik.id_konto WHERE kod =?",
                    BeanPropertyRowMapper.newInstance(BlikParametrs.class), blik);
            return false;
        }
        catch (EmptyResultDataAccessException e){return true;}
    }


    public void addBlik(String blik, String kontoId){
        jdbcTemplate.update("INSERT INTO `blik`(`kod`, `data_powstania`, `id_konto`) VALUES (? , NOW() ,?)",
        blik, kontoId);
    }
}


