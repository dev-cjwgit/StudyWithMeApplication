package entity;

import android.graphics.Bitmap;

/**
 * 메인 뷰의 리싸이클러뷰에 표시되는 엔티티 객체
 */
public class FrontPoster {
    private Bitmap bitmap; // 사진
    private String title; // 제목(교과명)

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