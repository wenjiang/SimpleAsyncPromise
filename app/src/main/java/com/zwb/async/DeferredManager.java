package com.zwb.async;

import android.support.annotation.NonNull;

import com.zwb.simpleasyncpromise.Promise;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by zwb on 2015/7/8.
 */
public interface DeferredManager {
    public static enum StartPolicy {
        DEFAULT, AUTO, MANAUL
    }

    public abstract Promise<ResultSet, SingleReject, BaseProgress> when(@NonNull Callable<?>... callables);

    public abstract <T> Promise<T, Throwable, Void> when(@NonNull Callable<T> callable);

    public abstract Promise<ResultSet, SingleReject, BaseProgress> when(@NonNull Promise... promises);

    public abstract <T> Promise<T, Throwable, Void> when(@NonNull Future<T> future);

    public abstract <T, K, P> Promise<T, K, P> when(@NonNull Promise<T, K, P> promise);

    public abstract Promise<Void, Throwable, Void> when(@NonNull Runnable runnable);

    public abstract <P> Promise<Void, Throwable, P> when(@NonNull DeferredRunnable<P> runnable);

    public abstract <T, P> Promise<T, Throwable, P> when(@NonNull DeferredCallable<T, P> callable);

    public abstract <T, P> Promise<T, Throwable, P> when(@NonNull DeferredFutureTask<T, P> task);

    public abstract Promise<ResultSet, SingleReject, BaseProgress> when(@NonNull Runnable... runnables);

    public abstract Promise<ResultSet, SingleReject, BaseProgress> when(@NonNull DeferredRunnable<?>... runnables);

    public abstract Promise<ResultSet, SingleReject, BaseProgress> when(@NonNull DeferredCallable<?, ?>... callables);

    public abstract Promise<ResultSet, SingleReject, BaseProgress> when(@NonNull Future<?>... futures);

    public abstract Promise<ResultSet, SingleReject, BaseProgress> when(DeferredFutureTask<?, ?>... tasks);
}

