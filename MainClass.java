package com.sample;

import java.util.*;

public class MainClass {

    /*
        Convert a date in yyyy/mm/dd into “name of the month” dd, yyyy.
        author: Z. H. Tsai 
        std_id: 0852223
        date: 2018/09/09
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Integer year, month, day;
        Date date = null;
        while (sc.hasNext()) { //當輸入仍有內容繼續迴圈

            /* 清空上一輪資料 */
            year = 0;
            month = 0;
            day = 0;
            date = null;

            /* 接收新資料 */
            String[] InputDate = sc.next().split("/");

            /* 脫離條件 */
            if (InputDate[0].equalsIgnoreCase("EOF")) {
                break; //當接收到 EOF 字串則跳離迴圈
            }

            /* 將資料轉換成用來處理的格式 */
            try {
                year = new Integer(InputDate[0]);
                month = new Integer(InputDate[1]);
                day = new Integer(InputDate[2]);
                date = new Date(year, month, day);
                System.out.println(date);
            } catch (NumberFormatException NFException) { 
                //格式錯誤：輸入非數字
                System.out.println("This process can only deal with "
                        + "numerical data(Integer)! " 
                        + NFException.getMessage());
            } catch (ArrayIndexOutOfBoundsException AIOBException) { 
                //格式錯誤：輸入太短
                System.out.println("Input data format is not match,"
                        + " please retry after recheck!");
            }

        }
    }

}

/* 建立 Date 類別 */
class Date {

    /* 變數 */
    private final int year, month, day;

    /* 
    * Constructor:
    *   parameter:
    *       year : int
    *       month : int
    *       day : int
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /* 檢查輸入日期是否合理 
    *   input : 
    *   output : boolean ~ Whether the date legal or not.
     */
    private boolean checkDate() {
        if (year <= 0 || month <= 0 || day <= 0) { //先去掉年月日不為正整數
            return false;
        }
        if (year == 1752 && month == 7) { //1752年7月為特別，少了11天
            return day == 1 || day == 2 || (day >= 14 && day <= 31); 
        }
        switch (month) { //對2月、大月、小月分開檢查
            case 2:
                if (isLeapYear()) { //先判斷是否為閏年
                    return day <= 29;
                } else {
                    return day <= 28;
                }
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return day <= 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return day <= 30;
            default:
                return false; //不是1~12月的其他輸入皆為不合理
        }
    }

    /* 閏年判斷 
    *   input : 
    *   output : boolean ~ This year is leap or common year.
     */
    private boolean isLeapYear() {
        if (year <= 1752) { //1752年以前，使用Julian calendar以致閏年計算方式錯誤
            return year % 4 == 0; 
        } else {
            return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
        }
    }

    /* Override toString() 方便輸出 */
    @Override
    public String toString() {
        if (checkDate()) { // 呼叫 checkDate()檢查日期合理性
            return String.format("%9s %02d, %04d.", Fullname(), day, year);
        } else { //日期不合理則不輸出日期，改為錯誤訊息
            return "Illegal Date!";
        }
    }

    /* 將月份轉換為英文全名 
    *   input : 
    *       month : int
    *   output : String ~ Fullname of month.
     */
    private String Fullname() {
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "";
        }
    }
}
