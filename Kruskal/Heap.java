package com.sample;

class Heap {

    public Vertex[] data;
    public int len;

    public Heap(Vertex[] data) { // wait for heapify done
        len = data.length;
        this.data = new Vertex[len];
        for(int i=0;i<len;i++){
            this.data[i] = data[i];
        }
        int target = len - len / 2;
        while (target-- > 0) {
            heapify(target);
        }
    }

    public void heapify(int target) { // need to implement
        int child;
        if ((child = findChild(target)) == -1) {
            return;
        }
        while (data[target].isLarger(data[child])) {
            swap(target, child);
            target = child;
            if ((child = findChild(target)) == -1) {
                return;
            }
        }
    }

    private int findChild(int target) {
        if (target * 2 + 2 < len) {
            return ((data[target * 2 + 2].isLarger(data[target * 2 + 1]))
                    ? target * 2 + 1 : target * 2 + 2);
        } else if (target * 2 + 1 < len) {
            return target * 2 + 1;
        } else {
            return -1;
        }
    }

    public void swap(int index1, int index2) {
        Vertex temp = data[index1];
        data[index1] = data[index2];
        data[index2] = temp;
    }

    public Vertex getMin() {
        swap(0, --len);
        heapify(0);
        return data[len];
    }

    public void updateNeighbor(Vertex vNew, Vertex[] vSet, Matrix adjMatrix) {
        RowNode tRow = adjMatrix.first;
        while (tRow.row != vNew.name) {
            if (tRow.next == null) {
                return;
            }
            tRow = tRow.next;
        }
        Element tCol = tRow.first;
        while (tCol.next != null) {
            tCol = tCol.next;
            if (vSet[tCol.col-1].dist > vNew.dist + tCol.data) {
                vSet[tCol.col-1].dist = vNew.dist + tCol.data;
                vSet[tCol.col-1].parent = vNew;
            }
        }
        int target = len - len / 2;
        while (target-- > 0) {
            heapify(target);
        }
    }

    public void show() {
        for (Vertex data1 : data) {
            System.out.print(" | " + data1);
        }
        System.out.println(" | ");
    }

}
