package android.support.v4.view;

import android.view.View;

/**
 * Implementors of this interface can add themselves as update listeners
 * to an <code>ViewPropertyAnimatorCompat</code> instance to receive callbacks on every animation
 * frame, after the current frame's values have been calculated for that
 * <code>ViewPropertyAnimatorCompat</code>.
 */
public interface ViewPropertyAnimatorUpdateListener {

    /**
     * <p>Notifies the occurrence of another frame of the animation.</p>
     *
     * @param view The view associated with the ViewPropertyAnimatorCompat
     */
    void onAnimationUpdate(View view);

}
