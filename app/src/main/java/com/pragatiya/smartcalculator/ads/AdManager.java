package com.pragatiya.smartcalculator.ads;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.Locale;

import com.pragatiya.smartcalculator.R;

public class AdManager {


    private Activity activity;
    private InterstitialAd interstitialAd;
    private Dialog loadingDialog;


    public AdManager(Activity activity) {
        this.activity = activity;
        MobileAds.initialize(activity);
    }

    public void loadBannerAd(LinearLayout adContainer) {
        MobileAds.initialize(activity, initializationStatus -> {
        });

        AdView adView = new AdView(activity);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(AdConfig.BANNER_ID);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adContainer.removeAllViews();
        adContainer.addView(adView);
    }

    public void loadInterstitialAd(OnInterstitialDismiss onInterstitialDismiss) {
        Log.d("dfsdfsdewer", "  2222");
        showLoadingDialog();

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(activity, AdConfig.INTERSTITIAL_ID, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitial) {
                        hideLoadingDialog();
                        interstitialAd = interstitial;
                        interstitialAd.show(activity);
                        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdClicked() {
                                Log.d("dfsdfsdewer", "Ad was clicked.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                onInterstitialDismiss.onDismiss();
                                Log.d("dfsdfsdewer", "Ad dismissed fullscreen content.");
                                interstitialAd = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                onInterstitialDismiss.onDismiss();
                                Log.e("dfsdfsdewer", "Ad failed to show fullscreen content.");
                                interstitialAd = null;
                            }

                            @Override
                            public void onAdImpression() {
                                Log.d("dfsdfsdewer", "Ad recorded an impression.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                Log.d("dfsdfsdewer", "Ad showed fullscreen content.");
                            }
                        });

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        hideLoadingDialog();
                        onInterstitialDismiss.onDismiss();
                        Log.d("dfsdfsdewer", loadAdError.toString());
                        interstitialAd = null;
                    }
                });

    }

    public void loadInterstitialAd() {
        Log.d("dfsdfsdewer", "  1111");
        showLoadingDialog();

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(activity, AdConfig.INTERSTITIAL_ID, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitial) {
                        hideLoadingDialog();
                        interstitialAd = interstitial;
                        interstitialAd.show(activity);
                        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdClicked() {
                                Log.d("dfsdfsdewer", "Ad was clicked.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Log.d("dfsdfsdewer", "Ad dismissed fullscreen content.");
                                interstitialAd = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                Log.e("dfsdfsdewer", "Ad failed to show fullscreen content.");
                                interstitialAd = null;
                            }

                            @Override
                            public void onAdImpression() {
                                Log.d("dfsdfsdewer", "Ad recorded an impression.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                Log.d("dfsdfsdewer", "Ad showed fullscreen content.");
                            }
                        });

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        hideLoadingDialog();
                        Log.d("dfsdfsdewer", loadAdError.toString());
                        interstitialAd = null;
                    }
                });

    }

    public void loadNativeAd(FrameLayout frameLayout, AdLayoutType layoutType) {

        AdLoader.Builder builder = new AdLoader.Builder(activity, AdConfig.NATIVE_ID);

        // OnLoadedListener implementation.
        builder.forNativeAd(nativeAd -> {

            int layoutResId;
            switch (layoutType) {
                case SMALL:
                    layoutResId = R.layout.admob_native_small;
                    break;
                // Replace with your actual small layout resource ID
                case MEDIUM:
                    layoutResId = R.layout.ad_unified;
                    break;
                // Replace with your actual medium layout resource ID
                case BIG:
                    layoutResId = R.layout.admob_native_big;
                    break;  // Replace with your actual big layout resource ID

                default:
                    throw new IllegalArgumentException();
            }

            LinearLayout adViewContainer = (LinearLayout) activity.getLayoutInflater().inflate(layoutResId, frameLayout, false);
            frameLayout.setVisibility(View.VISIBLE);
            NativeAdView adView = adViewContainer.findViewById(R.id.uadview);
            populateNativeAdView(nativeAd, adView, layoutType);
            frameLayout.removeAllViews();
            frameLayout.addView(adViewContainer);
        });

        VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        frameLayout.setVisibility(View.GONE);
                        String error = String.format(
                                Locale.getDefault(),
                                "domain: %s, code: %d, message: %s",
                                loadAdError.getDomain(),
                                loadAdError.getCode(),
                                loadAdError.getMessage());
                        Log.d("dfadfadf", "onAdFailedToLoad: Failed to load native ad with error " + error);
                    }


                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public void populateNativeAdView(NativeAd nativeAd, NativeAdView adView, AdLayoutType layoutType) {
        // Set the media view.
        if (layoutType != AdLayoutType.SMALL) {
            adView.setMediaView(adView.findViewById(R.id.ad_media));
        }

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every NativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        if (layoutType != AdLayoutType.SMALL) {
            adView.getMediaView().setMediaContent(nativeAd.getMediaContent());
        }

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getMediaContent().getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (nativeAd.getMediaContent() != null && nativeAd.getMediaContent().hasVideoContent()) {

            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    super.onVideoEnd();
                }
            });
        } else {
        }
    }

    public void loadRewardedAd(final RewardAdCallback callback) {
        showLoadingDialog();
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(activity, AdConfig.REWARD_ID, adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {

                hideLoadingDialog();

                rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        Log.e("RewardedAd", "Ad failed to show: " );
                        // The ad was dismissed, reload a new one
//                        callback.onUserEarnedReward(null);
                        if (callback != null) {
                            callback.onUserEarnedReward(null);
                        }
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
//                        callback.onUserEarnedReward(null);
                        Log.e("RewardedAd", "Ad failed to show: " + adError.getMessage());
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // The ad is being shown
                    }
                });
                rewardedAd.show(activity, rewardItem -> {
                    // Handle the reward

                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError adError) {

                hideLoadingDialog();
                callback.onAdLoadFailed(adError);
            }
        });
    }

    private void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new Dialog(activity);
            loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            loadingDialog.setCancelable(false);  // Prevent dialog from being dismissed
            loadingDialog.setContentView(R.layout.custom_loading_dialog); // Set custom layout
//            loadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent); // Make dialog background transparent
        }

        loadingDialog.show(); // Show the dialog
    }

    private void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
