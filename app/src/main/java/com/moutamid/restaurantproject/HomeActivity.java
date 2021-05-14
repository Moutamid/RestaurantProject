package com.moutamid.restaurantproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.logoutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();

                Toast.makeText(HomeActivity.this, "You are logged out successfully!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);

            }
        });

        findViewById(R.id.first_restaurant_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
                intent.putExtra("value", 1);
                startActivity(intent);
            }
        });
        findViewById(R.id.second_restaurant_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
                intent.putExtra("value", 2);
                startActivity(intent);
            }
        });
        findViewById(R.id.third_restaurant_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
                intent.putExtra("value", 3);
                startActivity(intent);
            }
        });

    }
}