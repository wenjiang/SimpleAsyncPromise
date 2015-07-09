package com.zwb.async;

import com.zwb.simpleasyncpromise.Promise;

/**
 * Created by zwb on 2015/7/8.
 */
public interface Deferred<T, K, P> extends Promise<T, K, P> {
    Deferred<T, K, P> reject(final K reject);

    Deferred<T, K, P> resolve(final T resolve);

    Deferred<T, K, P> notify(final P progress);

    Promise<T, K, P> promise();
}
