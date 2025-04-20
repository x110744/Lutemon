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

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TrainActivity extends AppCompatActivity {
    private Bag bag;
    private Lutemon selectedLutemon;
    private FrameLayout trainedMonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        Button backButton = findViewById(R.id.back);
        backButton.setOnClickListener(view -> {
            if (selectedLutemon != null) {
                bag.addMon(selectedLutemon);
                selectedLutemon = null;
            }
            Intent resultIntent = new Intent();
            resultIntent.putExtra("updatedBag", bag);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        Button selectButton = findViewById(R.id.select);
        selectButton.setOnClickListener(v -> showLutemonSelectionDialog());

        bag = (Bag) getIntent().getSerializableExtra("bag");
        trainedMonLayout = findViewById(R.id.trainedMon);
        Button trainButton = findViewById(R.id.trainButton);
        trainButton.setOnClickListener(v -> {
            if (selectedLutemon != null) {
                selectedLutemon.levelUp();
                updateLutemonDisplay(); // Refresh XP display
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (selectedLutemon != null) {
                    bag.addMon(selectedLutemon);
                    selectedLutemon = null;
                }
                Intent resultIntent = new Intent();
                resultIntent.putExtra("updatedBag", bag);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    private void showLutemonSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select a Lutemon");

        // Setup RecyclerView
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CustomAdapter adapter = new CustomAdapter(bag.getMons());
        recyclerView.setAdapter(adapter);

        builder.setView(recyclerView);
        AlertDialog dialog = builder.create();

        // Handle item selection
        adapter.setOnItemClickListener(position -> {
            Lutemon selected = bag.getMons().get(position);
            handleLutemonSelection(selected);
            dialog.dismiss();
        });

        dialog.show();
    }
    private void handleLutemonSelection(Lutemon newLutemon) {
        // Return existing Lutemon to bag if present
        if (selectedLutemon != null) {
            bag.addMon(selectedLutemon);
        }

        // Remove new selection from bag and display
        selectedLutemon = bag.takeMon(newLutemon.getId());
        updateLutemonDisplay();
    }

    @SuppressLint("SetTextI18n")
    private void updateLutemonDisplay() {
        trainedMonLayout.removeAllViews();

        if (selectedLutemon != null) {
            View cardView = getLayoutInflater().inflate(R.layout.card_big, trainedMonLayout, false);

            // Set Lutemon details
            TextView nameView = cardView.findViewById(R.id.monNameBig);
            TextView xpView = cardView.findViewById(R.id.monText);
            View colorCircle = cardView.findViewById(R.id.monPicture);

            nameView.setText(selectedLutemon.getName());
            xpView.setText(selectedLutemon.getExp() + " EXP");
            GradientDrawable drawable = (GradientDrawable) colorCircle.getBackground();
            drawable.setColor(Color.parseColor(selectedLutemon.getColor()));

            trainedMonLayout.addView(cardView);
        }
    }

}
