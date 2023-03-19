package chess.domain;

import chess.domain.position.Position;

public class ChessGame {

    private final ChessBoard chessBoard;
    private TeamColor teamColor;

    public ChessGame() {
        this.chessBoard = new ChessBoard();
        this.teamColor = TeamColor.WHITE;
    }

    public void move(Position source, Position dest) {
        chessBoard.move(source, dest, teamColor);
        teamColor = teamColor.transfer();
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

}
