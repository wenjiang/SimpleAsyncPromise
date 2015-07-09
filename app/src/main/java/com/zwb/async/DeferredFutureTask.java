package com.zwb.async;

import com.zwb.simpleasyncpromise.Promise;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by zwb on 2015/7/9.
 */
public class DeferredFutureTask<T, P> extends FutureTask<T> {
    protected final Deferred<T, Throwable, P> deferred;
    protected final DeferredManager.StartPolicy startPolicy;

    public DeferredFutureTask(Callable<T> callable) {
        super(callable);

        this.deferred = new DeferredObject<>();
        this.startPolicy = DeferredManager.StartPolicy.DEFAULT;
    }

    @Override
    protected void done() {
        try {
            if (isCancelled()) {
                deferred.reject(new CancellationException());
            }

            T result = get();
            deferred.resolve(result);
        } catch (InterruptedException e) {

        } catch (ExecutionException e) {
            deferred.reject(e.getCause());
        }
    }

    public DeferredFutureTask(Runnable runnable) {
        super(runnable, null);

        this.deferred = new DeferredObject<>();
        this.startPolicy = DeferredManager.StartPolicy.DEFAULT;
    }

    public DeferredFutureTask(DeferredCallable<T, P> callable) {
        super(callable);

        this.deferred = callable.getDeferred();
        this.startPolicy = callable.getStartPolicy();
    }

    public DeferredFutureTask(DeferredRunnable<P> runnable) {
        super(runnable, null);

        this.deferred = (Deferred<T, Throwable, P>) runnable.getDeferred();
        this.startPolicy = runnable.getStartPolicy();
    }

    public Promise<T, Throwable, P> promise() {
        return deferred.promise();
    }

    public DeferredManager.StartPolicy getStartPolicy() {
        return startPolicy;
    }
}
