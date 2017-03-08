package com.yipeng.imagecut;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ImageActivity extends Activity implements View.OnClickListener {
    private ClipImageLayout mClipImageLayout;
    private Button butConfirm;
    private Button butCancel;
    private String mPhat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);

        butCancel = (Button) findViewById(R.id.but_cancel);
        butCancel.setOnClickListener(this);
        butConfirm = (Button) findViewById(R.id.but_confirm);
        butConfirm.setOnClickListener(this);
        mPhat = getIntent().getStringExtra("PicFile");
        try {
            mClipImageLayout.setImage(mPhat);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_confirm:
                Bitmap bitmap = mClipImageLayout.clip();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] datas = baos.toByteArray();

                Intent intent = new Intent(this, ShowImageActivity.class);
                intent.putExtra("bitmap", datas);
                startActivity(intent);
                break;
            case R.id.but_camera:
                finish();
                break;
        }
    }
}
