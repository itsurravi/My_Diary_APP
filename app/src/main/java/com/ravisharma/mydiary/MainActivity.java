package com.ravisharma.mydiary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ravisharma.mydiary.Adapter.RecyclerAdapter;
import com.ravisharma.mydiary.Database.DiaryDB;
import com.ravisharma.mydiary.Model.Entry;
import com.ravisharma.mydiary.Prefrence.PrefManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rViewEntry;
    FloatingActionButton fbAddEntry;
    TextView textClock;

    DiaryDB db;

    List<Entry> entryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PrefManager pf = new PrefManager(this);
        String name = pf.getUserName();
        setTitle(name + "'s Diary");

        db = new DiaryDB(this);

        fbAddEntry = findViewById(R.id.fbAddEntry);
        rViewEntry = findViewById(R.id.rViewEntry);
        textClock = findViewById(R.id.textClock);

        entryList = new ArrayList<>();

        Runnable runnable = new CountDownRunner();
        Thread myThread = new Thread(runnable);
        myThread.start();

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(RecyclerView.VERTICAL);
        rViewEntry.setLayoutManager(lm);

        fbAddEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddEntryActivity.class);
                startActivityForResult(i, 101);
            }
        });

        new fetchData().execute();
    }

    class fetchData extends AsyncTask<Void, Void, ArrayList<Entry>> {

        ProgressDialog pd = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected ArrayList<Entry> doInBackground(Void... voids) {
            ArrayList<Entry> entries = new ArrayList<>();
            Cursor c = db.getEntries();
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    String idValue = c.getString(c.getColumnIndex(DiaryDB.ID));
                    String date = c.getString(c.getColumnIndex(DiaryDB.DATE));
                    String name = c.getString(c.getColumnIndex(DiaryDB.NAME));
                    String entry = c.getString(c.getColumnIndex(DiaryDB.ENTRY));

                    int id = Integer.parseInt(idValue);

                    Entry en = new Entry(id, name, date, entry);
                    entries.add(en);
                }
            } else {
                return null;
            }
            return entries;
        }

        @Override
        protected void onPostExecute(ArrayList<Entry> entries) {
            super.onPostExecute(entries);
            pd.dismiss();
            if(entries==null)
            {
                return;
            }
            entryList = entries;
            RecyclerAdapter ad = new RecyclerAdapter(MainActivity.this, entryList);
            rViewEntry.setAdapter(ad);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                new fetchData().execute();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                    textClock.setText(currentDateTimeString);
                } catch (Exception e) {

                }
            }
        });
    }

    class CountDownRunner implements Runnable {
        // @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                }
            }
        }
    }
}
