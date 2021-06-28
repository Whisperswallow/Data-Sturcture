package com.sample;

class Matrix {

    public Node first, target;
    private int row, column;

    public Matrix(int row, int column) {
        this.row = row;
        this.column = column;
        first = new Node();
        first.setData(-1, -1, 0);
    }

    /* 資料處理 */
    public void addNode(int x, int y, int data) { // 在LinkedList中新增node
        if (data == 0 || x > row || y > column) {
            return;
        }
        Node newNode = new Node();
        newNode.setData(x - 1, y - 1, data);
        target = first;
        // 讓加入的點以row-major的順序排好
        while (target.next != null && target.next.lessThan(newNode)) {
            target = target.next;
        }
        newNode.next = target.next;
        target.next = newNode;
    }

    public void setData(int x, int y, int data) {
        target = first.next;
        while (target != null) {
            if (target.getX() == x - 1 && target.getY() == y - 1) {
                target.setData(x - 1, y - 1, data);
            } else {
                first = first.next;
            }
        }
    }

    public Integer getData(int x, int y) {
        target = first.next;
        while (target != null) {
            if (target.getX() == x && target.getY() == y) {
                return target.getData();
            } else {
                first = first.next;
            }
        }
        return null;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    /* 矩陣運算 */
    public Matrix add(Matrix object) { // 矩陣加法
        if (row != object.getRow() || column != object.getColumn()) {
            System.out.println("Matrix dimension must be the same.");
            return this;
        }
        Matrix result = new Matrix(row, column);
        if (this == object) {
            target = first.next;
            while (target != null) {
                result.addNode(target.getX() + 1, target.getY() + 1, 2 * target.getData());
                target = target.next;
            }
            return result;
        } else {
            target = first.next;
            object.target = object.first.next;
            while (target != null && object.target != null) {
                if (target.lessThan(object.target)) {
                    result.addNode(target.getX() + 1, target.getY() + 1, target.getData());
                    target = target.next;
                } else if (object.target.lessThan(target)) {
                    result.addNode(object.target.getX() + 1, object.target.getY() + 1,
                            object.target.getData());
                    object.target = object.target.next;
                } else {
                    result.addNode(target.getX() + 1, target.getY() + 1,
                            target.getData() + object.target.getData());
                    target = target.next;
                    object.target = object.target.next;
                }
            }
            while (target != null) {
                result.addNode(target.getX() + 1, target.getY() + 1, target.getData());
                target = target.next;
            }
            while (object.target != null) {
                result.addNode(object.target.getX() + 1, object.target.getY() + 1,
                        object.target.getData());
                object.target = object.target.next;
            }
            return result;
        }
    }

    public Matrix multiply(Matrix object) { // 矩陣乘法
        if (column != object.getRow()) {
            System.out.println("Matrix dimension must be the same.");
            return this;
        }
        Matrix result = new Matrix(row, object.column);
        int data, objectRow = 0;
        Node pointer;
        Matrix objectT = object.transpose();
        mainLoop:
        for (int ctRow = 0;; ctRow++) {
            objectT.target = objectT.first.next;
            data = 0;
            target = first.next;
            while (target.getX() < ctRow) {
                target = target.next;
                if (target == null) {
                    break mainLoop;
                }
            }
            if (target.getX() != ctRow) {
                continue;
            }
            pointer = target;
            while (objectT.target != null) {
                if (objectRow != objectT.target.getX()) {
                    result.addNode(ctRow + 1, objectRow + 1, data);
                    pointer = target;
                    data = 0;
                    objectRow = objectT.target.getX();
                } else if (pointer == null || pointer.getX() != target.getX()) {
                    objectT.target = objectT.target.next;
                    pointer = target;
                    continue;
                }
                if (objectT.target.getY() > pointer.getY()) {
                    pointer = pointer.next;
                } else if (objectT.target.getY() < pointer.getY()) {
                    objectT.target = objectT.target.next;
                } else {
                    data += pointer.getData() * objectT.target.getData();
                    pointer = pointer.next;
                    objectT.target = objectT.target.next;
                }
            }
            result.addNode(ctRow + 1, objectRow + 1, data);
        }
        return result;
    }

    public Matrix transpose() { //矩陣轉置
        Matrix result = new Matrix(column, row);
        target = first.next;
        while (target != null) {
            result.addNode(target.getY() + 1, target.getX() + 1, target.getData());
            target = target.next;
        }
        return result;
    }

    /* 輸出相關 */
    public void show() { // 輸出矩陣
        target = first.next;
        for (int i = 0; i < row * column; i++) {
            if (target != null) {
                if (target.getX() * column + target.getY() == i) {
                    System.out.printf(" %d\t", target.getData());
                    target = target.next;
                } else {
                    System.out.print(" 0\t");
                }
            } else {
                System.out.print(" 0\t");
            }
            if ((i + 1) % column == 0) {
                System.out.println("");
            }
        }
    }

    public void showSize() { // 當前用了多少 node 來儲存這個矩陣
        target = first;
        int count = 0;
        while ((target = target.next) != null) {
            count++;
        }
        System.out.println("Size = " + count + " node(s).");
    }
}
