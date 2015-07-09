package com.zwb.async;

import com.zwb.simpleasyncpromise.DoneCallback;
import com.zwb.simpleasyncpromise.FailCallback;
import com.zwb.simpleasyncpromise.ProgressCallback;
import com.zwb.simpleasyncpromise.Promise;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zwb on 2015/7/9.
 */
public class TriggeredDeferredObject extends DeferredObject<ResultSet, SingleReject, BaseProgress> implements Promise<ResultSet, SingleReject, BaseProgress> {
    private final int numberOfPromises;
    private final AtomicInteger doneCount = new AtomicInteger();
    private final AtomicInteger failCount = new AtomicInteger();
    private final ResultSet results;

    public TriggeredDeferredObject(Promise... promises) {
        if (promises == null || promises.length == 0) {
            throw new IllegalArgumentException("Promises is null or empty");
        }

        this.numberOfPromises = promises.length;
        results = new ResultSet(numberOfPromises);

        int count = 0;
        for (final Promise promise : promises) {
            final int index = count++;
            promise.fail(new FailCallback<Object>() {
                @Override
                public void onFail(Object result) {
                    synchronized (TriggeredDeferredObject.this) {
                        if (!TriggeredDeferredObject.this.isPending()) {
                            return;
                        }

                        final int fail = failCount.incrementAndGet();
                        TriggeredDeferredObject.this.notify(new BaseProgress(doneCount.get(), fail, numberOfPromises));
                        TriggeredDeferredObject.this.reject(new SingleReject(index, promise, result));
                    }
                }
            }).progress(new ProgressCallback() {
                @Override
                public void onProgress(Object progress) {
                    synchronized (TriggeredDeferredObject.this) {
                        if (!TriggeredDeferredObject.this.isPending()) {
                            return;
                        }

                        TriggeredDeferredObject.this.notify(new SingleProgress(doneCount.get(), failCount.get(), numberOfPromises, index, promise, progress));
                    }
                }
            }).done(new DoneCallback() {
                @Override
                public void onDone(Object result) {
                    synchronized (TriggeredDeferredObject.this) {
                        if (!TriggeredDeferredObject.this.isPending()) {
                            return;
                        }

                        results.set(index, new SingleResult(index, promise, result));
                        int done = doneCount.incrementAndGet();

                        TriggeredDeferredObject.this.notify(new BaseProgress(done, failCount.get(), numberOfPromises));

                        if (done == numberOfPromises) {
                            TriggeredDeferredObject.this.resolve(results);
                        }
                    }
                }
            });
        }
    }
}
