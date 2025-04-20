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
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ArenaActivity extends AppCompatActivity {
    private Bag bag;
    private Lutemon lutemon1, lutemon2;
    private FrameLayout mon1Layout, mon2Layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena);

        // Initialize views
        mon1Layout = findViewById(R.id.mon1);
        mon2Layout = findViewById(R.id.mon2);
        Button selectMon1 = findViewById(R.id.selectMon1);
        Button selectMon2 = findViewById(R.id.selectMon2);
        Button startBattle = findViewById(R.id.startBattle);
        Button arenaBackButton = findViewById(R.id.arenaBackButton);

        // Get bag from intent
        bag = (Bag) getIntent().getSerializableExtra("bag");

        // Setup selection buttons
        selectMon1.setOnClickListener(v -> showSelectionDialog(1));
        selectMon2.setOnClickListener(v -> showSelectionDialog(2));

        // Battle start logic
        startBattle.setOnClickListener(v -> {
            if (lutemon1 != null && lutemon2 != null) {
                Intent intent = new Intent(this, BattleActivity.class);
                intent.putExtra("lutemon1", lutemon1);
                intent.putExtra("lutemon2", lutemon2);
                intent.putExtra("bag", bag);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Select both Lutemons first!", Toast.LENGTH_SHORT).show();
            }
        });

        // Back button handling
        arenaBackButton.setOnClickListener(v -> finishWithResult());
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishWithResult();
            }
        });
    }

    private void showSelectionDialog(int slot) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Lutemon");

        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CustomAdapter adapter = new CustomAdapter(bag.getMons());
        recyclerView.setAdapter(adapter);

        AlertDialog dialog = builder.setView(recyclerView).create();

        adapter.setOnItemClickListener(position -> {
            Lutemon selected = bag.getMons().get(position);
            handleSelection(selected, slot);
            dialog.dismiss();
        });
        dialog.show();
    }

    private void handleSelection(Lutemon newLutemon, int slot) {
        // Return previous selection to bag
        if (slot == 1 && lutemon1 != null) {
            bag.addMon(lutemon1);
        } else if (slot == 2 && lutemon2 != null) {
            bag.addMon(lutemon2);
        }

        // Assign new selection
        if (slot == 1) {
            lutemon1 = bag.takeMon(newLutemon.getId());
        } else {
            lutemon2 = bag.takeMon(newLutemon.getId());
        }
        updateDisplay();
    }

    @SuppressLint("SetTextI18n")
    private void updateDisplay() {
        updateLutemonCard(mon1Layout, lutemon1);
        updateLutemonCard(mon2Layout, lutemon2);
    }

    @SuppressLint("SetTextI18n")
    private void updateLutemonCard(FrameLayout layout, Lutemon lutemon) {
        layout.removeAllViews();
        if (lutemon != null) {
            View cardView = getLayoutInflater().inflate(R.layout.card_small, layout, false);

            // Set Lutemon details
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

    private void finishWithResult() {
        // Return any selected Lutemons to bag
        if (lutemon1 != null) bag.addMon(lutemon1);
        if (lutemon2 != null) bag.addMon(lutemon2);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("updatedBag", bag);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}