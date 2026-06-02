package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ProgramTest {

    @BeforeAll
    static void configureHeadlessMode() {
        System.setProperty("java.awt.headless", "true");
    }

    @Test
    void gameCreatedWithDefaultParameters() {
        Game game = new Game();

        assertAll(
                () -> assertEquals(State.PLAYING, game.state),
                () -> assertEquals('X', game.player1.symbol),
                () -> assertEquals('O', game.player2.symbol),
                () -> assertEquals(9, game.board.length)
        );

        for (char value : game.board) {
            assertEquals(' ', value);
        }
    }

    @Test
    void stateRecognitionWorksCorrectly() {
        Game game = new Game();

        char[] horizontalWin = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(horizontalWin));

        char[] verticalWin = {'O', ' ', ' ', 'O', ' ', ' ', 'O', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(verticalWin));

        char[] fullBoard = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(fullBoard));

        char[] unfinished = {'X', 'O', ' ', ' ', 'X', ' ', ' ', 'O', ' '};
        assertEquals(State.PLAYING, game.checkState(unfinished));
    }

    @Test
    void moveGeneratorReturnsAvailableCellsOnly() {
        Game game = new Game();

        char[] currentBoard = {'X', ' ', 'O', ' ', 'X', ' ', ' ', 'O', ' '};
        ArrayList<Integer> possibleMoves = new ArrayList<>();

        game.generateMoves(currentBoard, possibleMoves);

        assertEquals(5, possibleMoves.size());

        int[] expected = {1, 3, 5, 6, 8};
        for (int move : expected) {
            assertTrue(possibleMoves.contains(move));
        }
    }

    @Test
    void boardEvaluationProducesExpectedValues() {
        Game game = new Game();

        Player playerX = new Player();
        playerX.symbol = 'X';

        Player playerO = new Player();
        playerO.symbol = 'O';

        char[] winningBoard = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';

        assertEquals(Game.INF, game.evaluatePosition(winningBoard, playerX));
        assertEquals(-Game.INF, game.evaluatePosition(winningBoard, playerO));

        char[] tieBoard = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(0, game.evaluatePosition(tieBoard, playerX));

        char[] activeGame = {'X', 'O', ' ', ' ', 'X', ' ', ' ', 'O', ' '};
        assertEquals(-1, game.evaluatePosition(activeGame, playerX));
    }

    @Test
    void minimaxSelectsWinningCell() {
        Game game = new Game();

        char[] board = {'O', 'O', ' ', 'X', 'X', ' ', ' ', ' ', ' '};

        int move = game.MiniMax(board, game.player2);

        assertEquals(3, move);
    }

    @Test
    void minAndMaxFunctionsReturnCorrectResult() {
        Game game = new Game();

        char[] board = {'X', 'X', 'X', 'O', 'O', ' ', ' ', ' ', ' '};
        game.symbol = 'X';

        int maxScore = game.MaxMove(board, game.player1);
        int minScore = game.MinMove(board, game.player1);

        assertEquals(Game.INF, maxScore);
        assertEquals(Game.INF, minScore);
    }

    @Test
    void cellStoresAndUpdatesData() {
        TicTacToeCell testCell = new TicTacToeCell(4, 1, 1);

        assertAll(
                () -> assertEquals(4, testCell.getNum()),
                () -> assertEquals(1, testCell.getRow()),
                () -> assertEquals(1, testCell.getCol()),
                () -> assertEquals(' ', testCell.getMarker())
        );

        testCell.setMarker("X");

        assertEquals('X', testCell.getMarker());
        assertFalse(testCell.isEnabled());
    }

    @Test
    void utilityMethodsRunWithoutErrors() {
        Utility.print(new char[]{'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X'});
        Utility.print(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);

        Utility.print(list);
    }

    @Test
    void panelHandlesPlayerAndAiTurns() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));

        Component[] cells = panel.getComponents();
        assertEquals(9, cells.length);

        ((TicTacToeCell) cells[0]).doClick();

        int occupied = 0;

        for (Component component : cells) {
            TicTacToeCell cell = (TicTacToeCell) component;

            if (cell.getMarker() != ' ') {
                occupied++;
            }
        }

        assertTrue(occupied >= 2);
    }
}