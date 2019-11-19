package com.creativeads;

import android.util.Log;


public abstract class AbstractAdRewardedVideo implements AdRewardedVideo {
    private static final String TAG = "AbstractAdRewardedVideo";


    private RewardedVideoListener _listener;

    public void setListener(RewardedVideoListener listener) {
        _listener = listener;
    }

    public void notifyOnLoaded() {
        if (_listener != null) {
            _listener.onLoaded(this);
        }
    }

    public void notifyOnFailed(Error error) {
        if (_listener != null) {
            _listener.onFailed(this, error);
        }
    }

    public void notifyOnClicked() {
        if (_listener != null) {
            _listener.onClicked(this);
        }
    }

    public void notifyOnShown() {
        if (_listener != null) {
            _listener.onShown(this);
        }
    }

    public void notifyOnDismissed() {
        Log.d(TAG, "notifyOnDismissed...");
        if (_listener != null) {
            _listener.onDismissed(this);
        }
    }

    public void notifyOnRewardCompleted(Reward reward, Error error) {
        if (_listener != null) {
            _listener.onRewardCompleted(this, reward, error);
        }
    }


    // void onPause() {
    //     Log.d(TAG, "onPause...");
    // }

    // void onResume() {
    //     Log.d(TAG, "onResume...");
    // }

}
