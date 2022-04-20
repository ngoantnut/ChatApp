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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    Button btnReg;
    EditText etxtName, etxtPass, etxtMail;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etxtName=findViewById(R.id.etxtName);
        etxtMail =findViewById(R.id.etxtMail);
        etxtPass = findViewById(R.id.etxtPass);
        btnReg =findViewById(R.id.btnReg);

        auth = FirebaseAuth.getInstance();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username= etxtName.getText().toString();
                String txt_mail= etxtMail.getText().toString();
                String txt_pass =etxtPass.getText().toString();

                if(TextUtils.isEmpty(txt_username)||TextUtils.isEmpty(txt_mail)||TextUtils.isEmpty(txt_pass)){
                    Toast.makeText(RegisterActivity.this, "All fileds", Toast.LENGTH_SHORT).show();
                }
                else if (txt_pass.length()<6){
                    Toast.makeText(RegisterActivity.this, "Pass must be at least 6 chartaer", Toast.LENGTH_SHORT).show();
                }
                else {
                    reg(txt_username, txt_mail, txt_pass);
                }
            }
        });

    }
    private void reg(final String etxtName, String etxtMail, String etxtPass){
        auth.createUserWithEmailAndPassword(etxtMail, etxtPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userId =firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                            HashMap<String, String> hashMap= new HashMap<>();
                            hashMap.put("id",userId);
                            hashMap.put("username", etxtName);
                            hashMap.put("imgaUrl", "default");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if( task.isSuccessful()){
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();

                                    }
                                }
                            });
                        }
                    }
                });
    }
}