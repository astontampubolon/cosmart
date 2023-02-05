package com.example.springapp.servicetest;

import com.example.springapp.model.Book;
import com.example.springapp.service.GetBookService;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GetBookServiceTest {

    @InjectMocks
    private GetBookService getBookService;

    @Test
    void testGetListBookSucces1() throws Exception {
        List<Book> books = getBookService.process("Married people, fiction");
        Assert.assertEquals(3, books.size());
    }

    @Test
    void testGetListBookSucces2() throws Exception {
        List<Book> books = getBookService.process("aston");
        Assert.assertTrue(books.isEmpty());
    }

    @Test
    void testGetListBookFailed() throws Exception {
        try {
            getBookService.process("aston", null);
        } catch (Exception e) {
            Assert.assertEquals("java.lang.Exception", e.toString());
            return;
        }
        Assert.fail();
    }
}
