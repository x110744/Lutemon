package com.example.lutemon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Bag bag; // Declare as class-level variable
    private ActivityResultLauncher<Intent> trainActivityLauncher; // Add this
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bag = new Bag();
        bag.addMon(new Lutemon("Glorp"));
        bag.addMon(new Lutemon("Porple"));

        trainActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        bag = (Bag) result.getData().getSerializableExtra("updatedBag");
                    }
                }
        );

        Button bagButton = findViewById(R.id.bag);
        Button trainButton = findViewById(R.id.train);
        Button arenaButton = findViewById(R.id.arena);

        if (getIntent().hasExtra("updatedBag")) {
            bag = (Bag) getIntent().getSerializableExtra("updatedBag");
        } else {
            bag = new Bag();
            bag.addMon(new Lutemon("Glorp"));
            bag.addMon(new Lutemon("Porple"));
        }

        bagButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), BagActivity.class);
            intent.putExtra("bag", bag);
            view.getContext().startActivity(intent);
        });

        trainButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, TrainActivity.class);
            intent.putExtra("bag", bag);
            trainActivityLauncher.launch(intent); // Use launcher instead
        });

        arenaButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ArenaActivity.class);
            intent.putExtra("bag", bag);
            view.getContext().startActivity(intent);
        });
    }
}