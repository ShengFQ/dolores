package com.dolores.store.util;

import com.dolores.store.DoloresApplication;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sheng on 18/4/20.
 */

public class FileUtils {
    public static final String IMAGE_DIR_NAME = "Images";
    public static String IMAGE_PATH=OSUtils.getExternalCacheDir(DoloresApplication.getmApplicationContext(),IMAGE_DIR_NAME).getAbsolutePath()+ File.separator;
    // 用当前时间给取得的图片命名
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }
}
