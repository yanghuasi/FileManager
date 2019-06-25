package utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import seekbar.ggh.com.file.bean.FileBean;

public class OtherFileUtils {
    /**
     * 通过文件类型得到相应文件的集合
     **/
    public List<FileBean> getFilesByType(int fileType) {
        List<FileBean> files = new ArrayList<FileBean>();
        // 扫描files文件库
        Cursor c = null;
        try {
            Context context = null;
            ContentResolver mContentResolver = context.getContentResolver();
            c = mContentResolver.query(MediaStore.Files.getContentUri("external"), new String[]{"_id", "_data", "_size"}, null, null, null);
            int dataindex = c.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            int sizeindex = c.getColumnIndex(MediaStore.Files.FileColumns.SIZE);

            while (c.moveToNext()) {
                String path = c.getString(dataindex);

                if (FileManager.getFileType(path) == fileType) {
                    if (!FileManager.isExists(path)) {
                        continue;
                    }
                    long size = c.getLong(sizeindex);
                    FileBean fileBean = new FileBean(path, FileManager.getFileIconByPath(path));
                    files.add(fileBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return files;
    }
}
