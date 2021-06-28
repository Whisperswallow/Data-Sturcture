package com.sample;

public class Stack {

    private Node first, pointer;

    public Stack() {
        first = new Node();
        pointer = first;
    }

    // 往Stack放入新資料
    public void push(boolean data) {
        Node newNode = new Node();
        newNode.setData(data);
        newNode.next = null;
        pointer.next = newNode;
        newNode.before = pointer;
        pointer = newNode;
    }

    // 從Stack頂取出資料
    public boolean pop() {
        if (pointer == first) {
            throw new RuntimeException("empty");
        }
        boolean data = pointer.getData();
        pointer.before.next = null;
        pointer = pointer.before;
        return data;
    }

    public String getCode() {
        Node target = first;
        String out = "";
        while (target != pointer) {
            target = target.next;
            if (target.data) {
                out += "1";
            } else {
                out += "0";
            }
        }
        return out;
    }

    class Node { // Inner class

        private boolean data;
        public Node next = null, before = null;

        public Node() {
        }

        public void setData(boolean data) {
            this.data = data;
        }

        public boolean getData() {
            return data;
        }
    }
}
