package com.example.lutemon;

import java.io.Serializable;
import java.util.Random;

public class Lutemon implements Serializable {
    private final String name;
    private final String color;
    private final int atk;
    private final int def;
    private int exp;
    private int hp;
    private final int id;
    private static int idCount;

    public Lutemon(String name) {
        Random rng = new Random();
        this.name = name;
        this.color = "#FF"+String.format("%06x", rng.nextInt(16777216)); // 0xFFFFFF = 2^24
        this.atk = rng.nextInt(25);
        this.def = rng.nextInt(25);
        this.exp = 0;
        this.hp = 250 + rng.nextInt(50) - rng.nextInt(50);
        this.id = idCount;
        idCount++;
    }
    public String getName() {
        return this.name;
    }
    public String getColor() {
        return this.color;
    }
    public int getAtk() {
        return this.atk;
    }
    public int getDef() {
        return this.def;
    }
    public int getExp() {
        return this.exp;
    }
    public int getHp() {
        return this.hp;
    }
    public void setHp(int amount) {
        this.hp = amount;
    }
    public int getId() {
        return this.id;
    }
    public void levelUp() {
        this.exp++;
    }
    public int dealDmg() {
        return this.atk + this.exp;
    }
    public void takeDmg(int dmg) {
        if(dmg > this.hp + this.def) {
            this.hp = 0;
        } else {
            if(dmg > this.def) {
                this.hp = this.hp - (dmg-this.def);
            }
        }
    }
}
