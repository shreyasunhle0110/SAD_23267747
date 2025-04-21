package test.mainlibrary;

import org.junit.jupiter.api.*;

import mainlibrary.TransBookDao;

import static org.junit.jupiter.api.Assertions.*;

public class TransBookDaoTest {
    @Test
    public void testBookValidation() {
        assertTrue(TransBookDao.bookValidate(1).orElse(false));
    }
    
    @Test
    public void testUserValidation() {
        assertFalse(TransBookDao.userValidate(-1).orElse(true));
    }
}

