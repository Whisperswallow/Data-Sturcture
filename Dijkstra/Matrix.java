package com.sample;

public class Matrix {

    public RowNode first;
    public int row, col;

    public Matrix(int row, int col) {
        this.row = row;
        this.col = col;
        first = new RowNode(-1);
    }

    public void addNode(int x, int y, int data) {
        if (x <= row && y <= col) {
            RowNode target = first;
            while (target.row != x && target.next != null) {
                target = target.next;
            }
            if (target.row != x) {
                RowNode newRow = new RowNode(x);
                target.next = newRow;
                target = newRow;
            }
            target.addNode(y, data);
        } else {
            throw new RuntimeException("Vertex label is not in range.");
        }
    }
}

class RowNode {

    public RowNode next;
    public Element first;
    public int row;

    public RowNode(int row) {
        this.row = row;
        first = new Element(-1,-1);
    }

    public void addNode(int y, int data) {
        Element target = first;
        while (target.next != null) {
            target = target.next;
            if (target.col == y) {
                throw new RuntimeException("This edge has already in the graph.");
            }
        }
        target.next = new Element(y, data);
    }
}

class Element {

    public Element next;
    public int col, data;

    public Element(int col, int data) {
        this.col = col;
        this.data = data;
    }
}
