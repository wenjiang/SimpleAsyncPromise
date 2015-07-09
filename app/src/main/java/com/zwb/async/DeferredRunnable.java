package com.zwb.async;

/**
 * Created by zwb on 2015/7/9.
 */
public abstract class DeferredRunnable<P> implements Runnable {
    private final Deferred<Void, Throwable, P> deferred = new DeferredObject<>();
    private final DeferredManager.StartPolicy startPolicy;

    public DeferredRunnable() {
        this.startPolicy = DeferredManager.StartPolicy.DEFAULT;
    }

    public DeferredRunnable(DeferredManager.StartPolicy startPolicy) {
        this.startPolicy = startPolicy;
    }

    protected void notify(P progress) {
        deferred.notify(progress);
    }

    protected Deferred<Void, Throwable, P> getDeferred() {
        return deferred;
    }

    public DeferredManager.StartPolicy getStartPolicy() {
        return startPolicy;
    }
}