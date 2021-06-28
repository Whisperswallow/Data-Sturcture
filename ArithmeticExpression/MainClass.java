package com.sample;

import java.util.*;

/*
    Design and implement efficient data structure and algorithm 
    for the evaluation of arithmetic expressions and assignment 
    operation, in infix format.
    author: Z. H. Tsai 
    std_id: 0852223
    date: 2019/11/14
 */
public class MainClass {

    private static Stack<Double> numbers;
    private static Stack<Character> operations;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[] input;
        Double[] variableData = new Double[26];
        char oldOperation;
        String inputLine, outString;
        boolean negative, preIsNum, assign, nowAssign;
        int variableIndex;
        while (scanner.hasNext()) {
            numbers = new Stack<>();
            operations = new Stack<>();
            assign = true;
            negative = false;
            preIsNum = false;
            nowAssign = false;
            variableIndex = -1;
            inputLine = scanner.nextLine();
            outString = "";
            if (inputLine.equalsIgnoreCase("EOF")) {
                break;
            }
            input = (inputLine + "$").toCharArray();
            operations.push('$');
            for (int i = 0; i < input.length; i++) {
                // 判斷、處理輸入
                if (input[i] <= '9' && input[i] >= '0') { // 數字
                    assign = false;
                    if (preIsNum) {
                        numbers.push(10 * numbers.pop() + (input[i] - '0'));
                    } else {
                        numbers.push((double) (input[i] - '0'));
                    }
                    preIsNum = true;
                } else if (input[i] == '(') { // 左括號
                    assign = false;
                    operations.push(input[i]);
                    preIsNum = false;
                } else if (input[i] == ')') { // 右括號
                    assign = false;
                    if (negative) {
                        numbers.push(-numbers.pop());
                        negative = false;
                    }
                    oldOperation = operations.pop();
                    while (oldOperation != '(') {
                        numbers.push(doOperation(oldOperation, numbers.pop()));
                        oldOperation = operations.pop();
                    }
                    preIsNum = true;
                } else if (input[i] == '$') { // 終止符號
                    if (negative) {
                        numbers.push(-numbers.pop());
                    }
                    oldOperation = operations.pop();
                    while (oldOperation != '$') {
                        numbers.push(doOperation(oldOperation, numbers.pop()));
                        oldOperation = operations.pop();
                    }
                    double result=0;
                    try {
                        result = numbers.pop();
                    } catch (NullPointerException e) {
                        if (variableIndex != -1 && !nowAssign) {
                            if (variableData[variableIndex] != null) {
                                result=variableData[variableIndex];
                            } else {
                                System.out.println("error : " + (char) (variableIndex + 'a') + " is Undefined variable.");
                                break;
                            }
                            variableIndex = -1;
                        }
                    }
                    System.out.println(outString + result);
                    if (variableIndex != -1) {
                        variableData[variableIndex] = result;
                    }
                    break;
                } else if (input[i] == '=') { // 等號
                    assign = false;
                    preIsNum = false;
                    nowAssign = true;
                } else if (input[i] <= 'z' && input[i] >= 'a') { // 變數
                    if (assign) {
                        outString += input[i] + " = ";
                        variableIndex = input[i] - 'a';
                    } else {
                        if (variableData[input[i] - 'a'] != null) {
                            numbers.push(variableData[input[i] - 'a']);
                            preIsNum = true;
                        } else {
                            System.out.println("error : " + input[i] + " is Undefined variable.");
                            break;
                        }
                    }
                    assign = false;
                } else if (priority(input[i]) != 0) { // 運算符號
                    assign = false;
                    if (variableIndex != -1 && !nowAssign) { // 判斷是給值或變數運算
                        if (variableData[variableIndex] != null) { 
                            numbers.push(variableData[variableIndex]);
                            preIsNum = true;
                        } else { // 變數未定義
                            System.out.println("error : " + (char) (variableIndex + 'a') + " is Undefined variable.");
                            break;
                        }
                        variableIndex = -1;
                        outString = "";
                    }
                    if (input[i] == '-' && !preIsNum) { // 判斷負號
                        if (negative) {
                            System.out.println("Wrong input.");
                            break;
                        } else {
                            negative = true;
                        }
                        continue;
                    }
                    if (!preIsNum) {
                        System.out.println("Wrong input.");
                        break;
                    }
                    if (negative) { // 負號
                        numbers.push(-numbers.pop());
                        negative = false;
                    }

                    oldOperation = operations.pop();
                    while (priority(input[i]) >= priority(oldOperation)) {
                        numbers.push(doOperation(oldOperation, numbers.pop()));
                        oldOperation = operations.pop();
                    }
                    operations.push(oldOperation);
                    operations.push(input[i]);
                    preIsNum = false;
                } else {
                    // 判斷非已實作 operation 為非法輸入或空白
                    if (input[i] == ' ') { // 將空白忽略不看
                        continue;
                    }
                    System.out.println("Wrong input.");
                    break;
                }
            }
        }
    }

    // 決定 priority，越小越優先
    private static int priority(char operation) {
        int priority = 0;
        switch (operation) {
            case '$':
            case '(':
                priority++;
            case '+':
            case '-':
                priority++;
            case '*':
            case '/':
                priority++;
            case '^':
                priority++;
            case ' ':
                break;
            default:
                System.out.println("Unsupport operation.");
        }
        return priority;
    }

    // 進行運算
    private static double doOperation(char operation, double number) {
        double oldNumber = numbers.pop();
        switch (operation) {
            case '+':
                oldNumber += number;
                break;
            case '-':
                oldNumber -= number;
                break;
            case '*':
                oldNumber *= number;
                break;
            case '/':
                oldNumber /= number;
                break;
            case '^':
                if (number - (int) number != 0) {
                    System.out.println("Only support for positive integer power.");
                }
                if (number < 0) {
                    number = -number;
                    oldNumber = 1 / oldNumber;
                }
                double temp = oldNumber;
                while (--number > 0) {
                    oldNumber *= temp;
                }
                break;
            default:
                System.out.println("Wrong input.");
        }
        return oldNumber;
    }
}
