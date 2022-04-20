package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity {
    EditText mail, pass;
    Button btnLog, btnToReg;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.pass);

        auth = FirebaseAuth.getInstance();
        btnLog =findViewById(R.id.btnlogin);
        btnToReg=findViewById(R.id.btnToReg);
        btnToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                finish();
            }
        });
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email= mail.getText().toString();
                String txt_epass = pass.getText().toString();
                if(TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_epass)){
                    Toast.makeText(MainActivity.this, "All fileds", Toast.LENGTH_SHORT).show();
                }
                else {
                    auth.signInWithEmailAndPassword(txt_email, txt_epass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        Intent intent= new Intent(MainActivity.this, RegisterActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, "Pass must be at least 6 chartaer", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
        });
    }
}