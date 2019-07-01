package utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import seekbar.ggh.com.file.bean.MMM;
import seekbar.ggh.com.file.bean.Material;
import seekbar.ggh.com.file.bean.Video;


public class VideoUtils {

    /**
     * 获取本地所有的视频
     *
     * @return list
     */
    public static List<Video> getAllVideo(Context context) {
        List<Video> list = null;
        if (context != null) {
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null,
                    null, null);
            if (cursor != null) {
                list = new ArrayList<Video>();
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                    String title = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                    String album = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM));
                    String artist = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));
                    String displayName = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                    String mimeType = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                    String path = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                    long duration = cursor
                            .getInt(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                    long size = cursor
                            .getLong(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                    Video video = new Video();
                    video.setName(title);
                    video.setSize(size);
                    video.setPath(path);
                    video.setDuration(duration);
                    list.add(video);
                }
                cursor.close();
            }
        }
        return list;
    }
    // 获取视频缩略图
    public static Bitmap getVideoThumbnail(int id) {
        ContentResolver mContentResolver = null;
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bitmap = MediaStore.Video.Thumbnails.getThumbnail(mContentResolver, id, MediaStore.Images.Thumbnails.MICRO_KIND, options);
        return bitmap;
    }
//    public static List<Material> getAllLocalVideos(Context context, int uid) {
//        long totalUploadCount = MPSManager.getInstance(context).getMpsRecordCount(uid) + 1000;
//        String[] projection = {
//                MediaStore.Video.Media.DATA,
//                MediaStore.Video.Media.DISPLAY_NAME,
//                MediaStore.Video.Media.DURATION,
//                MediaStore.Video.Media.SIZE
//        };
//        //全部图片
//        String where = MediaStore.Images.Media.MIME_TYPE + "=? or "
//                + MediaStore.Video.Media.MIME_TYPE + "=? or "
//                + MediaStore.Video.Media.MIME_TYPE + "=? or "
//                + MediaStore.Video.Media.MIME_TYPE + "=? or "
//                + MediaStore.Video.Media.MIME_TYPE + "=? or "
//                + MediaStore.Video.Media.MIME_TYPE + "=? or "
//                + MediaStore.Video.Media.MIME_TYPE + "=? or "
//                + MediaStore.Video.Media.MIME_TYPE + "=? or "
//                + MediaStore.Video.Media.MIME_TYPE + "=?";
//        String[] whereArgs = {"video/mp4", "video/3gp", "video/aiv", "video/rmvb", "video/vob", "video/flv",
//                "video/mkv", "video/mov", "video/mpg"};
//        List<Material> list = new ArrayList<>();
//        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//                projection, where, whereArgs, MediaStore.Video.Media.DATE_ADDED + " DESC ");
//        if (cursor == null) {
//            return list;
//        }
//        try {
//            while (cursor.moveToNext()) {
//                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)); // 大小
//                if (size < 600 * 1024 * 1024) {//<600M
//                    Material materialBean = new Material();
//                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)); // 路径
//                    long duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)); // 时长
//                    materialBean.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)));
//                    materialBean.setLogo(path);
//                    materialBean.setFilePath(path);
//                    materialBean.setChecked(false);
//                    materialBean.setFileType(2);
//                    materialBean.setFileId(totalUploadCount++);
//                    materialBean.setUploadedSize(0);
//                    materialBean.setTimeStamps(System.currentTimeMillis() + "");
//                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
//                    format.setTimeZone(TimeZone.getTimeZone("GMT+0"));
//                    String t = format.format(duration);
//                    materialBean.setTime(context.getString(R.string.video_len) + t);
//                    materialBean.setFileSize(size);
//                    list.add(materialBean);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            cursor.close();
//        }
//        return list;
//    }
}
