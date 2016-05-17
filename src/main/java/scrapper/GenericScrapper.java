package scrapper;

import java.util.List;

/**
 * Main generic scrapper interface.
 * Created by Grinch on 9/21/2014.
 */
public interface GenericScrapper<E> {

    /**
     * Returns a list E elements on the given URL.
     * @param url url to extract all the html a-tag elements.
     * @return list of E elements representing all html a-tag elements.
     */
    List<E> getAllLinks(String url);

    /**
     * Returns a list E elements on the given URL that match the given regex pattern.
     * @param url url to extract all the html a-tag elements.
     * @param regex regex pattern to match in the html a-tag.
     * @return list of E elements representing regex matched html a-tag elements.
     */
    List<E> getAllLinksByRegex(String url, String regex);

    /**
     * Returns a list E elements on the given URL that match the given css pattern.
     * @param url url to extract all the html a-tag elements.
     * @param cssClassName css class that the links are contained in.
     * @return list of E elements representing a-tag elements.
     */
    List<E> getAllLinksByCssClass(String url, String cssClassName);

    /**
     * Returns a string value of html element defined by given id.
     * @param url url to extract all the html elements.
     * @param id id of the element to get.
     * @return string of element by id.
     */
    String getElementsHtmlById(String url, String id);

    /**
     * Returns raw HTML of given URL.
     * @param url destination to download as raw HTML.
     * @return raw HTML of given URL.
     */
    String getRawHtml(String url);

    /**
     * Method downloads given list of image URL into provided location on local drive.
     * @param imageUrls images to download.
     * @param outputFolder location to download to.
     */
    void downloadAllImages(List<String> imageUrls, String outputFolder);
}
