package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

public class AppTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testGameWinningConditions() {
        char[][] boards = {
            {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '}, // Row 0
            {' ', ' ', ' ', 'O', 'O', 'O', ' ', ' ', ' '}, // Row 1
            {'X', ' ', ' ', 'X', ' ', ' ', 'X', ' ', ' '}, // Col 0
            {' ', 'O', ' ', ' ', 'O', ' ', ' ', 'O', ' '}, // Col 1
            {'X', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X'}, // Diag 1
            {' ', ' ', 'O', ' ', 'O', ' ', 'O', ' ', ' '}  // Diag 2
        };
        for (char[] b : boards) {
            game.symbol = b[0] != ' ' ? b[0] : b[2]; 
            State s = game.checkState(b);
            assertTrue(s == State.XWIN || s == State.OWIN);
        }
    }

    @Test
    void testMiniMaxAndDraw() {
        game.board = new char[]{'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', ' '};
        game.player2.symbol = 'O';
        int move = game.MiniMax(game.board, game.player2);
        assertTrue(move > 0);

        char[] drawBoard = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(drawBoard));
    }

    @Test
    void testPanelAndAction() {
        // Покрытие TicTacToePanel и TicTacToeCell
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        
        ActionEvent event = new ActionEvent(panel.getComponent(0), ActionEvent.ACTION_PERFORMED, "");
        assertDoesNotThrow(() -> panel.actionPerformed(event));
        
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
    }

   @Test
    void testUtility() {
        Utility.print(new char[]{'A', 'B'});
        Utility.print(new int[]{1, 2, 3});
        Utility.print(new ArrayList<Integer>());

        Player p = new Player();
        p.symbol = 'X';
        game.symbol = 'X'; 
        
        char[] winBoard = {'X','X','X',' ',' ',' ',' ',' ',' '};
        assertEquals(100, game.evaluatePosition(winBoard, p));
        
        p.symbol = 'O';
        assertEquals(-100, game.evaluatePosition(winBoard, p));
    }
}