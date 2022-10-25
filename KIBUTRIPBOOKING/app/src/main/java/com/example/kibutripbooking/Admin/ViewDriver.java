package com.example.kibutripbooking.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kibutripbooking.MainActivity;
import com.example.kibutripbooking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewDriver extends AppCompatActivity {

    private ListView listViewBuses;
    private DatabaseReference databaseBus;
    private List<Driver> busList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_driver);
        getSupportActionBar().setTitle("Available Drivers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        busList = new ArrayList<>();

        listViewBuses = findViewById(R.id.listViewBusDetails);
        databaseBus = FirebaseDatabase.getInstance().getReference();
//         = FirebaseDatabase.getInstance().getReference("BusDetails");
        FirebaseDatabase.getInstance().getReference("DriverDetails")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        busList.clear();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Driver driver = snapshot.getValue(Driver.class);
                                busList.add(driver);
                            }
                            DriverList adapter = new DriverList(ViewDriver.this, busList);
                            listViewBuses.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        listViewBuses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


              //  Driver driver = busList.get(i);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    }
                });

            }
        });
    }
}

    // update ticket fees details

