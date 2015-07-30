package com.leo.funnyindicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        findViewById(R.id.btn_default_indicator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SampleActivity.this.startActivity(new Intent(SampleActivity.this, DefaultActivity.class));
            }
        });
        findViewById(R.id.btn_travel_color_indicator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SampleActivity.this.startActivity(new Intent(SampleActivity.this, TravelColorActivity.class));
            }
        });
        findViewById(R.id.btn_funny_head_indicator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SampleActivity.this.startActivity(new Intent(SampleActivity.this, FunnyHeadActivity.class));
            }
        });
    }

}
