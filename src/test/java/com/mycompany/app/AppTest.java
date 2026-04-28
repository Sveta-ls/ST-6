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
    void testCheckStateXWin() {
        char[] board = {'X', 'X', 'X', 'O', 'O', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateOWin() {
        char[] board = {'O', 'X', 'X', ' ', 'O', ' ', ' ', ' ', 'O'};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testCheckStateDraw() {
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X'; 
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    void testEvaluatePosition() {
        Player pX = new Player();
        pX.symbol = 'X';
    
        char[] winBoard = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
    
        game.symbol = 'X'; 
        assertEquals(100, game.evaluatePosition(winBoard, pX));
        Player pO = new Player();
        pO.symbol = 'O';
       
        assertEquals(-100, game.evaluatePosition(winBoard, pO));
    }

    @Test
    void testGenerateMoves() {
        char[] board = {'X', 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);

        assertEquals(7, moves.size()); 
    }

    @Test
    void testMiniMaxDecides() {
        game.board = new char[] {
            'O', 'O', ' ',
            'X', 'X', ' ',
            ' ', ' ', ' '
        };
        game.player2.symbol = 'O';
        int move = game.MiniMax(game.board, game.player2);
    
        assertEquals(3, move);
    }

    @Test
void testFullGameStepInPanel() {
    TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
    TicTacToeCell firstCell = (TicTacToeCell) panel.getComponent(0);
    ActionEvent clickEvent = new ActionEvent(firstCell, ActionEvent.ACTION_PERFORMED, "");
    assertDoesNotThrow(() -> panel.actionPerformed(clickEvent));
    assertNotEquals(' ', firstCell.getMarker());
}

    @Test
    void testUtilityPrint() {
        assertDoesNotThrow(() -> {
            Utility.print(new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '});
            Utility.print(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
            Utility.print(new ArrayList<Integer>());
        });
    }

    @Test
    void testPanelInitialization() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        assertNotNull(panel);
        assertEquals(9, panel.getComponentCount());
    }
}