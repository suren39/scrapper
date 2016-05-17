package api;

import scrapper.GenericScrapper;
import scrapper.GenericScrapperJsoupImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Full class description here.
 * Created by Grinch on 9/21/2014.
 * drivers
 */
public class GenericScrapperDriver {

    public static void main(String[] args) {

        final GenericScrapper<String> scrapper = new GenericScrapperJsoupImpl();

        final List<String> aTags =
                scrapper.getAllLinks("http://orderandchaosduels.wikia.com/wiki/Epic_Minion_Cards");

        final List<String> relativeURls = keepRelativeUrl(aTags);

        final List<String> fullImagePath = new ArrayList<>();
        final List<String> trash = new ArrayList<>();

        for (String link : relativeURls) {
            try {

                List<String> imagesByClass =
                        scrapper.getAllLinksByCssClass(link, "image-thumbnail");

                for (String temp : imagesByClass) {
                    if (temp.contains("images")) {
                        fullImagePath.add(temp);
                    } else {
                        trash.add(temp);
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR FOR: " + link);
            }
        }

        for (String cleanUrl : fullImagePath) {
            System.out.println(cleanUrl);
        }

        System.out.println("*******************************************************");

        for (String trashUrl : trash) {
            System.out.println(trashUrl);
        }

        scrapper.downloadAllImages(fullImagePath, "C://scrap//");
    }

    public static List<String> keepRelativeUrl(List<String> links) {

        final List<String> fullImages = new ArrayList<String>();

        for(String link : links) {
            if (link.contains("/wiki") && !link.contains(".jpg")) {
                fullImages.add("http://orderandchaosduels.wikia.com" + link);
            }
        }

        return fullImages;
    }
}
