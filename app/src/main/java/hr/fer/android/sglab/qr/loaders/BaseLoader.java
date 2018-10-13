package hr.fer.android.sglab.qr.loaders;

/*
 * Created by lracki on 12.10.2018..
 */

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

abstract class BaseLoader<T> extends AsyncTaskLoader<T> {

    T result;

    BaseLoader(Context context) {
        super(context);
    }

    protected void onStartLoading() {
        if(result == null) {
            forceLoad();
        } else {
            deliverResult(null);
        }
    }

    protected void onReset() {
        result = null;
        super.onReset();
    }
}
