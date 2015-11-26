package com.shitu.www.testcamera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity {

    private CameraView cv;
    private Camera mCamera = null;
    private Bitmap mBitmap = null;

    public Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        public void OnPictureTaken(byte[] data, Camera camera) {
            Log.i("TestCamera", "OnPictureTaken");

            Toast.makeText(getApplicationContext(), "Saving...", Toast.LENGTH_LONG).show();

            mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

            File imgFile = new File("/sdcard/TestCamera/" + new DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg");
            try {
                imgFile.createNewFile();
                BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(imgFile));
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                Toast.makeText(getApplicationContext(), "Saving Completed in <TestCamera> folder of SDCard", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        FrameLayout fl = new FrameLayout(this);

        cv = new CameraView(this);
        fl.addView(cv);

        TextView tv = new TextView(this);
        tv.setText("Please Press \"Camera\" Button");
        fl.addView(tv);

        SetContentView(fl);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_CAMERA && null != mCamera) {
            mCamera.takePicture(null, null, pictureCallback);
        }

        return  cv.KeyEvent(keyCode, event);
    }

    class CameraView extends SurfaceView {
        private SurfaceHolder holder = null;

        public CameraView(Context context) {
            super(context);

            holder = this.getHolder();
            holder.addCallback(new SurfaceHolder.Callback() {
                @override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    mCamera.stopPreview();
                    mCamera.release();
                    mCamera = null;
                }

                @override
                public void surfaceCreated(SurfaceHolder holder) {
                    mCamera = Camera.open();
                    try {
                        mCamera.setPreviewDisplay(holder);
                    } catch (IOException e) {
                        mCamera.release();
                        mCamera = null;
                    }
                }

                @override
                public void surfaceChanged(SurfaceHolder holder, int formate, int width, int height) {
                    Camera.Paramters param = mCamera.getParamters();
                    param.setPictureFormat(PixelFormat.JPEG);
                    param.setPreviewSize(854, 450);
                    param.setFocusMode("auto");
                    param.setPictureSize(2592, 1456);
                    mCamera.setParameters(param);
                    mCamera.startPreview();
                }
            });

            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
    }

}
