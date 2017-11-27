package cyberminer.services;

import cyberminer.dao.UrlDao;
import cyberminer.kwic.AlphabetizedCircularShift;
import cyberminer.kwic.CircularShift;
import cyberminer.kwic.LineStorage;
import cyberminer.kwic.NoiseEliminator;
import cyberminer.model.Index;
import cyberminer.model.SearchOption;
import cyberminer.model.SearchResult;
import cyberminer.model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service("urlService")
@Transactional
public class UrlServiceImpl implements UrlService {

    @Autowired
    private UrlDao urlDao;

    @Autowired
    CircularShift shifter;

    @Autowired
    AlphabetizedCircularShift alphabetizer;

    @Autowired
    NoiseEliminator noiseEliminator;

    @Override
    public boolean validateUrl(Url url) {

        return urlDao.validateUrl(url);
    }

    @Override
    public SearchResult search(String query) throws SQLException {
         return urlDao.search(query);

    }

    @Override
    public void generateIndices(Url url) throws SQLException {

        LineStorage sentence = new LineStorage(url.getDescription());

        shifter.setLineStorage(sentence);
        shifter.generateCircularShift();

        noiseEliminator.setShiftedLines(shifter.getLines());
        noiseEliminator.generateNoiselessLines();

        alphabetizer.setLines(noiseEliminator.getCleanLines());
        alphabetizer.generateAlphabetizedCircularShift(url.getId());

        urlDao.addIndices(alphabetizer.getSorted_lines(),url);

    }
    @Override
    public List<Index> getIndices()
    {
        return urlDao.getIndices();
    }

    @Override
    public Url addUrl(Url url) {

        return urlDao.addUrl(url);
    }

    @Override
    public boolean delUrl(int id) {
        return urlDao.delUrl(id);
    }

    @Override
    public List<Url> getUrls() throws SQLException {
        return urlDao.getUrls();
    }

    @Override
    public void addNoiseWords(String pNoiseWords) {
        String[] noiseWords = pNoiseWords.split(",");
        urlDao.addNoiseWords(noiseWords);
    }

    @Override
    public List<String> getNoiseWords() {
        return urlDao.getNoiseWords();
    }

    @Override
    public List<SearchOption> getSearchOptions() {
        return urlDao.getSearchOpt();
    }

    @Override
    public void updateSearchMethod(int id) {
        urlDao.updateSearchMethod(id);
    }

    @Override
    public void updateHitrate(int id) {
        urlDao.updateHitrate(id);
    }
}
