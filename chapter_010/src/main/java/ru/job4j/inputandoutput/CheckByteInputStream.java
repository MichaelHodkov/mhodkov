package ru.job4j.inputandoutput;

import org.apache.log4j.Logger;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class CheckByteInputStream {
    private static final Logger LOG = Logger.getLogger(CheckByteInputStream.class);

    //метод должен проверить. что в байтовом потоке записано четное число.
    public boolean isNumber(InputStream in) {
        BufferedInputStream bufferedInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        boolean result = false;
        try {
            bufferedInputStream = new BufferedInputStream(in);
            byteArrayOutputStream = new ByteArrayOutputStream();
            int stream = bufferedInputStream.read();
            while (stream != -1) {
                byteArrayOutputStream.write((byte) stream);
                stream = bufferedInputStream.read();
            }
            try {
                if (Integer.parseInt(byteArrayOutputStream.toString()) % 2 == 0) {
                    result = true;
                }
            } catch (NumberFormatException e) {
                LOG.info("Stream not number", e);
            }
        } catch (IOException e) {
            LOG.error("Error: read input stream", e);
        } finally {
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    LOG.error("Error: close buffered input stream", e);
                }
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    LOG.error("Error: close byte array output stream", e);
                }
            }
        }
        return result;
    }
}
