public class TestAssets {

    enum Component {URL, DESCRIPTION}
    enum ValidSite {GOOGLE, UTD, WIKIPEDIA, FANFICTION}
    enum SearchBy {ONE_WORD, MULTI_WORDS_ORIGINAL_ORDER, MULTI_WORDS_SHUFFLE_ORDER}

    public static final String[][] VALID_ITEMS = {
            {"http://www.google.com", "This is the Google home page"},
            {"http://www.utdallas.edu", "THE UNIVERSITY OF TEXAS AT DALLAS"},
            {"https://www.wikipedia.org", "Wikipedia is the world's largest encyclopedia yet"},
            {"https://www.fanfiction.net", "This is the FAN FICTION website don't ask me why"}
    };

    public static final String[][] INVALID_ITEMS = {
            {"djhabdjabs", "Invalid url are you dumb?"},
            {"http://another.stupid.site.weird", "what the hell this I just read?"}
    };

    public static final String[][] VALID_SEARCHES = {
            {"Google", "the Google home page", "page this is"},
            {"DALLAS", "UNIVERSITY OF TEXAS", "at Dallas the "},
            {"Wikipedia", "largest encyclopedia yet", "encyclopedia yet Wikipedia"},
            {"FICTION", "website don't ask me why", "why fan"}
    };

    public static final String[] INVALID_SEARCHES = {
            "Nonexist", "this website does not exist", "random thing to type"
    };

    public static final String SEARCH_WITH_MULTIPLE_RESULTS = "This is the";

}
