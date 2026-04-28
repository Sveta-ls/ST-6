package com.mycompany.app;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    public State state;
    public Player player1, player2, cplayer;
    public int nmove, q;
    public char symbol;
    public char[] board;
    public static final int INF = 100;

    public Game() {
        player1 = new Player(); player2 = new Player();
        player1.symbol = 'X'; player2.symbol = 'O';
        state = State.PLAYING;
        board = new char[9];
        for(int i=0; i<9; i++) board[i] = ' ';
    }

    public State checkState(char[] board) {
        if ((board[0] == symbol && board[1] == symbol && board[2] == symbol) ||
            (board[3] == symbol && board[4] == symbol && board[5] == symbol) ||
            (board[6] == symbol && board[7] == symbol && board[8] == symbol) ||
            (board[0] == symbol && board[3] == symbol && board[6] == symbol) ||
            (board[1] == symbol && board[4] == symbol && board[7] == symbol) ||
            (board[2] == symbol && board[5] == symbol && board[8] == symbol) ||
            (board[0] == symbol && board[4] == symbol && board[8] == symbol) ||
            (board[2] == symbol && board[4] == symbol && board[6] == symbol)) {
            return (symbol == 'X') ? State.XWIN : State.OWIN;
        }
        for (int i = 0; i < 9; i++) if (board[i] == ' ') return State.PLAYING;
        return State.DRAW;
    }

    void generateMoves(char[] board, ArrayList<Integer> move_list) {
        for (int i = 0; i < 9; i++) if (board[i] == ' ') move_list.add(i);
    }

    int evaluatePosition(char[] board, Player player) {
        State s = checkState(board);
        if (s == State.XWIN || s == State.OWIN || s == State.DRAW) {
            if ((s == State.XWIN && player.symbol == 'X') || (s == State.OWIN && player.symbol == 'O')) return +INF;
            if ((s == State.XWIN && player.symbol == 'O') || (s == State.OWIN && player.symbol == 'X')) return -INF;
            return 0;
        }
        return -1;
    }

    public int MiniMax(char[] board, Player player) {
        int best_val = -INF, index = 0;
        ArrayList<Integer> move_list = new ArrayList<>();
        int[] best_moves = new int[9];
        generateMoves(board, move_list);
        while (!move_list.isEmpty()) {
            int move = move_list.get(0);
            board[move] = player.symbol;
            symbol = player.symbol;
            int val = MinMove(board, player);
            if (val > best_val) { best_val = val; index = 0; best_moves[index] = move + 1; }
            else if (val == best_val) best_moves[++index] = move + 1;
            board[move] = ' '; move_list.remove(0);
        }
        if (index > 0) index = new Random().nextInt(index + 1);
        q = 0; return best_moves[index];
    }

    int MinMove(char[] board, Player player) {
        int pos_v = evaluatePosition(board, player);
        if (pos_v != -1) return pos_v;
        q++; int best_val = +INF;
        ArrayList<Integer> move_list = new ArrayList<>();
        generateMoves(board, move_list);
        while (!move_list.isEmpty()) {
            int move = move_list.get(0);
            symbol = (player.symbol == 'X') ? 'O' : 'X';
            board[move] = symbol;
            int val = MaxMove(board, player);
            if (val < best_val) best_val = val;
            board[move] = ' '; move_list.remove(0);
        }
        return best_val;
    }

    int MaxMove(char[] board, Player player) {
        int pos_v = evaluatePosition(board, player);
        if (pos_v != -1) return pos_v;
        q++; int best_val = -INF;
        ArrayList<Integer> move_list = new ArrayList<>();
        generateMoves(board, move_list);
        while (!move_list.isEmpty()) {
            int move = move_list.get(0);
            symbol = (player.symbol == 'X') ? 'X' : 'O';
            board[move] = symbol;
            int val = MinMove(board, player);
            if (val > best_val) best_val = val;
            board[move] = ' '; move_list.remove(0);
        }
        return best_val;
    }
}