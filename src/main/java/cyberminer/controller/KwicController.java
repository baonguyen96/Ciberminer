package cyberminer.controller;

import cyberminer.kwic.AlphabetizedCircularShift;
import cyberminer.kwic.CircularShift;
import cyberminer.kwic.LineStorage;
import cyberminer.kwic.NoiseEliminator;
import cyberminer.model.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/kwic")
public class KwicController {

    @Autowired
    CircularShift shifter;

    @Autowired
    AlphabetizedCircularShift alphabetizer;

    @Autowired
    NoiseEliminator noiseEliminator;

    @RequestMapping(path="/rotate", method = RequestMethod.GET)
    public @ResponseBody
    List<String> circularShift(@RequestParam("sentence") String pSentence)
    {
        LineStorage sentence = new LineStorage(pSentence);

        shifter.setLineStorage(sentence);
        shifter.generateCircularShift();

        return shifter.getLines();
    }

    @RequestMapping(path="/sort", method = RequestMethod.GET)
    public @ResponseBody List<Index> sortCircularShift()
    {
        noiseEliminator.setShiftedLines(shifter.getLines());
        noiseEliminator.generateNoiselessLines();

        alphabetizer.setLines(noiseEliminator.getCleanLines());
        alphabetizer.generateAlphabetizedCircularShift(-1);

        return alphabetizer.getSorted_lines();
    }

    @RequestMapping(path="/reset", method = RequestMethod.GET)
    public @ResponseBody boolean reset()
    {
        alphabetizer.reset();
        return true;
    }
}
