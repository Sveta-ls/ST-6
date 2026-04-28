package com.mycompany.app;
import javax.swing.*;
import java.awt.GridLayout;

public class Program {
    public static void main(String[] args) {
        JFrame frame = new JFrame("TicTacToe");
        frame.add(new TicTacToePanel(new GridLayout(3,3)));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(5, 5, 500, 500);
        frame.setVisible(true);
    }
}