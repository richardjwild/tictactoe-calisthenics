package tictactoe;

import java.util.List;

import static java.util.Arrays.asList;
import static tictactoe.Column.*;
import static tictactoe.Row.*;
import static tictactoe.Position.position;

public class GameJudge {

    private static final List<List<Position>> WINNING_COMBINATIONS = asList(
            asList(position(LEFT, TOP), position(CENTER, TOP), position(RIGHT, TOP)),
            asList(position(LEFT, MIDDLE), position(CENTER, MIDDLE), position(RIGHT, MIDDLE)),
            asList(position(LEFT, BOTTOM), position(CENTER, BOTTOM), position(RIGHT, BOTTOM)),
            asList(position(LEFT, TOP), position(LEFT, MIDDLE), position(LEFT, BOTTOM)),
            asList(position(CENTER, TOP), position(CENTER, MIDDLE), position(CENTER, BOTTOM)),
            asList(position(RIGHT, TOP), position(RIGHT, MIDDLE), position(RIGHT, BOTTOM)),
            asList(position(LEFT, TOP), position(CENTER, MIDDLE), position(RIGHT, BOTTOM)),
            asList(position(RIGHT, TOP), position(CENTER, MIDDLE), position(LEFT, BOTTOM))
    );

    private Board board;

    public GameJudge(Board board) {
        this.board = board;
    }

    public GameState decideResult() {
        if (playerHasWon())
            return GameState.GAME_OVER;
        if (board.isFull())
            return GameState.STALEMATE;
        return GameState.PLAYING;
    }

    private boolean playerHasWon() {
        return WINNING_COMBINATIONS.stream().anyMatch(board::allTakenByCurrentPlayer);
    }
}
