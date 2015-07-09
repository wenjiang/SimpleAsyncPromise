package com.zwb.simpleasyncpromise;

/**
 * Created by zwb on 2015/7/7.
 */
public abstract class ProgressCallback<P> {
    public abstract void onProgress(P progress);
}