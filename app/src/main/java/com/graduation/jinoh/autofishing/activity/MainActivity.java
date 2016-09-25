package com.graduation.jinoh.autofishing.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.graduation.jinoh.autofishing.R;
import com.graduation.jinoh.autofishing.fragment.OptionFragment;

public class MainActivity extends AppCompatActivity {

    Button freeButton;
    Button chargeButton;
    Button usageButton;
    Button helpButton;
    Button optionButton;
    Button boardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(getApplication(), FragmentContainer.class);
        startActivity(intent);

        /*
        freeButton = (Button)findViewById(R.id.free_button);
        chargeButton = (Button)findViewById(R.id.charge_button);
        //usageButton = (Button)findViewById(R.id.used_button);
        helpButton = (Button)findViewById(R.id.help_button);
        optionButton = (Button)findViewById(R.id.option_button);
        //boardButton = (Button)findViewById(R.id.board_button);

        freeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        chargeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OptionFragment optionFragment = new OptionFragment();
               // getFragmentManager().beginTransaction().add(R.id.mainFrame, optionFragment).commit();
               // Intent intent = new Intent(getApplication(), FragmentContainer.class);
               // startActivity(intent);
            }
        });
        */
        startActivity(intent);
    }
}
