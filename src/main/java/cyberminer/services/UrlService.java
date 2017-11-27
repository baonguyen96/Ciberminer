package cyberminer.services;

import cyberminer.model.Index;
import cyberminer.model.SearchOption;
import cyberminer.model.SearchResult;
import cyberminer.model.Url;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public interface UrlService {

    boolean validateUrl(Url url);

    SearchResult search(String query) throws SQLException;


    void generateIndices(Url url) throws SQLException;

    List<Index> getIndices();

    Url addUrl(Url url);

    boolean delUrl(int id);
    List<Url> getUrls() throws SQLException;

    void addNoiseWords(String noiseWords);
    List<String> getNoiseWords();

    List<SearchOption> getSearchOptions();

    void updateSearchMethod(int id);

    void updateHitrate(int id);
}
