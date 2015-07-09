package com.zwb.async;

import com.zwb.simpleasyncpromise.Promise;

/**
 * Created by zwb on 2015/7/8.
 */
public class SingleProgress extends BaseProgress {
    private final int index;
    private final Promise promise;
    private final Object progress;

    public SingleProgress(int done, int fail, int total, int index, Promise promise, Object progress) {
        super(done, fail, total);
        this.index = index;
        this.promise = promise;
        this.progress = progress;
    }

    public int getIndex() {
        return index;
    }

    public Promise getPromise() {
        return promise;
    }

    public Object getProgress() {
        return progress;
    }

    @Override
    public String toString() {
        return "SingleProgress[" +
                "index=" + index +
                ", promise=" + promise +
                ", progress=" + progress +
                ", getDone()=" + getDone() +
                ", getFail()=" + getFail() +
                ", getTotal()=" + getTotal() +
                ']';
    }
}
