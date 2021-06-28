package com.sample;

import java.util.*;

/*
 * Use Kruskal’s algorithm to compute minimum spanning tree of a weighted graph.
 *  author: Z. H. Tsai 
 *  std_id: 0852223
 *  date: 2019/11/14
 */
public class MainClass {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int order = scanner.nextInt(),end, weight;
        Matrix adjMatrix = new Matrix(order, order);
        for (int i = 1; i <= order; i++) {
            while ((end = scanner.nextInt()) != 0) {
                weight = scanner.nextInt();
                adjMatrix.addNode(i, end, weight);
                adjMatrix.addNode(end, i, weight);
            }
        }
        Vertex[] vSet = generateVertices(order);
        Edge[] eSet = adjMatrix.getEdgeSet(vSet), mst = new Edge[order - 1];
        mergeSort(eSet, 0, eSet.length - 1);
        int num = 0;
        for (int j = 0; j < eSet.length; j++) {
            Vertex root1 = find(eSet[j].end1), root2 = find(eSet[j].end2);
            if (root1 == root2) {
                continue;
            }
            union(root1, root2);
            mst[num++] = eSet[j];
            if (num >= order - 1) {
                break;
            }
        }
        for (Edge e : mst) {
            System.out.println(e);
        }
    }

    private static int readInt(Scanner scanner) {
        return scanner.nextInt();
    }

    public static Vertex find(Vertex end) {
        Vertex rootVertex = end;
        if (rootVertex.root != rootVertex) {
            rootVertex.root = find(rootVertex.root);
        }
        return rootVertex.root;
    }

    public static void union(Vertex root1, Vertex root2) {
        if (root1.rank >= root2.rank) {
            if (root1.rank == root2.rank) {
                root1.rank++;
            }
            root2.root = root1;
        } else {
            root1.root = root2;
        }
    }

    private static Vertex[] generateVertices(int order) {
        Vertex[] set = new Vertex[order];
        for (int i = 0; i < order; i++) {
            set[i] = new Vertex("v_" + (i + 1));
        }
        return set;
    }

    private static void mergeSort(Edge[] target, int left, int right) {
        if (left < right) {
            int k = (left + right) / 2;
            mergeSort(target, left, k);
            mergeSort(target, k + 1, right);
            merge(target, left, k, target, k + 1, right, target);
        }
    }

    private static void merge(Edge[] array1, int left1, int right1,
            Edge[] array2, int left2, int right2, Edge[] target) {
        int count1 = left1, count2 = left2, count = 0;
        Edge[] temp = new Edge[right1 + right2 - left1 - left2 + 2];
        while (count1 <= right1 && count2 <= right2) {
            if (array1[count1].isLess(array2[count2])) {
                temp[count++] = array1[count1++];
            } else {
                temp[count++] = array2[count2++];
            }
        }
        while (count1 <= right1) {
            temp[count++] = array1[count1++];
        }
        while (count2 <= right2) {
            temp[count++] = array2[count2++];
        }
        for (int i = left1; i <= right2; i++) {
            target[i] = temp[i - left1];
        }
    }
}
