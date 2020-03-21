package com.ravisharma.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.ravisharma.mydiary.Database.DiaryDB;

import java.util.Date;

public class AddEntryActivity extends AppCompatActivity {

    Button btnSave;
    TextInputEditText edEntry;
    DiaryDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        db = new DiaryDB(this);

        btnSave = findViewById(R.id.btnSave);
        edEntry = findViewById(R.id.edEntry);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entry = edEntry.getText().toString();
                if (!entry.isEmpty()) {
                    saveEntry(entry);
                } else {
                    Toast.makeText(AddEntryActivity.this, "Blank Field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveEntry(String entry) {
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        db.addEntry(currentDateTimeString, entry);
        Intent i = new Intent();
        i.putExtra("isEntryDone", true);
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent();
        setResult(RESULT_CANCELED, i);
    }
}
