package com.example.attendanceapp;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ListAdapter;

import java.util.ArrayList;

public class EditSessionStudents extends AppCompatActivity {

    private Database database ;
    private int classID ;
    private int sessionID ;

    private ArrayList<Student> classStudents ;
    private ArrayList<Integer> sessionStudents ;
    private ArrayList<Integer> insertStudents ;
    private ArrayList<Integer> deleteStudents ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS ,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_edit_session_students);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button submit = findViewById(R.id.submit);
        ListView listView = findViewById(R.id.listView) ;
        database = new Database() ;
        classID = getIntent().getIntExtra("Class ID" , 0 );
        sessionID = getIntent().getIntExtra("Session ID" , 0 );
        classStudents = database.getStudents(classID);
        Session session= database.getSession(classID , sessionID);
        ArrayList<Student> sessionStudent1 = session.getStudents() ;
        sessionStudents = new ArrayList<>() ;

        for (Student st : sessionStudent1){
            sessionStudents.add(st.getID());
        }
        insertStudents = new ArrayList<>() ;
        deleteStudents = new ArrayList<>() ;

        listView.setAdapter(new ListAdapter());

        submit.setOnClickListener(v-> {
            database.addStudentsToSession(classID , sessionID , insertStudents);
            database.removeStudentsFromSession(classID , sessionID , deleteStudents);
            finish();

        });

    }
    private class ListAdapter extends BaseAdapter{

        public ListAdapter (){}

        @Override
        public int getCount(){       // Ye batata hai ListView ko — total kitne items dikhane hain.
            return classStudents.size();
        }

        @Override
        public Student getItem(int i){ //Ye return karta hai ek specific item list ke andar se (uski position se).
            return classStudents.get(i);
        }

        //Ye mostly list ke liye ek unique ID return karta hai.
        //Simple case me tu bas position return kar de sakta hai.

        @Override
        public long getItemId(int i) {
            return i ; // id
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = getLayoutInflater();
            @SuppressLint({"InflateParams", "ViewHolder"})
            View v = inflater.inflate(R.layout.list_item_checkbox, null);

            CheckBox text = v.findViewById(R.id.text);
            Student student = classStudents.get(i);
            int studentID = student.getID();

            text.setText(student.getFirstName() + " " + student.getLastName());
            text.setChecked(sessionStudents.contains(studentID));

            text.setOnCheckedChangeListener((compoundButton , isChecked) -> {
                if (isChecked && !sessionStudents.contains(studentID)) {
                    addStudent(studentID);
                }else if (!isChecked && sessionStudents.contains(studentID)) {
                    removeStudent(studentID);
                }else if (isChecked && sessionStudents.contains(studentID)) {
                    if (deleteStudents.contains(studentID) && deleteStudents.size()!= 1) {
                        deleteStudents.remove(studentID) ;

                    }else {
                        deleteStudents = new ArrayList<>() ;
                    }
                }

            });

            return v;
        }}
    private void addStudent (int id) {
        if (!insertStudents.contains(id)) insertStudents.add(id);
        if (deleteStudents.contains(id)) deleteStudents.remove(id);
    }

    private void removeStudent (int id){
        if (insertStudents.contains(id)) insertStudents.remove(id) ;
        if (!deleteStudents.contains(id)) deleteStudents.add(id) ;

    }

}