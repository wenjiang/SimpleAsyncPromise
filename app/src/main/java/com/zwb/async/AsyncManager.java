package com.zwb.async;

import com.zwb.simpleasyncpromise.AlwaysCallback;
import com.zwb.simpleasyncpromise.DoneCallback;
import com.zwb.simpleasyncpromise.FailCallback;
import com.zwb.simpleasyncpromise.ProgressCallback;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * Created by zwb on 2015/7/7.
 */
public class AsyncManager {
    private ExecutorService executorService;

    public <T> AsyncManager when(Callable<T> callable) {
        return this;
    }

    public <T> AsyncManager then(DoneCallback<T> doneCallback) {
        return this;
    }

    public AsyncManager fail(FailCallback<Throwable> failCallback) {
        return this;
    }

    public AsyncManager always(AlwaysCallback alwaysCallback) {
        return this;
    }

    public AsyncManager progress(ProgressCallback progressCallback) {
        return this;
    }
}
