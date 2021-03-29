package domain;

import domain.piece.objects.Piece;
import domain.piece.position.Position;
import domain.score.Score;
import domain.state.State;
import domain.state.Wait;

import java.util.HashMap;
import java.util.Map;

public class ChessGame {
    private State state = new Wait(new HashMap<>());

    public void start(Map<Position, Piece> pieces) {
        state = state.run(pieces);
    }

    public void move(Position start, Position end) {
        state = state.move(start, end);
    }

    public Map<Boolean, Score> piecesScore() {
        return state.pieceScore();

    }

    public Board getBoard() {
        return state.getBoard();
    }

    public void finish() {
        state = state.finish();
    }

    public boolean isRunning() {
        return state.isRunning();
    }
}