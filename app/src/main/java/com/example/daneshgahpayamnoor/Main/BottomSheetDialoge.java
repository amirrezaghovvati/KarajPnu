package com.example.daneshgahpayamnoor.Main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.daneshgahpayamnoor.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.analytics.FirebaseAnalytics;

import retrofit2.http.HTTP;

public class BottomSheetDialoge extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.contact_us,container);
        TextView tvUrlGolestan=view.findViewById(R.id.tvUrlGolestan);
        TextView tvUrlContactUs=view.findViewById(R.id.tvUrlEmail);
        TextView tvUrlUniSite=view.findViewById(R.id.tvUrlUni);
        TextView tvUrlExamDepartment=view.findViewById(R.id.tvExamUrlDepartment);
        ImageView imgUniLink=view.findViewById(R.id.webPageLinkUni);
        ImageView imgUniGoles=view.findViewById(R.id.webPageLinkGoles);
        ImageView imgCall=view.findViewById(R.id.callIc);
        ImageView imgEmai=view.findViewById(R.id.emailIc);
        tvUrlUniSite.setText("https://karaj.alborz.pnu.ac...");
        tvUrlContactUs.setText("pnuKarajApplication2023@gmail.com");
        tvUrlExamDepartment.setText("02635741155");
        tvUrlGolestan.setText("https://osreg.pnu.ac.ir/...");
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

       // emailIntent.setType(HTTP.P);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"pnuKarajApplication2023@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "انتقادات و پیشنهادات");
        emailIntent.setType("text/html");
               tvUrlContactUs.setOnClickListener(v -> {startActivity(Intent.createChooser(emailIntent, "Send Email"));
               dismiss();});
               imgEmai.setOnClickListener(v ->{
                   startActivity(Intent.createChooser(emailIntent, "Send Email"));
                   dismiss();
               });

        Uri uniNumber=Uri.parse("tel:02635741155");
        Intent callIntent=new Intent(Intent.ACTION_DIAL,uniNumber);
        tvUrlExamDepartment.setOnClickListener(v ->{ startActivity(callIntent);
            dismiss();});
        imgCall.setOnClickListener(v ->{startActivity(callIntent);
            dismiss();});

        Uri webpage = Uri.parse("https://osreg.pnu.ac.ir");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        tvUrlGolestan.setOnClickListener(v -> {startActivity(webIntent);
            dismiss();});
        imgUniGoles.setOnClickListener(v -> {startActivity(webIntent);
            dismiss();});

        Uri webpageUniversity = Uri.parse("https://karaj.alborz.pnu.ac.ir/portal/home/");
        Intent webIntentUniversity = new Intent(Intent.ACTION_VIEW, webpageUniversity);
        tvUrlUniSite.setOnClickListener(v->{startActivity(webIntentUniversity);
        dismiss();});
        imgUniLink.setOnClickListener(v -> {startActivity(webIntentUniversity);
            dismiss();});

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        String str=getClass().getSimpleName();
        Log.i("TAG", "onResume: "+str);
        Bundle bundle=new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME,"Dialoge");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS,this.getClass().getSimpleName());
        FirebaseAnalytics.getInstance(getContext())
                .logEvent(FirebaseAnalytics.Event.SCREEN_VIEW,bundle);

    }
}
