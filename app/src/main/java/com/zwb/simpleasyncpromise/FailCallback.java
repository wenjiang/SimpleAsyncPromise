package com.zwb.simpleasyncpromise;

/**
 * Created by zwb on 2015/7/7.
 */
public abstract class FailCallback<T> {
    public abstract void onFail(T exception);
}
