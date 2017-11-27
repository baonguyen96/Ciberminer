package cyberminer.kwic;

import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface NoiseEliminator {
    void setShiftedLines(List<String> shiftedLines);

    List<String> getCleanLines();

    void generateNoiselessLines();
}
