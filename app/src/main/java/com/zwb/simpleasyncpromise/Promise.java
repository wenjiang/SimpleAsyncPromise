package com.zwb.simpleasyncpromise;

/**
 * Created by zwb on 2015/7/8.
 */
public interface Promise<T, K, P> {
    public enum State {
        PENDING, REJECTED, RESOLVED
    }

    public State getState();

    public boolean isPending();

    public boolean isResolved();

    public boolean isRejected();

    public Promise<T, K, P> then(DoneCallback<T> doneCallback);

    public Promise<T, K, P> then(DoneCallback<T> doneCallback, FailCallback<K> failCallback);

    public Promise<T, K, P> then(DoneCallback<T> doneCallback, FailCallback<K> failCallback, ProgressCallback<P> progressCallback);

    public Promise<T, K, P> done(DoneCallback<T> callback);

    public Promise<T, K, P> fail(FailCallback<K> failCallback);

    public Promise<T, K, P> always(AlwaysCallback<T, K> callback);

    public Promise<T, K, P> progress(ProgressCallback<P> callback);

    public void waitSafely(long timeout) throws InterruptedException;
}