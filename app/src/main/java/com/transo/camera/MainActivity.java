package com.transo.camera;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final File SDDIR = Environment.getExternalStorageDirectory();
    public static final String ROOTPATH = SDDIR.getAbsolutePath() + File.separator + "mAudit" + File.separator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn_take_photo).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CameraActivityForImage.class);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(ROOTPATH + System.currentTimeMillis() + ".jpg")));
            startActivityForResult(intent, 100);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Toast toast = Toast.makeText(MainActivity.this, "PHOTO SAVED", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }
    }
}
