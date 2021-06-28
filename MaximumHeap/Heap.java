package com.sample;

class Heap {

    /*類別成員*/
    int[] data, startPosition;
    int binaryDigit = 1;

    public Heap(int[] data) {
        /*初始化區塊*/
        int needSpace = 1, len = data.length; //needSpace：heap所需的陣列長度
        for (int i = 2; i < len; i *= 2) { // 計算 len 轉換為2進制後的位元長度
            binaryDigit++;
        }
        startPosition = new int[binaryDigit]; // 儲存每層節點在heap中的起始座標
        for (int i = len; i > 1; i = i - i / 2) { // 計算heap所需的陣列長度
            needSpace += i;
        }

        /* 建立heap區塊：將節點依規則填滿，並計算相關參數 */
        if (len == 1) { // 若是資料長度只有 1 則無需建構 heap
            this.data = data;
            return;
        }
        this.data = new int[needSpace];
        for (int i = 0; i < len; i++) { // 將資料依序放入最底層節點
            this.data[needSpace - len + i] = data[i];
        }
        int nextLen = len - len / 2;
        int ii = binaryDigit;
        startPosition[--ii] = needSpace - len; // 記錄倒數第二層節點起始位置
        while (nextLen > 1) {
            // 記錄第二層至倒數第三層節點起始位置
            startPosition[--ii] = startPosition[ii + 1] - nextLen;
            // 依序計算第 ii 層對應子節點位置，及其資料該填入的內容
            for (int i = 0; i < nextLen; i++) {
                if (2 * i + 2 > len) { // 只有一個子節點的情況
                    this.data[startPosition[ii] + i] = this.data[startPosition[ii + 1] + 2 * i];
                } else { // 有兩個子節點的情況
                    this.data[startPosition[ii] + i] = maximum(this.data[startPosition[ii + 1] + 2 * i], this.data[startPosition[ii + 1] + 2 * i + 1]);
                }
            }
            len = nextLen;
            nextLen = nextLen - nextLen / 2; // 更新為下一個目標層的長度
        }
        this.data[0] = maximum(this.data[1], this.data[2]); // 填第一層的值
    }

    private int maximum(int num1, int num2) { // 回傳最大值
        return (num1 > num2) ? num1 : num2;
    }

    public void show() { // 整個 heap 輸出( debug 用 )
        for (int i = 0; i < data.length; i++) {
            System.out.print("|" + data[i]);
        }
        System.out.print("|");
    }

    public int findMaximum(int l, int r) { // 找出 l 和 r 之間位置元素的最大值
        /*變數宣告、初始化區塊*/
        //將 l 和 r 轉 2 進制，因為儲存時 index 是從 0 開始，所以實際 index 要 -1
        boolean[] lBin = toBinary(l - 1), rBin = toBinary(r - 1);
        // maximum 以最小整數進行初始化
        int position = 0, maximum = Integer.MIN_VALUE, target;
        boolean lastOne = true, lastZero = true;
        // position 記錄兩個數字轉為二進制後從第幾位開始不同
        while (position < binaryDigit && !(lBin[position] ^ rBin[position])) {
            position++;
        }
        // 有以下 7 種情形的節點需要被拿出來判斷是否為最大值
        for (int i = binaryDigit - 1; i > position; i--) {
            if (lBin[i] && lastOne) {
                /* Case 1： lBin 中最後的 0 */
                // => 選最後的 0 出現的那一個節點
                target = 0;
                for (int j = 0; j <= i; j++) {
                    if (lBin[j]) {
                        target = target * 2 + 1;
                    } else {
                        target *= 2;
                    }
                }
                if (data[target + startPosition[i]] > maximum) {
                    maximum = data[target + startPosition[i]];
                }
                lastOne = false;
            } else if (!(lastOne || lBin[i])) {
                /* Case 2： lBin 中其他的 1 */
                // => 選把第 i 格的 0 改成 1 後代表的節點
                target = 0;
                for (int j = 0; j < i; j++) {
                    if (lBin[j]) {
                        target = target * 2 + 1;
                    } else {
                        target *= 2;
                    }
                }
                target = target * 2 + 1;
                if (data[target + startPosition[i]] > maximum) {
                    maximum = data[target + startPosition[i]];
                }
            }
            if (!rBin[i] && lastZero) {
                /* Case 3： rBin 中最後的 1 */
                // => 選最後的 1 出現的那一個節點
                target = 0;
                for (int j = 0; j <= i; j++) {
                    if (rBin[j]) {
                        target = target * 2 + 1;
                    } else {
                        target *= 2;
                    }
                }
                if (data[target + startPosition[i]] > maximum) {
                    maximum = data[target + startPosition[i]];
                }
                lastZero = false;

            } else if (!lastZero && rBin[i]) {
                /* Case 4： rBin 中其他的0 */
                // => 選把第 i 格的 1 改成 0 後代表的節點
                target = 0;
                for (int j = 0; j < i; j++) {
                    if (rBin[j]) {
                        target = target * 2 + 1;
                    } else {
                        target *= 2;
                    }
                }
                target = target * 2;
                if (data[target + startPosition[i]] > maximum) {
                    maximum = data[target + startPosition[i]];
                }

            }
        }
        /* 處理 lBin 的最後的 1 和 rBin 的最後的 0 並沒有出現的情況*/
        if (lastOne && lastZero) {
            /* Case 5： 兩個都沒出現 */
            // =>直接選 l 和 r 分開的節點
            target = 0;
            if (position == 0) {
                return data[0];
            }
            for (int j = 0; j < position; j++) {
                if (lBin[j]) {
                    target = target * 2 + 1;
                } else {
                    target *= 2;
                }
            }
            if (data[target + startPosition[position - 1]] > maximum) {
                maximum = data[target + startPosition[position - 1]];
            }
        } else if (lastOne) {
            /* Case 6： 只有 lBin 的最後的 1 沒有出現 */
            // => 選分叉後向右碰到的第一個節點
            target = 0;
            for (int j = 0; j <= position; j++) {
                if (lBin[j]) {
                    target = target * 2 + 1;
                } else {
                    target *= 2;
                }
            }
            if (data[target + startPosition[position]] > maximum) {
                maximum = data[target + startPosition[position]];
            }
        } else if (lastZero) {
            /* Case 7： 只有 rBin 的最後的 0 沒有出現 */
            // => 選分叉後向左碰到的第一個節點
            target = 0;
            for (int j = 0; j <= position; j++) {
                if (rBin[j]) {
                    target = target * 2 + 1;
                } else {
                    target *= 2;
                }
            }
            if (data[target + startPosition[position]] > maximum) {
                maximum = data[target + startPosition[position]];
            }
        }
        return maximum;
    }

    private boolean[] toBinary(int number) {
        // 將數字轉為代表 2 進制的 boolean 陣列
        // result[i] 代表第i位的值，true 代表 1，false 代表 0
        boolean[] result = new boolean[binaryDigit];
        for (int i = 0; i < binaryDigit; i++) {
            result[binaryDigit - i - 1] = (number) % 2 != 0;
            number /= 2;
        }
        return result;
    }
}
