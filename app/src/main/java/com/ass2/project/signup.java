package com.ass2.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    EditText name, email,password;
    TextView signup, tvLogin;

    //noor's = "http://192.168.100.82/loginsignup/insert.php"
    private static final String url ="http://192.168.18.40/project/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup = findViewById(R.id.signUpTVSignUp);
        tvLogin = findViewById(R.id.signUpTVSignIn);
        name = findViewById(R.id.nameETSignUp);
        email = findViewById(R.id.emailETSignUp);
        password = findViewById(R.id.passwordETSignUp);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!name.getText().toString().trim().equals("") &&
                        !email.getText().toString().trim().equals("") &&
                        !password.getText().toString().trim().equals("")){
                    insertdata();
                } else{
                    name.forceLayout();
                    email.forceLayout();
                    password.forceLayout();
                    Toast.makeText(signup.this, "Please input Required Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup.this, Login.class));
                Toast.makeText(signup.this, "Log In Click", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void insertdata() {
        final String n =name.getText().toString().trim();
        final String e =email.getText().toString().trim();
        final String p =password.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(signup.this,Login.class));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("name",n);
                param.put("email",e);
                param.put("password",p);
                return param;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }


}