package hr.fer.android.sglab.qr.widgets;

/*
 * Created by lracki on 11.10.2018..
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import hr.fer.android.sglab.qr.R;

public class CustomLoadingProgressAnimation extends ProgressDialog {

    private Context context;

    public CustomLoadingProgressAnimation(final Context context) {
        super(context, R.style.ProgressDialogTheme);

        this.context = context;

        setCanceledOnTouchOutside(false);
        setIndeterminate(true);
    }

    @Override
    public void show() {
        super.show();
        setContentView(R.layout.dialog_progress);

        FrameLayout layout = findViewById(R.id.container);

        LayoutInflater inflater = LayoutInflater.from(context);
        RelativeLayout progress = (RelativeLayout) inflater.inflate(R.layout.progress_bar_custom, null);
        layout.addView(progress);

        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(1400);
        findViewById(R.id.logo_circle1).startAnimation(anim);

        RotateAnimation anim2 = new RotateAnimation(0.0f, -360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim2.setInterpolator(new LinearInterpolator());
        anim2.setRepeatCount(Animation.INFINITE);
        anim2.setDuration(3000);
        findViewById(R.id.logo_circle2).startAnimation(anim2);

        RotateAnimation anim3 = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim3.setInterpolator(new LinearInterpolator());
        anim3.setRepeatCount(Animation.INFINITE);
        anim3.setDuration(2200);
        findViewById(R.id.logo_circle3).startAnimation(anim3);
    }
}
