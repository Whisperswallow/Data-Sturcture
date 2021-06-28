package com.sample;

public class Vertex {

    public Vertex parent;
    public int dist;
    public final int INF = 2147483647;
    public int name;

    public Vertex(int name) {
        parent = this;
        dist = INF;
        this.name = name;
    }

    public boolean isLarger(Vertex op) {
        return dist > op.dist;
    }

    public void setStart() {
        this.dist = 0;
    }

    @Override
    public String toString() {
        return "v_"+name;
    }
}
