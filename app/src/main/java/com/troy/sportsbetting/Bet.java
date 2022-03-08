package com.troy.sportsbetting;

public class Bet {
    public String stavka, result;

    public Bet(){

    }
    public Bet(String stavka, String result){

        this.stavka= stavka;
        this.result = result;
    }

    public String getStavka() {
        return stavka;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return this.stavka + "," + result;
    }
}
