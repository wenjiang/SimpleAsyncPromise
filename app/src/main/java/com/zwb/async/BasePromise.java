package com.zwb.async;

import android.util.Log;

import com.zwb.simpleasyncpromise.AlwaysCallback;
import com.zwb.simpleasyncpromise.DoneCallback;
import com.zwb.simpleasyncpromise.FailCallback;
import com.zwb.simpleasyncpromise.ProgressCallback;
import com.zwb.simpleasyncpromise.Promise;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by zwb on 2015/7/8.
 */
public class BasePromise<T, K, P> implements Promise<T, K, P> {
    protected volatile State state = State.PENDING;

    protected final List<DoneCallback<T>> doneCallbacks = new CopyOnWriteArrayList<>();
    protected final List<FailCallback<K>> failCallbacks = new CopyOnWriteArrayList<>();
    protected final List<ProgressCallback<P>> progressCallbacks = new CopyOnWriteArrayList<>();
    protected final List<AlwaysCallback<T, K>> alwaysCallbacks = new CopyOnWriteArrayList<>();

    protected T resolveResult;
    protected K rejectResult;

    @Override
    public State getState() {
        return state;
    }

    @Override
    public boolean isPending() {
        return state == State.PENDING;
    }

    @Override
    public boolean isResolved() {
        return state == State.RESOLVED;
    }

    @Override
    public boolean isRejected() {
        return state == State.REJECTED;
    }

    @Override
    public Promise<T, K, P> then(DoneCallback<T> doneCallback) {
        return done(doneCallback);
    }

    @Override
    public Promise<T, K, P> then(DoneCallback<T> doneCallback, FailCallback<K> failCallback) {
        done(doneCallback);
        fail(failCallback);
        return this;
    }

    @Override
    public Promise<T, K, P> then(DoneCallback<T> doneCallback, FailCallback<K> failCallback, ProgressCallback<P> progressCallback) {
        done(doneCallback);
        fail(failCallback);
        progress(progressCallback);
        return this;
    }

    @Override
    public Promise<T, K, P> done(DoneCallback<T> callback) {
        synchronized (this) {
            if (isResolved()) {
                triggerDone(callback, resolveResult);
            } else {
                doneCallbacks.add(callback);
            }
        }
        return this;
    }

    protected void triggerDone(DoneCallback<T> callback, T resolveResult) {
        callback.onDone(resolveResult);
    }

    protected void triggerDone(T resolveResult) {
        for (DoneCallback<T> callback : doneCallbacks) {
            try {
                triggerDone(callback, resolveResult);
            } catch (Exception e) {
                Log.e("BasePromise", "an uncaught exception occured in a DoneCallback");
            }
        }

        doneCallbacks.clear();
    }

    protected void triggerProgress(P progress){
        for(ProgressCallback<P> callback : progressCallbacks){
            try{
                triggerProgress(callback, progress);
            }catch (Exception e){
                Log.e("BasePromise", "an uncaught exception occured in a ProgressCallback");
            }
        }
    }

    protected void triggerProgress(ProgressCallback<P> callback, P progress) {
        callback.onProgress(progress);
    }

    @Override
    public Promise<T, K, P> fail(FailCallback<K> failCallback) {
        synchronized (this) {
            if (isRejected()) {
                triggerFail(failCallback, rejectResult);
            } else {
                failCallbacks.add(failCallback);
            }
        }
        return this;
    }

    protected void triggerFail(FailCallback<K> failCallback, K rejectResult) {
        failCallback.onFail(rejectResult);
    }

    protected void triggerFail(K rejectResult) {
        for (FailCallback<K> callback : failCallbacks) {
            try {
                triggerFail(callback, rejectResult);
            } catch (Exception e) {
                Log.e("BasePromise", e.toString());
            }
        }
    }

    @Override
    public Promise<T, K, P> always(AlwaysCallback<T, K> callback) {
        synchronized (this) {
            if (isPending()) {
                alwaysCallbacks.add(callback);
            } else {
                triggerAlways(callback, state, resolveResult, rejectResult);
            }
        }
        return this;
    }

    protected void triggerAlways(AlwaysCallback<T, K> callback, State state, T resolveResult, K rejectResult) {
        callback.onAlways(state, resolveResult, rejectResult);
    }

    protected void triggerAlways(State state, T resolveResult, K rejectResult) {
        for (AlwaysCallback<T, K> callback : alwaysCallbacks) {
            try {
                triggerAlways(callback, state, resolveResult, rejectResult);
            } catch (Exception e) {
                Log.e("BasePromise", "an uncaught exception occured in a AlwaysCallback");
            }
        }

        alwaysCallbacks.clear();
        synchronized (this) {
            this.notifyAll();
        }
    }

    @Override
    public Promise<T, K, P> progress(ProgressCallback<P> callback) {
        progressCallbacks.add(callback);
        return this;
    }

    @Override
    public void waitSafely(long timeout) throws InterruptedException {
        final long startTime = System.currentTimeMillis();
        synchronized (this) {
            while (this.isPending()) {
                try {
                    if (timeout <= 0) {
                        wait();
                    } else {
                        final long elapsed = System.currentTimeMillis() - startTime;
                        final long waitTime = timeout - elapsed;
                        wait(waitTime);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw e;
                }

                if (timeout > 0 && ((System.currentTimeMillis() - startTime) >= timeout)) {
                    return;
                } else {
                    continue;
                }
            }
        }
    }
}
