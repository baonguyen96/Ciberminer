package cyberminer.dao;

import cyberminer.model.Index;
import cyberminer.model.SearchOption;
import cyberminer.model.SearchResult;
import cyberminer.model.Url;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Qualifier("urlDao")
public class UrlDaoImpl implements UrlDao {

    private static final Logger logger = Logger.getLogger(UrlDaoImpl.class);

    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource (DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean validateUrl(Url pUrl) {

        try{
        jdbcTemplate.queryForObject(
                "SELECT * FROM URL WHERE urlLink = ?", new Object[]{pUrl.getUrlLink()},
                new UrlMapper());
        return false;
    }catch (EmptyResultDataAccessException e) {
        return true;
    }
    }

    @Override
    public List<Url> getUrls() throws SQLException {
        List<Map<String, Object>> result = null;
        List<Url> urls = new ArrayList<>();
        try{

            result = jdbcTemplate.queryForList("SELECT * FROM URL");
            for(int k=0;k<result.size();k++)
            {
                Url temp = mapRow(result.get(k),k);
                urls.add(temp);
            }

        }catch (EmptyResultDataAccessException e) {

        }
        return urls;
    }

    @Override
    public void addNoiseWords(String[] noiseWords) {
        for(int i =0;i<noiseWords.length;i++) {
            jdbcTemplate.update("INSERT IGNORE INTO NOISEWORDS (noiseWord) VALUES (?)", noiseWords[i]);
        }
    }

    @Override
    public List<String> getNoiseWords() {

        List<String> result = null;
        result = jdbcTemplate.queryForList("SELECT noiseword FROM NOISEWORDS",String.class);
        return result;
    }

    @Override
    public void updateHitrate(int id) {
        jdbcTemplate.update("UPDATE URL SET HITRATE=HITRATE+1 WHERE id="+id);
    }

    @Override
    public List<SearchOption> getSearchOpt() {

        List<Map<String,Object>> result = null;
        result = jdbcTemplate.queryForList("SELECT * FROM SEARCHOPT");
        List<SearchOption> options = new ArrayList<>();
        for(int k=0;k<result.size();k++)
        {
            Map<String,Object> rs = result.get(k);
            SearchOption searchOpt = new SearchOption();
            searchOpt.setId((int)rs.get("id"));
            searchOpt.setSearchopt((String)rs.get("searchopt"));
            searchOpt.setFlag((int)rs.get("flag"));
            options.add(searchOpt);
        }

        return options;
    }

    @Override
    public void updateSearchMethod(int id){
        int prevId = jdbcTemplate.queryForObject("SELECT ID FROM SEARCHOPT WHERE FLAG=1",Integer.class);
        jdbcTemplate.update("UPDATE SEARCHOPT SET FLAG=0 WHERE id="+prevId);
        jdbcTemplate.update("UPDATE SEARCHOPT SET FLAG=1 WHERE id="+id);
    }

    @Override
    public Url addUrl(final Url pUrl) {

         jdbcTemplate.update("INSERT INTO URL (urlLink, description,hitrate) VALUES (?, ?, ?)", pUrl.getUrlLink(), pUrl.getDescription(),pUrl.getHitrate());
         Url url = (Url)jdbcTemplate.queryForObject(
                 "SELECT * FROM URL WHERE urlLink = ?", new Object[]{pUrl.getUrlLink()},
                 new UrlMapper());
         System.out.println("Url Added!!");
         return url;

    }

    @Override
    public void addIndices(List<Index> indices,Url pUrl) throws SQLException {

        jdbcTemplate.update("TRUNCATE TABLE INDEXES");
        jdbcTemplate.update("ALTER TABLE INDEXES AUTO_INCREMENT = 1");
        for(int i=0 ;i<indices.size();i++) {
            jdbcTemplate.update("INSERT INTO INDEXES (urlId, shift) VALUES (?, ?)", indices.get(i).getUrlId(), indices.get(i).getShift());
        }
    }

    @Override
    public List<Index> getIndices() {
        List<Map<String,Object>> result = jdbcTemplate.queryForList("SELECT urlId,shift  FROM INDEXES");

        List<Index> prevIndices = new ArrayList<>();
        for(int k=0;k<result.size();k++)
        {
            Index index = new Index();
            index.setUrlId((int)result.get(k).get("urlId"));
            index.setShift((String)result.get(k).get("shift"));
            prevIndices.add(index);
        }
        return prevIndices;

    }


    @Override
    public boolean delUrl(int urlId) {

        jdbcTemplate.update("DELETE FROM INDEXES WHERE urlId="+urlId);
        jdbcTemplate.update("DELETE FROM URL WHERE id="+urlId);
        System.out.println("Url deleted!!");
        return false;
    }

    @Override
    public SearchResult search(String q) throws SQLException {


        //jdbcTemplate.queryForRowSet("SELECT ID FROM SEARCHOPT WHERE FLAG=1");
        String searchopt = jdbcTemplate.queryForObject("SELECT searchopt FROM SEARCHOPT WHERE FLAG=1",String.class);

        String[] keywords = q.split(" ");
        String statement =" ";
        List<Map<String, Object>> result = null;
        SearchResult searchResult = new SearchResult();
        String query = " ";
        if(searchopt.equals("AND")) {
            query = " SELECT DISTINCT urlId from INDEXES where shift like '" + q + "%'";
        }
        else if(searchopt.equals("OR")){
            statement="";
            for (int i = 0; i < keywords.length; i++) {
                statement = statement + " SELECT DISTINCT urlId from INDEXES where shift like '" + keywords[i] + "%' UNION";
            }
            query= statement.substring(0, statement.lastIndexOf(" "));
        }else if(searchopt.equals("NOT")) {
            statement="";
            for (int i = 0; i < keywords.length; i++) {
                statement = statement + " SELECT DISTINCT urlId from INDEXES where shift like '" + keywords[i] + "%' UNION";
            }
            String innerQuery = statement.substring(0, statement.lastIndexOf(" "));
            query = "SELECT * FROM URL WHERE id NOT IN (" + innerQuery + ")";

        }

        if(searchopt.equals("AND") || searchopt.equals("OR")) {
            List<Integer> urlIds = jdbcTemplate.queryForList(query, Integer.class);

            if(!urlIds.isEmpty()) {
                statement = "";
                for (int j = 0; j < urlIds.size(); j++) {
                    statement = statement + " SELECT * FROM URL WHERE id=" + urlIds.get(j) + " UNION";
                }

                query = statement.substring(0, statement.lastIndexOf(" "));
            }
        }

            result = jdbcTemplate.queryForList(query);
            List<Url> urls = new ArrayList<>();
            for(int k=0;k<result.size();k++)
            {
                Url temp = mapRow(result.get(k),k);
                urls.add(temp);
            }
            searchResult.setUrlList(urls);

        System.out.println(statement);

        return searchResult;
    }

    private Url mapRow(Map<String,Object> rs, int rowNum) throws SQLException {
            Url url = new Url();
            url.setId((int)rs.get("id"));
            url.setUrlLink((String)rs.get("urlLink"));
            url.setDescription((String)rs.get("description"));
            url.setHitrate((int)rs.get("hitrate"));
            return url;
        }

    private static final class UrlMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Url url = new Url();
            url.setId(rs.getInt("id"));
            url.setUrlLink(rs.getString("urlLink"));
            url.setDescription(rs.getString("description"));
            url.setHitrate(rs.getInt("hitrate"));
            return url;
        }
    }

}
