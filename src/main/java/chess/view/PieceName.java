package chess.view;

import chess.domain.TeamColor;

public enum PieceName {
    ROOK("R"),
    KNIGHT("N"),
    BISHOP("B"),
    QUEEN("Q"),
    KING("K"),
    PAWN("P");

    private final String name;

    PieceName(final String name) {
        this.name = name;
    }

    public String getName(TeamColor color) {
        if (color == TeamColor.WHITE) {
            return name.toLowerCase();
        }
        return name.toUpperCase();
    }

}