package com.transo.camera;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Size;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;

import com.transo.camera.Utils.CameraActivity;
import com.transo.camera.Utils.ImageUtils;

import java.io.File;

public class CameraActivityForImage extends CameraActivity implements ImageReader.OnImageAvailableListener {

    public static final Size DESIRED_PREVIEW_SIZE = new Size(960, 720);

    private Bitmap rgbFrameBitmap = null;
    private boolean isPersronRecognize = false;

    private RelativeLayout container;
    private AppCompatImageView button;

    private Uri intent_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        container = findViewById(R.id.container);

        savedInstanceState = getIntent().getExtras();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(MediaStore.EXTRA_OUTPUT)) {
                intent_uri = savedInstanceState.getParcelable(MediaStore.EXTRA_OUTPUT);
                System.out.println("intent_uri.getPath():" + intent_uri.getPath());
            } else {
                intent_uri = Uri.fromFile(new File(MainActivity.ROOTPATH + System.currentTimeMillis() + ".jpg"));
            }
        }

        button = findViewById(R.id.capture_button);

        button.setOnClickListener(v -> {
            isPersronRecognize = true;
            ImageUtils.saveBitmap(rgbFrameBitmap, intent_uri.getPath());
            setResult(RESULT_OK);
            finish();
        });


    }

    @Override
    public void onPreviewSizeChosen(final Size size, final int rotation) {
        previewWidth = size.getWidth();
        previewHeight = size.getHeight();

        System.out.println("Initializing at size " + previewWidth + "x" + previewHeight);
        rgbFrameBitmap = Bitmap.createBitmap(previewWidth, previewHeight, Bitmap.Config.ARGB_8888);


    }

    @Override
    protected void processImage() {
        if (isPersronRecognize) {
            return;
        }
        rgbFrameBitmap.setPixels(getRgbBytes(), 0, previewWidth, 0, 0, previewWidth, previewHeight);

        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f);
        rgbFrameBitmap = Bitmap.createBitmap(rgbFrameBitmap, 0, 0, rgbFrameBitmap.getWidth(), rgbFrameBitmap.getHeight(), matrix, true);
        readyForNextImage();

    }


    @Override
    protected int getLayoutId() {
        return R.layout.camera_connection_fragment_tracking;
    }

    @Override
    protected Size getDesiredPreviewFrameSize() {
        return DESIRED_PREVIEW_SIZE;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public synchronized void onDestroy() {
        super.onDestroy();
    }
}
