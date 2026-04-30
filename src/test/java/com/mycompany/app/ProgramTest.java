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

    void testCheckStateNoWinnerNotFull() {

        char[] board = {

                'X', 'O', 'X',

                'X', ' ', 'O',

                'O', 'X', ' '

        };

        game.symbol = 'X';

        assertEquals(State.PLAYING, game.checkState(board));

    }

    

    @Test

    void testCheckStateXWinVertical() {

        char[] board = {

                'X', 'O', ' ',

                'X', 'O', ' ',

                'X', ' ', ' '

        };

        game.symbol = 'X';

        assertEquals(State.XWIN, game.checkState(board));

    }

    

    @Test

    void testCheckStateXWinDiagonal() {

        char[] board = {

                'X', 'O', ' ',

                ' ', 'X', ' ',

                ' ', 'O', 'X'

        };

        game.symbol = 'X';

        assertEquals(State.XWIN, game.checkState(board));

    }

    

    @Test

    void testCheckStateOWinHorizontal() {

        char[] board = {

                'O', 'O', 'O',

                'X', 'X', ' ',

                ' ', ' ', ' '

        };

        game.symbol = 'O';

        assertEquals(State.OWIN, game.checkState(board));

    }

    

    @Test

    void testCheckStateOWinVertical() {

        char[] board = {

                'O', 'X', ' ',

                'O', 'X', ' ',

                'O', ' ', ' '

        };

        game.symbol = 'O';

        assertEquals(State.OWIN, game.checkState(board));

    }

    

    @Test

    void testCheckStateOWinDiagonal2() {

        char[] board = {

                ' ', ' ', 'O',

                ' ', 'O', ' ',

                'O', ' ', ' '

        };

        game.symbol = 'O';

        assertEquals(State.OWIN, game.checkState(board));

    }

    

    @Test

    void testGenerateMovesNoEmptyCells() {

        char[] board = {

                'X', 'O', 'X',

                'X', 'O', 'O',

                'O', 'X', 'X'

        };

        ArrayList<Integer> moves = new ArrayList<>();

        game.generateMoves(board, moves);

        assertEquals(0, moves.size());

    }

    

    @Test

    void testGenerateMovesAllEmptyCells() {

        char[] board = {

                ' ', ' ', ' ',

                ' ', ' ', ' ',

                ' ', ' ', ' '

        };

        ArrayList<Integer> moves = new ArrayList<>();

        game.generateMoves(board, moves);

        assertEquals(9, moves.size());

        for (int i = 0; i < 9; i++) {

            assertTrue(moves.contains(i));

        }

    }

    

    @Test

    void testEvaluatePositionNotTerminal() {

        char[] board = {

                'X', ' ', ' ',

                ' ', 'O', ' ',

                ' ', ' ', ' '

        };

        game.symbol = 'X';

        int result = game.evaluatePosition(board, game.player1);

        assertEquals(-1, result);

    }

    

    @Test

    void testMinMoveOnEmptyBoard() {

        char[] board = {

                ' ', ' ', ' ',

                ' ', ' ', ' ',

                ' ', ' ', ' '

        };

        int result = game.MinMove(board, game.player1);

        assertTrue(result >= -Game.INF && result <= Game.INF);

    }

    

    @Test

    void testMaxMoveOnEmptyBoard() {

        char[] board = {

                ' ', ' ', ' ',

                ' ', ' ', ' ',

                ' ', ' ', ' '

        };

        int result = game.MaxMove(board, game.player1);

        assertTrue(result >= -Game.INF && result <= Game.INF);

    }

    

    @Test

    void testMinMoveWithOneMoveLeft() {

        char[] board = {

                'X', 'O', 'X',

                'X', 'O', 'O',

                'O', 'X', ' '

        };

        int result = game.MinMove(board, game.player1);

        assertTrue(result >= -Game.INF && result <= Game.INF);

    }

    

    @Test

    void testMaxMoveWithOneMoveLeft() {

        char[] board = {

                'X', 'O', 'X',

                'X', 'O', 'O',

                'O', 'X', ' '

        };

        int result = game.MaxMove(board, game.player2);

        assertTrue(result >= -Game.INF && result <= Game.INF);

    }

    

    @Test

    void testMiniMaxBlocksFork() {

        game.board = new char[]{

                'X', ' ', ' ',

                ' ', 'X', ' ',

                ' ', ' ', 'O'

        };

        int bestMove = game.MiniMax(game.board, game.player2);

        assertTrue(bestMove >= 1 && bestMove <= 9);

    }

    

    @Test

    void testMiniMaxReturnsValidMove() {

        game.board = new char[]{

                ' ', ' ', ' ',

                ' ', ' ', ' ',

                ' ', ' ', ' '

        };

        int bestMove = game.MiniMax(game.board, game.player2);

        assertTrue(bestMove >= 1 && bestMove <= 9);

    }

    

    @Test

    void testMultipleBestMovesRandomSelection() {

        game.board = new char[]{

                'X', ' ', ' ',

                ' ', ' ', ' ',

                ' ', ' ', ' '

        };

        for (int i = 0; i < 10; i++) {

            int move = game.MiniMax(game.board, game.player2);

            assertTrue(move >= 1 && move <= 9);

        }

    }

    @Test
    void testMiniMaxBlocksHuman() {
        game.board = new char[]{
                'X', 'X', ' ',
                ' ', 'O', ' ',
                ' ', ' ', ' '
        };
        int move = game.MiniMax(game.board, game.player2);
        assertEquals(3, move);
    }

}