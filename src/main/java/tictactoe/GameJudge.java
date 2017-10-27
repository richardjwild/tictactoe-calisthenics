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

    public static State decideResult(Board board) {
        if (playerHasWon(board))
            return State.GAME_OVER;
        if (board.isFull())
            return State.STALEMATE;
        return State.PLAYING;
    }

    private static boolean playerHasWon(Board board) {
        return WINNING_COMBINATIONS.stream().anyMatch(board::allTakenByCurrentPlayer);
    }
}
