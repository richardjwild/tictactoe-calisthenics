package tictactoe.internal;

import javafx.util.Pair;
import tictactoe.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private static final int BOARD_SIZE = 9;

    private TicTacToe game;
    private Map<Pair<Column, Row>, Player> positionsTaken = new HashMap<>();
    private Player currentPlayer;

    public Board(TicTacToe game) {
        this.game = game;
    }

    public Board forPlayer(Player player) {
        currentPlayer = player;
        return this;
    }

    public ResultDecider position(Column column, Row row) {
        Pair<Column, Row> position = new Pair<>(column, row);
        if (positionsTaken.containsKey(position))
            throw new PositionAlreadyTakenException();
        positionsTaken.put(position, currentPlayer);
        return new ResultDecider(game, this);
    }

    boolean allTakenByCurrentPlayer(List<Pair<Column, Row>> positions) {
        return positions.stream().allMatch(this::isTakenByCurrentPlayer);
    }

    boolean isFull() {
        return positionsTaken.keySet().size() == BOARD_SIZE;
    }

    private boolean isTakenByCurrentPlayer(Pair<Column, Row> position) {
        return positionsTaken.get(position) == currentPlayer;
    }
}
