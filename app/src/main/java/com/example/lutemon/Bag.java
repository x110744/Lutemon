package com.example.lutemon;

import java.io.Serializable;
import java.util.ArrayList;

public class Bag implements Serializable {
    private ArrayList<Lutemon> mons;
    public Bag() {
        this.mons = new ArrayList<>();
    }
    public void addMon(Lutemon mon) {
        this.mons.add(mon);
    }
    public Lutemon takeMon(int id) {
        int idx = 0;
        for(int i=0; i<mons.size(); i++) {
            if(mons.get(i).getId()==id) {
                idx=i;
                break;
            }
        }
        return mons.remove(idx);
    }
    public ArrayList<Lutemon> getMons() {
        return this.mons;
    }
}
