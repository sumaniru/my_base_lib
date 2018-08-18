package com.lhd.base.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.lhd.base.data.OtherStatus;

/**
 * Created by arutoria on 2017/7/1.
 */

public class ChooseImgUtil {

    private Activity context;

    public ChooseImgUtil(Activity context) {
        this.context = context;
    }

    public void chooseImg(int requestCode) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    OtherStatus.PERMISSION_WRITE_EXTERNAL_STORAGE);
        } else {
            Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
            intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
            context.startActivityForResult(intent, requestCode);
        }
    }


    public void chooseVideo(int requestCode) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    OtherStatus.PERMISSION_WRITE_EXTERNAL_STORAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            context.startActivityForResult(intent, requestCode);
        }
    }

//    public void chooseDraw(int size, int requestCode) {
//        if (ContextCompat.checkSelfPermission(context,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(context,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    OtherStatus.PERMISSION_WRITE_EXTERNAL_STORAGE);
//        } else {
//            Picker.from(context).count(size).enableCamera(false)
//                    .setEngine(new ImageLoaderEngine())
//                    .forResult(requestCode);
//        }
//    }

}
