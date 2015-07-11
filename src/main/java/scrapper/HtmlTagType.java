package scrapper;

/**
 * Full class description here.
 * Created by Grinch on 9/21/2014.
 * scrapper
 */
public enum HtmlTagType {

    /**
     * <a>Html tag</a>
     */
    A("a"),

    /**
     * <a href="thisTag"> Html tag</a>
     */
    HREF("href");

    private final String tag;

    private HtmlTagType(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return this.tag;
    }
}
