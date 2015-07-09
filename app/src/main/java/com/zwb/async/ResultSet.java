package com.zwb.async;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by zwb on 2015/7/8.
 */
public class ResultSet implements Iterable<SingleResult> {
    private final List<SingleResult> results;

    public ResultSet(int size) {
        this.results = new CopyOnWriteArrayList<>(new SingleResult[size]);
    }

    protected void set(int index, SingleResult result) {
        results.set(index, result);
    }

    public SingleResult get(int index) {
        return results.get(index);
    }

    public int size() {
        return results.size();
    }

    public Iterator<SingleResult> iterator() {
        return results.iterator();
    }
}