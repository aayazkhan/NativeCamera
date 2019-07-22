package com.transo.camera.Utils;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtils {
    /**
     * Saves a Bitmap object to disk for analysis.
     *
     * @param bitmap   The bitmap to save.
     * @param filename The location to save the bitmap to.
     */
    public static void saveBitmap(final Bitmap bitmap, final String filename) {
        System.out.println("Saving " + bitmap.getWidth() + "x" + bitmap.getHeight() + " bitmap to " + filename + ".");

        final File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }

        try {
            final FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 99, out);
            out.flush();
            out.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

}
