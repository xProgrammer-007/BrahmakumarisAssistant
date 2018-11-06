package com.bkassistant;

import android.app.Application;

public class Globals extends Application {
    private MediaPlayerApi mediaPlayerApi;

    public MediaPlayerApi getMediaPlayerApi() {
        return mediaPlayerApi;
    }

    public void setMediaPlayerApi(MediaPlayerApi mediaPlayerApi) {
        this.mediaPlayerApi = mediaPlayerApi;
    }




}
