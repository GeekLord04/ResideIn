package com.geekym.residein.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.geekym.residein.Activities.HomeScreen;
import com.geekym.residein.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextView signup , forgot_password;
    EditText email,pass;
    Button signin;
    boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signup = findViewById(R.id.signup_text);


        InitializeMethods();

        // Function to see password and hide password
        pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;
                if (event.getAction()==MotionEvent.ACTION_UP){
                    if (event.getRawX()>=pass.getRight()-pass.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = pass.getSelectionEnd();
                        if (passwordVisible){
                            pass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibility_off,0);
                            // for hide password
                            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;
                        }else{
                            pass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibility,0);
                            // for show password
                            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        pass.setSelection(selection);
                        return true;
                    }

                }
                return false;
            }
        });


        // FORGOT PASSWORD Function
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this,ForgotPass.class);
                startActivity(intent);
            }
        });
        // SIGN IN Function
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtext = email.getText().toString().trim();
                String passtext = pass.getText().toString().trim();
                if (emailtext.isEmpty()){
                    email.setError("Field can't be empty");
                    email.requestFocus();
                    return;
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(emailtext).matches()){
                    email.setError("Please enter a valid Email id");
                    email.requestFocus();
                    return;
                }
                else if (passtext.isEmpty()){
                    pass.setError("Field can't be empty");
                    pass.requestFocus();
                    return;
                }
                else if (passtext.length()<6){
                    pass.setError("Password must be at least 6 characters");
                    pass.requestFocus();
                    return;
                }
                else {

                    mAuth.signInWithEmailAndPassword(emailtext,passtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                Intent intent2 = new Intent(SignIn.this, HomeScreen.class);
                                startActivity(intent2);
                                finishAffinity();
                            }
                            else {
                                Toast.makeText(SignIn.this, "Failed to Login! Please check your credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignIn.this , SignUp.class);
                startActivity(i);
            }
        });
    }

    private void InitializeMethods() {
        forgot_password = findViewById(R.id.forgot_pass);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emaillogin);
        pass = findViewById(R.id.passwordlogin);
        signin = findViewById(R.id.signin);
    }
}