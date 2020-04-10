
package com.galaxy.instadownloader.util;

import android.text.TextUtils;

import com.galaxy.instadownloader.domain.model.InstagramImage;
import com.galaxy.instadownloader.domain.model.InstagramMedia;
import com.galaxy.instadownloader.domain.model.InstagramVideo;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.logging.Logger;

/**
 * @author gdong
 */
public class InstagramMediaFactory {

    private static final Logger logger = Logger.getLogger(InstagramMediaFactory.class.getName());

    private static final String INSTAGRAM_TYPE_PHOTO = "instapp:photo";

    private static final String INSTAGRAM_TYPE_VIDEO = "video";

    /**
     * Creates appropriate InstagramMedia instance according to the given document
     *
     * @param document the input document
     * @return the parsed InstagramMedia instance, or <code>null</code> if nothing
     * can be parsed
     */
    public static InstagramMedia createInstagramMedia(Document document) {
        InstagramMedia instagramMedia = new InstagramMedia();

        String type = parseMetaProperty("og:type", document);
        if (type == null) {
            return null;
        }
        if (INSTAGRAM_TYPE_PHOTO.equals(type)) {
            instagramMedia.setData(parseImage(document));
            return instagramMedia;
        } else if (INSTAGRAM_TYPE_VIDEO.equals(type)) {
            instagramMedia.setData(parseVideo(document));

            return instagramMedia;
        }
        logger.warning("Unknown type: " + type);
        return null;
    }

    private static InstagramVideo parseVideo(Document document) {
        InstagramVideo video = new InstagramVideo();
        video.setUrl(parseMetaProperty("og:video", document));
        video.setPosterUrl(parseMetaProperty("og:image", document));
        video.setType(parseMetaProperty("og:video:type", document));
        video.setWidth(parseMetaProperty("og:video:width", document));
        video.setHeight(parseMetaProperty("og:video:height", document));
        video.setSecureUrl(parseMetaProperty("og:video:secure_url", document));
        video.setDescription(parseMetaProperty("og:description", document));
        video.setTitle(parseMetaProperty("og:title", document));
        return video;
    }

    private static InstagramImage parseImage(Document document) {

        InstagramImage image = new InstagramImage();
        image.setUrl(parseMetaProperty("og:image", document));
        image.setDescription(parseMetaProperty("og:description", document));
        image.setTitle(parseMetaProperty("og:title", document));
        image.setLinks(document.outerHtml());
        return image;
    }

    /**
     * gets property value of the given property, parsing from the given document
     *
     * @param property the property attribute value of meta
     * @param document the jsoup document to parse from
     * @return parsed content, if any
     */
    public static String parseMetaProperty(String property, Document document) {

        if (TextUtils.isEmpty(property) || document == null) {
            throw new IllegalArgumentException(
                    "property name and document cannot be null");
        }
        Elements metaElems = document
                .select(String.format("meta[property='%s']", property));
        return metaElems.isEmpty() ? null : metaElems.first().attr("content");
    }
}
