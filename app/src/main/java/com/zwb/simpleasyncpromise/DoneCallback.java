package com.zwb.simpleasyncpromise;

/**
 * Created by zwb on 2015/7/7.
 */
public abstract class DoneCallback<T> {
    public abstract void onDone(T result);
}
