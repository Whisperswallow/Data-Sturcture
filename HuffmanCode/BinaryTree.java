package com.sample;

class BinaryTree {

    public Pack root;

    public BinaryTree(Heap heap) {
        generateTree(heap);
    }

    private void generateTree(Heap heap) {
        Pack firstNode, secondNode, innerNode;
        while (heap.len > 1) {
            heap.swap(0, --heap.len);
            heap.heapify(0);
            firstNode = heap.data[heap.len];
            secondNode = heap.data[0];
            innerNode = new Pack((byte) -1, firstNode.num + secondNode.num);
            firstNode.parent = innerNode;
            secondNode.parent = innerNode;
            if (firstNode.islarger(secondNode)) {
                innerNode.leftChild = secondNode;
                innerNode.rightChild = firstNode;
            } else {
                innerNode.leftChild = firstNode;
                innerNode.rightChild = secondNode;
            }
            heap.data[0]=innerNode;
            heap.heapify(0);
        }
        root=heap.data[0];
    }
    
    public void show(Pack pack){
        if(pack.leftChild!=null){
            System.out.println(pack+" | "+pack.leftChild+" | "+pack.rightChild);
            show(pack.leftChild);
            show(pack.rightChild);
        }
    }
}
