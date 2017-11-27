package cyberminer.kwic;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("circularShift")
@Transactional
public class CircularShiftImpl implements CircularShift {

    private  List<String> lines;
    private LineStorage lineStorage;

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public LineStorage getLineStorage() {
        return lineStorage;
    }

    @Override
    public void setLineStorage(LineStorage lineStorage) {
        this.lineStorage = lineStorage;
    }

    @Override
    public List<String> getLines() {
        return lines;
    }

    @Override
    public void generateCircularShift()
    {
        this.setLines(new ArrayList<String>());

        String upcase = lineStorage.getLine().trim();

        List<String> words = new ArrayList(Arrays.asList(upcase.split(" ")));

        int lastIndex = words.size() - 1;

        lines.add(upcase);
        for (int i = 0; i < lastIndex ; ++i) {
            words.add(lastIndex,words.remove(0));
            lines.add(arrToString(words));
        }
    }

    private static String arrToString(List<String> arr){
        StringBuilder builder = new StringBuilder();
        for (String node: arr) {
            builder.append(node);
            builder.append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

}
