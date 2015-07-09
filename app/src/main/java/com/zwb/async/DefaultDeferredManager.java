package com.zwb.async;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zwb on 2015/7/8.
 */
public class DefaultDeferredManager extends AbstractDeferredManager {
    private final ExecutorService executorService;
    public static final boolean DEFAULT_AUTO_SUBMIT = true;
    private boolean autoSubmit = DEFAULT_AUTO_SUBMIT;

    public DefaultDeferredManager() {
        this.executorService = Executors.newCachedThreadPool();
    }

    public DefaultDeferredManager(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    protected void submit(Callable callable) {
        executorService.submit(callable);
    }

    @Override
    protected void submit(Runnable runnable) {
        executorService.submit(runnable);
    }

    @Override
    public boolean isAutoSubmit() {
        return autoSubmit;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setAutoSubmit(boolean autoSubmit){
        this.autoSubmit = autoSubmit;
    }
}
