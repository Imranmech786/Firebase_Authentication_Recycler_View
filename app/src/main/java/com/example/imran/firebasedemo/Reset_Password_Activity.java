package com.example.imran.firebasedemo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Reset_Password_Activity extends AppCompatActivity {

    Button reset_pass, back_btn;
    EditText reset_email;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset__password);

        auth = FirebaseAuth.getInstance();

        reset_pass = (Button) findViewById(R.id.btn_reset_password);
        reset_email = (EditText) findViewById(R.id.reset_email);
        back_btn = (Button) findViewById(R.id.btn_back);

        Reset_password();
        BackButton();

    }

    private void BackButton() {

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void Reset_password() {

        reset_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = reset_email.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                //  progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Reset_Password_Activity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Reset_Password_Activity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }

                                // progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });

    }


}