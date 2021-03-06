package com.zaptas.printindiamart;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Forget_Password extends AppCompatActivity {
    EditText textView2;
    ImageView imageViewForgetPassword;
    JSONObject jsonObject;
    private static Response response;
    String msg;
    EditText Email, password;
    public static String logo;
    public static String email, password_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__password);
        textView2 = (EditText) findViewById(R.id.textView2);
        imageViewForgetPassword = findViewById(R.id.iv_changePassword);
        imageViewForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    public void update(View arg) {
        email = textView2.getText().toString();
        if (email.equals("")) {
            Toast.makeText(getApplicationContext(), "Enter email id", Toast.LENGTH_SHORT).show();
        } else {
            new forget().execute();
        }

    }


    class forget extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;

        /*public void add_filcal(View arg){
            Intent inaction3 = new Intent(v.getContext(), FilCal_ParticularItem.class);
        }*/
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog((Forget_Password.this));
            dialog.setMessage("Loading...");

            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            jsonObject = getDataFromWeb();

            try {
                msg = jsonObject.getString("msg");


            } catch (JSONException je) {
                //  Log.i(ParticularImage.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), msg,
                    Toast.LENGTH_LONG).show();


        }
    }

    public static JSONObject getDataFromWeb() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()

                    //   http://spacenterio.com/subdomain/filfox/ApiAdminController/query_get/ff12092018131049

                    .url("https://printindiamart.com/public/api/seller_forgot_password/" + email)
                    .build();

            response = client.newCall(request).execute();
            //  System.out.println("8878788787"+"http://spacenterio.com/subdomain/filfox/ApiAdminController/query_get/"+meth_username);
            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {
            // Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }
}
