package com.zwb.async;

import com.zwb.simpleasyncpromise.Promise;

/**
 * Created by zwb on 2015/7/9.
 */
public class DeferredObject<T, K, P> extends BasePromise<T, K, P> implements Deferred<T, K, P> {
    @Override
    public Deferred<T, K, P> reject(final K reject) {
        synchronized (this) {
            if (!isPending()) {
                throw new IllegalStateException("Deferred object already finished, cannot reject again");
            }

            this.state = State.REJECTED;
            this.rejectResult = reject;

            try {
                triggerFail(reject);
            } finally {
                triggerAlways(state, null, reject);
            }

        }
        return this;
    }

    @Override
    public Deferred<T, K, P> resolve(final T resolve) {
        synchronized (this) {
            if (!isPending()) {
                throw new IllegalStateException("Deferred object already finished, cannot resolve again");
            }

            this.state = State.RESOLVED;
            this.resolveResult = resolve;

            try {
                triggerDone(resolve);
            } finally {
                triggerAlways(state, resolve, null);
            }
        }
        return this;
    }

    @Override
    public Deferred<T, K, P> notify(final P progress) {
        synchronized (this) {
            if (!isPending()) {
                throw new IllegalStateException("Deferred object already finished, cannot notify progress");
            }

            triggerProgress(progress);
        }
        return this;
    }

    @Override
    public Promise<T, K, P> promise() {
        return this;
    }
}
