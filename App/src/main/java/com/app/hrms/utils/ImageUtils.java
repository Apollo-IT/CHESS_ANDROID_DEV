package com.app.hrms.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 7/23/2016.
 */

public class ImageUtils {
    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static Bitmap fixBitmap(String filePath) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        Bitmap fixedBitmap = rotateBitmap(bitmap, orientation);

        int width = fixedBitmap.getWidth();
        int height = fixedBitmap.getHeight();
        int maxLength = width;
        if(maxLength < height) {
            maxLength = height;
        }

        float scale = 160.0f / maxLength;
        int newWidth = (int)(width * scale);
        int newHeight = (int)(height * scale);

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(fixedBitmap, newWidth, newHeight, true);
        return resizedBitmap;
        /*try {
            FileOutputStream out = new FileOutputStream(filePath);
            fixedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public static Bitmap photoBitmap(String filePath) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        Bitmap fixedBitmap = rotateBitmap(bitmap, orientation);

        int width = fixedBitmap.getWidth();
        int height = fixedBitmap.getHeight();
        int maxLength = width;
        if(maxLength < height) {
            maxLength = height;
        }

        float scale = 640.0f / maxLength;
        int newWidth = (int)(width * scale);
        int newHeight = (int)(height * scale);

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(fixedBitmap, newWidth, newHeight, true);
        return resizedBitmap;
        /*try {
            FileOutputStream out = new FileOutputStream(filePath);
            fixedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertToBase64(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] b = stream.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public static String convertToBase64(ByteArrayOutputStream stream) {
        byte[] b = stream.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap convertToBitmap(String base64_str) {
        byte[] b = Base64.decode(base64_str, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        return bitmap;
    }
}
