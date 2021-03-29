package domain.piece.objects;

import domain.piece.position.Direction;
import domain.piece.position.Position;
import domain.score.Score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static domain.piece.Color.BLACK;

public class Pawn extends Piece {
    private static final Score SCORE = Score.of(1);
    public static final Map<Position, Pawn> PAWNS = new HashMap<Position, Pawn>() {{
        put(Position.of("a7"), Pawn.of("P", true));
        put(Position.of("b7"), Pawn.of("P", true));
        put(Position.of("c7"), Pawn.of("P", true));
        put(Position.of("d7"), Pawn.of("P", true));
        put(Position.of("e7"), Pawn.of("P", true));
        put(Position.of("f7"), Pawn.of("P", true));
        put(Position.of("g7"), Pawn.of("P", true));
        put(Position.of("h7"), Pawn.of("P", true));
        put(Position.of("a2"), Pawn.of("p", false));
        put(Position.of("b2"), Pawn.of("p", false));
        put(Position.of("c2"), Pawn.of("p", false));
        put(Position.of("d2"), Pawn.of("p", false));
        put(Position.of("e2"), Pawn.of("p", false));
        put(Position.of("f2"), Pawn.of("p", false));
        put(Position.of("g2"), Pawn.of("p", false));
        put(Position.of("h2"), Pawn.of("p", false));
    }};

    private Pawn(String name, boolean isBlack) {
        super(name, SCORE, isBlack);
    }

    public static Pawn of(String name, boolean color) {
        return new Pawn(name, color);
    }

    public static Map<Position, Pawn> initialPawnPieces() {
        return PAWNS;
    }

    private boolean movableDiagonal(List<Direction> directions, Map<Position, Piece> board, Position start, Position end) {
        return directions.stream()
                .map(direction -> start.move(direction))
                .filter(nextPosition -> nextPosition.notEmptyPosition() && nextPosition.equals(end))
                .anyMatch(nextPosition -> !isEmptyPiecePosition(board, nextPosition) && !this.isSameColor(board.get(nextPosition)));
    }

    private boolean movableLinear(Direction direction, Map<Position, Piece> board, Position start, Position end) {
        Position next = start.move(direction);
        if (next.equals(end) && (next.notEmptyPosition() && isEmptyPiecePosition(board, end))) { // 한칸 이동이고 이동 가능하면 true를 반환한다.
            return true;
        }

        // 2칸 이동이 가능하다면 true, 아니라면 false를 반환한다.
        return PAWNS.containsKey(start)
                && (next.move(direction).notEmptyPosition() && next.move(direction).equals(end))
                && !board.containsKey(end);
    }

    private List<Direction> selectDirectionByColor() {
        if (this.isSameColor(BLACK.getValue())) {
            return Direction.blackPawnDirection();
        }
        return Direction.whitePawnDirection();
    }

    @Override
    public boolean canMove(Map<Position, Piece> board, Position start, Position end) {
        List<Direction> directions = new ArrayList<>(selectDirectionByColor());
        boolean result = movableLinear(directions.remove(0), board, start, end);
        if (!result) {
            result = movableDiagonal(directions, board, start, end);
        }
        return result;
    }

    @Override
    public boolean isPawn() {
        return true;
    }
}