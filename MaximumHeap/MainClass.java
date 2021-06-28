package com.sample;

import java.util.*;
/*
    Design and implement efficient data structure and algorithm for 
    a set of m queries.
    author: Z. H. Tsai 
    std_id: 0852223
    date: 2019/11/14
 */

public class MainClass {

    public static int[] inputData;

    public static void main(String[] args) {
        /* 接收輸入版本 */
        /*Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // 資料長度
        inputData = new int[n];
        for (int i = 0; i < n; i++) { // 記錄資料
            inputData[i] = scanner.nextInt();
        }
        Heap heap = new Heap(inputData); // 以資料建 heap
        int m = scanner.nextInt();
        for (int i = 0; i < m; i++) {
            int l = scanner.nextInt(), r = scanner.nextInt();
            // 原始方法找答案 (確認 heap 有解出正確答案)
            System.out.println("Original way : " + originFindMaximun(l, r));
            // 用 heap 找答案
            //heap.show(); // debug 用，顯示出建立的 heap 內容
            System.out.println("By heap : " + heap.findMaximum(l, r));
        }*/

        /* 隨機測試版本 */
        int n = (int) (19882297); //10000000~20000000
        inputData = new int[n];
        for (int i = 0; i < n; i++) {
            inputData[i] = generator(-5 * n, 5 * n);
        }
        Heap heap = new Heap(inputData); // 以資料建 heap
        int m = 43; // 30~100
        long sumOFM=0, sumHFM=0;
        while (m-- > 0) {
            int l = generator(1, n), r = generator(1, n);
            if (l > r) {
                int temp = l;
                l = r;
                r = temp;
            }
            int oFM, hFM;
            long startOFM, startHFM, stopOFM, stopHFM;
            startOFM = System.currentTimeMillis();
            oFM = originFindMaximun(l, r);
            stopOFM = System.currentTimeMillis();
            sumOFM +=stopOFM-startOFM;
            System.out.println("Original : " + oFM);
            startHFM = System.currentTimeMillis();
            hFM = heap.findMaximum(l, r);
            stopHFM = System.currentTimeMillis();
            sumHFM +=stopHFM-startHFM;
            System.out.println("Heap : " + oFM);
        }
        System.out.println("\nUsing time :\n\tOringinal : " + sumOFM +
                "(ms)\n\tHeap : " + sumHFM +"(ms)");
    }

    /* 原始版本找最大值 */
    private static int originFindMaximun(int l, int r) {
        int max = Integer.MIN_VALUE;
        for (int i = l - 1; i < r; i++) { // 掃過整串資料，找出最大值
            if (max < inputData[i]) {
                max = inputData[i];
            }
        }
        return max;
    }

    /* 隨機測試用數字生成方法 */
    private static int generator(int min, int max) {
        return (int) (min + (max - min) * Math.random());
    }
}
