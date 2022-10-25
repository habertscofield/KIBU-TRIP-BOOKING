package com.example.kibutripbooking.Admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kibutripbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    View view;
    int  t1Day, t1Month, t1Year,t1Hour, t1Minute;
    AutoCompleteTextView  autoCompleteTextView1;
    AutoCompleteTextView  autoCompleteTextView2;
    private TextInputLayout textInputLayout;
    private TextInputEditText driverName,busNumber,phoneNumber,driverLicense,region,driverAddress;
    TextView mDisplayDate, mDisplayTime;
    private DatabaseReference root;
    private DatePickerDialog.OnDateSetListener mDatesetListener;
    private FirebaseDatabase db;
    DatabaseReference databaseReference;
    EditText edt_add_price;
    Button btnadd_price;
    TextView currentPrice;
    DatabaseReference priceRef, priceRetrieve;

    private static final int REQUEST_CODE = 100;
    MaterialCardView addBus, addAdmin, logout, addprice, registerUser,addbook,addpesa;
    AlertDialog.Builder dialogBuilder, pricedialogBuilder, deleteBusDialog;
    AlertDialog dialog, admindialog, pricedialog, deleteDialog;

    private Uri imageUri;

    EditText bus_Id, bus_Number, busFrom, busTo, travels_Name, travelDate, bus_Condition;
    private Button comMPhotoSelect, comMSave, comMCancel, btnadminsave, btnadmincancel, savereading, cancelreading;
    Button busSave, annImageSelect, annSave, annCancel;
    private EditText adminusername, adminpassword;

    EditText readingday, readingdate, englishreading, kiswahilireading, kikuyureading,
            englishinjili, kiswahiliinjili, kikuyuinjili;

    private DatabaseReference mBusRef;
    private DatabaseReference mReadingRef;
    private FirebaseAuth firebaseAuth;
    private  String userId;

    ProgressDialog LoadingBar;
  //  private StorageReference mMemRef;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setTitle("Admin Panel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mAuth = FirebaseAuth.getInstance();
        LoadingBar = new ProgressDialog(this);
        addBus = findViewById(R.id.addMember);
        mBusRef = FirebaseDatabase.getInstance().getReference();
        addAdmin = findViewById(R.id.addAdministrator);
        registerUser = findViewById(R.id.register);
        addbook=findViewById(R.id.addbook);
        addpesa=findViewById(R.id.pesa);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Dashboard.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });

        addBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateBusDialog();
            }
        });

        addAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ViewDriver.class);
                startActivity(intent);
            }
        });
        addpesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Intent intent=new Intent(getApplicationContext(), ViewPayment.class);
             //   startActivity(intent);
            }
        });
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Register.class);
               startActivity(intent);
            }
        });

    }

    private void CreateBusDialog() {
        dialogBuilder = new AlertDialog.Builder(Dashboard.this);
        View busView = getLayoutInflater().inflate(R.layout.add_driver, null);

        autoCompleteTextView2  = busView.findViewById(R.id.busTo);
        String[] to = getResources().getStringArray(R.array.from);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.dropdown_item, to);
        autoCompleteTextView2.setAdapter(arrayAdapter1);

        driverName = busView.findViewById(R.id.travelsName);
        busNumber = busView.findViewById(R.id.busno);
        phoneNumber=busView.findViewById(R.id.busNumber);
        driverAddress=busView.findViewById(R.id.address);
      //  region=busView.findViewById(R.id.busTo);
        driverLicense=busView.findViewById(R.id.busFare);
        busSave = busView.findViewById(R.id.btnaddBus);
        dialogBuilder.setView(busView);
        dialog = dialogBuilder.create();
        dialog.show();
        busSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String driverNameI = driverName.getText().toString().trim();
                String busNumberI = busNumber.getText().toString().trim();
                String license=driverLicense.getText().toString().trim();
                String address=driverAddress.getText().toString().trim();
                String reg=autoCompleteTextView2.getText().toString().trim();
                String phone=phoneNumber.getText().toString().trim();
                String driverId = mBusRef.push().getKey();

                if (TextUtils.isEmpty(driverNameI)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Driver Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(busNumberI)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Bus Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(address))
                {
                    Toast.makeText(Dashboard.this, "Please Enter driver address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Please Enter phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(license)) {
                    Toast.makeText(getApplicationContext(), "Please Enter driver license", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(reg)) {
                    Toast.makeText(getApplicationContext(), "Please Select region", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    LoadingBar.setMessage("Adding Driver Please Wait...");
                    LoadingBar.show();
                    LoadingBar.setCancelable(false);

                    Driver driver = new Driver( driverId,driverNameI, busNumberI, address,reg,phone,license);


                    mBusRef.child("DriverDetails").child(driverId).setValue(driver).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Dashboard.this, "Driver Added Successfully", Toast.LENGTH_SHORT).show();
                                driverName.setText("");
                                busNumber.setText("");
                                phoneNumber.setText("");
                                driverAddress.setText("");
                                driverLicense.setText("");
                                autoCompleteTextView2.setText("");
                                Intent intent=new Intent(getApplicationContext(),ViewDriver.class);
                                startActivity(intent);
                                LoadingBar.dismiss();

                            }else {
                                Toast.makeText(Dashboard.this, "error", Toast.LENGTH_SHORT).show();
                                LoadingBar.dismiss();
                            }
                        }
                    });

                }


//
            }
        });
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }


    @Override
    public void onClick(View view) {
        if (view == addBus) {
            CreateBusDialog();
        }
    }

}