package com.elevator.model;

import com.elevator.model.Elevator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestListener implements Runnable{
   @Override
    public void run() {
        Elevator elevator = Elevator.getInstance();
        elevator.start();
        while (true){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String s = bufferedReader.readLine();
                if (isValid(s)){
                    System.out.println("User Press : " + s);
                    elevator.addFloor(Integer.valueOf(s));

                }else {
                    System.out.println("Введите с 1 по 7 этаж");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    boolean isValid(String s){
        if (!s.matches("[0-9]+")){
            return false;
        }
        if (Integer.valueOf(s)<=0 || Integer.valueOf(s)>7){
            return false;
        }else {
            Pattern p = Pattern.compile("\\d{1,2}");
            Matcher m = p.matcher(s);
            return m.matches();
        }
    }


}
