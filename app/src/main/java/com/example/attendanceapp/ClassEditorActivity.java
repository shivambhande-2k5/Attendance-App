package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClassEditorActivity extends AppCompatActivity { // AppCompatActivity ka matlab hi hai ki ye ek screen hai.

    private int classID; //Ye store karega ki hum kis class ki baat kar rahe hain
    private Database database;
    private ArrayList<Student> students; //  Ye ek khaali list hai.  Isme hum us class ke saare Student objects ko store karenge jo hum database se nikalenge.
    private ListAdapter listAdapter; // Iska kaam hai students list se data lekar screen par ListView me dikhana.

    @Override
    protected void onCreate(Bundle savedInstanceState) {  // onCreate ek special method hai jo Android tab call karta hai jab ye screen memory me load hoti hai.
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);  // Ye line screen ko full-screen jaisa look deti hai, jisse status bar (jahan time aur battery dikhti hai) ke peeche bhi tumhara layout chala jaata hai. Ye UI-related cheez hai.
        setContentView(R.layout.activity_class_editor); // XML file se jodta hai java file ko

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        EditText class_name = findViewById(R.id.new_class);
        Button add_students = findViewById(R.id.add_students);
        Button create = findViewById(R.id.create);
        Button delete = findViewById(R.id.delete);
        ListView listview = findViewById(R.id.listView);
        /* findViewById(...): Ye method XML design file me jaata hai aur di gayi ID (R.id.new_class) waale element (jaise EditText, Button) ko dhoondh kar laata hai,
         taaki tum uspar Java se kaam kar sako. Yahan hum screen ke saare UI elements ko Java variables me store kar rahe hain.*/

        database = new Database();
        /* Yahan hum Database class ka ek naya object bana rahe hain aur use database variable me daal rahe hain.
         Ab is variable se hum database ke saare functions (jaise getStudents, addClass) call kar sakte hain.*/

        if (getIntent().hasExtra("Class ID")) {     // Iska matlab hai ki hum "Purani Class" par click karke aaye hain.
            class_name.setText(getIntent().getStringExtra("Class Name"));
            classID = getIntent().getIntExtra("Class ID", 0);
            class_name.setEnabled(false);
            create.setVisibility(View.GONE);
        } else {          // Jb new class button pe click krte ho to ye else block chelega    // iska matlab hai ki hum Add Student button krke aaye hai
            add_students.setVisibility(View.GONE); // add student button hide
            delete.setVisibility(View.GONE);       // delete button hide
            classID = database.getNextClassID();
        }

        students = database.getStudents(classID);

        listAdapter = new ListAdapter();
        listview.setAdapter(listAdapter);

        add_students.setOnClickListener(v -> {
            Intent intent = new Intent(ClassEditorActivity.this, StudentEditorActivity.class);
            intent.putExtra("Class ID", classID);
            startActivity(intent);
        });

        create.setOnClickListener(v -> {
            String name = class_name.getText().toString().trim();      // User ne app mai dala hua naam liya
            if (name.isEmpty()) {
                Toast.makeText(this, "Enter Class Name", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                try {
                    int newID = database.getNextClassID();     // database se id li
                    ClassData c = new ClassData(newID, name);   // database mai value set kr diya fir
                    database.addClass(c);

                    runOnUiThread(() -> {
                        Toast.makeText(this, "Class Created", Toast.LENGTH_SHORT).show();
                        class_name.setEnabled(false);
                        create.setVisibility(View.GONE);
                        add_students.setVisibility(View.VISIBLE);
                        delete.setVisibility(View.VISIBLE);

                        students = database.getStudents(newID);
                        listAdapter.notifyDataSetChanged();
                    });
                } catch (Exception e) {
                    runOnUiThread(() ->
                            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
                }
            }).start();
        });

        delete.setOnClickListener(v -> {
            new Thread(() -> {
                database.deleteClass(classID);
                runOnUiThread(this::finish);
            }).start();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(() -> {
            students = database.getStudents(classID);
            runOnUiThread(() -> listAdapter.notifyDataSetChanged());
        }).start();
    }

    private class ListAdapter extends BaseAdapter {

        public ListAdapter() {
        }

        @Override
        public int getCount() {
            return students.size();
        }

        @Override
        public Student getItem(int i) {
            return students.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = getLayoutInflater();
            @SuppressLint({"InflateParams", "ViewHolder"})
            View v = inflater.inflate(R.layout.list_item, null);

            TextView text = v.findViewById(R.id.text);
            text.setText(students.get(i).getFirstName() + " " + students.get(i).getLastName());
            text.setOnClickListener(v1 -> {
                Intent intent = new Intent(ClassEditorActivity.this, StudentEditorActivity.class);
                intent.putExtra("Class ID", classID);
                intent.putExtra("Student ID", students.get(i).getID());
                startActivity(intent);
            });

            return v;
        }
    }

}
