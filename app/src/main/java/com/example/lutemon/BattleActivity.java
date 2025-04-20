package com.example.lutemon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BattleActivity extends AppCompatActivity {
    private Lutemon lutemon1, lutemon2;
    private FrameLayout mon1Layout, mon2Layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        mon1Layout = findViewById(R.id.mon1);
        mon2Layout = findViewById(R.id.mon2);
        Button seeResult = findViewById(R.id.seeResult);

        lutemon1 = (Lutemon) getIntent().getSerializableExtra("lutemon1");
        lutemon2 = (Lutemon) getIntent().getSerializableExtra("lutemon2");
        Bag bag = (Bag) getIntent().getSerializableExtra("bag");

        // Display the selected Lutemons
        updateLutemonDisplay();

        seeResult.setOnClickListener(view -> {
            // Run the battle
            BattleArena arena = new BattleArena();
            Lutemon winner = arena.battle(lutemon1, lutemon2);

            // Update the bag with post-battle state
            bag.addMon(winner);


            Intent intent = new Intent(this, WinnerActivity.class);
            intent.putExtra("winner", winner);
            intent.putExtra("bag", bag);
            startActivity(intent);
        });
    }

    private void updateLutemonDisplay() {
        updateLutemonCard(mon1Layout, lutemon1);
        updateLutemonCard(mon2Layout, lutemon2);
    }

    @SuppressLint("SetTextI18n")
    private void updateLutemonCard(FrameLayout layout, Lutemon lutemon) {
        layout.removeAllViews();
        if (lutemon != null) {
            View cardView = getLayoutInflater().inflate(R.layout.card_small, layout, false);

            TextView nameView = cardView.findViewById(R.id.monName);
            TextView atkView = cardView.findViewById(R.id.monAtk);
            TextView defView = cardView.findViewById(R.id.monDef);
            TextView expView = cardView.findViewById(R.id.monExp);
            TextView hpView = cardView.findViewById(R.id.monHp);
            View colorCircle = cardView.findViewById(R.id.profileCircle);

            nameView.setText(lutemon.getName());
            atkView.setText("ATK: " + lutemon.getAtk());
            defView.setText("DEF: " + lutemon.getDef());
            expView.setText("EXP: " + lutemon.getExp());
            hpView.setText("HP: " + lutemon.getHp());

            GradientDrawable drawable = (GradientDrawable) colorCircle.getBackground();
            drawable.setColor(Color.parseColor(lutemon.getColor()));

            layout.addView(cardView);
        }
    }
}