package tictactoe.internal;

import javafx.util.Pair;
import tictactoe.Column;
import tictactoe.Row;
import tictactoe.TicTacToe;

import java.util.List;

import static java.util.Arrays.asList;
import static tictactoe.Column.*;
import static tictactoe.Row.*;

public class ResultDecider {

    private static Pair<Column, Row> pair(Column c, Row r) {
        return new Pair<>(c, r);
    }

    private static final Pair<Column, Row> LEFT_TOP = pair(LEFT, TOP);
    private static final Pair<Column, Row> CENTER_TOP = pair(CENTER, TOP);
    private static final Pair<Column, Row> RIGHT_TOP = pair(RIGHT, TOP);
    private static final Pair<Column, Row> LEFT_MIDDLE = pair(LEFT, MIDDLE);
    private static final Pair<Column, Row> CENTER_MIDDLE = pair(CENTER, MIDDLE);
    private static final Pair<Column, Row> RIGHT_MIDDLE = pair(RIGHT, MIDDLE);
    private static final Pair<Column, Row> LEFT_BOTTOM = pair(LEFT, BOTTOM);
    private static final Pair<Column, Row> CENTER_BOTTOM = pair(CENTER, BOTTOM);
    private static final Pair<Column, Row> RIGHT_BOTTOM = pair(RIGHT, BOTTOM);

    private static final List<List<Pair<Column, Row>>> WINNING_COMBINATIONS = asList(
            asList(LEFT_TOP, CENTER_TOP, RIGHT_TOP),
            asList(LEFT_MIDDLE, CENTER_MIDDLE, RIGHT_MIDDLE),
            asList(LEFT_BOTTOM, CENTER_BOTTOM, RIGHT_BOTTOM),
            asList(LEFT_TOP, LEFT_MIDDLE, LEFT_BOTTOM),
            asList(CENTER_TOP, CENTER_MIDDLE, CENTER_BOTTOM),
            asList(RIGHT_TOP, RIGHT_MIDDLE, RIGHT_BOTTOM),
            asList(LEFT_TOP, CENTER_MIDDLE, RIGHT_BOTTOM),
            asList(RIGHT_TOP, CENTER_MIDDLE, LEFT_BOTTOM)
    );

    private boolean hasWon = false;

    ResultDecider(TicTacToe game, Board board) {
        hasWon = WINNING_COMBINATIONS.stream().anyMatch(board::allTakenByCurrentPlayer);
        if (hasWon || board.isFull()) {
            game.over();
        }
    }

    public boolean hasWon() {
        return hasWon;
    }
}
