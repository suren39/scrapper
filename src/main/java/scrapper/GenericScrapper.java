package scrapper;

import java.util.List;

/**
 * Full class description here.
 * Created by Grinch on 9/21/2014.
 * scrapper
 */
public interface GenericScrapper<E> {

    List<E> getAllLinks(String url);
    List<E> getAllLinksByRegex(String url, String regex);
    List<E> getAllLinksByClass(String url, String cssClassName);
    List<E> getAllImageSources(String url);

    String getElementsHtmlById(String url, String id);
    String getRawHtml(String url);

    void downloadAllImages(List<String> imageUrls, String outputFolder);
    void downloadAllImages(String url, String outputFolder);
}
