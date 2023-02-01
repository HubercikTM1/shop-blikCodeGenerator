package com.example.blik;

import com.example.blik.models.BlikParametrs;
import com.example.blik.models.Konto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@RestController
public class BankController {
    KontoRepository kontoRepository;
    public BankController(KontoRepository kontoRepository) {
        this.kontoRepository = kontoRepository;
    }


    @GetMapping("/api/konto")
    public Konto getKonto(){return kontoRepository.getAllProducts();}




    @GetMapping("/api/blikPay/{blik}/{amount}")
    public String PayBlik(@PathVariable("blik") Integer blik, @PathVariable ("amount") double amount){
        String check = "false";
        try{
            BlikParametrs dbResult = kontoRepository.payBlikCheck(blik);//null
            if (dbResult.getWaluta() >= amount){
                double newMoneyAmount =  dbResult.getWaluta() - amount;
                kontoRepository.payment(dbResult.getId(), newMoneyAmount);
                kontoRepository.removeBlik(blik);
                check = "true";
            }
            return check;

        } catch (EmptyResultDataAccessException e){return ("false");}
    }




    @GetMapping( "api/generateBlik/{login}/{pass}")
    public String generateBlik(@PathVariable("login") String login, @PathVariable("pass") String pass) throws InterruptedException {
        String kontoId = kontoRepository.getKontoId(login, pass);
        if (kontoId.equals("0")) {
            return "ERROR";
        }
        else {
            String blik;
            do {
                blik = kontoRepository.genarateBlik();
            } while (!kontoRepository.isBlikUnique(blik));
            kontoRepository.addBlik(blik, kontoId);
            int blikI = Integer.parseInt(blik);
            Timer t = new Timer();

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    kontoRepository.removeBlik(blikI);
                }
            };

            t.schedule(task, 120000);




            return blik;

        }
    }

}
