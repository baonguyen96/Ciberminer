package cyberminer.kwic;//package kwic;

import cyberminer.model.Index;
import cyberminer.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("alphabetizedCircularShift")
@Transactional
public class AlphabetizedCircularShiftImpl implements AlphabetizedCircularShift {

    private List<String> lines= new ArrayList<String>();

    private List<Index> sorted_lines = new ArrayList();

    @Autowired
    UrlService urlService;

    @Override
    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public void setSorted_lines(List<Index> sorted_lines) {
        this.sorted_lines = sorted_lines;
    }

    @Override
    public void generateAlphabetizedCircularShift(int id){

        Collections.sort(lines);
        List<Index> indices = new ArrayList<>();
        for(int i=0; i<lines.size();i++)
        {
            Index index = new Index();
            index.setUrlId(id);
            index.setShift(lines.get(i));
            indices.add(index);
        }

        this.setSorted_lines(mergeIndices(indices,urlService.getIndices()));
    }

    private List<Index> mergeIndices(List<Index> indices, List<Index> prevIndices) {

        List<Index> merged = new ArrayList<>(indices.size()+prevIndices.size());

        merged.addAll(indices);
        merged.addAll(prevIndices);

        Collections.sort(merged);

        return merged;
    }

    @Override
    public List<Index> getSorted_lines() {
        return sorted_lines;
    }

    @Override
    public List<String> getLines() {
        return lines;
    }

    @Override
    public void reset() {
        lines = new ArrayList<String>();
    }
}
