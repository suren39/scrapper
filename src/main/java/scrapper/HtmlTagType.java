package scrapper;

/**
 * Different HTML tag types.
 * Created by Grinch on 9/21/2014.
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

    HtmlTagType(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return this.tag;
    }
}
