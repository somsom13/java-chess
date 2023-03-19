package chess.domain.path;

import chess.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class Path {

    private static final String POSITION_NOT_IN_PATH_ERROR_MESSAGE = "경로에 존재하지 않는 위치입니다.";
    private final List<Position> positions;

    public Path(final List<Position> positions) {
        this.positions = positions;
    }

    public static Path ofSinglePath(final Position current, final Direction direction) {
        List<Position> positions = new ArrayList<>();
        Position next = current.findNextPosition(direction);
        if (next != null) {
            positions.add(next);
        }
        return new Path(positions);
    }

    public static Path ofMultiPath(Position current, final Direction direction, int size) {
        List<Position> positions = new ArrayList<>();
        while (positions.size() < size && (current = current.findNextPosition(direction)) != null) {
            positions.add(current);
        }
        return new Path(positions);
    }

    public boolean hasPosition(Position position) {
        return positions.contains(position);
    }

    public Position findPositionByIndex(int index) {
        return positions.get(index);
    }

    public int findIndexByPosition(Position position) {
        if (positions.contains(position)) {
            return positions.indexOf(position);
        }
        throw new IllegalArgumentException(POSITION_NOT_IN_PATH_ERROR_MESSAGE);
    }

    public List<Position> positions() {
        return new ArrayList<>(positions);
    }

    public int size() {
        return positions.size();
    }

}
