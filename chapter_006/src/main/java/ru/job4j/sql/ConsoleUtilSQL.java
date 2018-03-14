package ru.job4j.sql;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ConsoleUtilSQL {
    private String url;
    private String login;
    private String password;
    private int n;
    private SQLConnect connect;
    private SQLRequest request;
    private JAXBWorker jaxbWorker;
    private XSLTWorker xsltWorker;
    private ParsingWork parsingWork;
    private MyDate date;
    private long timeStar;
    private long timeStop;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getUrl() {
        return this.url;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public int getN() {
        return this.n;
    }

    public void setup(String url, String login, String password, int n) {
        setUrl(url);
        setLogin(login);
        setPassword(password);
        setN(n);
    }

    public void start() {
        timeStar = System.currentTimeMillis();
        date = new MyDate();
        date.printDate("Star");
        date.printDate("Connect");
        this.connect = new SQLConnect(getUrl(), getLogin(), getPassword(), false);
        this.request = new SQLRequest(this.connect.getConnection());
        date.printDate("done");
        date.printDate("Create table TEST");
        this.request.createTable();
        date.printDate("Done");
        date.printDate(String.format("Add (1..%d) in table TEST", getN()));
        this.request.addData(getN());
        date.printDate("Done");
        date.printDate("Create XML");
        jaxbWorker = new JAXBWorker();
        jaxbWorker.work(this.request.selectItems(), "chapter_006/1.xml");
        date.printDate("Done");
        date.printDate("Convert XML");
        xsltWorker = new XSLTWorker();
        xsltWorker.work("chapter_006/1.xml", "chapter_006/2.xml", "chapter_006/style.xsl");
        date.printDate("Done");
        date.printDate("Parsing XML");
        parsingWork = new ParsingWork();
        date.printDate(String.format("Done (sum = %d)", parsingWork.work("chapter_006/2.xml")));
    }

    public void stop() {
        this.connect.close();
        date.printDate("Connect end");
        timeStop = System.currentTimeMillis();
        long totalTime = (timeStop - timeStar) / 1000;
        int min = (int) totalTime / 60;
        int sec = (int) totalTime - (min * 60);
        System.out.println(String.format("Total time work: %d min %d sec", min, sec));
    }
}
