package tictactoe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static tictactoe.Player.O;

public class Board {

    private static final int BOARD_SIZE = 9;

    private Map<Position, Player> positionsTaken = new HashMap<>();
    private Player currentPlayer;

    public boolean lastPlayerWas(Player player) {
        return player == ofNullable(currentPlayer).orElse(O);
    }

    public void nextPlayerIs(Player player) {
        currentPlayer = player;
    }

    public void take(Position position) {
        if (isTaken(position))
            throw new PositionAlreadyTakenException();
        positionsTaken.put(position, currentPlayer);
    }

    private boolean isTaken(Position position) {
        return positionsTaken.containsKey(position);
    }

    public boolean allTakenByCurrentPlayer(List<Position> positions) {
        return positions.stream().allMatch(this::isTakenByCurrentPlayer);
    }

    private boolean isTakenByCurrentPlayer(Position position) {
        return positionsTaken.get(position) == currentPlayer;
    }

    public boolean isFull() {
        return positionsTaken.keySet().size() == BOARD_SIZE;
    }
}
