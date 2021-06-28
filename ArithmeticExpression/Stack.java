package com.sample;

public class Stack<T> { // 泛型 T 為儲存資料的型別

    private Node<T> first, pointer;

    public Stack() {
        first = new Node();
        pointer = first;
    }

    // 往Stack放入新資料
    public void push(T data) {
        Node<T> newNode = new Node<>();
        newNode.setData(data);
        newNode.next = null;
        pointer.next = newNode;
        newNode.before = pointer;
        pointer = newNode;
    }

    // 從Stack頂取出資料
    public T pop() {
        if (pointer == first) {
            //System.out.println("The stack is empty.");
            return null;
        }
        T data = pointer.getData();
        pointer.before.next = null;
        pointer = pointer.before;
        return data;
    }
}

class Node<T> { // 泛型 T 為儲存資料的型別

    private T data;
    public Node<T> next = null, before = null;

    public Node() {
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
