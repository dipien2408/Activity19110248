package com.android.s19110248;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String tien;
    private String laisuat;
    private String kihan;

    private EditText e_tien;
    private EditText e_laisuat;
    private EditText e_kihan;

    private Button btn_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (e_tien.getText().toString().trim() != ""  && e_laisuat.getText().toString().trim() != ""
                        && e_kihan.getText().toString().trim() != ""){

                    tien = e_tien.getText().toString().trim();
                    laisuat = e_laisuat.getText().toString().trim();
                    kihan = e_kihan.getText().toString().trim();

                    Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                    intent.putExtra("tien", tien);
                    intent.putExtra("laisuat", laisuat);
                    intent.putExtra("kihan", kihan);

                    startActivity(intent);

                }else {

                    Toast.makeText(getApplicationContext(), "Please input again !", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void init(){

        Bundle extras = getIntent().getExtras();

        e_laisuat = findViewById(R.id.laiSuat);
        e_tien = findViewById(R.id.Tien);
        e_kihan = findViewById(R.id.kiHan);

        btn_main = findViewById(R.id.btn);

        if(extras != null){
            tien = extras.getString("tien");
            laisuat = extras.getString("laisuat");
            kihan = extras.getString("kihan");

            e_tien.setText(tien);
            e_laisuat.setText(laisuat);
            e_kihan.setText(kihan);

        }
    }
}