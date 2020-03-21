package com.ravisharma.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.ravisharma.mydiary.Prefrence.PrefManager;

public class RegisterActivity extends AppCompatActivity {

    Button btnSave;
    TextInputEditText edName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PrefManager pf = new PrefManager(this);
        if (pf.isLoggedIn()) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }

        setContentView(R.layout.activity_register);

        edName = findViewById(R.id.edName);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = edName.getText().toString();
                if (!a.isEmpty()) {
                    saveUser(a);
                } else {
                    edName.setError("Please Enter Your Name");
                    edName.requestFocus();
                }
            }
        });
    }

    private void saveUser(String name) {
        PrefManager pf = new PrefManager(this);
        pf.saveUserName(name);
        pf.setLoggedIn(true);

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
