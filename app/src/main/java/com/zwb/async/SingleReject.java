package com.zwb.async;

import com.zwb.simpleasyncpromise.Promise;

/**
 * Created by zwb on 2015/7/8.
 */
public class SingleReject {
    private final int index;
    private final Promise promise;
    private final Object reject;

    public SingleReject(int index, Promise promise, Object reject) {
        this.index = index;
        this.promise = promise;
        this.reject = reject;
    }

    public int getIndex() {
        return index;
    }

    public Promise getPromise() {
        return promise;
    }

    public Object getReject() {
        return reject;
    }

    @Override
    public String toString() {
        return "SingleReject【" +
                "index=" + index +
                ", promise=" + promise +
                ", reject=" + reject +
                '】';
    }
}
