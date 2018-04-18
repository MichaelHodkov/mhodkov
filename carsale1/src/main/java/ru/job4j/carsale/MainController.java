package ru.job4j.carsale;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.job4j.models.Advert;
import ru.job4j.models.Brand;
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
    private final CarStorage carStorage = CarStorage.INSTANCE;

    public MainController() {
        carStorage.start();
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
                model.addAttribute("Adverts", this.carStorage.getAdvertDay());
            } else if (filter.equals("pic")) {
                model.addAttribute("Adverts", getAdvertsPic(carStorage.getActivAdvert()));
            } else if (filter.equals("brand")) {
                model.addAttribute("Adverts", this.carStorage.getAdvertBrand(Integer.parseInt(selBrand)));
            } else if (filter.equals("all")) {
                model.addAttribute("Adverts", this.carStorage.getActivAdvert());
            }
        } else {
            model.addAttribute("Adverts", this.carStorage.getActivAdvert());
        }
        model.addAttribute("Brands", this.carStorage.getList(Brand.class.getSimpleName()));
        return "MainPage";
    }

    private List<Advert> getAdvertsPic(List<Advert> list) {
        List<Advert> newList = new ArrayList<Advert>();
        for (Advert advert : list) {
            if (advert.getPicture().length > 0) {
                newList.add(advert);
            }
        }
        return newList;
    }
}
