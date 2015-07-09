package com.zwb.async;

import android.support.annotation.NonNull;

import com.zwb.simpleasyncpromise.Promise;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by zwb on 2015/7/8.
 */
public abstract class AbstractDeferredManager implements DeferredManager {
    protected abstract void submit(Callable callable);

    protected abstract void submit(Runnable runnable);

    public abstract boolean isAutoSubmit();

    @Override
    public Promise<ResultSet, SingleReject, BaseProgress> when(Callable<?>... callables) {
        Promise[] promises = new Promise[callables.length];

        for (int i = 0, length = callables.length; i < length; i++) {
            promises[i] = when(callables[i]);
        }
        return when(promises);
    }

    @Override
    public <T> Promise<T, Throwable, Void> when(@NonNull Callable<T> callable) {
        return when(new DeferredFutureTask<T, Void>(callable));
    }

    @Override
    public Promise<ResultSet, SingleReject, BaseProgress> when(Promise... promises) {
        return new TriggeredDeferredObject(promises).promise();
    }

    @Override
    public <T> Promise<T, Throwable, Void> when(@NonNull final Future<T> future) {
        return when(new DeferredCallable<T, Void>(StartPolicy.AUTO) {

            @Override
            public T call() throws Exception {
                try {
                    return future.get();
                } catch (InterruptedException e) {
                    throw e;
                } catch (ExecutionException e) {
                    if (e.getCause() instanceof Exception) {
                        throw (Exception) e.getCause();
                    } else {
                        throw e;
                    }
                }
            }
        });
    }

    @Override
    public <T, K, P> Promise<T, K, P> when(Promise<T, K, P> promise) {
        return promise;
    }

    @Override
    public Promise<Void, Throwable, Void> when(Runnable runnable) {
        return when(new DeferredFutureTask<Void, Void>(runnable));
    }

    @Override
    public <P> Promise<Void, Throwable, P> when(DeferredRunnable<P> runnable) {
        return when(new DeferredFutureTask<Void, P>(runnable));
    }

    @Override
    public <T, P> Promise<T, Throwable, P> when(DeferredCallable<T, P> callable) {
        return when(new DeferredFutureTask<T, P>(callable));
    }

    @Override
    public <T, P> Promise<T, Throwable, P> when(DeferredFutureTask<T, P> task) {
        if (task.getStartPolicy() == StartPolicy.AUTO || (task.getStartPolicy() == StartPolicy.DEFAULT && isAutoSubmit())) {
            submit(task);
        }
        return task.promise();
    }

    @Override
    public Promise<ResultSet, SingleReject, BaseProgress> when(Runnable... runnables) {
        Promise[] promises = new Promise[runnables.length];

        for (int i = 0, length = runnables.length; i < length; i++) {
            if (runnables[i] instanceof DeferredRunnable) {
                promises[i] = when((DeferredRunnable) runnables[i]);
            } else {
                promises[i] = when(runnables[i]);
            }
        }
        return when(promises);
    }

    @Override
    public Promise<ResultSet, SingleReject, BaseProgress> when(DeferredRunnable<?>... runnables) {
        Promise[] promises = new Promise[runnables.length];

        for (int i = 0, length = runnables.length; i < length; i++) {
            promises[i] = when(runnables[i]);
        }
        return when(promises);
    }

    @Override
    public Promise<ResultSet, SingleReject, BaseProgress> when(DeferredCallable<?, ?>... callables) {
        Promise[] promises = new Promise[callables.length];

        for (int i = 0, length = callables.length; i < length; i++) {
            promises[i] = when(callables[i]);
        }
        return when(promises);
    }

    @Override
    public Promise<ResultSet, SingleReject, BaseProgress> when(Future<?>... futures) {
        Promise[] promises = new Promise[futures.length];

        for (int i = 0, length = futures.length; i < length; i++) {
            promises[i] = when(futures[i]);
        }
        return when(promises);
    }

    @Override
    public Promise<ResultSet, SingleReject, BaseProgress> when(DeferredFutureTask<?, ?>... tasks) {
        Promise[] promises = new Promise[tasks.length];

        for (int i = 0, length = tasks.length; i < length; i++) {
            promises[i] = when(tasks[i]);
        }
        return when(promises);
    }
}
