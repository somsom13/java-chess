package chess.domain.piece;

import chess.domain.*;
import chess.domain.coordinate.ChessCoordinate;

import java.util.*;
import java.util.stream.Collectors;

public class King extends ChessPiece {
    private static final King kingBlack = new King(PieceType.KING_BLACK);
    private static final King kingWhite = new King(PieceType.KING_WHITE);

    public static King getInstance(Team team) {
        if (team == Team.BLACK) {
            return kingBlack;
        }
        if (team == Team.WHITE) {
            return kingWhite;
        }
        throw new IllegalArgumentException("만들 수 없는 팀입니다.");
    }

    private King(PieceType pieceType) {
        super(pieceType);
    }

    @Override
    public Set<ChessCoordinate> getMovableCoordinates(PieceTeamProvider pieceTeamProvider, ChessCoordinate from) {
        List<ChessCoordinate> candidates = new ArrayList<>();

        from.getX().move(DECREASE_ONE).ifPresent(proveYSide(from, candidates));
        from.getX().move(INCREASE_ONE).ifPresent(proveYSide(from, candidates));

        proveXSide(from, candidates).accept(from.getY());
        proveYSide(from, candidates).accept(from.getX());

        return candidates.stream()
                .filter((coord) -> pieceTeamProvider.getTeamAt(coord) != getType().getTeam())
                .collect(Collectors.toSet());
    }

}