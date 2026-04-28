package com.mycompany.app;
import javax.swing.JButton;
import java.awt.Font;

public class TicTacToeCell extends JButton {
    private int num;
    private int row;
    private int col;
    private char marker;

    public TicTacToeCell(int num, int x, int y) {
        this.num = num;
        this.row = y;
        this.col = x;
        this.marker = ' ';
        setText(Character.toString(marker));
        setFont(new Font("Arial", Font.PLAIN, 40));
    }
    public void setMarker(String m) {
        marker = m.charAt(0);
        setText(m);
        setEnabled(false);
    }
    public char getMarker() { return marker; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public int getNum() { return num; }
}