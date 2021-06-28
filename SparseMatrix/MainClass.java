package com.sample;

import java.util.*;

/*
    Implement an efficient data structure linkedList for sparse matrices.
    author: Z. H. Tsai 
    std_id: 0852223
    date: 2019/11/01
 */
public class MainClass {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Matrix resultMatrix = null, secondMatrix;
        int row = 0, position;
        String opt;
        helpList();
        System.out.println("Enter a matrix:\n");
        while (resultMatrix == null) {
            try {
                row = scanner.nextInt();
                resultMatrix = new Matrix(row, scanner.nextInt());
                for (int i = 1; i <= row; i++) {

                    while ((position = scanner.nextInt()) != 0) {
                        resultMatrix.addNode(i, position, scanner.nextInt());
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong input, please enter again.");
                resultMatrix = null;
                while (scanner.hasNext()) {
                    scanner.next();
                }
                System.out.println("Enter a matrix:\n");
            }
        }
        System.out.println("The matrix you enter is \n");
        resultMatrix.show();
        System.out.print("The size of matrix is ");
        resultMatrix.showSize();
        while (true) {
            try {
                System.out.println("Enter your operation, enter \"Help\""
                        + " or \"H\" for help.");
                opt = scanner.next();
                if (opt.equalsIgnoreCase("add") || opt.equalsIgnoreCase("a")
                        || opt.equalsIgnoreCase("+")) {
                    System.out.println("Enter your second matrix:");
                    int secondRow = scanner.nextInt();
                    secondMatrix = new Matrix(secondRow, scanner.nextInt());
                    for (int i = 1; i <= secondRow; i++) {
                        while ((position = scanner.nextInt()) != 0) {
                            secondMatrix.addNode(i, position, scanner.nextInt());
                        }
                    }
                    resultMatrix = resultMatrix.add(secondMatrix);
                } else if (opt.equalsIgnoreCase("substract")
                        || opt.equalsIgnoreCase("s") || opt.equalsIgnoreCase("-")) {
                    System.out.println("Enter your second matrix:");
                    int secondRow = scanner.nextInt();
                    secondMatrix = new Matrix(secondRow, scanner.nextInt());
                    for (int i = 1; i <= secondRow; i++) {
                        while ((position = scanner.nextInt()) != 0) {
                            secondMatrix.addNode(i, position, -scanner.nextInt());
                        }
                    }
                    resultMatrix = resultMatrix.add(secondMatrix);
                } else if (opt.equalsIgnoreCase("transpose")
                        || opt.equalsIgnoreCase("t") || opt.equalsIgnoreCase("'")) {
                    resultMatrix = resultMatrix.transpose();
                } else if (opt.equalsIgnoreCase("multiply")
                        || opt.equalsIgnoreCase("m") || opt.equalsIgnoreCase(".")) {
                    System.out.println("Enter your second matrix:");
                    int secondRow = scanner.nextInt();
                    secondMatrix = new Matrix(secondRow, scanner.nextInt());
                    for (int i = 1; i <= secondRow; i++) {
                        while ((position = scanner.nextInt()) != 0) {
                            secondMatrix.addNode(i, position, scanner.nextInt());
                        }
                    }
                    resultMatrix = resultMatrix.multiply(secondMatrix);
                } else if (opt.equalsIgnoreCase("power")
                        || opt.equalsIgnoreCase("p") || opt.equalsIgnoreCase("^")) {
                    secondMatrix = resultMatrix;
                    System.out.println("Enter the power:");
                    int power = scanner.nextInt();
                    while (--power > 0) {
                        resultMatrix = resultMatrix.multiply(secondMatrix);
                    }
                } else if (opt.equalsIgnoreCase("clear") || opt.equalsIgnoreCase("c")
                        || opt.equalsIgnoreCase("clr")) {
                    System.out.println("Result is now clear.\nEnter a new matrix:");
                    row = scanner.nextInt();
                    resultMatrix = new Matrix(row, scanner.nextInt());
                    for (int i = 1; i <= row; i++) {
                        while ((position = scanner.nextInt()) != 0) {
                            resultMatrix.addNode(i, position, scanner.nextInt());
                        }
                    }
                } else if (opt.equalsIgnoreCase("help") || opt.equalsIgnoreCase("h")) {
                    helpList();
                } else if (opt.equalsIgnoreCase("exit") || opt.equalsIgnoreCase("e")) {
                    break;
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong input, please enter again.");
                while (scanner.hasNext()) {
                    scanner.next();
                }
            }
            System.out.println("The matrix you have now is");
            resultMatrix.show();
            System.out.print("The size of matrix is ");
            resultMatrix.showSize();

        }
    }

    private static void helpList() {
        System.out.println("         Provided operations\n"
                + "-------------------------------------\n"
                + "  Fullname │ Abbreviation │ Symbol  \n"
                + "-------------------------------------\n"
                + "    Add    │      A       │   +     \n"
                + " Substract │      S       │   -     \n"
                + " Transpose │      T       │   '     \n"
                + "  Multiply │      M       │   .     \n"
                + "   Power   │      P       │   ^     \n"
                + "   Clear   │      C       │  clr    \n"
                + "    Help   │      H       │         \n"
                + "    exit   │      E       │         \n");
    }
}
