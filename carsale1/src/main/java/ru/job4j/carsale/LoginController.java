package ru.job4j.carsale;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.models.User;
import ru.job4j.storage.CarStor;
import ru.job4j.storage.UserStorage;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@Controller
public class LoginController {
    private static final Logger LOG = Logger.getLogger(LoginController.class);
    private final CarStor carStor = CarStor.INSTANCE;
    private int id = -1;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "LoginPage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String getAccess(@RequestParam("login") String login,
                            @RequestParam("password") String password,
                            HttpSession session,
                            ModelMap model) {
        List<User> list = carStor.getuStor().getAll();
        if (isAccess(list, login, password)) {
            session.setAttribute("login", login);
            session.setAttribute("user_id", id);
            return "redirect:main";
        } else {
            model.addAttribute("login", login);
            model.addAttribute("error", "invalid access");
        }
        return "LoginPage";
    }

    @RequestMapping(value = "/exit", method = RequestMethod.GET)
    public String exitSession(HttpSession session) {
        session.invalidate();
        return "redirect:main";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String getNewUserPage() {
        return "NewUserPage";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addNewUser(@RequestParam("login") String login,
                                @RequestParam("password") String password,
                                @RequestParam("password2") String password2,
                                HttpSession session,
                                ModelMap model) {
        if (!password.equals(password2)) {
            model.addAttribute("login", login);
            model.addAttribute("error", "Пароли не совпадают.");
            return "NewUserPage";
        } else if (password.matches("^[a-zA-z0-9]") || password.length() < 8) {
            model.addAttribute("login", login);
            model.addAttribute("error",
                    "Длина пароля должна быть не менее 8 символов. Обязатльно из букв и/или цифр.");
            return "NewUserPage";
        } else {
            List<User> list = carStor.getuStor().getAll();
            for (User user : list) {
                if (user.getLogin().equalsIgnoreCase(login)) {
                    model.addAttribute("login", login);
                    model.addAttribute("error", "Такой логин уже занят.");
                    return "NewUserPage";
                }
            }
            id = (carStor.getuStor().add(new User(login, password))).getId();
            session.setAttribute("login", login);
            session.setAttribute("user_id", id);
            return "redirect:main";
        }
    }

    private boolean isAccess(List<User> list, String login, String password) {
        boolean access = false;
        for (User user : list) {
            if (user.getLogin().equalsIgnoreCase(login) && user.getPassword().equals(password)) {
                id = user.getId();
                access = true;
                break;
            }
        }
        return access;
    }
}
