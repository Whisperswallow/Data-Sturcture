package com.sample;

import java.util.Scanner;

/*
    Design and implement efficient data structure and algorithm for 
    finding shortest path from the start cell s to the goal cell t 
    in a maze of dimension m × n.
    author: Z. H. Tsai 
    std_id: 0852223
    date: 2019/10/25
 */
public class MainClass {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Maze targetMaze;
        int row, column, position;
        /*
        while ((row = scanner.nextInt()) != 0) {
            column = scanner.nextInt();
            targetMaze = new Maze(row, column);

            for (int i = 1; i <= row; i++) {
                while ((position = scanner.nextInt()) != 0) {
                    targetMaze.setElement(i * (column + 2)
                            + position, scanner.next().charAt(0));
                }
            }
            targetMaze.showMaze();
            targetMaze.solve();
            targetMaze.showSolvedMaze();
            targetMaze.showPath();
        }*/
        
        /* 測試大型隨機生成迷宮用 */
        
        row = 1;
        column = 117000000;
        targetMaze = randomMaze(row,column,0);
        //targetMaze.showMaze();
        targetMaze.solve();
        //targetMaze.showSolvedMaze();
        //targetMaze.showPath();
        
    }
    
    // 測試用，給定密度，隨機生成迷宮
    private static Maze randomMaze(int row, int column, double dense) {
        int startRow = (int) (Math.random() * (row) + 1);
        int startColumn = (int) (Math.random() * (column) + 1);
        int terminateRow = 0, terminateColumn = 0;
        do {
            terminateRow = (int) (Math.random() * (row) + 1);
            terminateColumn = (int) (Math.random() * (column) + 1);
        } while (terminateRow == startRow && terminateColumn == startColumn);
        if (dense < 0) {
            dense = 0;
        } else if (dense > 1) {
            dense = 1;
        }

        Maze maze = new Maze(row, column);
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= column; j++) {
                if (i == startRow && j == startColumn) {
                    maze.setElement(i * (column + 2) + j, 'S');
                } else if ((i == terminateRow && j == terminateColumn)) {
                    maze.setElement(i * (column + 2) + j, 'T');
                } else if (Math.random() < dense) {
                    maze.setElement(i * (column + 2) + j, 'X');
                }
            }
        }
        return maze;
    }

}

class Maze {

    private final int[] maze;
    private final int n1, n2;
    private final int WALL = -1, INFINITY;
    private int start = 0, terminate = 0;

    public Maze(int row, int column) {
        n1 = row + 2;
        n2 = column + 2;
        maze = new int[n1 * n2];
        INFINITY = row * column + 1;
        for (int i = 0; i < n1 * n2; i++) {
            if (i < n2 || i % n2 == 0 || (i + 1) % n2 == 0 || i >= (n1 - 1) * n2) {
                maze[i] = WALL;
            } else {
                maze[i] = INFINITY;
            }
        }
    }
    
    /* 設值 */

    // 設定迷宮內容
    public void setElement(int position, char element) {
        int number;
        switch (element) {
            case 'x':
            case 'X':
                number = WALL;
                break;
            case 't':
            case 'T':
                if (terminate != 0) {
                    System.out.println("Can't assign terminate twice!");
                    return;
                }
                terminate = position;
                number = INFINITY;
                break;
            case 's':
            case 'S':
                if (start != 0) {
                    System.out.println("Can't assign start twice!");
                    return;
                }
                start = position;
                number = 0;
                break;
            default:
                System.out.println(element + "is illegal element!");
                return;
        }
        maze[position] = number;
    }

    // 用來更新最短距離
    public void setElement(int position, int element) {
        maze[position] = element;
    }

    /* 取值 */
    
    public int getElement(int position) {
        return maze[position];
    }

    public int getTerminate() {
        return terminate;
    }

    public int getStart() {
        return start;
    }

    public int getLength() {
        return n1;
    }

    public int getWidth() {
        return n2;
    }

    /* 迷宮的方法 */
    
    // 解迷宮，設定個格子最短路徑
    public void solve() {
        Queue waitQueue = new Queue(2 * ((n1 < n2) ? n1 : n2) + 1);
        int center;
        int[] neighbor = new int[4];
        waitQueue.push(start);
        while (!waitQueue.isEmpty()) {
            center = waitQueue.pop();

            neighbor[0] = center - n2; // 上方
            neighbor[1] = center + n2; // 下方
            neighbor[2] = center - 1; // 左方
            neighbor[3] = center + 1; // 右方

            for (int i = 0; i < 4; i++) {
                if (maze[neighbor[i]] > maze[center] + 1) {
                    maze[neighbor[i]] = maze[center] + 1;
                    waitQueue.push(neighbor[i]);
                }
            }
        }
    }

