package simonhkswan.uploadapp;

import android.graphics.Bitmap;

public class Bitman{
    public static int[] getPixelSubset(Bitmap bitmap, int x, int y, int width, int height) {
        int[] pixels = new int[bitmap.getHeight()*bitmap.getWidth()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), x, y, width, height);
        int[] pixelSubset = new int[width*height];
        for (int i = 0; i < height; i++) {
            System.arraycopy(pixels, i*bitmap.getWidth(), pixelSubset, i*width, width);
        }
        return pixelSubset;
    }
}
