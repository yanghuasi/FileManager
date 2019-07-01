package utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import seekbar.ggh.com.file.bean.Material;
import seekbar.ggh.com.file.manager.ImgFolderBean;

import static android.content.ContentValues.TAG;

public class PhotoUtils {
    /**
     * 获取sd卡所有的图片文件
     *
     * @return
     * @throws
     */
    public static List<String> getSystemPhotoList(Context context)
    {
        List<String> result = new ArrayList<String>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null || cursor.getCount() <= 0) return null; // 没有图片
        while (cursor.moveToNext())
        {
            int index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String path = cursor.getString(index); // 文件地址
            File file = new File(path);
            if (file.exists())
            {
                result.add(path);
                Log.i(TAG, path);
            }
        }

        return result ;
    }
    /**
     * 得到图片文件夹集合
     */
    public static List<ImgFolderBean> getImageFolders(Context context) {
        List<ImgFolderBean> folders = new ArrayList<ImgFolderBean>();
        // 扫描图片
        Cursor c = null;
        try {
            ContentResolver mContentResolver = context.getContentResolver();
             c = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                    MediaStore.Images.Media.MIME_TYPE + "= ? or " + MediaStore.Images.Media.MIME_TYPE + "= ?",
                    new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
            List<String> mDirs = new ArrayList<String>();//用于保存已经添加过的文件夹目录
            while (c.moveToNext()) {
                String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));// 路径
                File parentFile = new File(path).getParentFile();
                if (parentFile == null)
                    continue;

                String dir = parentFile.getAbsolutePath();
                if (mDirs.contains(dir))//如果已经添加过
                    continue;

                mDirs.add(dir);//添加到保存目录的集合中
                ImgFolderBean folderBean = new ImgFolderBean();
                folderBean.setDir(dir);
                folderBean.setFistImgPath(path);
                if (parentFile.list() == null)
                    continue;
                int count = parentFile.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        if (filename.endsWith(".jpeg") || filename.endsWith(".jpg") || filename.endsWith(".png")) {
                            return true;
                        }
                        return false;
                    }
                }).length;

                folderBean.setCount(count);
                folders.add(folderBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return folders;
    }
    /**
     * 获取本地所有的图片
     *
     * @return list
     */
//    public static List<Material> getAllLocalPhotos(Context context, int uid) {
//        long totalUploadCount = MPSManager.getInstance(context).getMpsRecordCount(uid) + 1000;
//        List<Material> list = new ArrayList<>();
//        String[] projection = {
//                MediaStore.Images.Media.DATA,
//                MediaStore.Images.Media.DISPLAY_NAME,
//                MediaStore.Images.Media.SIZE
//        };
//        //全部图片
//        String where = MediaStore.Images.Media.MIME_TYPE + "=? or "
//                + MediaStore.Images.Media.MIME_TYPE + "=? or "
//                + MediaStore.Images.Media.MIME_TYPE + "=?";
//        //指定格式
//        String[] whereArgs = {"image/jpeg", "image/png", "image/jpg"};
//        //查询
//        Cursor cursor = context.getContentResolver().query(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, where, whereArgs,
//                MediaStore.Images.Media.DATE_MODIFIED + " desc ");
//        if (cursor == null) {
//            return list;
//        }
//        //遍历
//        while (cursor.moveToNext()) {
//            Material materialBean = new Material();
//            //获取图片的名称
//            materialBean.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
//            long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)); // 大小
//
//            //获取图片的生成日期
//            byte[] data = cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//
//            String path = new String(data, 0, data.length - 1);
//            File file = new File(path);
//
//            if (size < 5 * 1024 * 1024) {//<5M
//                long time = file.lastModified();
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                String t = format.format(time);
//                materialBean.setTime(t);
//                materialBean.setLogo(path);
//                materialBean.setFilePath(path);
//                materialBean.setFileSize(size);
//                materialBean.setChecked(false);
//                materialBean.setFileType(6);
//                materialBean.setFileId(totalUploadCount++);
//                materialBean.setUploadedSize(0);
//                materialBean.setTimeStamps(System.currentTimeMillis() + "");
//                list.add(materialBean);
//            }
//        }
//        cursor.close();
//        return list;
//    }

    /**是否是图片文件*/
    public static boolean isPicFile(String path){
        path = path.toLowerCase();
        if (path.endsWith(".jpg") || path.endsWith(".jpeg") || path.endsWith(".png")){
            return true;
        }
        return false;
    }

    public static boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png")
                || FileEnd.equals("jpeg") || FileEnd.equals("bmp")|| FileEnd.equals("gif")) {

            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }
}
