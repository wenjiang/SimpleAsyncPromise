package com.zwb.async;

/**
 * Created by zwb on 2015/7/8.
 */
public class BaseProgress {
    private final int done;
    private final int fail;
    private final int total;

    public BaseProgress(int done, int fail, int total) {
        this.done = done;
        this.fail = fail;
        this.total = total;
    }

    public int getDone() {
        return done;
    }

    public int getFail() {
        return fail;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "BaseProgress[" +
                "done=" + done +
                ", fail=" + fail +
                ", total=" + total +
                ']';
    }
}
