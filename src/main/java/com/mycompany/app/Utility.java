package com.mycompany.app;
import java.util.ArrayList;

public class Utility {
    public static void print(char[] board) {
        System.out.println();
        for (char c : board) System.out.print(c + "-");
        System.out.println();
    }
    public static void print(int[] board) {
        System.out.println();
        for (int i : board) System.out.print(i + "-");
        System.out.println();
    }  
    public static void print(ArrayList<Integer> moves) {
        System.out.println();
        for (Integer move : moves) System.out.print(move + "-");
        System.out.println();
    }  
}