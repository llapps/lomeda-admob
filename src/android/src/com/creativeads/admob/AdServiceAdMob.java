package com.creativeads.admob;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.creativeads.AdBanner;
import com.creativeads.AdInterstitial;
import com.creativeads.AdRewardedVideo;
import com.creativeads.AdService;
// import com.unity3d.ads.metadata.MetaData;

import org.json.JSONObject;



public class AdServiceAdMob implements AdService {
    private static final String TAG = "AdServiceAdMob";
    private boolean _initialized = false;
    private boolean _personalizedAdsConsent;
    private String _bannerAdUnit;
    private String _interstitialAdUnit;
    private String _rewardedVideoAdUnit;
    private String _testDeviceId;
    private boolean _isTest;
    private Activity _activity;

    private int _gender;
    private int _uAgeConsent;

    public void configure(Activity activity, JSONObject obj) {
        String appId = obj.optString("appId");
        _testDeviceId = obj.optString("testDeviceId");
        String isTestStr = obj.optString("isTest");
        Log.d(TAG, "isTestStr: " + isTestStr);
        _isTest = obj.optBoolean("isTest", false);



        Log.d(TAG, "obj: " + obj.toString());



        String banner = obj.optString("banner");
        String interstitial = obj.optString("interstitial");
        String rewardedVideo = obj.optString("rewardedVideo");
        boolean personalizedAdsConsent = obj.optBoolean("personalizedAdsConsent");
        String gender = obj.optString("gender");
        String uAgeConsent = obj.optString("uAgeConsent");
        if (appId == null) {
            throw new RuntimeException("Empty App AdUnit");
        }

        Log.d(TAG, "Initializing with appId: " + appId);

        if (!_initialized) {
            MobileAds.initialize(activity, appId);
          //  AudienceNetworkInitializeHelper.initialize(activity);
            _initialized = true;
        }
        _activity = activity;
        _bannerAdUnit = banner;
        _interstitialAdUnit = interstitial;
        _rewardedVideoAdUnit = rewardedVideo;
        _personalizedAdsConsent = personalizedAdsConsent;
        _gender = getAdGender(gender);
        _uAgeConsent = getUAgeConsent(uAgeConsent);
    }

    /**
     *
     * @param gender Gender in string (MALE/FEMALE)
     * @return
     */
    private int getAdGender(String gender) {
        if("MALE".equals(gender.toUpperCase())) {
            return AdRequest.GENDER_MALE;
        } else if("FEMALE".equals(gender.toUpperCase())) {
            return AdRequest.GENDER_FEMALE;
        } else {
            Log.d(TAG, "no gender...");
            return AdRequest.GENDER_UNKNOWN;
        }
    }

    /**
     *
     * @param uAgeConsent under age of consent (TRUE/FALSE)
     * @return
     */
    private int getUAgeConsent(String uAgeConsent) {
        if("TRUE".equals(uAgeConsent.toUpperCase())) {
            return AdRequest.TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE;
        } else if("FALSE".equals(uAgeConsent.toUpperCase())) {
            return AdRequest.TAG_FOR_UNDER_AGE_OF_CONSENT_FALSE;
        } else {
            Log.d(TAG, "no under age of consent...");
            return AdRequest.TAG_FOR_UNDER_AGE_OF_CONSENT_UNSPECIFIED;
        }
    }


    /**
     * Sets the consent of the user. This consent is used for each type of ads
     * @param consentGiven The consent
     */
    public void setConsent(boolean consentGiven) {
        Log.d(TAG, "setConsent: " + consentGiven);
        _personalizedAdsConsent = consentGiven;
        // MetaData gdprMetaData = new MetaData(_activity); 
        // gdprMetaData.set("gdpr.consent", true); 
        // gdprMetaData.commit();
    }

    public AdBanner createBanner(Context ctx) {
        return createBanner(ctx, null, AdBanner.BannerSize.SMART_SIZE);
    }

    public AdBanner createBanner(Context ctx, String adUnit, AdBanner.BannerSize size) {
        if (adUnit == null || adUnit.length() == 0) {
            adUnit = _bannerAdUnit;
        }
        if (adUnit == null || adUnit.length() == 0) {
            throw new RuntimeException("Empty AdUnit");
        }
        return new AdBannerAdMob(ctx, adUnit, size, _personalizedAdsConsent, _testDeviceId, _isTest, _gender, _uAgeConsent);
    }

    public AdInterstitial createInterstitial(Context ctx) {
        return createInterstitial(ctx, null);
    }

    public AdInterstitial createInterstitial(Context ctx, String adUnit) {

        if (adUnit == null || adUnit.length() == 0) {
            adUnit = _interstitialAdUnit;
        }
        if (adUnit == null || adUnit.length() == 0) {
            throw new RuntimeException("Empty AdUnit");
        }
        return new AdInterstitialAdMob(ctx, adUnit, _personalizedAdsConsent, _testDeviceId, _isTest, _gender, _uAgeConsent);
    }

    public AdRewardedVideo createRewardedVideo(Context ctx) {
        return createRewardedVideo(ctx, null);
    }

    public AdRewardedVideo createRewardedVideo(Context ctx, String adUnit) {
        if (adUnit == null || adUnit.length() == 0) {
            adUnit = _rewardedVideoAdUnit;
        }
        if (adUnit == null || adUnit.length() == 0) {
            throw new RuntimeException("Empty AdUnit");
        }
        Log.d(TAG, "creating AdRewardedAdMob, _personalizedAdsConsent: " + _personalizedAdsConsent);
        return new AdRewardedAdMob(ctx, adUnit, _personalizedAdsConsent, _testDeviceId, _isTest);
    }
}
