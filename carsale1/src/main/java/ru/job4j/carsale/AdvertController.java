package ru.job4j.carsale;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.job4j.models.Advert;
import ru.job4j.models.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@Controller
public class AdvertController {
    private static final Logger LOG = Logger.getLogger(AdvertController.class);
    private final CarStorage carStorage = CarStorage.INSTANCE;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getNewAdvertPage(HttpSession session) {
        if (session.getAttribute("login") == null) {
            return "redirect:login";
        } else {
            return "NewAdvertPage";
        }
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String getAdvert(@RequestParam("id") String id) {
        return "ViewAdvertPage";
    }

    @RequestMapping(value = "/useradverts", method = RequestMethod.GET)
    public String getUserAdvertsPage() {
        return "ViewUserAdvertPage";
    }

    @RequestMapping(value = "/useradverts", method = RequestMethod.POST)
    public String delAdvert(@RequestParam("id") String id) {
        Advert advert = carStorage.getAdvert(Integer.parseInt(id));
        carStorage.delObject(advert);
        return "ViewUserAdvertPage";
    }

    @RequestMapping(value = "/editstatus", method = RequestMethod.POST)
    public String editStatusAdvert(@RequestParam("id") String id) {
        Advert advert = carStorage.getAdvert(Integer.parseInt(id));
        advert.setStatus(!advert.isStatus());
        carStorage.addObject(advert);
        return "redirect:view";
    }

    @RequestMapping(value = "/getmodel", method = RequestMethod.POST)
    @ResponseBody
    public  List<Model> getModelsList(@RequestParam("id") String id) {
        if (id != null) {
            return carStorage.getModels(Integer.parseInt(id));
        }
        return null;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addAdvert(HttpSession session, HttpServletRequest req) {
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            boolean isMultipart = ServletFileUpload.isMultipartContent(req);
            if (isMultipart) {
                int id = (int) req.getSession().getAttribute("user_id");
                String name = "";
                String desc = "";
                int idBrand = -1;
                int idModel = -1;
                byte[] picture = {};
                Iterator iter = new ServletFileUpload(factory).parseRequest(req).iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (item.isFormField()) {
                        String text = IOUtils.toString(item.getInputStream(), "utf-8");
                        if (item.getFieldName().equals("selBrand")) {
                            idBrand = Integer.parseInt(text);
                        } else if (item.getFieldName().equals("selModel")) {
                            idModel = Integer.parseInt(text);
                        } else if (item.getFieldName().equals("name")) {
                            name = text;
                        } else if (item.getFieldName().equals("desc")) {
                            desc = text;
                        }
                    } else {
                        if (item.getContentType().contains("image/jpeg")) {
                            picture = Base64.getEncoder().encode(item.get());
                        }
                    }
                }
                if (!name.isEmpty() && !desc.isEmpty() && idBrand > 0 && idModel > 0) {
                    carStorage.addObject(new Advert(carStorage.getUser(id), idBrand, idModel, name, desc, picture));
                }
            }
            return "redirect:main";
        } catch (FileUploadException e) {
            LOG.error("Ошибка при работе с загрузкой файла", e);
        } catch (IOException e) {
            LOG.error("Ошибка при работе с потоком", e);
        }
        return "redirect:add";
    }
}
