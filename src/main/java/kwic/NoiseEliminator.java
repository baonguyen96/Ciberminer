package kwic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NoiseEliminator implements INoiseEliminator {

    private  List<String> cleanLines = new ArrayList<String>();
    private List<String> shiftedLines;
    private static final List<String> noiseWords =
            Collections.unmodifiableList(Arrays.asList("THIS", "THE","IN","ON","IS"));

    public NoiseEliminator(List<String> pLines) {
        shiftedLines = pLines;
    }

    public List<String> getCleanLines() {
        return cleanLines;
    }

    public void generateNoiselessLines() {

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
