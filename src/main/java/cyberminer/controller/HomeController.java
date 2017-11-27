package cyberminer.controller;

import cyberminer.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.Map;

@Controller
@RequestMapping(value="/home")
public class HomeController {

    @Autowired
    UrlService urlService;

    @GetMapping("")
    public String hello(Map<String,Object> map) throws SQLException {
        map.put("urls",urlService.getUrls());
        map.put("options",urlService.getSearchOptions());
        return "index";
    }

}
