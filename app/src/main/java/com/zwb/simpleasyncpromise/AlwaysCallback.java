package com.zwb.simpleasyncpromise;

/**
 * Created by zwb on 2015/7/7.
 */
public abstract class AlwaysCallback<T, K> {
    public abstract void onAlways(Promise.State state, T resolveResult, K rejectResult);
}
