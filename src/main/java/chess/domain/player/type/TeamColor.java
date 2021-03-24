package chess.domain.player.type;

import java.util.Arrays;

public enum TeamColor {
    WHITE("white", "백"),
    BLACK("black", "흑");

    private final String value;
    private final String koreanName;

    TeamColor(String value, String koreanName) {
        this.value = value;
        this.koreanName = koreanName;
    }

    public static TeamColor of(String teamColorInput) {
        return Arrays.stream(values())
            .filter(teamColor -> teamColor.value.equals(teamColorInput))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀 색깔 입니다."));
    }

    public TeamColor oppositeTeamColor() {
        if (this == WHITE) {
            return BLACK;
        }
        return WHITE;
    }

    public String KoreanName() {
        return koreanName;
    }
}