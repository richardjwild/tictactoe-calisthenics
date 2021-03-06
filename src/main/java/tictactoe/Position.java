package tictactoe;

public class Position {

    public static Position position(Column column, Row row) {
        return new Position(column, row);
    }

    private final Column column;
    private final Row row;

    private Position(Column column, Row row) {
        this.column = column;
        this.row = row;
    }

    @Override
    public int hashCode() {
        return (row.ordinal() * 3) + column.ordinal();
    }

    @Override
    public boolean equals(Object o) {
        Position other = (Position) o;
        return this.row == other.row && this.column == other.column;
    }
}