    // 輸出迷宮框架
    public void showMaze() {
        char c;
        System.out.println("");
        System.out.println("");
        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < n2; j++) {
                if (maze[i * n2 + j] == -1) {
                    c = 'X';
                } else if (i * n2 + j == start) {
                    c = 'S';
                } else if (i * n2 + j == terminate) {
                    c = 'T';
                } else {
                    c = ' ';
                }
                System.out.print("  " + c + "  ");
            }
            System.out.println("");
            System.out.println("");
        }
    }

    // 輸出迷宮的格子的最短路徑
    public void showSolvedMaze() {
        // 2位數內可顯示，3位數會過度壅擠，超過4位數會導致格子偏移
        System.out.println("");
        System.out.println("");
        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < n2; j++) {
                if (maze[i * n2 + j] == -1) {
                    System.out.print("  X  ");
                } else if (i * n2 + j == start) {
                    System.out.print("  S  ");
                } else if (i * n2 + j == terminate) {
                    System.out.print("  T  ");
                } else if (maze[i * n2 + j] == INFINITY) {
                    System.out.print("  -  ");
                } else if (maze[i * n2 + j] < 100) {
                    System.out.print("\033[31m" //改變輸出顏色(紅)
                            + String.format("%3d  ", maze[i * n2 + j])
                            + "\033[0m"); //恢復輸出顏色(黑)
                } else if (maze[i * n2 + j] < 10000) {
                    System.out.print("\033[31m"
                            + String.format("%4d ", maze[i * n2 + j])
                            + "\033[0m"
                    );
                } else {
                    System.out.print(maze[i * n2 + j]);
                }
            }
            System.out.println("");
            System.out.println("");
        }
    }

    // 輸出與最短路徑相關的內容
    public void showPath() { // need rewrite
        if (maze[terminate] == INFINITY) {
            System.out.println("This maze has no solution.");
            return;
        }
        if (n1 * n2 < 300) { // 理論最大可接受到398，比398大可能導致count超過整數上限
            System.out.print("有" + countPath(start, terminate)
                    + "種不同的最短路徑，");
        } else {
            System.out.println("迷宮過大，略過路徑總數計算");
        }
        System.out.println("最短要走" + maze[terminate] + "步");
        listPath(start, terminate);
    }

    // 列出其中一條最短路徑
    private void listPath(int s, int t) {
        if (s == t) {
            return;
        }
        int[] neighbors = {t - n2, t + n2, t - 1, t + 1};
        for (int i = 0; i < 4; i++) {
            if (maze[neighbors[i]] == maze[t] - 1) {
                listPath(s, neighbors[i]);
                System.out.print("(" + neighbors[i] % n2
                        + "," + neighbors[i] / n2 + ")->");
                if (maze[neighbors[i]] % 15 == 14) {
                    System.out.println("");
                }
                break;
            }
        }
        if (t == terminate) {
            System.out.println("(" + terminate % n2 + "," + terminate / n2 + ")");
        }
    }

    // 計算最短路徑的總數
    private int countPath(int s, int t) {
        int counter = 0;
        if (s == t) {
            return 1;
        }
        int[] neighbors = {t - n2, t + n2, t - 1, t + 1};
        for (int i = 0; i < 4; i++) {
            if (maze[neighbors[i]] == maze[t] - 1) {
                counter += countPath(s, neighbors[i]);
            }
        }
        return counter;
    }
}

class Queue {

    private final int[] data;
    private int inPointer, outPointer;

    public Queue(int length) {
        data = new int[length];
        inPointer = 0;
        outPointer = length - 1;
    }

    // 加入內容
    public void push(int element) {
        if (outPointer == (inPointer + 1) % data.length) {
            System.out.println("The queue is full!");
            return;
        }
        data[inPointer] = element;
        inPointer = (inPointer + 1) % data.length;
    }

    // 取用內容
    public int pop() {
        if (isEmpty()) {
            System.out.println("The queue is empty!");
            return -1;
        }
        outPointer = (outPointer + 1) % data.length;
        return data[outPointer];
    }

    public boolean isEmpty() {
        return inPointer == (outPointer + 1) % data.length;
    }

    public boolean isFull() {
        return outPointer == (inPointer + 1) % data.length;
    }
}
