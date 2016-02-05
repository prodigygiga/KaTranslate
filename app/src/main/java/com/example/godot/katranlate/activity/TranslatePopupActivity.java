package com.example.godot.katranlate.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.godot.katranlate.R;

public class TranslatePopupActivity extends Activity {

    ImageView closeButton;
    TextView translationResultTextView;

    private String translationResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_popup);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        translationResult = extras.getString("translationResult");


        translationResultTextView = (TextView) findViewById(R.id.popupTranslationResult);
        translationResultTextView.setText(translationResult);

        closeButton = (ImageView) findViewById(R.id.popupCloseButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
