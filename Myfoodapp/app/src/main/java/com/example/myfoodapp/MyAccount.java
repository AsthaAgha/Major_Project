package com.example.myfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

public class MyAccount extends AppCompatActivity {

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        fAuth = FirebaseAuth.getInstance();

        Button logout = findViewById(R.id.logoutButton);
        Button managePassword = findViewById(R.id.managePasswordButton);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign out the current user
                fAuth.signOut();

                // Redirect to the login screen and clear the activity stack
                Intent iLogout = new Intent(MyAccount.this, MainActivity2.class);
                iLogout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(iLogout);
            }
        });

        managePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send a password reset email to the current user's email address
                String userEmail = fAuth.getCurrentUser().getEmail();
                if (userEmail != null) {
                    fAuth.sendPasswordResetEmail(userEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MyAccount.this,
                                                "Password reset email sent to " + userEmail,
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MyAccount.this,
                                                "Failed to send password reset email.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}