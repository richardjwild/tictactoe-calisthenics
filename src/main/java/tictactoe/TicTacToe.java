package tictactoe;

import static tictactoe.Position.position;

public class TicTacToe {

    private Board board = new Board();
    private State gameState = State.PLAYING;

    public TicTacToe play(Player player) {
        preventPlayIfGameIsOver();
        preventPlayIfNotTheirTurn(player);
        board.nextPlayerIs(player);
        return this;
    }

    private void preventPlayIfGameIsOver() {
        if (gameState != State.PLAYING)
            throw new GameIsOverException();
    }

    private void preventPlayIfNotTheirTurn(Player player) {
        if (board.lastPlayerWas(player))
            throw new NotYourTurnException();
    }

    public TicTacToe at(Column column, Row row) {
        board.take(position(column, row));
        gameState = new GameJudge(board).decideResult();
        return this;
    }

    public boolean hasWon() {
        return gameState == State.GAME_OVER;
    }

    public boolean isStalemated() {
        return gameState == State.STALEMATE;
    }
}
