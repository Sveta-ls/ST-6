package com.mycompany.app;


import static org.junit.jupiter.api.Assertions.*;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ProgramTest {

    @BeforeAll
    static void setupHeadless() {
        System.setProperty("java.awt.headless", "true");
    }

    @Test
    void gameInitializesWithEmptyBoardAndPlayers() {
        Game game = new Game();

        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(9, game.board.length);
        for (char cell : game.board) {
            assertEquals(' ', cell);
        }
    }

    @Test
    void checkStateDetectsWinsDrawAndPlaying() {
        Game game = new Game();

        char[] xWin = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(xWin));

        char[] oWin = {'O', ' ', ' ', 'O', ' ', ' ', 'O', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(oWin));

        char[] draw = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(draw));

        char[] playing = {'X', 'O', ' ', ' ', 'X', ' ', ' ', 'O', ' '};
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(playing));
    }

    @Test
    void returnsOnlEmptyPos() {
        Game game = new Game();
        char[] board = {'X', ' ', 'O', ' ', 'X', ' ', ' ', 'O', ' '};
        ArrayList<Integer> moves = new ArrayList<>();

        game.generateMoves(board, moves);

        assertEquals(5, moves.size());
        assertTrue(moves.contains(1));
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(5));
        assertTrue(moves.contains(6));
        assertTrue(moves.contains(8));
    }

    @Test
    void evaluatePositionReturnsExpectedScores() {
        Game game = new Game();
        Player xPlayer = new Player();
        xPlayer.symbol = 'X';
        Player oPlayer = new Player();
        oPlayer.symbol = 'O';

        char[] xWin = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(xWin, xPlayer));
        assertEquals(-Game.INF, game.evaluatePosition(xWin, oPlayer));

        char[] draw = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(0, game.evaluatePosition(draw, xPlayer));

        char[] playing = {'X', 'O', ' ', ' ', 'X', ' ', ' ', 'O', ' '};
        game.symbol = 'X';
        assertEquals(-1, game.evaluatePosition(playing, xPlayer));
    }

    @Test
    void minimaxFindsImmediateWinningMoveForO() {
        Game game = new Game();
        char[] board = {'O', 'O', ' ', 'X', 'X', ' ', ' ', ' ', ' '};

        int bestMove = game.MiniMax(board, game.player2);

        assertEquals(3, bestMove);
    }

    @Test
    void minAndMaxMoveReturnScore() {
        Game game = new Game();
        char[] board = {'X', 'X', 'X', 'O', 'O', ' ', ' ', ' ', ' '};
        game.symbol = 'X';

        int maxVal = game.MaxMove(board, game.player1);
        int minVal = game.MinMove(board, game.player1);

        assertEquals(Game.INF, maxVal);
        assertEquals(Game.INF, minVal);
    }

    @Test
    void cellStoresCoordinatesAndMarker() {
        TicTacToeCell cell = new TicTacToeCell(4, 1, 1);

        assertEquals(4, cell.getNum());
        assertEquals(1, cell.getCol());
        assertEquals(1, cell.getRow());
        assertEquals(' ', cell.getMarker());

        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    void utilityPrintMethodsExecute() {
        Utility.print(new char[] {'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X'});
        Utility.print(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(3);
        Utility.print(moves);
    }

    @Test
    void panelProcessesHumanAndComputerMoves() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        Component[] components = panel.getComponents();
        assertEquals(9, components.length);

        TicTacToeCell firstCell = (TicTacToeCell) components[0];
        firstCell.doClick();

        int markedCells = 0;
        for (Component component : components) {
            TicTacToeCell cell = (TicTacToeCell) component;
            if (cell.getMarker() != ' ') {
                markedCells++;
            }
        }
        assertTrue(markedCells >= 2);
    }
}