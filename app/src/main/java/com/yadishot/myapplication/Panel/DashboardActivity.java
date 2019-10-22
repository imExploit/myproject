package com.yadishot.myapplication.Panel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.yadishot.myapplication.SharedPreferencesConfig;
import com.yadishot.myapplication.UserActions.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {

    Button btnExitProfile, btnUploadImage;
    SharedPreferencesConfig sharedPreferencesConfig;
    TextInputEditText fullnameEdt, usernameEdt, passwordEdt, personWork;
    Bitmap bitmap;
    ImageView profileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());

        if (!sharedPreferencesConfig.readUserLoginStatus()){
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish();
        }
        profileImage = findViewById(R.id.profileImage);
        fullnameEdt = findViewById(R.id.fullnameEdt);
        usernameEdt = findViewById(R.id.usernameEdt);
        personWork = findViewById(R.id.personWork);
        btnExitProfile = findViewById(R.id.btnExitProfile);
        btnUploadImage = findViewById(R.id.btnUploadImage);

        fullnameEdt.setText(sharedPreferencesConfig.readUserFullname());
        usernameEdt.setText(sharedPreferencesConfig.readUsername());
        personWork.setText(sharedPreferencesConfig.readPersonWork());

        btnExitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferencesConfig.writeUserLoginStatus(false);
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
    }

    private void uploadImage() {
        Intent selectImage = new Intent(Intent.ACTION_GET_CONTENT);
        selectImage.setType("image/*");
        startActivityForResult(selectImage, 404);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 404 && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            UploadProfileImage(sharedPreferencesConfig.readUserId(), getStringImage(bitmap));
        }
    }

    private void UploadProfileImage(final String readUserId, final String photo) {
        String uploadProfileImageUrl = "https://karafarini.horse-breeding-danaei.ir/upload.php";

        StringRequest request = new StringRequest(Request.Method.POST, uploadProfileImageUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("response");
                    if (success.equals("1")){
                        Toast.makeText(DashboardActivity.this, "پروفایل شما بروزرسانی شد", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
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
                params.put("user_id", readUserId);
                params.put("photo", photo);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);
        return encodedImage;
    }
}
