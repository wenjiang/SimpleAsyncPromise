package com.zwb.async;

import com.zwb.simpleasyncpromise.Promise;

/**
 * Created by zwb on 2015/7/8.
 */
public class SingleResult {
    private final int index;
    private final Promise promise;
    private final Object result;

    public SingleResult(int index, Promise promise, Object result) {
        this.index = index;
        this.promise = promise;
        this.result = result;
    }

    public int getIndex() {
        return index;
    }

    public Promise getPromise() {
        return promise;
    }

    public Object getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "SingleResult [index=" + index + ", promise=" + promise + ", result=" + result + "]";
    }
}
