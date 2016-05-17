package scrapper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JSOUP generic scrapper implementation.
 * Created by Grinch on 9/21/2014.
 */
public class GenericScrapperJsoupImpl implements GenericScrapper<String> {

    private static final Log LOG = LogFactory.getLog(GenericScrapperJsoupImpl.class);

    @Override
    public List<String> getAllLinks(String url) {
        try {
            final Document page =
                    Jsoup.connect(url).get();
            final List<String> anchors = new ArrayList<>();
            final Elements anchorTagElements =
                    page.getElementsByTag(HtmlTagType.A.getTag());
            final Iterator<Element> elements =
                    anchorTagElements.iterator();
            while (elements.hasNext()) {
                final Element element = elements.next();
                anchors.add(element.attr(HtmlTagType.HREF.getTag()));
            }
            return anchors;

        } catch (IOException e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage(), e);
            }
        }
        return null;
    }

    @Override
    public List<String> getAllLinksByRegex(String url, String regex) {
        try {
            final Pattern pattern =
                    Pattern.compile(regex);
            final Document page =
                    Jsoup.connect(url).get();
            final List<String> anchors = new ArrayList<>();
            final Elements anchorTagElements =
                    page.getElementsByTag(HtmlTagType.A.getTag());
            for (Element element : anchorTagElements) {
                final String aTag = element.attr(HtmlTagType.HREF.getTag());
                final Matcher matcher = pattern.matcher(aTag);
                if (matcher.find()) {
                    anchors.add(aTag);
                }
            }
            return anchors;

        } catch (IOException e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage(), e);
            }
        }
        return null;
    }

    @Override
    public List<String> getAllLinksByCssClass(String url, String cssClassName) {
        try {
            final Document page =
                    Jsoup.connect(url).get();
            final List<String> anchors =
                    new ArrayList<>();
            final Elements anchorTagElements =
                    page.getElementsByClass(cssClassName);
            final Iterator<Element> elements =
                    anchorTagElements.iterator();
            while (elements.hasNext()) {
                final Element element = elements.next();
                if (element != null) {
                    anchors.add(element.attr(HtmlTagType.HREF.getTag()));
                }
            }
            return anchors;

        } catch (IOException e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage(), e);
            }
        }
        return null;
    }

    @Override
    public String getElementsHtmlById(String url, String id) {
        try {
            final Document page =
                    Jsoup.connect(url).timeout(0).execute().parse();
            final Element element =
                    page.getElementById(id);
            if (element != null) {
                return element.html();
            }
            return null;
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage(), e);
            }
        }
        return null;

    }

    @Override
    public String getRawHtml(String url) {
        try {
            final Document page =
                    Jsoup.connect(url).timeout(0).execute().parse();
            return page.html();
        } catch (IOException e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage(), e);
            }
        }
        return null;
    }

    @Override
    public void downloadAllImages(List<String> imageUrls, String outputFolder) {
        if (!CollectionUtils.isEmpty(imageUrls) &&
                !StringUtils.isBlank(outputFolder)) {
            for (String urlString : imageUrls) {
                try {
                    final FileType imageType =
                            this.resolveImageType(urlString);
                    if (imageType != null) {
                        final File fos =
                                new File(outputFolder + urlString.hashCode() + imageType.getExtension());
                        final URL url = new URL(urlString);
                        final BufferedImage image =  ImageIO.read(url);
                        ImageIO.write(image, imageType.getAcronym(), fos);
                    }
                } catch (IOException e) {
                    if (LOG.isErrorEnabled()) {
                        LOG.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

    /**
     * Helper method to find file type by its extension.
     * @param imagePath url of the image path, extension will determine the file type.
     * @return file type by extension, null if not found.
     */
    private FileType resolveImageType(String imagePath) {
        if (!StringUtils.isBlank(imagePath)) {
            for(FileType imageType : FileType.imageTypes) {
                final String extension = imageType.getExtension();
                if (imagePath.contains(extension) ||
                        imagePath.contains(extension.toUpperCase())) {
                    return imageType;
                }
            }
        }
        return null;
    }
}
