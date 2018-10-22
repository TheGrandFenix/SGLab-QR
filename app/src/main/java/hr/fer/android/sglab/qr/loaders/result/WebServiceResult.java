package hr.fer.android.sglab.qr.loaders.result;

/*
 * Created by lracki on 12.10.2018..
 */

public class WebServiceResult<T> {

    private T result;
    private Exception error;

    public WebServiceResult(T result) {
        this.result = result;
    }

    public WebServiceResult(Exception error) {
        this.error = error;
    }

    public T getResult() {
        return result;
    }

    public Exception getException() {
        return error;
    }
}
