package com.blecua84.pokerapp.api.service.impl;

import com.blecua84.pokerapp.api.exceptions.ExceptionUtil;
import com.blecua84.pokerapp.api.service.Combinatorial;

/**
 * Class for implementing interface Combinatorial.
 *
 * @author josejavier.blecua
 */
public class CombinatorialImpl implements Combinatorial {

    private final int[] indexes;
    private final int items;

    public CombinatorialImpl(int subItems, int items) {
        ExceptionUtil.checkMinValueArgument(subItems, 1, "subItems");
        ExceptionUtil.checkMinValueArgument(items, subItems, "items");
        this.indexes = new int[subItems];
        this.items = items;
    }

    @Override
    public long combinations() {
        return combinations(indexes.length, items);
    }

    @Override
    public int size() {
        return indexes.length;
    }

    public int getSubItems() {
        return indexes.length;
    }

    public int getItems() {
        return items;
    }

    private boolean hasNext(int index) {
        return indexes[index] + (indexes.length - index) < items;
    }

    private void move(int index) {
        if (hasNext(index)) {
            indexes[index]++;
            int last = indexes[index];
            for (int i = index + 1; i < indexes.length; i++) {
                this.indexes[i] = ++last;
            }
        } else {
            move(index - 1);
        }
    }

    @Override
    public int[] next(int[] items) {
        if (hasNext()) {
            move(indexes.length - 1);
            System.arraycopy(indexes, 0, items, 0, indexes.length);
        }
        return items;
    }

    @Override
    public boolean hasNext() {
        return hasNext(0) || hasNext(indexes.length - 1);
    }

    private void init() {
        int index = indexes.length;
        for (int i = 0; i < indexes.length; i++) {
            this.indexes[i] = i;
        }
        this.indexes[index - 1]--;
    }

    @Override
    public void clear() {
        init();
    }

    public static long combinations(int subItems, int items) {
        long result = 1;
        int sub = Math.max(subItems, items - subItems);
        for (int i = sub + 1; i <= items; i++) {
            result = (result * i) / (i - sub);
        }
        return result;
    }
}
