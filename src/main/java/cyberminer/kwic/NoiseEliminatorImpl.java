package cyberminer.kwic;

import cyberminer.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("noiseEliminator")
@Transactional
public class NoiseEliminatorImpl implements NoiseEliminator {



    private  List<String> cleanLines = new ArrayList<String>();

    public List<String> getShiftedLines() {
        return shiftedLines;
    }

    @Override
    public void setShiftedLines(List<String> shiftedLines) {
        this.shiftedLines = shiftedLines;
    }

    private List<String> shiftedLines;

    @Autowired
    UrlService urlService;

    public List<String> getCleanLines() {
        return cleanLines;
    }
    public void setCleanLines(List<String> cleanLines) {
        this.cleanLines = cleanLines;
    }

    public void generateNoiselessLines() {

        this.setCleanLines(new ArrayList<String>());
        List<String> noiseWords = urlService.getNoiseWords();
        for(int i=0; i<shiftedLines.size();i++)
        {
            String[] temp = shiftedLines.get(i).split(" ");
            if(noiseWords.contains(temp[0]))
            {
                continue;
            }
            else
            {
                cleanLines.add(shiftedLines.get(i));
            }
        }
    }
}
