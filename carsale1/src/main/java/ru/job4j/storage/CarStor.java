package ru.job4j.storage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.job4j.models.Advert;
import ru.job4j.models.Brand;
import ru.job4j.models.Model;
import ru.job4j.models.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public enum CarStor {

    INSTANCE;

    private final UserStorage uStor = new UserStorage();
    private final BrandStorage bStor = new BrandStorage();
    private final ModelStorage mStor = new ModelStorage();
    private final AdvertStorage aStor = new AdvertStorage();

    public UserStorage getuStor() {
        return uStor;
    }

    public BrandStorage getbStor() {
        return bStor;
    }

    public ModelStorage getmStor() {
        return mStor;
    }

    public AdvertStorage getaStor() {
        return aStor;
    }
}
