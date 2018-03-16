package ru.job4j.jsoup;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class Setup {
    private static Logger log = Logger.getLogger(Parser.class);
    private String url;
    private String startUrl;
    private String login;
    private String password;
    private int hour = 24;
    private boolean flagProvlem = false;

    Setup(String fileName) {
        log.info(String.format("read seting (%s)", fileName));
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String stLine;
            while ((stLine = bufferedReader.readLine()) != null) {
                if (stLine.contains("#url=")) {
                    this.url = stLine.replace("#url=", "");
                } else if (stLine.contains("#start_url=")) {
                    this.startUrl = stLine.replace("#start_url=", "");
                } else if (stLine.contains("#login=")) {
                    this.login = stLine.replace("#login=", "");
                } else if (stLine.contains("#password=")) {
                    this.password = stLine.replace("#password=", "");
                } else if (stLine.contains("#hr=")) {
                    this.hour = Integer.valueOf(stLine.replace("#hr=", ""));
                }
            }
            if (url == null) {
                throw new NullPointerException("url is null");
            } else if (login == null) {
                throw new NullPointerException("login is null");
            } else if (password == null) {
                throw new NullPointerException("password is null");
            } else if (startUrl == null) {
                throw new NullPointerException("first page is null");
            }
        } catch (Exception e) {
            flagProvlem = true;
            log.error(e);
        }
    }

    public String getUrl() {
        return url;
    }

    public String getStartUrl() {
        return startUrl;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getHour() {
        return hour;
    }

    public boolean isFlagProvlem() {
        return flagProvlem;
    }
}
