package ru.job4j.inputandoutput;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class DeleteBlockWordTest {
    private static final Logger LOG = Logger.getLogger(DeleteBlockWordTest.class);
    DeleteBlockWord deleteBlockWord = new DeleteBlockWord();

    @Test
    public void whenTestBlockListInInputAndOutputStream() {
        ByteArrayInputStream testIn = new ByteArrayInputStream(new String("Test tost cust").getBytes());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String[] strings = {"tost"};
        deleteBlockWord.dropAbuses(testIn, byteArrayOutputStream, strings);
        System.out.println(byteArrayOutputStream);
    }
}