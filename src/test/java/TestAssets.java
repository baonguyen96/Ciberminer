public class TestAssets {

    enum Component {URL, DESCRIPTION}
    enum ValidSite {GOOGLE, UTD, WIKIPEDIA, FANFICTION}
    enum InvalidSite {INVALID_1, INVALID_2}
    enum SearchBy {ONE_WORD, MULTI_WORDS_1, MULTI_WORDS_2}

    public static final String[][] VALID_ITEMS = {
            {"http://www.google.com", "This is the Google home page"},
            {"http://www.utdallas.edu", "THE UNIVERSITY OF TEXAS AT DALLAS"},
            {"https://www.wikipedia.org", "Wikipedia is the world's largest encyclopedia yet"},
            {"https://www.fanfiction.net", "FAN FICTION website don't ask me why"}
    };

    public static final String[][] INVALID_ITEMS = {
            {"djhabdjabs", "Invalid url are you dumb?"},
            {"http://another.stupid.site.weird", "what the hell this I just read?"}
    };

    public static final String[][] VALID_SEARCHES = {
            {"Google", "the Google home page", "page This is"},
            {"DALLAS", "UNIVERSITY OF TEXAS", "OF TEXAS AT DALLAS"},
            {"Wikipedia", "encyclopedia yet Wikipedia", "is the world's"},
            {"FICTION", "website don't ask me why", "why FAN"}
    };

    public static final String[] INVALID_SEARCH = {
            "Nonexist", "this website does not exist", "random thing to type"
    };

}
