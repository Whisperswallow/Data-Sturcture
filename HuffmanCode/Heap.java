package com.sample;

class Heap {

    public Pack[] data;
    public int len;

    public Heap(Pack[] data) { // wait for heapify done
        this.data = data;
        len = data.length;
        int target = len-len / 2;
        while (target-- > 0) {
            heapify(target);
        }
    }

    public void heapify(int target) { // need to implement
        int child;
        if ((child = findChild(target)) == -1) {
            return;
        }
        while (data[target].islarger(data[child])) {
            swap(target, child);
            target = child;
            if ((child = findChild(target)) == -1) {
                return;
            }
        }
    }

    private int findChild(int target) {
        if (target * 2 + 2 < len) {
            return ((data[target * 2 + 2].islarger(data[target * 2 + 1]))
                    ? target * 2 + 1 : target * 2 + 2);
        } else if (target * 2 + 1 < len) {
            return target * 2 + 1;
        } else {
            return -1;
        }
    }

    public void swap(int index1, int index2) {
        Pack temp = data[index1];
        data[index1] = data[index2];
        data[index2] = temp;
    }

    public void show() { // 整個 heap 輸出( debug 用 )
        int i=0;
        for (Pack data1 : data) {
            System.out.print(" | " + data1);
        }
        System.out.println(" | ");
    }
    
}
