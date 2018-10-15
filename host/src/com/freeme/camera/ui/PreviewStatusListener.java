package com.freeme.camera.ui;

import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.TextureView;
import android.view.View;

public interface PreviewStatusListener extends TextureView.SurfaceTextureListener {
    /**
     * The preview status listener needs to provide an
     * {@link android.view.GestureDetector.OnGestureListener} in order to listen
     * to the touch events that happen on preview.
     *
     * @return a listener that listens to touch events
     */
    public GestureDetector.OnGestureListener getGestureListener();

    /**
     * An {@link android.view.View.OnTouchListener} can be provided in addition to
     * or instead of a {@link android.view.GestureDetector.OnGestureListener}
     * for listening to touch events on the preview.  The listener is called whenever
     * there is a touch event on the {@link com.freeme.camera.ui.PreviewOverlay}.
     */
    public View.OnTouchListener getTouchListener();

    /**
     * Gets called when preview TextureView gets a layout change call.
     */
    public void onPreviewLayoutChanged(View v, int left, int top, int right,
                                       int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom);

    /**
     * This listener gets notified when the actual preview frame changes due
     * to a transform matrix being applied to the TextureView
     */
    public interface PreviewAreaChangedListener {
        public void onPreviewAreaChanged(RectF previewArea);
    }

    /**
     * The preview status listener needs to know for the specific module whether
     * preview TextureView should automatically adjust its transform matrix based
     * on the current aspect ratio, width and height of the TextureView.
     *
     * @return whether transform matrix should be automatically adjusted
     */
    public boolean shouldAutoAdjustTransformMatrixOnLayout();

    /**
     * The preview status listener needs to know for the specific module whether
     * bottom bar should be automatically adjusted when preview has changed size
     * or orientation.
     *
     * @return whether bottom bar should be automatically adjusted
     */
    public boolean shouldAutoAdjustBottomBar();

    /**
     * Gets called when the preview is flipped (i.e. 180-degree rotated).
     */
    public void onPreviewFlipped();

    /**
     * This listener gets notified when the preview aspect ratio is changed.
     */
    public interface PreviewAspectRatioChangedListener {
        public void onPreviewAspectRatioChanged(float aspectRatio);
    }
}
