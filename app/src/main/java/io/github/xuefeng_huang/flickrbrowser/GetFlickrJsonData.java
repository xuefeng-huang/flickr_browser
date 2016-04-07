package io.github.xuefeng_huang.flickrbrowser;

import android.net.Uri;

import java.util.List;

/**
 * Created by xuefeng on 4/7/2016.
 */
public class GetFlickrJsonData extends GetRawData {
    private final String LOG_TAG = GetFlickrJsonData.class.getSimpleName();
    private List<Photo> mPhotos;
    private Uri mDestinationUri;

    public GetFlickrJsonData(String searchCriteria, boolean matchAll) {
        super(null);
        createAndUpdateUrl(searchCriteria, matchAll);
    }

    private boolean createAndUpdateUrl(String searchCriteria, boolean matchAll) {
        final String BASE_URL = "https://api.flickr.com/services/feeds/photos_public.gne";
        final String TAG_PARAM = "tags";
        final String TAGMODE_PARAM = "tagmode";
        final String FORMAT_PARAM = "format";
        final String NO_JSON_CALL_BACK_PARAM = "nojsoncallback";

        mDestinationUri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(TAG_PARAM,
                searchCriteria).appendQueryParameter(TAGMODE_PARAM, matchAll ? "all" : "any")
                .appendQueryParameter(FORMAT_PARAM, "json")
                .appendQueryParameter(NO_JSON_CALL_BACK_PARAM, "1")
                .build();

        return mDestinationUri != null;
    }

    public class DownloadJsonData extends DownloadRawData {

    }
}
