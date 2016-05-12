package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * Created by johnjastrow on 5/4/16.
 */
@Controller
public class GameTrackerController {
    @Autowired
    GameRepository games;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model, String genre, Integer releaseYear) {
        String userName = (String) session.getAttribute("username");
        User user = users.findFirstByName(userName);
        if (user != null){
            model.addAttribute("user", user);
        }

        List<Game> gameList;
        if (genre != null) {
            gameList = games.findByGenre(genre);
        }
        else if (releaseYear != null) {
            gameList = games.findByReleaseYear(releaseYear);
        }
        else if (user != null) {
            gameList = games.findByUser(user);   // TODO: may want to add 'ByUser' to previous as well
        }
        else {
            gameList = games.findAll();
        }
        model.addAttribute("games", gameList);
        return "home";
    }

    @RequestMapping(path = "/add-game", method = RequestMethod.POST)
    public String addGame(HttpSession session, String gameName, String gamePlatform, String gameGenre, String gameYear) {
        String userName = (String) session.getAttribute("username");
        User user = users.findFirstByName(userName);

        if (gameGenre == null) gameGenre = "";
        if (gamePlatform == null) gamePlatform = "";
        if (gameName == null) gameName = "";

        int gameYearInt = 2000;
        if (gameYear != null && ! gameYear.isEmpty()) {
            gameYearInt = Integer.parseInt(gameYear);
        }

        Game game = new Game(gameName, gamePlatform, gameGenre, gameYearInt, user);
        games.save(game);
        return "redirect:/";
    }

    @Autowired
    UserRepository users;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password) throws Exception {
        User user = users.findFirstByName(userName);
        if (user == null){
            user = new User(userName, password);
            users.save(user);
        }
        else if (! password.equals(user.password)) {
            throw new Exception("Invalid login");
        }

        session.setAttribute("username", userName);

        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
