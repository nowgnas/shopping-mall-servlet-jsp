package config;

import org.apache.ibatis.session.SqlSession;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;

public class TestConfig {

    public void init(String resourceName, SqlSession session) throws Exception {
        Connection connection = session.getConnection();

        // Execute data initialization script
        String initDataScript = readScript(resourceName);
        Statement statement = connection.createStatement();
        statement.executeUpdate(initDataScript);

        statement.close();
        connection.commit();
    }

    private String readScript(String resourceName) throws Exception {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            return new String(bytes);
        }
    }

    public void destroy(SqlSession session) {

    }
}
