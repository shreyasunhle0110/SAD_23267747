package mainlibrary;

import org.junit.jupiter.api.*;
import org.mockito.*;
import java.sql.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsersDaoTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @Test
    void testAddUser_SQLInjectionAttempt() throws SQLException {
        String maliciousInput = "admin' OR '1'='1";
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("SQL syntax error"));
        assertThatThrownBy(() -> UsersDao.addUser(maliciousInput, "password"))
            .isInstanceOf(SQLException.class)
            .hasMessageContaining("SQL syntax error");
    }

    @Test
    void testHashPassword() {
        String password = "securePassword123";
        String hashedPassword = UsersDao.hashPassword(password);
        assertThat(hashedPassword).isNotNull().isNotEqualTo(password);
    }

    @Test
    void testValidateUser_Valid() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        boolean result = UsersDao.validateUser("admin", "password");
        assertThat(result).isTrue();
    }

    @Test
    void testValidateUser_Invalid() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);
        boolean result = UsersDao.validateUser("admin", "wrongpassword");
        assertThat(result).isFalse();
    }
}
