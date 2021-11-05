package entity;

import android.graphics.Bitmap;

public class FrontPoster {
    private Bitmap bitmap;
    private String title;

    public FrontPoster(Bitmap bitmap, String title) {
        this.title = title;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getTitle() {
        return title;
    }
}