package com.troy.sportsbetting;

public class Bet {
    public String stavka, result, koeff, win, league;


    public Bet(){

    }
    public Bet(String stavka, String result, String koeff, String league){

        this.stavka= stavka;
        this.result = result;
        this.koeff = koeff;
        this.league = league;
    }
    public Bet(String stavka, String result, String koeff, String league, String win){

        this.stavka= stavka;
        this.result = result;
        this.koeff = koeff;
        this.win = win;
        this.league = league;
    }

    public String getStavka() {
        return stavka;
    }

    public String getResult() {
        return result;
    }

    public String getKoeff() {
        return koeff;
    }

    public String getWin() {
        return win;
    }

    public String getLeague() {
        return league;
    }
}
