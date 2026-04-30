package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.awt.GridLayout;

public class ProgramTest {
    
    private Game game;
    
    @BeforeEach
    void setUp() {
        System.setProperty("java.awt.headless", "true");
        game = new Game();
        game.player1.symbol = 'X';
        game.player2.symbol = 'O';
    }

    @Test
    void testCheckStateAllWins() {
        game.symbol = 'X';
        char[] hBoard = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(hBoard));
        
        char[] vBoard = {'X', ' ', ' ', 'X', ' ', ' ', 'X', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(vBoard));

        char[] d1Board = {'X', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X'};
        assertEquals(State.XWIN, game.checkState(d1Board));
        char[] d2Board = {' ', ' ', 'X', ' ', 'X', ' ', 'X', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(d2Board));
    }

    @Test
    void testCheckStateOWin() {
        game.symbol = 'O';
        char[] board = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testCheckStateDrawFull() {
        char[] board = {
            'X', 'O', 'X',
            'X', 'O', 'O',
            'O', 'X', 'X'
        };
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(board));
    }
    @Test
    void testEvaluatePositionDetailed() {
        char[] xWinBoard = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        // Оценка для победителя
        assertEquals(100, game.evaluatePosition(xWinBoard, game.player1));
        // Оценка для проигравшего
        assertEquals(-100, game.evaluatePosition(xWinBoard, game.player2));
        
        char[] emptyBoard = new char[9];
        for(int i=0; i<9; i++) emptyBoard[i] = ' ';
        assertEquals(-1, game.evaluatePosition(emptyBoard, game.player1));
    }

    @Test
    void testMiniMaxWinningMove() {
        game.board = new char[]{
            'O', 'O', ' ',
            'X', 'X', ' ',
            ' ', ' ', ' '
        };
        int move = game.MiniMax(game.board, game.player2);
        assertEquals(3, move);
    }

    @Test
    void testUtilityMethods() {
        char[] charBoard = {'X', 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        int[] intBoard = {1, 0, 0, 0, 0, 0, 0, 0, 0};
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
       
        assertDoesNotThrow(() -> {
            Utility.print(charBoard);
            Utility.print(intBoard);
            Utility.print(moves);
        });
    }

    @Test
    void testTicTacToeCell() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(1, cell.getNum());
    }

    @Test
    void testPanelAndAction() {

        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3,3));
        assertNotNull(panel);
    
        TicTacToeCell firstCell = (TicTacToeCell) panel.getComponent(0);
        assertDoesNotThrow(() -> {
            firstCell.doClick();
        });
        assertEquals('X', firstCell.getMarker());
    }
}