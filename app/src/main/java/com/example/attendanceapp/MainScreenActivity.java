package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainScreenActivity extends AppCompatActivity {

    private Database database;
    private ArrayList<ClassData> classes;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main_screen);

        ListView listview = findViewById(R.id.listView);
        Button new_class = findViewById(R.id.new_class);

        new_class.setOnClickListener(v -> {
            Intent intent = new Intent(MainScreenActivity.this, ClassEditorActivity.class);
            startActivity(intent);
        });

        classes = new ArrayList<>();
        listAdapter = new ListAdapter();
        listview.setAdapter(listAdapter);

        loadClassesFromDB(); // Background me load
    }

    private void loadClassesFromDB() {

        new Thread(() -> {
            database = new Database();
            ArrayList<ClassData> data = database.getAllClasses();

            runOnUiThread(() -> {
                classes.clear();
                classes.addAll(data);
                listAdapter.notifyDataSetChanged();
            });
        }).start();
    }

    @Override
    protected void onResume() { // works when user come back from other screen
        super.onResume();
        loadClassesFromDB();
    }

    public class ListAdapter extends BaseAdapter {

        public ListAdapter() {}
        @Override
        public int getCount() {
            return classes.size();
        }
        @Override
        public ClassData getItem(int i) {
            return classes.get(i);
        }
        @Override
        public long getItemId(int i) {
            return i;
        }
        @Override

        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = getLayoutInflater(); // converts xml data into an object
            @SuppressLint({"InflateParams", "ViewHolder"})

            View v = inflater.inflate(R.layout.list_item, null);
            TextView text = v.findViewById(R.id.text);

            ClassData currentClass = classes.get(i);
            String displayText = currentClass.getID() + ". " + currentClass.getClassName();
            text.setText(displayText);

            text.setOnClickListener(v1-> {
                Intent intent = new Intent(MainScreenActivity.this, ShowSession.class);
                intent.putExtra("Class ID", classes.get(i).getID());
                intent.putExtra("Class Name", classes.get(i).getClassName());
                startActivity(intent);
            });

            text.setOnLongClickListener(v1-> {
                Intent intent = new Intent(MainScreenActivity.this, ClassEditorActivity.class);
                intent.putExtra("Class ID", classes.get(i).getID());
                intent.putExtra("Class Name", classes.get(i).getClassName());
                startActivity(intent);
                return true;

            });
            return v;
        }
    }
}
