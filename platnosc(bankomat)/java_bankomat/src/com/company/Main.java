package com.company;


import java.awt.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.charset.*;
import org.json.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;


public class Main {

    public static void main(String[] args) throws MalformedURLException, ParseException {

        Scanner sc = new Scanner(System.in);
        int sum = 0;
        int sum2 = 0;
        double amount=0;
        List<String> products = new ArrayList<String>();

        String Skan = "";
        do{

            System.out.println("Zeskanuj produkt:\n");
            //System.out.println("Wpisz end aby zakonczyc skanowanie");
            Skan = sc.next();
            switch (Skan){
                case "4009900478984":
                    System.out.println("Guma");
                    System.out.println("Cena: 3zl");
                    amount+=3;
                    products.add("Guma");
                    break;

                case "4027301373042":
                    System.out.println("Myszka");
                    System.out.println("Cena: 30zl");
                    amount+=30;
                    products.add("Myszka");
                    break;

                case "5449000131836":
                    System.out.println("Cola");
                    System.out.println("Cena: 5zl");
                    amount+=5;
                    products.add("Cola");
                    break;

                default:
                    break;
            }
        }while (!Skan.equals("end"));

        //double amountNotRounded = Math.random() * 1000;
        //double amount = Math.round(amountNotRounded*100.0)/100.0;
        System.out.println(products);
        System.out.println("Kwota do zapłacenia: " + amount);

        System.out.println("Wybierz sposób płatności: \n1: Gotówka \n2: Karta \n3: Blik");
        int sposobPlatnosci = Integer.parseInt(sc.next());


        if(sposobPlatnosci == 1){
            System.out.println("Proszę podać banknoty: ");
            String banknotyPodane = sc.next();


            String[] banknotyStringi = banknotyPodane.split(";");

            for(int i=0; i<banknotyStringi.length; i++){
                sum += Integer.parseInt(banknotyStringi[i]);
                System.out.println(Integer.parseInt(banknotyStringi[i]));
            }
            System.out.println("Suma twoich banknotów wynosi: "+sum);




            if(sum == amount){
                System.out.println("Dziękujemy za płatność!");
                System.out.println("Drukowanie paragonu.");
                System.out.println();
            }

            else if(sum > amount){
                double reszta = sum - amount;
                System.out.println("Dziękujemy za płatność!");
                System.out.println("Reszta wynosi: " + reszta);
                System.out.println("Drukowanie paragonu.");
                System.out.println();
                System.out.println("Paragon:");
                for(int a=0; a<products.size(); a++){
                    System.out.println(products.get(a));
                }
            }

            else if(sum < amount){
                System.out.println("Za mało gotówki!");
                System.out.println("wpisz Y żeby dołożyć banknoty");
                System.out.println("wpisz X żeby zakończyć transakcję");
                System.out.println("wpisz Z żeby zmienić sposób płatności");
                String wybor = sc.next();
                System.out.println("Twój wybór to: " + wybor);

                switch (wybor){

                    case "X":
                        break;

                    case "Z":
                        System.out.println("Zmiana sposobu płatności!");
                        System.out.println("Proszę odpalić ponownie program w celu zmiany sposobu płatności.");
                        break;

                    case "Y":
                        System.out.println("Proszę dołożyć banknoty: ");
                        String dolozone = sc.next();

                        String[] dolozoneStringi = dolozone.split(";");

                        for(int i=0; i<dolozoneStringi.length; i++){
                            sum2 += Integer.parseInt(dolozoneStringi[i]);
                            System.out.println(Integer.parseInt(dolozoneStringi[i]));
                        }
                        System.out.println("Suma dołożonych banknotów wynosi: "+sum2);

                        if(sum2+sum == amount){
                            System.out.println("Dziękujemy za płatność!");
                            System.out.println("Drukowanie paragonu.");
                            System.out.println();
                            System.out.println("Paragon:");
                            for(int a=0; a<products.size(); a++){
                                System.out.println(products.get(a));
                            }

                        }

                        else if(sum2+sum > amount){
                            double reszta = sum2+sum - amount;
                            System.out.println("Dziękujemy za płatność!");
                            System.out.println("Reszta wynosi: " + reszta);
                            System.out.println("Drukowanie paragonu.");
                            System.out.println();
                            System.out.println("Paragon:");
                            for(int a=0; a<products.size(); a++){
                                System.out.println(products.get(a));
                            }

                            //dokonczyc
                        }

                        else {
                            System.out.println("Nie masz wystarczająco środków!");
                            System.out.println("Zapraszamy ponownie jak będziesz miał wystarczająco środków.");

                        }

                        break;

                    default:
                        System.out.println("Błąd!");

                }



            }


        }

        else if(sposobPlatnosci == 2){
            System.out.println("Proszę podać podać kod do karty: ");
            double kodKarty = sc.nextDouble();

            if(kodKarty<100000000000d || kodKarty>999999999999d){
                System.out.println("Nieprawidłowy number karty!");
            }
            else {

                System.out.println("Podaj datę ważności swojej karty (MM-YY) ex.10-24:");
                String expirationDate = sc.next();
                String[] words = expirationDate.split("-");
                System.out.println(words[0]);
                System.out.println(words[1]);

                if(Integer.parseInt(words[0])>=1&&Integer.parseInt(words[0])<=12){
                    System.out.println("Podaj kod CCV karty:");
                    int cardCCV = sc.nextInt();

                    if(cardCCV>=100&&cardCCV<=999){
                        System.out.println("Dziękujemy za płatność!");
                        System.out.println("Drukowanie paragonu.");
                        System.out.println();
                        System.out.println("Paragon:");
                        for(int a=0; a<products.size(); a++){
                            System.out.println(products.get(a));
                        }
                    }
                    else {
                        System.out.println("Nieprawidłowy kod CCV karty!");
                    }
                }
                else{
                    System.out.println("Nieprawidłowa data ważności karty!");
                }

            }


        }

        else if(sposobPlatnosci == 3) {
            System.out.println("Proszę podać kod Blik: ");
            int kodBlik = sc.nextInt();

            if(kodBlik < 100000 || kodBlik > 999999) {
                System.out.println("Nieprawidłowy kod blik!");
            }
            else {

                URL url = new URL("http://192.168.1.107:8080/api/blikPay/" + kodBlik + "/" + amount);
                StringBuilder builder = new StringBuilder();

                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(), UTF_8))) {
                    String str;
                    while ((str = bufferedReader.readLine()) != null) {
                        builder.append(str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String jsonString = builder.toString();

                JSONParser parser = new JSONParser();
                //JSONObject json = (JSONObject) parser.parse(jsonString);

                System.out.println(jsonString);

                if(jsonString.equals("true")){
                    System.out.println("Dziękujemy za płatność!");
                    System.out.println("Drukowanie paragonu.");
                    System.out.println();
                    System.out.println("Paragon:");
                    for(int a=0; a<products.size(); a++){
                        System.out.println(products.get(a));
                    }
                }
                else {
                    System.out.println("Nie masz wystarczająco środków na koncie!");
                    System.out.println("Płatność anulowana");
                }



            }


        } else {
            System.out.println("Podana wartość jest poza zakresem [1..3]");
        }


    }
}

