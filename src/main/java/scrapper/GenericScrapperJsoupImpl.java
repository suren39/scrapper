package scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Full class description here.
 * Created by Grinch on 9/21/2014.
 * scrapper
 */
public class GenericScrapperJsoupImpl implements GenericScrapper<String> {

    @Override
    public List<String> getAllLinks(String url) {
        try {
            final Document page = Jsoup.connect(url).get();
            final List<String> anchors = new ArrayList<String>();
            Elements anchorTagElements = page.getElementsByTag(HtmlTagType.A.getTag());
            Iterator<Element> elements = anchorTagElements.iterator();
            while (elements.hasNext()) {
                Element element = elements.next();
                anchors.add(element.attr(HtmlTagType.HREF.getTag()).toString());
            }
            return anchors;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getAllLinksByRegex(String url, String regex) {
        try {
            final Pattern pattern = Pattern.compile(regex);
            final Document page = Jsoup.connect(url).get();
            final List<String> anchors = new ArrayList<String>();
            Elements anchorTagElements = page.getElementsByTag(HtmlTagType.A.getTag());
            Iterator<Element> elements = anchorTagElements.iterator();
            while (elements.hasNext()) {
                Element element = elements.next();
                final String aTag = element.attr(HtmlTagType.HREF.getTag()).toString();
                final Matcher matcher = pattern.matcher(aTag);
                if (matcher.find()) {
                    anchors.add(aTag);
                }
            }
            return anchors;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getAllLinksByClass(String url, String cssClassName) {
        try {
            final Document page = Jsoup.connect(url).get();
            final List<String> anchors = new ArrayList<String>();
            Elements anchorTagElements = page.getElementsByClass(cssClassName);
            Iterator<Element> elements = anchorTagElements.iterator();
            while (elements.hasNext()) {
                Element element = elements.next();
                anchors.add(element.attr(HtmlTagType.HREF.getTag()).toString());
            }
            return anchors;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getAllImageSources(String url) {
        return null;
    }

    @Override
    public String getElementsHtmlById(String url, String id) {
        try {
            final Document page = Jsoup.connect(url).timeout(0).execute().parse();
                   /* .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com").get();*/
            final Element element = page.getElementById(id);
            if (element != null) {
                return element.html();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
        return null;

    }

    @Override
    public String getRawHtml(String url) {
        try {
            final Document page = Jsoup.connect(url).timeout(0).execute().parse();
            return page.html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void downloadAllImages(List<String> imageUrls, String outputFolder) {

        for (String urlString : imageUrls) {
            try {
                FileType imageType = this.resolveImageType(urlString);
                File fos = new File(outputFolder + urlString.hashCode() + imageType.getExtension());
                URL url = new URL(urlString);
                BufferedImage image =  ImageIO.read(url);
                ImageIO.write(image, imageType.getAcronym(), fos);
            } catch (MalformedURLException mfe) {
                mfe.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private FileType resolveImageType(String imagePath) {
        for(FileType imageType : FileType.imageTypes) {
            final String extension = imageType.getExtension();
            if (imagePath.contains(extension) ||
                    imagePath.contains(extension.toUpperCase())) {
                return imageType;
            }
        }
        return null;
    }

    @Override
    public void downloadAllImages(String url, String outputFolder) {

    }
}
