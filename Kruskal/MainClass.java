package com.sample;

import java.util.*;

/*
 * Use Dijkstra Algoritm to find the shortest path for s to t.
 *  author: Z. H. Tsai 
 *  std_id: 0852223
 *  date: 2019/11/14
 */
public class MainClass {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int order = scanner.nextInt(), end, weight;
        Matrix adjMatrix = new Matrix(order, order);
        for (int i = 1; i <= order; i++) {
            while ((end = scanner.nextInt()) != 0) {
                weight = scanner.nextInt();
                adjMatrix.addNode(i, end, weight);
                adjMatrix.addNode(end, i, weight);
            }
        }
        Vertex[] vSet = generateVertices(order), S = new Vertex[order];
        int start = scanner.nextInt() - 1;
        vSet[start].setStart();
        Heap vHeap = new Heap(vSet);
        Vertex target;
        for (int i = 0; i < S.length; i++) {
            target = vHeap.getMin();
            vHeap.updateNeighbor(target, vSet, adjMatrix);
            S[i] = vSet[target.name - 1];
        }
        int terminate = scanner.nextInt() - 1;
        showPath(vSet[terminate]);
        System.out.println("\ndistance = " + vSet[terminate].dist);
    }

    private static Vertex[] generateVertices(int order) {
        Vertex[] set = new Vertex[order];
        for (int i = 0; i < order; i++) {
            set[i] = new Vertex(i + 1);
        }
        return set;
    }

    public static void showPath(Vertex terminate) {
        if (terminate.parent != terminate) {
            showPath(terminate.parent);
            System.out.print("→");
        }
        System.out.print(terminate);
    }
}
