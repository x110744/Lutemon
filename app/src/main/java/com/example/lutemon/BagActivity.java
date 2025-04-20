package com.example.lutemon;

import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BagActivity extends AppCompatActivity {
    private String lutemonName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());

        Bag bag = (Bag) getIntent().getSerializableExtra("bag");
        CustomAdapter customAdapter = new CustomAdapter(bag.getMons());

        RecyclerView recyclerView = findViewById(R.id.bagRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(customAdapter);

        Button newMon = findViewById(R.id.newMon);
        newMon.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(BagActivity.this);
            builder.setTitle("Lutemon name:");
            builder.setMessage("Please enter a name for your new Lutemon.");
            final EditText input = new EditText(BagActivity.this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("Confirm", (dialog, which) -> {
                lutemonName = input.getText().toString().trim();
                if(lutemonName.isEmpty()) {
                    Toast.makeText(BagActivity.this, "Name required", Toast.LENGTH_SHORT).show();
                } else {
                    bag.addMon(new Lutemon(lutemonName));
                    customAdapter.notifyDataSetChanged();
                }
                dialog.dismiss();
            });
            builder.setOnDismissListener(dialog -> {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });
    }
}
