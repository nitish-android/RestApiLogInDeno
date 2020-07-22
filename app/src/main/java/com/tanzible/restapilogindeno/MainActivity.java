package com.tanzible.restapilogindeno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.Call;
import okhttp3.Response;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    private AppCompatButton btnLogin;
    private EditText etMobileNo;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Validation.isNetworkAvailable(MainActivity.this)){
                    Toast.makeText(MainActivity.this,"NO INTERNET CONNECTION ",Toast.LENGTH_LONG).show();
                }
                else {
                    if(!etMobileNo.getText().toString().isEmpty()){
                        loginTask(etMobileNo.getText().toString());
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                    }

                }




            }
        });
    }

    private void init() {
        btnLogin = (AppCompatButton)findViewById(R.id.btnLogin);
        etMobileNo = (EditText)findViewById(R.id.etMobileNo);


    }

    private void loginTask(final String mobile_no) {
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please wait");
        progressDialog.show();

        AppPreferance.setPhonno(getApplicationContext(),mobile_no, "mobileno");
        MyApiEndpointInterface apiService = ApiClient.getClient().create(MyApiEndpointInterface.class);
        Phone phone=new Phone();
        phone.setPhone(mobile_no);
        ApiClient.getUserService().login_task(phone).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(retrofit2.Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                try {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null) {
                        if (loginResponse.getStatus().equalsIgnoreCase("true")) {
                            Intent intent = new Intent(MainActivity.this,ConfirmOtp.class);
                            intent.putExtra("phone",mobile_no);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(retrofit2.Call<LoginResponse> call, Throwable t) {
                Log.d("Failure",t.toString());
                progressDialog.dismiss();
            }
        });

    }

}