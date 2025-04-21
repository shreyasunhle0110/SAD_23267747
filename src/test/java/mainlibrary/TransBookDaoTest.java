package mainlibrary;

import org.junit.jupiter.api.*;
import org.mockito.*;
import java.sql.*;
import java.util.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransBookDaoTest {

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

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, Integer.MAX_VALUE})
    void testBookValidation_EdgeCases(int bookId) throws SQLException {
        when(mockResultSet.next()).thenReturn(false);
        Optional<Boolean> result = TransBookDao.bookValidate(bookId);
        assertThat(result).isPresent().contains(false);
    }

    @Test
    void testReturnBook_SQLInjectionAttempt() throws SQLException {
        String maliciousInput = "1; DROP TABLE IssuedBook;--";
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("SQL syntax error"));
        assertThatThrownBy(() -> TransBookDao.returnBook(Integer.parseInt(maliciousInput), 1))
            .isInstanceOf(SQLException.class)
            .hasMessageContaining("SQL syntax error");
    }

    @Test
    void testIssueBook_Success() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        Optional<Integer> result = TransBookDao.issueBook(1, 1, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        assertThat(result).isPresent().contains(1);
    }

    @Test
    void testCheckIssuedBook_Valid() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        Optional<Boolean> result = TransBookDao.checkIssuedBook(1);
        assertThat(result).isPresent().contains(true);
    }
}
