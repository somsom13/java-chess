package chess.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class RollbackConnectionProvider {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "chess";
    private static final String OPTION = "?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호
    private static final String CUSTOM_ERROR_HANDLING = "&allowPublicKeyRetrieval=true";

    private RollbackConnectionProvider() {
    }

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" +
                DATABASE + OPTION + CUSTOM_ERROR_HANDLING, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            return connection;
        } catch (final Exception e) {
            System.err.println("DB 연결 오류: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
