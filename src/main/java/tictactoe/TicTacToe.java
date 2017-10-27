package tictactoe;

import tictactoe.internal.Board;

import static java.util.Optional.ofNullable;

public class TicTacToe {

    private Player lastPlayer;
    private Board board = new Board(this);
    private boolean over = false;

    public Board play(Player player) {
        if (over)
            throw new GameIsOverException();
        if (player == ofNullable(lastPlayer).orElse(Player.O))
            throw new NotYourTurnException();
        lastPlayer = player;
        return board.forPlayer(player);
    }

    public void over() {
        over = true;
    }
}
