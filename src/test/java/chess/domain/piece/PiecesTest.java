package chess.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.InitialPiece;
import chess.domain.TeamColor;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class PiecesTest {

    @DisplayName("한 줄에 같은 색의 폰이 2개 이상이라면 각각 0.5점으로 계산한다.")
    @Test
    void 같은_팀_폰_2개() {
        Pieces pieces = new Pieces(List.of(InitialPiece.BLACK_PAWN.getPiece(),
            InitialPiece.BLACK_PAWN.getPiece(),
            InitialPiece.WHITE_PAWN.getPiece()));

        assertThat(pieces.calculateScoreOfTeam(TeamColor.BLACK)).isEqualTo(1);
    }

    @DisplayName("한 줄에 같은 색의 폰이 1개 이하면 1점으로 계산한다.")
    @Test
    void 같은_팀_폰_1개() {
        Pieces pieces = new Pieces(List.of(InitialPiece.BLACK_PAWN.getPiece(),
            InitialPiece.BLACK_PAWN.getPiece(),
            InitialPiece.WHITE_PAWN.getPiece()));

        assertThat(pieces.calculateScoreOfTeam(TeamColor.WHITE)).isEqualTo(1);
    }

    @DisplayName("한 줄에 있는 현재 팀의 점수를 계산한다.")
    @Test
    void 같은_팀_점수_계산() {
        Pieces pieces = new Pieces(List.of(InitialPiece.BLACK_PAWN.getPiece(),
            InitialPiece.BLACK_BISHOP.getPiece(),
            InitialPiece.BLACK_KNIGHT.getPiece(),
            InitialPiece.WHITE_QUEEN.getPiece()));

        assertThat(pieces.calculateScoreOfTeam(TeamColor.BLACK)).isEqualTo(6.5);
    }

}