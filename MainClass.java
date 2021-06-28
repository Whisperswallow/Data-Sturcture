package com.sample;

/*
    Implement the sorting algorithms and compare their performances.
        1. Insertion sort
        2. Selection sort
        3. Merge sort
        4. Quick sort
        5. Heap sort
    author: Z. H. Tsai 
    std_id: 0852223
    date: 2019/10/11
 */
import java.io.*;
import java.util.*;

public class MainClass {

    public static void main(String[] args) {
        /* 宣告 */
        int ct = 0;
        long start, end;
        //int num1=10, num2=10, num3=10, num4=10;
        int num1=1000, num2=1000, num3=1000, num4=1000;
        
        /* 測試區塊
        *
        * 共同訊息：
        *   每個測試區塊均會進行1000輪的測試，每輪測試中，每個演算法拿到的資料大小均為相同，
        *   輸出固定為 array_size, comsume_time(ms) ，所有資料皆須完成檢查後才會進行輸
        *   出，輸出格式為 .csv 檔。消耗時間採用於呼叫各排序演算法前後分別抓取系統時間，進
        *   行相減後得出，忽視電腦運算途中由運算以外原因造成的時間消耗。
        *
        * 測試區塊一 
        *
        * 排序法：insertion, selection, merge, quick, heap
        * 資料量：10000 ~ 99999
        * 資料初始狀態：隨機順序
         */
        try (FileWriter fw = new FileWriter("c5_1000r.csv")) {
            for (int ii = 0; ii < num1; ii++) {
                int i = (int) (10000 + Math.random() * 90000),
                        seed = new Random().nextInt();
                int[] target1 = createArray(i, seed),
                        target2 = createArray(i, seed),
                        target3 = createArray(i, seed),
                        target4 = createArray(i, seed),
                        target5 = createArray(i, seed);
                start = System.currentTimeMillis();
                insertionSort(target1);
                end = System.currentTimeMillis();
                if (checkCorrect(target1)) {
                    //showArray(target);
                    fw.write(i + "," + (end - start) + ",");
                    System.out.print(i + "," + (end - start) + ",");
                } else {
                    fw.write(",,");
                    System.out.print("Error!,");
                }
                start = System.currentTimeMillis();
                selectionSort(target2);
                end = System.currentTimeMillis();
                if (checkCorrect(target2)) {
                    //showArray(target);
                    fw.write(i + "," + (end - start) + ",");
                    System.out.print(i + "," + (end - start) + ",");
                } else {
                    fw.write(",,");
                    System.out.print("Error!,");
                }
                start = System.currentTimeMillis();
                mergeSort(target3, 1, target3.length - 1);
                end = System.currentTimeMillis();
                if (checkCorrect(target3)) {
                    //showArray(target);
                    fw.write(i + "," + (end - start) + ",");
                    System.out.print(i + "," + (end - start) + ",");
                } else {
                    fw.write(",,");
                    System.out.print("Error!,");
                }
                start = System.currentTimeMillis();
                quickSort(target4, 1, target4.length - 1);
                end = System.currentTimeMillis();
                if (checkCorrect(target4)) {
                    //showArray(target);
                    fw.write(i + "," + (end - start) + ",");
                    System.out.print(i + "," + (end - start) + ",");
                } else {
                    fw.write(",,");
                    System.out.print("Error!,");
                }
                start = System.currentTimeMillis();
                heapSort(target5);
                end = System.currentTimeMillis();
                if (checkCorrect(target5)) {
                    //showArray(target);
                    fw.write(i + "," + (end - start) + "\n");
                    System.out.print(i + "," + (end - start) + "\n");
                } else {
                    fw.write(",\n");
                    System.out.print("Error!\n");
                }
                fw.flush();
            }
            System.out.println("存檔完成");
        } catch (IOException ex) {
            System.out.println("存檔失敗：" + ex.getLocalizedMessage());
        }

        /* 測試區塊二 
        *
        * 排序法：merge, quick, heap
        * 資料量：100000 ~ 19999999
        * 資料初始狀態：隨機順序
         */
        try (FileWriter fw = new FileWriter("c3_1000r.csv")) {
            for (int ii = 0; ii < num2; ii++) {
                int i = (int) (100000 + Math.random() * 19900000),
                        seed = new Random().nextInt();
                int[] target1 = createArray(i, seed),
                        target2 = createArray(i, seed),
                        target3 = createArray(i, seed);
                start = System.currentTimeMillis();
                mergeSort(target1,1,target1.length-1);
                end = System.currentTimeMillis();
                if (checkCorrect(target1)) {
                    //showArray(target);
                    fw.write(i + "," + (end - start) + ",");
                    System.out.print(i + "," + (end - start) + ",");
                } else {
                    fw.write(",,");
                    System.out.print("Error!,");
                }
                start = System.currentTimeMillis();
                quickSort(target2,1,target2.length-1);
                end = System.currentTimeMillis();
                if (checkCorrect(target2)) {
                    //showArray(target2);
                    fw.write(i + "," + (end - start) + ",");
                    System.out.print(i + "," + (end - start) + ",");
                } else {
                    fw.write(",,");
                    System.out.print("Error!,");
                }
                start = System.currentTimeMillis();
                heapSort(target3);
                end = System.currentTimeMillis();
                if (checkCorrect(target3)) {
                    //showArray(target);
                    fw.write(i + "," + (end - start) + "\n");
                    System.out.print(i + "," + (end - start) + "\n");
                } else {
                    fw.write(",\n");
                    System.out.print("Error!\n");
                }
                fw.flush();
            }
            System.out.println("存檔完成");
        } catch (IOException ex) {
            System.out.println("存檔失敗：" + ex.getLocalizedMessage());
        }

        /* 測試區塊三 
        *
        * 排序法：merge, quick, heap
        * 資料量：100000 ~ 19999999
        * 資料初始狀態：遞增 (排序完成狀態)
         */
        try (FileWriter fw = new FileWriter("c3_1000r_incData.csv")) {
            for (int ii = 0; ii < num3; ii++) {
                int i = (int) (100000 + Math.random() * 19900000);
                int[] target1 = createIncreasingArray(i),
                        target2 = createIncreasingArray(i),
                        target3 = createIncreasingArray(i);
                start = System.currentTimeMillis();
                mergeSort(target1,1,target1.length-1);
                end = System.currentTimeMillis();
                if (checkCorrect(target1)) {
                    //showArray(target1);
                    fw.write(i + "," + (end - start) + ",");
                    System.out.print(i + "," + (end - start) + ",");
                } else {
                    fw.write(",,");
                    System.out.print("Error!,");
                }
                start = System.currentTimeMillis();
                quickSort(target2,1,target2.length-1);
                end = System.currentTimeMillis();
                if (checkCorrect(target2)) {
                    //showArray(target2);
                    fw.write(i + "," + (end - start) + ",");
                    System.out.print(i + "," + (end - start) + ",");
                } else {
                    fw.write(",,");
                    System.out.print("Error!,");
                }
                start = System.currentTimeMillis();
                heapSort(target3);
                end = System.currentTimeMillis();
                if (checkCorrect(target3)) {
                    //showArray(target3);
                    fw.write(i + "," + (end - start) + "\n");
                    System.out.print(i + "," + (end - start) + "\n");
                } else {
                    fw.write(",\n");
                    System.out.print("Error!\n");
                }
                fw.flush();
            }
            System.out.println("存檔完成");
        } catch (IOException ex) {
            System.out.println("存檔失敗：" + ex.getLocalizedMessage());
        }

        /* 測試區塊四 
        *
        * 排序法：insertion, selection, merge, quick, heap
        * 資料量：100000 ~ 19999999
        * 資料初始狀態：遞減 (初始排序與目標排序完全相反)
         */
        try (FileWriter fw = new FileWriter("c3_1000r_decData.csv")) {
            for (int ii = 0; ii < num4; ii++) {
                int i = (int) (100000 + Math.random() * 19900000);
                int[] target1 = createDecreasingArray(i),
                        target2 = createDecreasingArray(i),
                        target3 = createDecreasingArray(i);
                start = System.currentTimeMillis();
                mergeSort(target1,1,target1.length-1);
                end = System.currentTimeMillis();
                if (checkCorrect(target1)) {
                    //showArray(target1);
                    fw.write(i + "," + (end - start) + ",");
                    System.out.print(i + "," + (end - start) + ",");
                } else {
                    fw.write(",,");
                    System.out.print("Error!,");
                }
                start = System.currentTimeMillis();
                quickSort(target2,1,target2.length-1);
                end = System.currentTimeMillis();
                if (checkCorrect(target2)) {
                    //showArray(target2);
                    fw.write(i + "," + (end - start) + ",");
                    System.out.print(i + "," + (end - start) + ",");
                } else {
                    fw.write(",,");
                    System.out.print("Error!,");
                }
                start = System.currentTimeMillis();
                heapSort(target3);
                end = System.currentTimeMillis();
                if (checkCorrect(target3)) {
                    //showArray(target3);
                    fw.write(i + "," + (end - start) + "\n");
                    System.out.print(i + "," + (end - start) + "\n");
                } else {
                    fw.write(",\n");
                    System.out.print("Error!\n");
                }
                fw.flush();
            }
            System.out.println("存檔完成");
        } catch (IOException ex) {
            System.out.println("存檔失敗：" + ex.getLocalizedMessage());
        }

    }

