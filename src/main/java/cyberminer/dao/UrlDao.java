package cyberminer.dao;

import cyberminer.model.Index;
import cyberminer.model.SearchOption;
import cyberminer.model.SearchResult;
import cyberminer.model.Url;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public interface UrlDao {

    boolean validateUrl(Url url);

    List<SearchOption> getSearchOpt();

    void updateSearchMethod(int id);

    Url addUrl(Url url);

    void addIndices(List<Index> indices,Url url) throws SQLException;

    SearchResult search(String query) throws SQLException;

    List<Index> getIndices();

    boolean delUrl(int urlId);

    List<Url> getUrls() throws SQLException;

    void addNoiseWords(String[] noiseWords);

    List<String> getNoiseWords();

    void updateHitrate(int id);
}
