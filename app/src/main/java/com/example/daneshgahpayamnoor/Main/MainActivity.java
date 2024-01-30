package com.example.daneshgahpayamnoor.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.daneshgahpayamnoor.Model.ResultActivity;
import com.example.daneshgahpayamnoor.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //finding View
        progressBar=findViewById(R.id.mainProgressBar);
        Button fabSearch = findViewById(R.id.btnSearch);
        ExtendedFloatingActionButton fabContactUni = findViewById(R.id.btnContact);
        searchBar=findViewById(R.id.edtStudentNumber);


        //usage
        progressBar.setVisibility(View.GONE);
        //
        fabSearch.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


            if (isOnline(MainActivity.this)){
                if (searchBar.getText().length()<9){
                    Toast.makeText(MainActivity.this, "شماره دانشجویی شامل 9 رقم است!", Toast.LENGTH_SHORT).show();
                    searchBar.setText("");
                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }else if (searchBar.getText().length()==9){
                    Intent intent=new Intent(MainActivity.this, ResultActivity.class);
                    intent.putExtra("xTra",searchBar.getText().toString());
                    startActivity(intent);
                    Bundle bundle=new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM,searchBar.getText().toString());
                    FirebaseAnalytics.getInstance(this)
                            .logEvent(FirebaseAnalytics.Event.SEARCH,bundle);
                }
            }else {
                Toast.makeText(MainActivity.this,"لطفا اتصال اینترنت خود را بررسی و یا VPN خود را خاموش کنید.",Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });

        //contact us
        fabContactUni.setOnClickListener(v ->{
            BottomSheetDialoge dialogContactUs=new BottomSheetDialoge() ;
            dialogContactUs.show(getSupportFragmentManager(),null);
        });
    }
    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        searchBar.setText("");
    }
}