    /* 建構內容為隨機的陣列 */
    private static int[] createArray(int number, long seed) {
        Random random = new Random();
        if (number > 0) {
            int[] array = new int[number + 1];
            for (int i = 1; i <= number; i++) {
                array[i] = random.nextInt();
            }
            return array;
        }
        return new int[1];
    }

    /* 建構內容遞增的陣列 */
    private static int[] createIncreasingArray(int number) {
        if (number > 0) {
            int[] array = new int[number + 1];
            array[1] = 0;
            int num = 0;
            for (int i = 2; i <= number; i++) {
                num += (int) (Math.random() * 10);
                array[i] = num;
            }
            return array;
        }
        return new int[1];
    }

    /* 建構內容遞減的陣列 */
    private static int[] createDecreasingArray(int number) {
        if (number > 0) {
            int[] array = new int[number + 1];
            array[1] = 0;
            int num = 0;
            for (int i = 2; i <= number; i++) {
                num -= (int) (Math.random() * 10);
                array[i] = num;
            }
            return array;
        }
        return new int[1];
    }

    /* 輸出陣列內容 */
    private static void showArray(int[] target) {
        for (int i = 1; i < target.length; i++) {
            System.out.println(i + ". " + target[i]);
        }
    }

    /*檢查陣列是否成功排序*/
    private static boolean checkCorrect(int[] target) {
        for (int i = 1; i < target.length - 1; i++) {
            if (target[i] > target[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private static void insertionSort(int[] target) {
        int insertNumber, j;
        for (int i = 2; i < target.length; i++) {
            insertNumber = target[i];
            target[0] = insertNumber;
            j = i - 1;
            while (target[j] > insertNumber) {
                target[j + 1] = target[j--];
            }
            target[j + 1] = insertNumber;
        }
    }

    private static void selectionSort(int[] target) {
        int size = target.length - 1, minValue, minPointer;
        for (int i = 1; i < size; i++) {
            minValue = target[i];
            minPointer = i;
            for (int j = i + 1; j <= size; j++) {
                if (target[j] < minValue) {
                    minPointer = j;
                    minValue = target[j];
                }
            }
            target[minPointer] = target[i];
            target[i] = minValue;
        }
    }

    private static void mergeSort(int[] target, int left, int right) {
        if (left < right) {
            int k = (left + right) / 2;
            mergeSort(target, left, k);
            mergeSort(target, k + 1, right);
            merge(target, left, k, target, k + 1, right, target);
        }
    }

    private static void merge(int[] array1, int left1, int right1,
            int[] array2, int left2, int right2, int[] target) {
        int count1 = left1, count2 = left2, count = 0;
        int[] temp = new int[right1 + right2 - left1 - left2 + 2];
        while (count1 <= right1 && count2 <= right2) {
            if (array1[count1] < array2[count2]) {
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

    private static void quickSort(int[] target, int left, int right) {
        if (left < right) {
            int middle = partition(target, left, right);
            quickSort(target, left, middle - 1);
            quickSort(target, middle + 1, right);
        }
    }

    private static int partition(int[] target, int left, int right) {
        int random = (int) (Math.random() * (right-left)) + left;
        int tmp = target[random];
        target[random] = target[right];
        target[right] = tmp;
        int count = left - 1;
        for (int i = left; i < right; i++) {
            if (target[i] <= target[right]) {
                count++;
                tmp = target[count];
                target[count] = target[i];
                target[i] = tmp;
            }
        }
        tmp = target[++count];
        target[count] = target[right];
        target[right] = tmp;
        return count;
    }

    private static void heapSort(int[] target) {
        int numberOfNode = target.length - 1;
        int root = numberOfNode / 2;
        int pointer = numberOfNode;
        while (root > 0) {
            shift(target, root--, numberOfNode);
        }

        while (pointer > 0) {
            int temp = target[1];
            target[1] = target[pointer];
            target[pointer--] = temp;
            shift(target, 1, pointer);
        }
    }

    private static void shift(int[] target, int root, int numberOfNode) {
        int rootPointer = root, child = 2 * root, rootValue = target[root];
        while (child <= numberOfNode) {
            if (child < numberOfNode) {
                if (target[child] < target[child + 1]) {
                    child++;
                }
            }
            if (rootValue > target[child]) {
                break;
            }
            target[rootPointer] = target[child];
            rootPointer = child;
            child = 2 * child;
        }
        target[rootPointer] = rootValue;
    }

}
