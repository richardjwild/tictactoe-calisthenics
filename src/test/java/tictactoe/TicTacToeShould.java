package tictactoe;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.fest.assertions.Assertions.assertThat;
import static tictactoe.Column.CENTER;
import static tictactoe.Column.LEFT;
import static tictactoe.Column.RIGHT;
import static tictactoe.Player.O;
import static tictactoe.Player.X;
import static tictactoe.Row.BOTTOM;
import static tictactoe.Row.MIDDLE;
import static tictactoe.Row.TOP;

@RunWith(JUnitParamsRunner.class)
public class TicTacToeShould {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void not_permit_o_to_play_first() {
        TicTacToe game = new TicTacToe();
        thrown.expect(NotYourTurnException.class);
        game.play(O);
    }

    @Test
    public void permit_x_to_play_first() {
        TicTacToe game = new TicTacToe();
        game.play(X);
    }

    @Test
    public void permit_o_to_play_after_x() {
        TicTacToe game = new TicTacToe();
        game.play(X);
        game.play(O);
    }

    @Test
    public void not_permit_x_to_play_twice_in_succession() {
        TicTacToe game = new TicTacToe();
        thrown.expect(NotYourTurnException.class);
        game.play(X);
        game.play(X);
    }

    @Test
    public void not_permit_o_to_play_twice_in_succession() {
        TicTacToe game = new TicTacToe();
        thrown.expect(NotYourTurnException.class);
        game.play(X);
        game.play(O);
        game.play(O);
    }

    @Test
    public void permit_player_to_choose_a_position() {
        TicTacToe game = new TicTacToe();
        game.play(X).position(CENTER, MIDDLE);
    }

    @Test
    public void constrain_columns_to_left_center_right() {
        TicTacToe game = new TicTacToe();
        game.play(X).position(LEFT, MIDDLE);
        game.play(O).position(CENTER, MIDDLE);
        game.play(X).position(RIGHT, MIDDLE);
    }

    @Test
    public void constrain_rows_to_top_middle_bottom() {
        TicTacToe game = new TicTacToe();
        game.play(X).position(CENTER, TOP);
        game.play(O).position(CENTER, MIDDLE);
        game.play(X).position(CENTER, BOTTOM);
    }

    @Test
    public void not_permit_player_to_choose_position_already_chosen() {
        TicTacToe game = new TicTacToe();
        thrown.expect(PositionAlreadyTakenException.class);
        game.play(X).position(CENTER, MIDDLE);
        game.play(O).position(CENTER, BOTTOM);
        game.play(X).position(CENTER, MIDDLE);
    }

    @Test
    public void not_judge_either_player_to_have_won_after_first_move() {
        TicTacToe game = new TicTacToe();
        boolean xHasWon = game.play(X).position(CENTER, MIDDLE).hasWon();
        assertThat(xHasWon).isFalse();
        boolean oHasWon = game.play(O).position(CENTER, BOTTOM).hasWon();
        assertThat(oHasWon).isFalse();
    }

    @Parameters({
            "LEFT,TOP,LEFT,BOTTOM,CENTER,TOP,CENTER,BOTTOM,RIGHT,TOP",
            "LEFT,MIDDLE,LEFT,BOTTOM,CENTER,MIDDLE,CENTER,BOTTOM,RIGHT,MIDDLE",
            "LEFT,BOTTOM,LEFT,TOP,CENTER,BOTTOM,CENTER,TOP,RIGHT,BOTTOM",
            "LEFT,TOP,RIGHT,TOP,LEFT,MIDDLE,RIGHT,MIDDLE,LEFT,BOTTOM",
            "CENTER,TOP,RIGHT,TOP,CENTER,MIDDLE,RIGHT,MIDDLE,CENTER,BOTTOM",
            "RIGHT,TOP,LEFT,TOP,RIGHT,MIDDLE,LEFT,MIDDLE,RIGHT,BOTTOM",
            "LEFT,TOP,RIGHT,TOP,CENTER,MIDDLE,RIGHT,MIDDLE,RIGHT,BOTTOM",
            "RIGHT,TOP,LEFT,TOP,CENTER,MIDDLE,LEFT,MIDDLE,LEFT,BOTTOM"})
    @Test
    public void judge_player_to_have_won_when_they_make_a_line(Column c1, Row r1, Column c2, Row r2, Column c3, Row r3, Column c4, Row r4, Column c5, Row r5) {
        TicTacToe game = new TicTacToe();
        assertThat(game.play(X).position(c1, r1).hasWon()).isFalse();
        assertThat(game.play(O).position(c2, r2).hasWon()).isFalse();
        assertThat(game.play(X).position(c3, r3).hasWon()).isFalse();
        assertThat(game.play(O).position(c4, r4).hasWon()).isFalse();
        assertThat(game.play(X).position(c5, r5).hasWon()).isTrue();
    }

    @Test
    public void not_judge_player_to_have_won_when_blocked() {
        TicTacToe game = new TicTacToe();
        game.play(X).position(LEFT, TOP);
        game.play(O).position(CENTER, TOP);
        assertThat(game.play(X).position(RIGHT, TOP).hasWon()).isFalse();
    }

    @Test
    public void not_permit_player_to_play_after_game_is_won() {
        thrown.expect(GameIsOverException.class);
        TicTacToe game = new TicTacToe();
        game.play(X).position(LEFT, TOP);
        game.play(O).position(LEFT, BOTTOM);
        game.play(X).position(CENTER, TOP);
        game.play(O).position(CENTER, BOTTOM);
        game.play(X).position(RIGHT, TOP);
        game.play(O).position(RIGHT, BOTTOM);
    }

    @Test
    public void not_permit_player_to_play_after_board_is_full() {
        thrown.expect(GameIsOverException.class);
        TicTacToe game = new TicTacToe();
        game.play(X).position(CENTER, MIDDLE);
        game.play(O).position(RIGHT, TOP);
        game.play(X).position(LEFT, BOTTOM);
        game.play(O).position(CENTER, BOTTOM);
        game.play(X).position(RIGHT, BOTTOM);
        game.play(O).position(LEFT, MIDDLE);
        game.play(X).position(RIGHT, MIDDLE);
        assertThat(game.play(O).position(LEFT, TOP).hasWon()).isFalse();
        assertThat(game.play(X).position(CENTER, TOP).hasWon()).isFalse();
        game.play(O).position(LEFT, TOP);
    }
}
