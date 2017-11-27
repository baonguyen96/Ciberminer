package cyberminer.controller;

import cyberminer.model.Url;
import cyberminer.services.UrlService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

@Controller
@RequestMapping(value="/url")
public class UrlController {

    private static final Logger logger = Logger.getLogger(UrlController.class);

    @Autowired
    UrlService urlService;


    @RequestMapping(value="/addUrl", method = RequestMethod.GET)
    public ModelAndView addUrl()
    {
        return new ModelAndView("addUrl", "url", new Url());
    }

    @RequestMapping(value="/addUrl", method = RequestMethod.POST)
    public String addUrl(@RequestParam("url") String url,@RequestParam("description") String desc,Model model) throws SQLException {
        Url pUrl = new Url();
        pUrl.setUrlLink(url);
        pUrl.setDescription(desc);
        if(urlService.validateUrl(pUrl)) {

          urlService.generateIndices(urlService.addUrl(pUrl));
        }
        else
        {
            return "error";
        }
        model.addAttribute("message","Add Successful!");
        return "success";
    }


    @RequestMapping(value="/hitrate", method = RequestMethod.POST)
    public @ResponseBody String updateHitrate(@RequestParam("urlId") int id) throws SQLException {
        urlService.updateHitrate(id);
        return "Success";
    }


    @RequestMapping(value="/deleteUrl", method = RequestMethod.POST)
    public String deleteUrl(@RequestParam("id") int id,Model model) throws SQLException {
        urlService.delUrl(id);
        model.addAttribute("message","Delete Successful!");
        return "success";
    }


}
