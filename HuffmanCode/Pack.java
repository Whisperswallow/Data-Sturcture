package com.sample;

class Pack {

    public int num;
    public byte data;
    public Pack leftChild, rightChild, parent;

    public Pack(byte data, int num) {
        this.num = num;
        this.data = data;
    }

    boolean islarger(Pack pack) {
        return num > pack.num;
    }

    @Override
    public String toString() {
        String out = " : ";
        out = MainClass.charOutput(data)+out;
        return out + num;
    }

}
