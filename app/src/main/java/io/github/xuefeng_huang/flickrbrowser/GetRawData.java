package io.github.xuefeng_huang.flickrbrowser;

/**
 * Created by xuefeng on 4/5/2016.
 */
enum DownloadStatus {IDLE, PROCESSING, NOT_INITIALISED, FAILED_OR_EMPTY, OK}

public class GetRawData {
    private String LOG_TAG = GetRawData.class.getSimpleName();
    private String mUrl;
    private String mData;
    private DownloadStatus mDownloadStatus;


    public GetRawData(String mUrl) {
        this.mUrl = mUrl;
    }
}
