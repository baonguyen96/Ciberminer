package kwic;

import java.util.List;

public interface INoiseEliminator {
    List<String> getCleanLines();

    void generateNoiselessLines();
}
