package com.graduation.jinoh.autofishing.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.graduation.jinoh.autofishing.R;
import com.graduation.jinoh.autofishing.fragment.OptionFragment;

public class FragmentContainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        startActivity(new Intent(this, SplashActivity.class));

        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                OptionFragment optionFragment = new OptionFragment();
                getFragmentManager().beginTransaction().add(R.id.mainFrame, optionFragment).commit();
            }
        }, 2200);


    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
        super.onBackPressed();
    }

}
