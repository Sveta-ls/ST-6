package com.mycompany.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.awt.GridLayout;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ProgramTest {

    private ByteArrayOutputStream testout = new ByteArrayOutputStream();
    private PrintStream stdout = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(testout));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(stdout);
    }

    @Test
    public void testPlayer() {
        Player player = new Player();
        player.symbol = 'X';
        player.move = 1;
        player.selected = true;
        player.win = false;

        Assertions.assertEquals('X', player.symbol);
        Assertions.assertEquals(1, player.move);
        Assertions.assertEquals(true, player.selected);
        Assertions.assertEquals(false, player.win);
    }

    @Test
    public void testGame() {
        Game game = new Game();
        Assertions.assertEquals(State.PLAYING, game.state);
        Assertions.assertEquals('X', game.player1.symbol);
        Assertions.assertEquals('O', game.player2.symbol);

        for (int i = 0; i < 9; i++) {
            Assertions.assertEquals(' ', game.board[i]);
        }
    }

    @Test
    public void testCheckState() {
        Game game = new Game();
        
        for (int i = 1; i <= 8; i++) {
            game.symbol = 'X';
            Assertions.assertEquals(State.XWIN, game.checkState(genWinState('X', i)));
            
            game.symbol = 'O';
            Assertions.assertEquals(State.OWIN, game.checkState(genWinState('O', i)));
        }
        
        char[] board1 = {'X', 'O', 'X', 
                         'X', 'O', 'O', 
                         'O', 'X', 'X'};
        Assertions.assertEquals(State.DRAW, game.checkState(board1));
        
        game.symbol = '?';
        char[] board2 = {'?', '?', '?', 
                         ' ', ' ', ' ', 
                         ' ', ' ', ' '};
        Assertions.assertEquals(State.PLAYING, game.checkState(board2));
    }

    @Test
    public void testEvaluatePosition() {
        Game game = new Game();
        
        game.symbol = 'X';
        Assertions.assertEquals(Game.INF, game.evaluatePosition(genWinState('X', 1), game.player1));
        Assertions.assertEquals(-Game.INF, game.evaluatePosition(genWinState('X', 1), game.player2));
        
        game.symbol = 'O';
        Assertions.assertEquals(-Game.INF, game.evaluatePosition(genWinState('O', 1), game.player1));
        Assertions.assertEquals(Game.INF, game.evaluatePosition(genWinState('O', 1), game.player2));
        
        char[] board1 = {'O', 'O', 'X',
                         'X', 'X', 'O',
                         'O', 'X', 'X'};
        Assertions.assertEquals(0, game.evaluatePosition(board1, game.player1));
        
        char[] board2 = {'X', ' ', ' ',
                         ' ', ' ', ' ',
                         ' ', ' ', ' '};
        Assertions.assertEquals(-1, game.evaluatePosition(board2, game.player1));
        
        Player player3 = new Player();
        player3.symbol = '?';
        game.symbol = 'X';
        Assertions.assertEquals(-1, game.evaluatePosition(genWinState('X', 1), player3));
        game.symbol = 'O';
        Assertions.assertEquals(-1, game.evaluatePosition(genWinState('O', 1), player3));
    }

    @Test
    public void testMiniMax() {
        Game game = new Game();
        
        char[] board1 = {' ', ' ', ' ', 
                         ' ', ' ', ' ', 
                         ' ', ' ', ' '};
        game.board = board1;
        int move1 = game.MiniMax(board1, game.player1);
        Assertions.assertTrue(move1 > 0 && move1 <= 9);
        
        char[] board2 = {'O', ' ', ' ', 
                         ' ', 'X', 'X', 
                         ' ', ' ', ' '};
        game.board = board2;
        Assertions.assertEquals(4, game.MiniMax(board2, game.player2));

        char[] board3 = {'O', ' ', ' ', 
                         'O', 'X', ' ', 
                         ' ', 'X', 'X'};
        game.board = board3;
        Assertions.assertEquals(7, game.MiniMax(board3, game.player2));
        
        char[] board4 = {' ', 'X', ' ', 
                         'X', ' ', 'O', 
                         ' ', 'O', ' '};
        game.board = board4;
        Assertions.assertEquals(1, game.MiniMax(board4, game.player1));
        
        char[] board5 = {'X', ' ', ' ', 
                         ' ', 'O', ' ', 
                         ' ', ' ', 'X'};
        game.board = board5;
        int move = game.MiniMax(board5, game.player2);
        Assertions.assertTrue(move == 2 || move == 4 || move == 6 || move == 8);
    }

    @Test
    public void testGenerateMoves() {
        Game game = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        
        game.generateMoves(game.board, moves);
        Assertions.assertEquals(9, moves.size());
        
        game.board[0] = 'X';
        moves.clear();
        game.generateMoves(game.board, moves);
        Assertions.assertEquals(8, moves.size());
    }
    
    @Test
    public void testTicTacToeCell() {
        TicTacToeCell cell = new TicTacToeCell(1, 2, 3);
        Assertions.assertEquals(1, cell.getNum());
        Assertions.assertEquals(2, cell.getCol());
        Assertions.assertEquals(3, cell.getRow());
        Assertions.assertEquals(' ', cell.getMarker());

        cell.setMarker("X");
        Assertions.assertEquals('X', cell.getMarker());
    }

    @Test
    public void testTicTacToePanel() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        Assertions.assertNotNull(panel);
    }

    @Test
    public void testUtility() {
        Utility utility = new Utility();
        String sep = System.lineSeparator();

        char[] board1 = {' ', 'O', ' ',
                         'O', 'X', 'X',
                         'X', ' ', ' '};
        utility.print(board1);
        Assertions.assertEquals(sep + " -O- -O-X-X-X- - -" + sep, testout.toString());
        testout.reset();

        int[] board2 = {1, 2, 3,
                        4, 5, 6,
                        7, 8, 9};
        utility.print(board2);
        Assertions.assertEquals(sep + "1-2-3-4-5-6-7-8-9-" + sep, testout.toString());
        testout.reset();
        
        ArrayList<Integer> moves = new ArrayList<>(Arrays.asList(0, 2, 7, 8));
        utility.print(moves);
        Assertions.assertEquals(sep + "0-2-7-8-" + sep, testout.toString());
    }



    private char[] genWinState(char symbol, int winState) {
        char[] board = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        switch (winState) {
            case 1: board[0] = symbol; board[1] = symbol; board[2] = symbol; break;
            case 2: board[3] = symbol; board[4] = symbol; board[5] = symbol; break;
            case 3: board[6] = symbol; board[7] = symbol; board[8] = symbol; break;
            case 4: board[0] = symbol; board[3] = symbol; board[6] = symbol; break;
            case 5: board[1] = symbol; board[4] = symbol; board[7] = symbol; break;
            case 6: board[2] = symbol; board[5] = symbol; board[8] = symbol; break;
            case 7: board[0] = symbol; board[4] = symbol; board[8] = symbol; break;
            case 8: board[2] = symbol; board[4] = symbol; board[6] = symbol; break;
        }
        return board;
    }
}