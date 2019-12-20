package com.creativeads.admob;

import android.os.Bundle;
import android.util.Log;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;


public class AdMobUtils {
    private static final String TAG = "AdMobUtils";

    public static AdRequest getAdRequest(boolean adsConsent, boolean isTest, String testDeviceId, int gender, int underAgeOfConsent) {
        AdRequest.Builder req = new AdRequest.Builder();
        if (!adsConsent) {
            Bundle extras = new Bundle();
            extras.putString("npa", "1");
            req = req.addNetworkExtrasBundle(AdMobAdapter.class, extras);
        }

        if (isTest && testDeviceId != null) {
            Log.d(TAG, "getAdRequest TESTING...testDeviceId: " + testDeviceId);
            req = req.addTestDevice(testDeviceId);
        } else {
            Log.d(TAG, "getAdRequest NOT TESTING...isTest: " + isTest + ", testDeviceId: " + testDeviceId);
        }

        switch(gender) {
            case AdRequest.GENDER_UNKNOWN:
                Log.d(TAG, "AdRequest.GENDER_UNKNOWN...");
                break;
            
            case AdRequest.GENDER_FEMALE:
            case AdRequest.GENDER_MALE:
                Log.d(TAG, "OLAdRequest.GENDER_MALE...");
                //req = req.setGender(gender);
                req.setGender(gender);
                break;
            default:
                break;
        }


        switch(underAgeOfConsent) {
            case AdRequest.TAG_FOR_UNDER_AGE_OF_CONSENT_UNSPECIFIED:
                Log.d(TAG, "AdRequest.TAG_FOR_UNDER_AGE_OF_CONSENT_UNSPECIFIED...");
                
                break;
            
            case AdRequest.TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE:
            case AdRequest.TAG_FOR_UNDER_AGE_OF_CONSENT_FALSE:
                Log.d(TAG, "OLAdRequest.TAG_FOR_UNDER_AGE_OF_CONSENT_FALSE...");
               // req = req.setTagForUnderAgeOfConsent(underAgeOfConsent);
                req.setTagForUnderAgeOfConsent(underAgeOfConsent);
                break;
            default:
                break;
        }

        return req.build();
    }

    public static AdRequest getAdRequest(boolean adsConsent, boolean isTest, String testDeviceId) {
        Log.d(TAG, "false call to getAdRequest...");
        return getAdRequest(adsConsent, isTest, testDeviceId, AdRequest.GENDER_UNKNOWN, AdRequest.TAG_FOR_UNDER_AGE_OF_CONSENT_UNSPECIFIED);
    }

}
