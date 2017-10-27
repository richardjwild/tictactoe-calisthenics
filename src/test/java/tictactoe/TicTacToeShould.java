package tictactoe;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.fest.assertions.Assertions.assertThat;
import static tictactoe.Column.*;
import static tictactoe.Player.O;
import static tictactoe.Player.X;
import static tictactoe.Row.*;

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
        game.play(X).at(CENTER, MIDDLE);
    }

    @Test
    public void constrain_columns_to_left_center_right() {
        TicTacToe game = new TicTacToe();
        game.play(X).at(LEFT, MIDDLE);
        game.play(O).at(CENTER, MIDDLE);
        game.play(X).at(RIGHT, MIDDLE);
    }

    @Test
    public void constrain_rows_to_top_middle_bottom() {
        TicTacToe game = new TicTacToe();
        game.play(X).at(CENTER, TOP);
        game.play(O).at(CENTER, MIDDLE);
        game.play(X).at(CENTER, BOTTOM);
    }

    @Test
    public void not_permit_player_to_choose_position_already_chosen() {
        TicTacToe game = new TicTacToe();
        thrown.expect(PositionAlreadyTakenException.class);
        game.play(X).at(CENTER, MIDDLE);
        game.play(O).at(CENTER, BOTTOM);
        game.play(X).at(CENTER, MIDDLE);
    }

    @Test
    public void not_judge_either_player_to_have_won_after_first_move() {
        TicTacToe game = new TicTacToe();
        boolean xHasWon = game.play(X).at(CENTER, MIDDLE).hasWon();
        assertThat(xHasWon).isFalse();
        boolean oHasWon = game.play(O).at(CENTER, BOTTOM).hasWon();
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
        assertThat(game.play(X).at(c1, r1).hasWon()).isFalse();
        assertThat(game.play(O).at(c2, r2).hasWon()).isFalse();
        assertThat(game.play(X).at(c3, r3).hasWon()).isFalse();
        assertThat(game.play(O).at(c4, r4).hasWon()).isFalse();
        assertThat(game.play(X).at(c5, r5).hasWon()).isTrue();
    }

    @Test
    public void not_judge_player_to_have_won_when_blocked() {
        TicTacToe game = new TicTacToe();
        game.play(X).at(LEFT, TOP);
        game.play(O).at(CENTER, TOP);
        assertThat(game.play(X).at(RIGHT, TOP).hasWon()).isFalse();
    }

    @Test
    public void not_permit_player_to_play_after_game_is_won() {
        TicTacToe game = new TicTacToe();
        game.play(X).at(LEFT, TOP);
        game.play(O).at(LEFT, BOTTOM);
        game.play(X).at(CENTER, TOP);
        game.play(O).at(CENTER, BOTTOM);
        game.play(X).at(RIGHT, TOP);
        thrown.expect(GameIsOverException.class);
        game.play(O).at(RIGHT, BOTTOM);
    }

    @Test
    public void judge_game_stalemated_when_board_full_and_nobody_has_won() {
        TicTacToe game = new TicTacToe();
        assertThat(game.play(X).at(CENTER, MIDDLE).isStalemated()).isFalse();
        assertThat(game.play(O).at(RIGHT, TOP).isStalemated()).isFalse();
        assertThat(game.play(X).at(LEFT, BOTTOM).isStalemated()).isFalse();
        assertThat(game.play(O).at(CENTER, BOTTOM).isStalemated()).isFalse();
        assertThat(game.play(X).at(RIGHT, BOTTOM).isStalemated()).isFalse();
        assertThat(game.play(O).at(LEFT, MIDDLE).isStalemated()).isFalse();
        assertThat(game.play(X).at(RIGHT, MIDDLE).isStalemated()).isFalse();
        assertThat(game.play(O).at(LEFT, TOP).isStalemated()).isFalse();
        assertThat(game.play(X).at(CENTER, TOP).isStalemated()).isTrue();
    }

    @Test
    public void not_permit_player_to_play_after_board_is_full() {
        TicTacToe game = new TicTacToe();
        game.play(X).at(CENTER, MIDDLE);
        game.play(O).at(RIGHT, TOP);
        game.play(X).at(LEFT, BOTTOM);
        game.play(O).at(CENTER, BOTTOM);
        game.play(X).at(RIGHT, BOTTOM);
        game.play(O).at(LEFT, MIDDLE);
        game.play(X).at(RIGHT, MIDDLE);
        game.play(O).at(LEFT, TOP);
        game.play(X).at(CENTER, TOP);
        thrown.expect(GameIsOverException.class);
        game.play(O).at(LEFT, TOP);
    }
}
