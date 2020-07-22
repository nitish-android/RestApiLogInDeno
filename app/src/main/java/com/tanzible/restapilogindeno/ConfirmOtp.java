package com.tanzible.restapilogindeno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmOtp extends AppCompatActivity {
    private ImageView imgBack;
    private EditText etOtp;
    private AppCompatButton btnVerify;
    private AppSession appSession;
    private ProgressDialog progressDialog;
    private static String phoneNumber=null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phone");
        setContentView(R.layout.activity_confirm_otp);
        init();
        requestSMSPermission();




        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Validation.isNetworkAvailable(ConfirmOtp.this)){
                    Toast.makeText(ConfirmOtp.this,"NO INTERNET CONNECTION ",Toast.LENGTH_LONG).show();
                }
                else {
                    if(!etOtp.getText().toString().isEmpty()){
                        confirmOtpTask(etOtp.getText().toString());
                    }
                    else {
                        Toast.makeText(ConfirmOtp.this, "Please enter OTP.", Toast.LENGTH_SHORT).show();
                    }

                }




            }
        });


    }

    private void requestSMSPermission() {
        String permission = Manifest.permission.RECEIVE_SMS;

        int grant = ContextCompat.checkSelfPermission(this,permission);
        if (grant != PackageManager.PERMISSION_GRANTED)
        {
            String[] permission_list = new String[1];
            permission_list[0] = permission;

            ActivityCompat.requestPermissions(this, permission_list,1);
        }

    }

    private void init() {
        imgBack =  (ImageView)findViewById(R.id.backimg);
        btnVerify =  (AppCompatButton)findViewById(R.id.btnVerify);
        etOtp =  (EditText)findViewById(R.id.etOtp);
        new OTP_Receiver().setEditText(etOtp);
    }



    private void confirmOtpTask(String otp) {
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please wait");
        progressDialog.show();

        MyApiEndpointInterface apiService = ApiClient.getClient().create(MyApiEndpointInterface.class);

        VerifyModel verifyModel=new VerifyModel();
        if (phoneNumber != null) {
            Log.d("mobile", phoneNumber);
            verifyModel.setPhone(phoneNumber);
            verifyModel.setCode(otp);

//        asd;flakdsjflk
            apiService.confirm_otp(verifyModel).enqueue(new Callback<UserDataResponse>() {
                @Override
                public void onResponse(Call<UserDataResponse> call, Response<UserDataResponse> response) {

                    try {

                        UserDataResponse userDataResponse = response.body();

                        if (userDataResponse.getStatus().equalsIgnoreCase("true")) {
                            if (userDataResponse.getMessage().equalsIgnoreCase("verified successfully")) {
                                progressDialog.dismiss();
                                startActivity(new Intent(ConfirmOtp.this, HomeActivity.class));
                                finish();

                                // startActivity(new Intent(ConfirmOtp.this,NavigationDrawer.class));
                            } else {

                                progressDialog.dismiss();
                                Toast.makeText(ConfirmOtp.this, userDataResponse.getMessage(), Toast.LENGTH_SHORT).show();


                            }


                        } else {
                            progressDialog.dismiss();

                            Toast.makeText(ConfirmOtp.this, userDataResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<UserDataResponse> call, Throwable t) {

                }
            });
        }
//        alskdjflaksdj

    }

    }