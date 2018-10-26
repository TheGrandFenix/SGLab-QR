package hr.fer.android.sglab.qr.utils;

/*
 * Created by lracki on 26.10.2018..
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;

@SuppressLint({"HandlerLeak"})
public class TimeoutHandler {
    public static final String EVENT_TIMEOUT = ".EVENT_ON_TIMEOUT";

    private static final int HANDLER_TIMEOUT_MESSAGE = 12345;

    private TimeoutHandler.TimeoutListener timeoutListener = null;
    private Handler timeoutHandler = null;
    private final Context context;

    private boolean timeoutHandled = false;
    private long lastTimer;
    private long timeoutInMiliSeconds;
    private String timeoutAction;

    public TimeoutHandler(Context context, String timeoutAction, TimeoutHandler.TimeoutListener listener) {
        lastTimer = SystemClock.elapsedRealtime();
        this.context = context.getApplicationContext();
        timeoutInMiliSeconds = 5000;
        this.timeoutAction = timeoutAction;
        timeoutListener = listener;

        timeoutHandler = new Handler() {
            public void handleMessage(Message msg) {
                synchronized(TimeoutHandler.this) {
                    handleTimeout();
                }
            }
        };

        timeoutHandler.sendEmptyMessageDelayed(HANDLER_TIMEOUT_MESSAGE, timeoutInMiliSeconds);
    }

    private synchronized void handleTimeout() {
        if(!isTimeoutHandled()) {
            timeoutHandled = true;

            Intent intent = new Intent(timeoutAction);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            if(timeoutListener != null) {
                timeoutListener.onTimeout();
            }

        }
    }

    private boolean isTimeoutHandled() {
        return timeoutHandled;
    }

    @SuppressLint({"HandlerLeak"})
    public void resetTimer() {
        synchronized(this) {
            timeoutHandled = false;
        }

        lastTimer = SystemClock.elapsedRealtime();
        timeoutHandler.removeMessages(HANDLER_TIMEOUT_MESSAGE);
        timeoutHandler.sendEmptyMessageDelayed(HANDLER_TIMEOUT_MESSAGE, timeoutInMiliSeconds);
    }

    public interface TimeoutListener {
        void onTimeout();
    }
}

