package com.ravisharma.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ViewEntryActivity extends AppCompatActivity {

    TextView textTime, textEntry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry);

        textEntry = findViewById(R.id.textEntry);
        textTime = findViewById(R.id.textTime);

        Bundle bundle = getIntent().getExtras();

        String time = bundle.getString("time");
        String entry = bundle.getString("entry");

        textTime.setText(time);
        textEntry.setText(entry);
    }
}
