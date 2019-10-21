package com.yadishot.myapplication.UserActions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.yadishot.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private TextView getOldAccount, getNewCode;
    private Button btnRegister;
    private TextInputEditText fullnameEdt, usernameEdt, passwordEdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        viewFinds();

        getOldAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        getNewCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, ResendActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = fullnameEdt.getText().toString().trim();
                String username = usernameEdt.getText().toString().trim();
                String password = passwordEdt.getText().toString().trim();
                if (fullname.isEmpty() && username.isEmpty() && password.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "فیلد ها باید کاملا پر باشند", Toast.LENGTH_SHORT).show();
                }else{
                    register(fullname, username, password);
                }
            }
        });
    }

    private void register(final String fullname, final String username, final String password) {
        String insert_url = "https://karafarini.horse-breeding-danaei.ir/register.php";

        StringRequest request = new StringRequest(Request.Method.POST, insert_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("response");

                    if (success.equals("0")){
                        Toast.makeText(RegisterActivity.this, "این کاربر وجود دارد", Toast.LENGTH_SHORT).show();
                    }else if (success.equals("1")){
                        Toast.makeText(RegisterActivity.this, "خطا در ارتباط با پایگاه داده", Toast.LENGTH_SHORT).show();
                    }else if (success.equals("2")){
                        Toast.makeText(RegisterActivity.this, "ثبت نام با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("fullname", fullname);
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void viewFinds() {
        btnRegister = findViewById(R.id.btnRegister);
        fullnameEdt = findViewById(R.id.fullnameEdt);
        usernameEdt = findViewById(R.id.usernameEdt);
        passwordEdt = findViewById(R.id.passwordEdt);
        getOldAccount = findViewById(R.id.loginOldAccount);
        getNewCode = findViewById(R.id.resendNewCode);
    }
}
