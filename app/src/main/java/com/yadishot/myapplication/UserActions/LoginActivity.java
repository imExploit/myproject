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
import com.yadishot.myapplication.Panel.DashboardActivity;
import com.yadishot.myapplication.R;
import com.yadishot.myapplication.SharedPreferencesConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText usernameEdts, passwordEdts;
    private Button btnLogin;
    private TextView createNewAccount;
    private SharedPreferencesConfig sharedPreferencesConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());
        if (sharedPreferencesConfig.readUserLoginStatus()){
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            LoginActivity.this.finish();
        }
        usernameEdts = findViewById(R.id.usernameEdtLog);
        passwordEdts = findViewById(R.id.passwordEdtLog);
        btnLogin = findViewById(R.id.btnLogin);
        createNewAccount = findViewById(R.id.createNewAccount);

        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEdts.getText().toString().trim();
                String password = passwordEdts.getText().toString().trim();
                if (username.isEmpty()){
                    Toast.makeText(LoginActivity.this, "نام کاربری خود را وارد کنید", Toast.LENGTH_SHORT).show();
                }else if (password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "کلمه عبور خود را وارد کنید", Toast.LENGTH_SHORT).show();
                }else {
                    login(username, password);
                }
            }
        });
    }

    private void login(final String username, final String password) {
        String loginUrl = "https://karafarini.horse-breeding-danaei.ir/login.php";

        StringRequest request = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("response");
                    String username = jsonObject.getString("username");
                    String userFullname = jsonObject.getString("user_fullname");
                    String user_password = jsonObject.getString("user_password");
                    String user_id = jsonObject.getString("user_id");
                    String personWork = jsonObject.getString("user_work");
                    if (success.equals("2")){
                        Toast.makeText(LoginActivity.this, "ورود موفقیت آمیز", Toast.LENGTH_SHORT).show();
                        // set sharedPreferences Config
                        sharedPreferencesConfig.writeUserLoginStatus(true);
                        sharedPreferencesConfig.writeUsername(username);
                        sharedPreferencesConfig.writeUserFullname(userFullname);
                        sharedPreferencesConfig.writeUserPassword(user_password);
                        sharedPreferencesConfig.writeUserId(user_id);
                        sharedPreferencesConfig.writeUserPersonWork(personWork);

                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        finish();
                    } else if (success.equals("1")){
                        Toast.makeText(LoginActivity.this, "کلمه عبور اشتباه است", Toast.LENGTH_SHORT).show();
                    } else if (success.equals("0")){
                        Toast.makeText(LoginActivity.this, "این کاربر وجود ندارد", Toast.LENGTH_SHORT).show();
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
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
