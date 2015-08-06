package com.regfacu.utils.emptylistener;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;

/**
 * @author Facundo Mengoni.
 * @author mengonifacundo@gmail.com
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class EmptyAnimatorListener implements Animator.AnimatorListener {
    @Override
    public void onAnimationStart(Animator animator) {
    }

    @Override
    public void onAnimationEnd(Animator animator) {
    }

    @Override
    public void onAnimationCancel(Animator animator) {
    }

    @Override
    public void onAnimationRepeat(Animator animator) {
    }
}
