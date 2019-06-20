package utils;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.support.annotation.DrawableRes;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.WindowManager;

import java.io.InputStream;

public class BitmapUtils {


    public static boolean isLandScreen(Context context) {
        int ori =context.getResources().getConfiguration().orientation;//获取屏幕方向
        return ori == Configuration.ORIENTATION_LANDSCAPE;
    }

    //压缩图片
    public static Bitmap decodeSampleBitMapFromFile(Context context, String filePath, float sampleScale) {

        //先得到bitmap的高宽
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        //再用屏幕一半高宽、缩小后的高宽对比，取小值进行缩放
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int reqWidth = wm.getDefaultDisplay().getWidth();
        int reqHeight = wm.getDefaultDisplay().getWidth();
        int scaleWidth = (int) (options.outWidth * sampleScale);
        int scaleHeight = (int) (options.outHeight * sampleScale);
        reqWidth = Math.min(reqWidth, scaleWidth);
        reqHeight = Math.min(reqHeight, scaleHeight);
        options = sampleBitmapOptions(context, options, reqWidth, reqHeight);//进行图片的尺寸压缩实现节约内存
        Bitmap bm = BitmapFactory.decodeFile(filePath, options);//decodeFile从本地读取照片

       // Log.e("xxx", bm.getByteCount() + "");
        return bm;
    }
    public static Bitmap decodeSampleBitMapFromResource(Context context, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        options = sampleBitmapOptions(context, options, reqWidth, reqHeight);
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), resId, options);
        Log.e("xxx", bm.getByteCount() + "");
        return bm;
    }

    //打水印式压缩
    public static Bitmap createBitmapThumbnail(Bitmap bitMap, boolean needRecycle, int newHeight, int newWidth) {
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        // 计算缩放比例
        float scale = Math.min((float) newWidth / width, (float) (newHeight) / height);
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        // 得到新的图片
        Bitmap newBitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true);
        if (needRecycle)
            bitMap.recycle();
        return newBitMap;
    }

    //进行图片的尺寸压缩实现节约内存
    public static BitmapFactory.Options sampleBitmapOptions(
            Context context, BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int targetDensity = context.getResources().getDisplayMetrics().densityDpi;
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);//计算压缩尺寸
        double xSScale = ((double) options.outWidth) / ((double) reqWidth);
        double ySScale = ((double) options.outHeight) / ((double) reqHeight);

        double startScale = xSScale > ySScale ? xSScale : ySScale;

        options.inScaled = true;
        options.inDensity = (int) (targetDensity * startScale);
        options.inTargetDensity = targetDensity;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return options;
    }

    //计算压缩尺寸
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    //从Assets读取位图
    public static Bitmap getBitmapFromAssets(Context context, String path){
        InputStream open = null;//通过这个输入流，就可以把数据从硬盘，读取到Java的虚拟中来，也就是读取到内存中
        Bitmap bitmap = null;
        try {
            String temp =  path;
            open = context.getAssets().open(temp);//打开文件
            BitmapFactory.Options options = new BitmapFactory.Options();//Bitmap的高效使用--BitmapFactory.options
            options = sampleBitmapOptions(context, options, 10, 10);//进行图片的尺寸压缩实现节约内存
            bitmap = BitmapFactory.decodeStream(open, null, options);//decodeStream设置一张大图
            return bitmap;
        } catch (Exception e) {e.printStackTrace();
            return null;
        }
    }

    /**
     * 从矢量图SVG获取位图bitmap
     * 需要配置以下代码
     * android {
     *     defaultConfig {
     *         vectorDrawables.useSupportLibrary = true
     *     }
     *  }
     * @param context
     * @param drawableId
     * @return
     */
    public static Bitmap getBitmapFromDrawable(Context context, @DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawable || drawable instanceof VectorDrawableCompat) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

            return bitmap;
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }

}
