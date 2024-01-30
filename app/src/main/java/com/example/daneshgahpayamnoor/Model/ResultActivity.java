package com.example.daneshgahpayamnoor.Model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daneshgahpayamnoor.R;
import com.example.daneshgahpayamnoor.Result.ApiProvider;
import com.example.daneshgahpayamnoor.Result.Student;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class ResultActivity extends AppCompatActivity implements StudentAdapter.StudentEvent {
    private ShimmerFrameLayout shimmerProfile;
    private ShimmerFrameLayout shimmerList;
    private RecyclerView rvResult;
    private RelativeLayout profileView;
    private TextView tvName;
    private TextView tvDegree;
    private TextView tvStudentId;
    private NestedScrollView recyclerViewList;
    private FrameLayout emptyState404;
    private MaterialButton btnRetry404;
    private FrameLayout emptyStateNoInternet;
    private MaterialButton btnRetryNoInternet;
    private ResultViewModel resultViewModel;
    private StudentAdapter adapter;
    private String extra="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //finding views
        shimmerProfile=findViewById(R.id.shimmerProfile);
        shimmerList=findViewById(R.id.shimmerList);
        tvStudentId=findViewById(R.id.studentIdTV);
        tvDegree=findViewById(R.id.degreeTV);
        tvName=findViewById(R.id.fullNameMainTV);
        recyclerViewList=findViewById(R.id.mainListView);
        emptyState404=findViewById(R.id.emptyState404);
        btnRetry404=findViewById(R.id.btnRetry404);
        emptyStateNoInternet=findViewById(R.id.emptyStateNoInternet);
        btnRetryNoInternet=findViewById(R.id.retryNoInternet);
        rvResult=findViewById(R.id.rvMain);
        profileView=findViewById(R.id.profileView);
        //implementation
        extra= getIntent().getStringExtra("xTra");
        adapter=new StudentAdapter(this);
        resultViewModel=new ViewModelProvider(this,new ResutlViewModelFactory(new MainRepository(ApiProvider.getApiProvider()),extra )).get(ResultViewModel.class);
        //controlling views
        shimmerList.startShimmer();
        shimmerList.setVisibility(View.VISIBLE);
        shimmerProfile.startShimmer();
        shimmerProfile.setVisibility(View.VISIBLE);


        //chekcing internet




        //
    if (isOnline(this)) {
        resultViewModel.getStudentInfoList()
                .observe(this, new Observer<List<Student>>() {
                    @Override
                    public void onChanged(List<Student> students) {
                        if (students.size() > 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    shimmerList.stopShimmer();
                                    shimmerList.setVisibility(View.GONE);
                                    shimmerProfile.stopShimmer();
                                    shimmerProfile.setVisibility(View.GONE);
                                    profileView.setVisibility(View.VISIBLE);
                                    recyclerViewList.setVisibility(View.VISIBLE);
                                }
                            });

                            adapter.setData(students);
                            rvResult.setAdapter(adapter);
                            rvResult.setLayoutManager(new LinearLayoutManager(ResultActivity.this, LinearLayoutManager.VERTICAL, false));
                        }


                    }
                });
        resultViewModel.getUserError().observe(ResultActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.length() > 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            shimmerList.stopShimmer();
                            shimmerList.setVisibility(View.GONE);
                            shimmerProfile.stopShimmer();
                            shimmerProfile.setVisibility(View.GONE);
                            emptyState404.setVisibility(View.VISIBLE);
                            btnRetry404.setOnClickListener(v -> {
                                onBackPressed();
                            });
                        }
                    });

                }
            }
        });
    }else if(!isOnline(this)) {
        Toast.makeText(this, "اینترنت خود را بررسی و دوباره تلاش کنید!", Toast.LENGTH_SHORT).show();
        shimmerList.stopShimmer();
        shimmerList.setVisibility(View.GONE);
        shimmerProfile.stopShimmer();
        shimmerProfile.setVisibility(View.GONE);
        emptyStateNoInternet.setVisibility(View.VISIBLE);
        btnRetryNoInternet.setOnClickListener(v -> {
            onBackPressed();
        });
    }
        //validation



    }

    @Override
    public void onResult(String name, String studentID, String semester) {
        tvDegree.setText(semester);
        tvName.setText(name);
        tvStudentId.setText(studentID);

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if(!isOnline(this)) {
            Toast.makeText(this, "اینترنت خود را بررسی و دوباره تلاش کنید!", Toast.LENGTH_SHORT).show();
            shimmerList.stopShimmer();
            shimmerList.setVisibility(View.GONE);
            shimmerProfile.stopShimmer();
            shimmerProfile.setVisibility(View.GONE);
            emptyStateNoInternet.setVisibility(View.VISIBLE);
            btnRetryNoInternet.setOnClickListener(v -> {
                onBackPressed();
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isOnline(this)) {
            resultViewModel.getStudentInfoList()
                    .observe(this, new Observer<List<Student>>() {
                        @Override
                        public void onChanged(List<Student> students) {
                            if (students.size() > 0) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        shimmerList.stopShimmer();
                                        shimmerList.setVisibility(View.GONE);
                                        shimmerProfile.stopShimmer();
                                        shimmerProfile.setVisibility(View.GONE);
                                        profileView.setVisibility(View.VISIBLE);
                                        recyclerViewList.setVisibility(View.VISIBLE);
                                        emptyStateNoInternet.setVisibility(View.GONE);
                                        emptyState404.setVisibility(View.GONE);
                                    }
                                });

                                adapter.setData(students);
                                rvResult.setAdapter(adapter);
                                rvResult.setLayoutManager(new LinearLayoutManager(ResultActivity.this, LinearLayoutManager.VERTICAL, false));
                            }


                        }
                    });
            resultViewModel.getUserError().observe(ResultActivity.this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    if (s.length() > 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                shimmerList.stopShimmer();
                                shimmerList.setVisibility(View.GONE);
                                shimmerProfile.stopShimmer();
                                shimmerProfile.setVisibility(View.GONE);
                                emptyState404.setVisibility(View.VISIBLE);
                                emptyStateNoInternet.setVisibility(View.GONE);
                                btnRetry404.setOnClickListener(v -> {
                                    onBackPressed();
                                });
                            }
                        });

                    }
                }
            });
        }else if(!isOnline(this)) {
         //   Toast.makeText(this, "اینترنت خود را بررسی و دوباره تلاش کنید!", Toast.LENGTH_SHORT).show();
            shimmerList.stopShimmer();
            shimmerList.setVisibility(View.GONE);
            shimmerProfile.stopShimmer();
            shimmerProfile.setVisibility(View.GONE);
            emptyStateNoInternet.setVisibility(View.VISIBLE);
            profileView.setVisibility(View.GONE);
            recyclerViewList.setVisibility(View.GONE);
            emptyState404.setVisibility(View.GONE);
            btnRetryNoInternet.setOnClickListener(v -> {
                onBackPressed();
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resultViewModel.onCleared();
    }

    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }
}