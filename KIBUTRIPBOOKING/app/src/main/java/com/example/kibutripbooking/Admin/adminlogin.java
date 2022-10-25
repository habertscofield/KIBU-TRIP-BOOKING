package com.example.kibutripbooking.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import com.example.kibutripbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class adminlogin extends AppCompatActivity {

    private Button adminLogin;
    private EditText email, password;
    private String AdminEmail, AdminPassword;
    private FirebaseAuth mAUTH;
    private FirebaseUser currentUser;
    private AppCompatCheckBox checkbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        getSupportActionBar().setTitle("Admin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            mAUTH = FirebaseAuth.getInstance();
            //remove ine case
            currentUser = mAUTH.getCurrentUser();
            adminLogin = (Button) findViewById(R.id.btnAdminLogin);
            email = (EditText) findViewById(R.id.inputAdminEmail);
            password = (EditText) findViewById(R.id.inputAdminPassword);
            checkbox = (AppCompatCheckBox) findViewById(R.id.checkbox);

            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                }
            });
            adminLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    allowAdminToLogin();
                }

            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void allowAdminToLogin() {
        String Email=email.getText().toString();
        String pass=password.getText().toString();
        if (TextUtils.isEmpty(Email)){
            Toast.makeText(this, "please enter your email address", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(pass)){
            Toast.makeText(this, "please enter your password", Toast.LENGTH_SHORT).show();

        }
        else{
            mAUTH.signInWithEmailAndPassword(Email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(Email.equals("admin@gmail.com") && pass.equals("Adminpass")){
                        Toast.makeText(adminlogin.this, "Login successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(adminlogin.this,Dashboard.class));
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Toast.makeText(adminlogin.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent=new Intent(getApplicationContext(), Login.class);
        startActivityForResult(intent,0);
        return true;
    }
}

