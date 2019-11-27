# CreativeAds Plugin

## Install Plugin
```
cordova plugin add ../creativeads-admob/ --variable APPLICATION_ID="ca-app-pub-3940256099942544~3347511713"
```

Where appId is the AdMob app id.

## Configuring AdMob
```
CreativeAds.AdMob.configure({
    android: {
        appId: "ca-app-pub-3940256099942544~3347511713",
        banner: "ca-app-pub-3940256099942544/6300978111",
        interstitial: "ca-app-pub-3940256099942544/1033173712",
        personalizedAdsConsent: false,
        testDeviceId: "A8CA27EE6F83C9D384A8523CDE61C70C",
        isTest: true,
        gender: "MALE",
        uAgeConsent: "FALSE"
    }
});
```

appId: the appId for AdMob
banner: the ad unit id for the banner
interstitial: the ad unit id for the interstitial
personalizedAdsConsent(optional): GDPR consent bool. (see later)
testDeviceId(optional): test device id for testing with own AdMob units (not the provided test ad units)
isTest(optional): true if we are in test mode. All the AdMob request is sent with the testDeviceId param.
gender(optional): gender value for mediation values: "MALE"/"FEMALE",
uAgeConsent(optional): under age of consent values: "TRUE"/"FALSE"

### Getting the test device id
To test your ad, you can use your device as test device that you can set in the configuration.
To get your test device id, check the log of your app and grep for the text 
```
AdRequest.Builder.addTestDevice("XXXXXXXXXXXXXXXXXXXXXXXXXXXX") 
```
Where 
XXXXXXXXXXXXXXXXXXXXXXXXXXXX
is your device id.

## Creating/releasing Banner
```
var banner = CreativeAds.AdMob.createBanner();
```

or

```
var banner = CreativeAds.AdMob.createBanner("ca-app-pub-3940256099942544/6300978111");
```

To destroy the banner object after use

```
CreativeAds.AdMob.releaseBanner(banner);
```

## Banners
### Listening to banner events

```
banner.on("load", function(){
   console.log("Banner loaded " + banner.width, banner.height);
});

banner.on("fail", function(){
   console.log("Banner failed to load");
});

banner.on("show", function(){
   console.log("Banner shown a modal content");
});

banner.on("dismiss", function(){
   console.log("Banner dismissed the modal content");
});

banner.on("click", function(){
   console.log("Banner clicked");
});
```

### Life Cycle Events
First thing you have to do after creating a banner is to listen to its life cycle events:

- load: A banner has been loaded and is ready to be shown.
- fail: The banner load has failed so there is no ad available to be shown at the moment.
- show: The banner has been showed probably as consequence of calling the “show” method.
- dismiss: The banner has been collapsed.
- click: The user has clicked on the banner.

### Showing/hiding a banner

```
banner.show();
banner.hide();
```

### Setting banner layout

```
banner.setLayout(<<layout>>);
```
where <<layout>> string can be
    "TOP_CENTER",
    "BOTTOM_CENTER"

### Setting banner position

```
banner.setPosition(<<position>>);
```
where <<position>> string can be
    "SMART",
    "BANNER",
    "MEDIUM_RECT",
    "LEADERBOARD"

## Interstitials
### Creating/releasing an interstitial

```
var interstitial = CreativeAds.AdMob.createInterstitial();
```

or 

```
var interstitial = CreativeAds.AdMob.createInterstitial("ca-app-pub-3940256099942544/1033173712");
```

To destroy the interstitial object after use

```
CreativeAds.AdMob.releaseInterstitial(interstitial);
```

### Listening to interstitial events

```
interstitial.on("load", function(){
    console.log("Interstitial loaded");
});

interstitial.on("fail", function(error){
    console.log("Interstitial failed: " + JSON.stringify(error));
});

interstitial.on("show", function(){
    console.log("Interstitial shown");
});

interstitial.on("dismiss", function(){
    console.log("Interstitial dismissed");
});

interstitial.on("click", function(){
   console.log("Interstitial clicked");
});
```

### Life Cycle Events
First thing you have to do after creating an interstitial is to listen to its life cycle  events:

- load: An interstitial has been loaded and is ready to be shown.
- fail: The interstitial load has failed so there is no ad available to be shown at the moment.
- show: The interstitial has been showed probably as consequence of calling the “show” method.
- dismiss: The interstitial has been closed by the user.
- click: The user has clicked on the interstitial.

### Showing an interstitial

```
interstitial.show()
```


## User Consent
Because of EU General Data Protection Regulation, personalized content can be provided only by explicit consent of the user.
It is the developer's responsibility to ask the consent eligible for the GDPR requirements.
Consent for personalized ads can be set:
```
CreativeAds.setConsent(false);
```

or using the 'personalizedAdsConsent' field in the configure function:

```
CreativeAds.AdMob.configure({
    android: {
        appId: "ca-app-pub-3940256099942544~3347511713",
        banner: "ca-app-pub-3940256099942544/6300978111",
        interstitial: "ca-app-pub-3940256099942544/1033173712",
        personalizedAdsConsent: false,
    }
});
```

personalizedAdsConsent
- true - user consent granted.
