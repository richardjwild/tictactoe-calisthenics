package tictactoe;

public class GameState {

    private State state;

    public GameState() {
        state = State.PLAYING;
    }

    public GameState(State state, Player lastPlayer) {
        this.state = state;
    }

    public boolean gameOn() {
        return state == State.PLAYING;
    }

    public boolean isWon() {
        return state == State.GAME_OVER;
    }

    public boolean isStalemated() {
        return state == State.STALEMATE;
    }

}
