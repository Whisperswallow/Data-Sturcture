package com.sample;

import java.io.*;

/*
    Design and implement efficient data structure and algorithm for computing 
    Huffman code for each symbol in the input file. Then compress the input
    file, print out the compression ratio, and decode the encoded file.
    a set of m queries.
    author: Z. H. Tsai 
    std_id: 0852223
    date: 2019/11/14
 */
public class MainClass {

    public static int[] charTable = new int[128];
    public static String[] encodeList = new String[128];

    public static void main(String[] args) throws IOException {
        int ctSymbol = 0;
        String FilePath = "src/CNN20191211_short.txt";//"src/test.txt""src/data.txt""src/com/sample/MainClass.java"
        readFile(FilePath);
        System.out.println("\n---------------------------");
        // showTable(); // check charTable
        for (int i : charTable) {
            if (i != 0) {
                ctSymbol++;
            }
        }
        Pack[] tempArray = new Pack[ctSymbol];
        ctSymbol = 0;
        for (int i = 0; i < charTable.length; i++) {
            if (charTable[i] != 0) {
                tempArray[ctSymbol++] = new Pack((byte) i, charTable[i]);
            }
        }
        Heap dataHeap = new Heap(tempArray);
        // dataHeap.show(); // check dataHeap
        BinaryTree dataTree = new BinaryTree(dataHeap);
        // dataTree.show(dataTree.root); // check dataTree
        fillList(dataTree.root, new Stack());
        // showList(); // encodeList
        encodeFile(FilePath);

        decodeFile("src/encode.txt", dataTree);
    }

    private static void fillList(Pack pointer, Stack code) {
        if (pointer.leftChild == null) {
            encodeList[pointer.data] = code.getCode();
            return;
        }
        code.push(false);
        fillList(pointer.leftChild, code);
        code.pop();
        code.push(true);
        fillList(pointer.rightChild, code);
        code.pop();
    }

    private static void showTable() {
        for (int i = 0; i < 128; i++) {
            String out;
            if (charTable[i] != 0) {
                out = charOutput(i);
                System.out.println(out + " " + i + ":" + charTable[i]);
            }
        }
    }

    public static String charOutput(int c) {
        String out;
        switch ((char) c) {
            case (char) -1:
                out = "inner";
                break;
            case 32:
                out = "space";
                break;
            case 10:
                out = "\\n";
                break;
            case 9:
                out = "\\t";
                break;
            case 13:
                out = "\\v";
                break;
            default:
                out = (char) c + "";
                break;
        }
        return out;
    }

    private static void readFile(String path) throws IOException {
        try (FileReader fr = new FileReader(path)) {
            char in;
            while ((in = (char) fr.read()) != (char) -1) {
                System.out.print(in); //(int) in+ " "
                charTable[(int) in]++;
            }
        } catch (IOException e) {
            throw e;
        }
    }

    private static void encodeFile(String path) throws IOException {
        try (FileReader fr = new FileReader(path);
                BufferedWriter bw = new BufferedWriter(new FileWriter("src/encode.txt"))) {
            char in;
            while ((in = (char) fr.read()) != (char) -1) {
                bw.write(encodeList[in]);
            }
        } catch (IOException e) {
            throw e;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/encodeList.txt"))) {
            for (int i = 0; i < encodeList.length; i++) {
                if (encodeList[i] != null) {
                    bw.write(i+ ":" + encodeList[i]);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw e;
        }
    }

    private static void decodeFile(String path, BinaryTree dataTree) throws IOException {
        try (FileReader fr = new FileReader(path);
                BufferedWriter bw = new BufferedWriter(new FileWriter("src/decode.txt"))) {
            char in;
            Pack pointer = dataTree.root;
            while ((in = (char) fr.read()) != (char) -1) {
                if (pointer.leftChild == null) {
                    bw.write((char) pointer.data);
                    System.out.print((char) pointer.data);
                    pointer = dataTree.root;
                }
                if (in == '0') {
                    pointer = pointer.leftChild;
                }
                if (in == '1') {
                    pointer = pointer.rightChild;
                }
            }
            bw.write((char) pointer.data);
            System.out.print((char) pointer.data);
        } catch (IOException e) {
            throw e;
        }

    }

    private static void showList() {
        for (int i = 0; i < encodeList.length; i++) {
            if (encodeList[i] != null) {
                System.out.println(charOutput(i) + ":" + encodeList[i]);
            }
        }
    }
}
