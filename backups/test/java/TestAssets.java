public class TestAssets {

    enum Component {URL, DESCRIPTION}
    enum ValidSite {GOOGLE, UTD, WIKIPEDIA, FANFICTION}
    enum SearchBy {ONE_WORD, MULTI_WORDS}

    // add then delete
    public static final String[] FACEBOOK = {"https://www.facebook.com", "Social media black hole (end)"};

    public static final String[][] VALID_ITEMS = {
            // facebook will be deleted
            {"http://www.google.com", "This is Google home page (end)"},
            {"http://www.utdallas.edu", "THE UNIVERSITY OF TEXAS AT DALLAS (end)"},
            {"https://www.wikipedia.org", "Wikipedia is the world's largest encyclopedia yet (end)"},
            {"https://www.fanfiction.net", "This is FAN FICTION website don't ask me why (end)"}
    };

    public static final String[][] INVALID_ITEMS = {
            {"djhabdjabs", "Invalid url are you dumb?"},
            {"http://another.stupid.site.weird", "wth did I just read?"}
    };

    public static final String[][] VALID_SEARCHES = {
            // facebook will be deleted
            {"Google", "Google home page"},
            {"DALLAS", "UNIVERSITY OF TEXAS"},
            {"Wikipedia", "largest encyclopedia yet"},
            {"FICTION", "website don't ask me why"}
    };

    public static final String[] INVALID_SEARCHES = {
            "Nonexist",
            FACEBOOK[Component.DESCRIPTION.ordinal()]
    };

    public static final String SEARCH_WITH_MULTIPLE_RESULTS = "This is";

    public static final String SEARCH_COMMON_TO_ALL = "(end)";

    public static final String IGNORED_SYMBOLS = "'@','#','`'";

}
