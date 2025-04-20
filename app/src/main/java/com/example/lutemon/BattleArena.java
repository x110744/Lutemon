package com.example.lutemon;

import java.util.Random;

public class BattleArena {
    public BattleArena() {}
    public Lutemon battle(Lutemon mon1, Lutemon mon2) {
        Random rng = new Random();
        Lutemon winner;
        int hp1=mon1.getHp(), hp2=mon2.getHp();
        while(true) {
            // mons can randomly take crit damage
            // otherwise the loop might never break
            mon2.takeDmg(mon1.dealDmg() + rng.nextInt(11));
            if(mon2.getHp()==0) {
                mon1.setHp(hp1); // heal winner back to full hp
                winner = mon1;
                break;
            }
            mon1.takeDmg(mon2.dealDmg() + rng.nextInt(11));
            if(mon1.getHp()==0) {
                winner=mon2;
                mon2.setHp(hp2);
                break;
            }
        }
                winner.levelUp();
        return  winner;
        //      chicken
        //      dinner
    }
}
