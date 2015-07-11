package drivers;

import scrapper.GenericScrapper;
import scrapper.GenericScrapperJsoupImpl;
import util.PatternMatcher;
import util.UtilPrinter;

import java.util.ArrayList;
import java.util.List;

/**
 * Full class description here.
 * Created by Grinch on 4/11/2015.
 * drivers
 */
public class SciHyScrapperDriver {

    private static final String ROOT_URL = "http://www.nhl.com/";
    private static final String DEFAULT_SEED = "http://www.nhl.com/stats/player?pg=###";
    private static final String DEFAULT_SEED_CARROT = "###";
    private static final String DEFAULT_REGEX = "player.htm.id";
    private static final String DEFAULT_PAGES_REGEX = "player.*pg.*";

    public static void main(String[] args) {

        // count max pages of players / currently 30 players per page
        final Integer maxPages = countMaxPages();

        // max loop count
        UtilPrinter.print(maxPages);

        // initializing the main generic scrapper interface
        final GenericScrapper<String> genericScrapper = new GenericScrapperJsoupImpl();

        // the list will hold every play
        final List<String> allPlayersUrls = new ArrayList<String>();

        for (int i = 1; i <= maxPages; i++) {

            // log
            UtilPrinter.print("Starting page " + i);

            // initialize the seed
            final String seed = initSeed();

            // create new page URL
            final String currentPageUrl = seed.replace(DEFAULT_SEED_CARROT, i + "");

            // log
            UtilPrinter.print("Going to URL: " + currentPageUrl);

            // get all the links from the seed page
            final List<String> playerUrls =
                    genericScrapper.getAllLinksByRegex(currentPageUrl, initPlayerRegex());

            // aggregate players
            allPlayersUrls.addAll(playerUrls);

            UtilPrinter.print("Added player count: " + playerUrls.size());
        }

        // print for now
        UtilPrinter.print(allPlayersUrls);
        UtilPrinter.print(allPlayersUrls.size());
    }

    private static String initPlayerRegex() {
        return DEFAULT_REGEX;
    }

    private static String initTotalPagesRegex() {
        return DEFAULT_PAGES_REGEX;
    }

    private static String initSeed() {
        return DEFAULT_SEED;
    }

    private static Integer countMaxPages() {

        // initializing the main generic scrapper interface
        final GenericScrapper<String> genericScrapper = new GenericScrapperJsoupImpl();

        // initialize the seed
        final String seed = initSeed();

        // get all the links from the seed page
        final List<String> pagePaginationUrls =
                genericScrapper.getAllLinksByRegex(seed, initTotalPagesRegex());

        // get the last element in the index
        final Integer lastListIndex = pagePaginationUrls.size() - 1;

        // get the last index
        final String lastIndexUrl = pagePaginationUrls.get(lastListIndex);

        // get last index of '='
        final Integer lastEqualIndex = lastIndexUrl.lastIndexOf('=') + 1;

        // get everything after the '='
        final String stringMax = lastIndexUrl.substring(lastEqualIndex, lastIndexUrl.length());

        // return converted
        return Integer.valueOf(stringMax);
    }
}
