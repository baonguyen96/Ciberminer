package cyberminer.kwic;

import cyberminer.model.Index;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AlphabetizedCircularShift {

    void reset();

    void setLines(List<String> lines);

    void generateAlphabetizedCircularShift(int id);

    List<Index> getSorted_lines();

    List<String> getLines();

}
