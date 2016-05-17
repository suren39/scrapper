package scrapper;

import java.util.Collections;
import java.util.HashSet;

import java.util.Set;

/**
 * Utility class for file types and their extensions.
 * Created by Grinch on 9/23/2014.
 * scrapper
 */
public enum FileType {

    PNG(".png", "png"),
    JPG(".jpg", "jpg"),
    JPEG(".jpeg", "jpeg"),
    GIF(".gif", "gif");

    public final static Set<FileType> imageTypes;

    static {
        final Set<FileType> tempImageTypes = new HashSet<>();
        tempImageTypes.add(PNG);
        tempImageTypes.add(JPG);
        tempImageTypes.add(JPEG);
        tempImageTypes.add(GIF);
        imageTypes = Collections.unmodifiableSet(tempImageTypes);
    }

    private final String extension;
    private final String acronym;

    FileType(String extension, String acronym) {
        this.extension = extension;
        this.acronym = acronym;
    }

    public String getExtension() {
        return this.extension;
    }

    public String getAcronym() {
        return this.acronym;
    }
}
