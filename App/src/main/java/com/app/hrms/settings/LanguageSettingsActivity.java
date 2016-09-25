package com.app.hrms.settings;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.app.hrms.R;


public class LanguageSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_language);

        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText(R.string.select_language);

        final TextView txtLanguage = (TextView)findViewById(R.id.txtLanguage);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.btnSelectLang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence langs[] = new CharSequence[] {
                        getResources().getString(R.string.simple_chinese),
                        getResources().getString(R.string.english)
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(LanguageSettingsActivity.this);
                builder.setItems(langs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtLanguage.setText(langs[which]);
                    }
                });
                builder.show();
            }
        });
    }
}
