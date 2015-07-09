package com.zwb.async;

import java.util.concurrent.Callable;

/**
 * Created by zwb on 2015/7/9.
 */
public abstract class DeferredCallable<T, P> implements Callable<T> {
    private final Deferred<T, Throwable, P> deferred = new DeferredObject<>();
    private final DeferredManager.StartPolicy startPolicy;

    public DeferredCallable() {
        this.startPolicy = DeferredManager.StartPolicy.DEFAULT;
    }

    public DeferredCallable(DeferredManager.StartPolicy startPolicy) {
        this.startPolicy = startPolicy;
    }

    protected Deferred<T, Throwable, P> getDeferred() {
        return deferred;
    }

    public DeferredManager.StartPolicy getStartPolicy() {
        return startPolicy;
    }

    public void notify(P progress) {
        deferred.notify(progress);
    }
}
