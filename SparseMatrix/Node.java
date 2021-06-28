package com.sample;

public class Node {

    private int x, y, data;
    public Node next;

    public Node() {
        next = null;
    }

    public void setData(int x, int y, int data) {
        this.x = x;
        this.y = y;
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean lessThan(Node target) { // 判斷點該排的位置的先後順序
        return x < target.getX() || (x == target.getX() && y < target.getY());
    }
}
