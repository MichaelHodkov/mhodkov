package ru.job4j.carsale;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.job4j.models.Advert;
import ru.job4j.models.Brand;
import ru.job4j.storage.AdvertStorage;
import ru.job4j.storage.BrandStorage;
import ru.job4j.storage.CarStor;
import ru.job4j.storage.DefaultValue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@Controller
public class MainController {
    private static final Logger LOG = Logger.getLogger(MainController.class);
    private final CarStor carStor = CarStor.INSTANCE;

    public MainController() {
        new DefaultValue().addRoot();
        new DefaultValue().addBrand();
        new DefaultValue().addModel();
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String showMainPage(@RequestParam(value = "filter", required = false) String filter,
                               @RequestParam(value = "selBrand", required = false) String selBrand,
                               ModelMap model) {
        if (filter != null && !filter.isEmpty()) {
            if (filter.equals("day")) {
                model.addAttribute("Adverts", getDayAdvert());
            } else if (filter.equals("pic")) {
                model.addAttribute("Adverts", getPicAdvert());
            } else if (filter.equals("brand")) {
                model.addAttribute("Adverts",
                        carStor.getaStor().findByBrand(carStor.getbStor().findById(Integer.parseInt(selBrand))));
            } else if (filter.equals("all")) {
                model.addAttribute("Adverts", getActivAdvert());
            }
        } else {
            model.addAttribute("Adverts", getActivAdvert());
        }
        model.addAttribute("Brands", carStor.getbStor().getAll());
        return "MainPage";
    }

    private List<Advert> getActivAdvert() {
        List<Advert> list = carStor.getaStor().getAll();
        List<Advert> activeList = new ArrayList<>();
        for (Advert advert: list) {
            if (advert.isStatus()) {
                activeList.add(advert);
            }
        }
        return activeList;
    }

    private List<Advert> getPicAdvert() {
        List<Advert> list = carStor.getaStor().getAll();
        List<Advert> picList = new ArrayList<>();
        for (Advert advert: list) {
            if (advert.getPicture().length > 0) {
                picList.add(advert);
            }
        }
        return picList;
    }

    private List<Advert> getDayAdvert() {
        List<Advert> list = carStor.getaStor().getAll();
        List<Advert> dayList = new ArrayList<>();
        Timestamp time = new Timestamp(System.currentTimeMillis() - 86400000);
        for (Advert advert: list) {
            if (advert.getTime().after(time)) {
                dayList.add(advert);
            }
        }
        return dayList;
    }
}
