package com.icche.shop;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AboutApp extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 22) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_about_app);
        Button button = findViewById(R.id.feedback);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/email");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {getString(R.string.feedbackMail)});
                intent.putExtra(Intent.EXTRA_SUBJECT, (getString(R.string.feedbacksub)));
                intent.putExtra(Intent.EXTRA_TEXT,(getString(R.string.msg_feedback)));
                startActivity(Intent.createChooser(intent, (getString(R.string.feedTitle))));
            }
        });
    }

}
