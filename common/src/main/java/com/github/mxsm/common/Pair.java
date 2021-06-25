package com.github.mxsm.common;

/**
 * @author mxsm
 * @Date 2021/6/25
 * @Since 0.1
 */
public class Pair <L,R>{

    private L left;

    private R right;

    private Pair(L left, R right){
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    public static <L,R> Pair<L,R> builder(L left, R right){
        return new Pair<>(left, right);
    }
}
