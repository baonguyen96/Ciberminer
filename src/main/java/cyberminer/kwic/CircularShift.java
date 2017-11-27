package cyberminer.kwic;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CircularShift {

    void setLineStorage(LineStorage lineStorage);

    List<String> getLines();
    void generateCircularShift();

}
