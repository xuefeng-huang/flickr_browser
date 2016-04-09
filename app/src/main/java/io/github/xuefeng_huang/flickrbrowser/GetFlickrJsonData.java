package io.github.xuefeng_huang.flickrbrowser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
        mPhotos = new ArrayList<>();
    }

    public void execute() {
        DownloadJsonData downloadJsonData = new DownloadJsonData();
        downloadJsonData.execute(mDestinationUri.toString());
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

    public List<Photo> getMPhotos() {
        return mPhotos;
    }

    public void processResult() {
        if (getmDownloadStatus() != DownloadStatus.OK) {
            return;
        }

        final String FLICKR_ITEMS = "items";
        final String FLICKR_TITLE = "title";
        final String FLICKR_MEDIA = "media";
        final String FLICKR_PHOTO_URL = "m";
        final String FLICKR_AUTHOR = "author";
        final String FLICKR_AUTHOR_ID = "author_id";
        final String FLICKR_LINK = "link";
        final String FLICKR_TAGS = "tags";

        try {
            JSONObject jsonObject = new JSONObject(getmData());
            JSONArray itemArray = jsonObject.getJSONArray(FLICKR_ITEMS);
            for (int i = 0; i < itemArray.length(); i++) {
                JSONObject jsonPhoto = itemArray.getJSONObject(i);
                String title = jsonPhoto.getString(FLICKR_TITLE);
                String author = jsonPhoto.getString(FLICKR_AUTHOR);
                String author_id = jsonPhoto.getString(FLICKR_AUTHOR_ID);
                String link = jsonPhoto.getString(FLICKR_LINK);
                String tags = jsonPhoto.getString(FLICKR_TAGS);

                JSONObject jsonMedia = jsonPhoto.getJSONObject(FLICKR_MEDIA);
                String photoUrl = jsonMedia.getString(FLICKR_PHOTO_URL);

                Photo photoObject = new Photo(title, author, author_id, link, tags, photoUrl);
                this.mPhotos.add(photoObject);
            }
        }catch (JSONException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "error processing json");
        }
    }

    public class DownloadJsonData extends DownloadRawData {
        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            processResult();
        }

        @Override
        protected String doInBackground(String... params) {
            String[] par = {mDestinationUri.toString()};
            return super.doInBackground(par);
        }
    }
}
