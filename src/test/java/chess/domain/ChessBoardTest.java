package chess.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ChessBoardTest {

    private static final String WRONG_START_ERROR_MESSAGE = "시작 위치에 말이 없습니다.";
    private static final String OBSTACLE_IN_PATH_ERROR_MESSAGE = "경로에 다른 말이 있어서 이동할 수 없습니다.";
    private static final String WRONG_PAWN_PATH_ERROR_MESSAGE = "폰은 공격 할 때만 대각선으로 이동할 수 있습니다.";
    private static final String WRONG_DESTINATION_ERROR_MESSAGE = "해당 말이 갈 수 없는 위치입니다.";

    private ChessBoard chessBoard;

    @BeforeEach
    void setUp() {
        chessBoard = new ChessBoard();
    }

    @DisplayName("체스판에 초기 말을 세팅한다.")
    @Test
    void 체스판_초기화() {
        ChessBoard chessBoard = new ChessBoard();

        Map<Position, Piece> positionPieces = chessBoard.piecesByPosition();

        assertThat(positionPieces).hasSize(32);
    }

    @DisplayName("시작 위치에 말이 없으면 예외를 던진다.")
    @Test
    void 시작위치_말없음_예외() {
        assertThatThrownBy(() -> chessBoard.move(List.of(3, 1), List.of(4, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(WRONG_START_ERROR_MESSAGE);
    }

    @DisplayName("경로에 장애물이 있으면 예외를 던진다.")
    @Test
    void 경로_장애물_예외() {
        assertThatThrownBy(() -> chessBoard.move(List.of(1, 1), List.of(3, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(OBSTACLE_IN_PATH_ERROR_MESSAGE);
    }

    @DisplayName("폰은 대각선 이동 요청 시 공격 대상이 없으면 예외를 던진다.")
    @Test
    void 폰_공격_대상없음_예외() {
        assertThatThrownBy(() -> chessBoard.move(List.of(2, 1), List.of(3, 2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(WRONG_PAWN_PATH_ERROR_MESSAGE);
    }

    @DisplayName("말이 갈 수 없는 위치로 이동 요청 시 예외를 던진다.")
    @Test
    void 갈수없는_위치_예외() {
        assertThatThrownBy(() -> chessBoard.move(List.of(2, 1), List.of(5, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(WRONG_DESTINATION_ERROR_MESSAGE);
    }

    @DisplayName("폰은 대각선에 상대 팀의 말이 있을 때 공격할 수 있다.")
    @Test
    void 폰_대각선_공격() {
        chessBoard.move(List.of(2, 1), List.of(4, 1));
        chessBoard.move(List.of(4, 1), List.of(5, 1));
        chessBoard.move(List.of(5, 1), List.of(6, 1));
        chessBoard.move(List.of(6, 1), List.of(7, 2));

        Map<Position, Piece> piecesByPosition = chessBoard.piecesByPosition();

        assertThat(piecesByPosition.get(new Position(7, 2)))
                .isEqualTo(new Pawn(TeamColor.WHITE));
    }

    @DisplayName("말은 도착지가 이동할 수 있는 위치이면, 이동하고 이전의 위치는 비워준다.")
    @Test
    void 말_이동_위치_반영() {
        chessBoard.move(List.of(2, 1), List.of(4, 1));

        Map<Position, Piece> piecesByPosition = chessBoard.piecesByPosition();

        assertThat(piecesByPosition.containsKey(new Position(2, 1)))
                .isFalse();
        assertThat(piecesByPosition.get(new Position(4, 1)))
                .isEqualTo(new Pawn(TeamColor.WHITE));
    }
}
