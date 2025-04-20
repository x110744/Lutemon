package com.example.lutemon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        Lutemon winner = (Lutemon) getIntent().getSerializableExtra("winner");
        Bag bag = (Bag) getIntent().getSerializableExtra("bag");
        FrameLayout winnerContainer = findViewById(R.id.winnerCardContainer);
        Button returnHome = findViewById(R.id.returnHome);

        displayWinnerCard(winnerContainer, winner);

        returnHome.setOnClickListener(v -> {
            // Return to MainActivity with updated bag
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("updatedBag", bag);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void displayWinnerCard(FrameLayout container, Lutemon winner) {
        container.removeAllViews();
        if (winner != null) {
            View cardView = getLayoutInflater().inflate(R.layout.card_big, container, false);

            // Set winner details
            TextView nameView = cardView.findViewById(R.id.monNameBig);
            TextView winnerText = cardView.findViewById(R.id.monText);
            View colorCircle = cardView.findViewById(R.id.monPicture);

            nameView.setText(winner.getName());
            winnerText.setText("WINNER!");
            GradientDrawable drawable = (GradientDrawable) colorCircle.getBackground();
            drawable.setColor(Color.parseColor(winner.getColor()));

            container.addView(cardView);
        }
    }
}