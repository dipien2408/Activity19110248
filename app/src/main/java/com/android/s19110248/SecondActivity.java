package com.android.s19110248;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.android.s19110248.databinding.ActivitySecondBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class SecondActivity extends AppCompatActivity {

    String tien;
    String laisuat;
    String kihan;

    private Button btnBack;
    private Button btnResult;

    private TextView tienlai;
    private TextView tongtien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        init();
        btnResult = findViewById(R.id.btnResult);
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screenShot();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("tien", String.valueOf(tien));
                intent.putExtra("laisuat", String.valueOf(laisuat));
                intent.putExtra("kihan", String.valueOf(kihan));
                startActivity(intent);
            }
        });
    }

    public void init(){
        tienlai = findViewById(R.id.tienlai);
        tongtien = findViewById(R.id.tongtien);

        compute();
    }
    public void compute(){

        //Số tiền lãi = Số tiền gửi x lãi suất (%/năm) x số ngày thực gửi/360

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            tien = extras.getString("tien");
            laisuat = extras.getString("laisuat");
            kihan = extras.getString("kihan");

            if(tien == null && laisuat == null && kihan == null){
                tienlai.setText("Nhập lại !");
                tongtien.setText(String.valueOf(0.000));
                return;
            }
            double n_tien = Double.parseDouble(tien);
            double n_laisuat = Double.parseDouble(laisuat);
            double n_kihan = Double.parseDouble(kihan);
            double n_tienlai = (n_tien * (n_laisuat / 100) * (n_kihan * 30)) / 360;
            tienlai.setText(String.valueOf(n_tienlai));
            tongtien.setText(String.valueOf(n_tienlai + n_tien));
        }
    }

    private void screenShot() {
        Date date = new Date();
        CharSequence now = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);
        String filename = Environment.getExternalStorageDirectory() + "/ScreenShooter/" + now + ".jpg";
        View root = getWindow().getDecorView();
        root.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(root.getDrawingCache());
        root.setDrawingCacheEnabled(false);

        File file = new File(filename);
        file.getParentFile().mkdirs();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "image/*");
            startActivity(intent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 121){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}