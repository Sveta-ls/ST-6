package com.mycompany.app;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToePanel extends JPanel implements ActionListener {
    private Game game;
    private TicTacToeCell[] cells = new TicTacToeCell[9];

    public TicTacToePanel(GridLayout layout) {
        super(layout);
        for(int i=0; i<9; i++) {
            cells[i] = new TicTacToeCell(i, i%3, i/3);
            cells[i].addActionListener(this);
            add(cells[i]);
        }
        game = new Game();
        game.cplayer = game.player1;
    }

    public void actionPerformed(ActionEvent ae) {
        int i = 0;
        for(TicTacToeCell jb : cells) {
            if(ae.getSource() == jb) jb.setMarker(Character.toString(game.cplayer.symbol));
            game.board[i++] = jb.getMarker();
        }
        if(game.cplayer == game.player1) {
            game.player2.move = game.MiniMax(game.board, game.player2);
            game.cplayer = game.player2;
            if(game.player2.move > 0) cells[game.player2.move-1].doClick();
        } else {
            game.cplayer = game.player1;
        }
        game.state = game.checkState(game.board);
        if(game.state != State.PLAYING) {
            String msg = (game.state == State.DRAW) ? "Ничья" : "Выиграли " + (game.state == State.XWIN ? "крестики" : "нолики");
            JOptionPane.showMessageDialog(null, msg);
            System.exit(0);
        }
    }
}