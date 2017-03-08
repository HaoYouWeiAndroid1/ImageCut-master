package com.yipeng.imagecut;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button butCamera;
    private Button butAlbum;
    private String picname;
    private File mPicFile;
    public static final int NONE = 0;
    public static final int PHOTOHRAPH = 1;// 拍照
    public static final int PHOTORESOULT = 3;// 结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butAlbum = (Button) findViewById(R.id.but_album);
        butCamera = (Button) findViewById(R.id.but_camera);
        butCamera.setOnClickListener(this);
        butAlbum.setOnClickListener(this);
        getInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_album:
                gralleryUpload();
                break;
            case R.id.but_camera:
                cameraUpload();
                break;
        }
    }

    // 本地相册选择
    protected void gralleryUpload() {
        UUID uuid = UUID.randomUUID();
        picname = uuid.toString() + ".jpg";
        mPicFile = new File(Environment.getExternalStorageDirectory(), picname);

        Intent getAlbum = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum, PHOTORESOULT);
    }

    // 照相机
    protected void cameraUpload() {
        PackageManager pm = getPackageManager();
        UUID uuid1 = UUID.randomUUID();
        picname = uuid1.toString() + ".jpg";
        mPicFile = new File(Environment.getExternalStorageDirectory(), picname);
        Intent cIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPicFile));
        startActivityForResult(cIntent, PHOTOHRAPH);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == NONE) {
            return;
        }
        String path = "";
        if (requestCode == PHOTORESOULT) {
            Uri originalUri = data.getData();        //获得图片的uri
            String[] proj = {MediaStore.Images.Media.DATA};
            //好像是android多媒体数据库的封装接口，具体的看Android文档
            Cursor cursor = managedQuery(originalUri, proj, null, null, null);
            //按我个人理解 这个是获得用户选择的图片的索引值
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            //将光标移至开头 ，这个很重要，不小心很容易引起越界
            cursor.moveToFirst();
            //最后根据索引值获取图片路径
            path = cursor.getString(column_index);
        }
        if (requestCode == PHOTOHRAPH) {
            path = mPicFile.getAbsolutePath();
        }
        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra("PicFile", path);
        startActivity(intent);
        Log.e("LOG", "文件的路径：" + path + ";文件大小=" + FileSizeUtil.getFileOrFilesSize(path, FileSizeUtil.SIZETYPE_MB));
    }


    public static Bitmap getThumbnail(Context mContext, Uri uri, int size) throws FileNotFoundException, IOException {
        InputStream input = mContext.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_4444;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;
        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;
        double ratio = (originalSize > size) ? (originalSize / size) : 1.0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true;//optional
        bitmapOptions.outHeight = (int) (onlyBoundsOptions.outHeight / ratio);
        bitmapOptions.outWidth = (int) (onlyBoundsOptions.outWidth / ratio);
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;//optional
        input = mContext.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio) {
        int k = Integer.highestOneBit((int) Math.floor(ratio)) + 1;
        if (k == 0) {
            return 1;
        } else {
            return k;
        }
    }

    private void getInfo() {
        RetrofitUtils.getApiInstance().getInfo("4df6fd90908852c679558c4e2d5a6ffd", "1", "1")
                .enqueue(new RetrofitUtils.ApiCallback<aaaa>() {
            @Override
            public void onResponse(Response<aaaa> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Toast.makeText(getApplicationContext(), response.message(), 1).show();
                }
            }
        });
    }
}
