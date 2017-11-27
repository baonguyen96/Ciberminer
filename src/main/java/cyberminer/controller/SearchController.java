package cyberminer.controller;

import cyberminer.model.SearchResult;
import cyberminer.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.Map;

@Controller
@RequestMapping(value="/searchUrl")
public class SearchController {

    @Autowired
    UrlService urlService;

    @RequestMapping(value="", method = RequestMethod.GET)
    public String search(Model model) throws SQLException {
        model.addAttribute("keywords");
        return "searchUrl";
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public String search(@RequestParam("search") String query, Map<String,Object> map) throws SQLException {
        SearchResult searchResult = urlService.search(query);
        map.put("res",searchResult);
        return "searchUrl";
    }
}
