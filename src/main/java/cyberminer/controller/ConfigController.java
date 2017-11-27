package cyberminer.controller;

import cyberminer.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping(value="/configuration")
public class ConfigController {

    @Autowired
    UrlService urlService;
    @GetMapping("")
    public String config() {
        return "configuration";
    }

    @GetMapping("searchOpt")
    public String getSearchOptions(Map<String,Object> map) {
        map.put("options",urlService.getSearchOptions());
        return "searchOptions";
    }

    @RequestMapping(value="searchOpt",method= RequestMethod.POST)
    public String updateSearch(@RequestParam("searchOption") String id,Model model){
        urlService.updateSearchMethod(Integer.parseInt(id));
        model.addAttribute("message","Search Option Updated!");
        return "success";
    }

    @RequestMapping(value="noiseWords",method= RequestMethod.POST)
    public String addNoiseWords(@RequestParam("noiseWords") String nw,Model model){
        urlService.addNoiseWords(nw);
        model.addAttribute("message","Noise Words Updated!");
        return "success";
    }
}
