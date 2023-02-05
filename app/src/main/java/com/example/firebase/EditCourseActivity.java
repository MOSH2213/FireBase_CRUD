package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditCourseActivity extends AppCompatActivity {

    private TextInputEditText courseNameEdt,coursePriceEdt,courseSuitedForEdt,courseImgEdt,courseLinkEdt,courseDescEdt;
    private Button updateCourseBtn,deleteCourseBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databseReference;
    private String courseID;
    private CourseRVModal courseRVModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        firebaseDatabase = FirebaseDatabase.getInstance();

        courseNameEdt = findViewById(R.id.idEdtCourseName);
        coursePriceEdt = findViewById(R.id.idEdtCoursePrice);
        courseSuitedForEdt = findViewById(R.id.idEdtCourseSuitedFor);
        courseImgEdt = findViewById(R.id.idEdtCourseImageLink);
        courseLinkEdt = findViewById(R.id.idEdtCourseLink);
        courseDescEdt = findViewById(R.id.idEdtCourseDesc);
        updateCourseBtn = findViewById(R.id.idBtnUpdateCourse);
        deleteCourseBtn = findViewById(R.id.idBtnDeleteCourse);
        loadingPB = findViewById(R.id.idPBLoading);

        courseRVModal = getIntent().getParcelableExtra("course");
        if(courseRVModal!=null){
            courseNameEdt.setText(courseRVModal.getCourseName());
            coursePriceEdt.setText(courseRVModal.getCoursePrice());
            courseSuitedForEdt.setText(courseRVModal.getBestSuitedFor());
            courseImgEdt.setText(courseRVModal.getCourseImg());
            courseLinkEdt.setText(courseRVModal.getCourseLink());
            courseDescEdt.setText(courseRVModal.getCourseDescription());
            courseID = courseRVModal.getCourseID();
        }

        databseReference = firebaseDatabase.getReference("Courses").child(courseID);
        updateCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String courseName = courseNameEdt.getText().toString();
                String coursePrice = coursePriceEdt.getText().toString();
                String suitedFor = courseSuitedForEdt.getText().toString();
                String courseImg = courseImgEdt.getText().toString();
                String courseLink = courseLinkEdt.getText().toString();
                String courseDesc = courseDescEdt.getText().toString();

                //using map instead of modal to store data in DB
                Map<String,Object> map = new HashMap<>();
                map.put("courseName",courseName);
                map.put("courseDescription",courseDesc);
                map.put("coursePrice",coursePrice);
                map.put("bestSuitedFor",suitedFor);
                map.put("courseImg",courseImg);
                map.put("courselink",courseLink);
                map.put("courseID",courseID);

                databseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databseReference.updateChildren(map);
                        Toast.makeText(EditCourseActivity.this,"Course updated",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditCourseActivity.this,MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditCourseActivity.this,"",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCourse();
            }
        });


    }
    private void deleteCourse(){
        databseReference.removeValue();
        Toast.makeText(this,"Course Deleted..",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditCourseActivity.this,MainActivity.class));
    }
}