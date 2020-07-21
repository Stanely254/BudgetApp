package com.stanley.budgetwatch.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;

public class ScreenshotUtils {
    private static ScreenshotUtils screenshotUtils;

    private ScreenshotUtils(){}

    public static ScreenshotUtils getInstance(){
        if(screenshotUtils == null){
            synchronized (ScreenshotUtils.class){
                if(screenshotUtils == null){
                    screenshotUtils = new ScreenshotUtils();
                }
            }
        }
        return screenshotUtils;
    }

    /**
     * Measures and takes a screenshot of the provided {@link View}.
     *
     * @param view The view of which the screenshot is taken
     * @return A {@link Bitmap} for the taken screenshot.
     */
    public Bitmap takeScreenshotForView(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(view.getHeight(), View.MeasureSpec.EXACTLY));
        view.layout((int) view.getX(), (int) view.getY(), (int) view.getX() + view.getMeasuredWidth(), (int) view.getY() + view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public Bitmap takeScreenshotForScreen(Activity activity) {
        return takeScreenshotForView(activity.getWindow().getDecorView().getRootView());
    }


}
