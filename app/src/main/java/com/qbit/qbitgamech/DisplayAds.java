package com.qbit.qbitgamech;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.qbit.qbitgamech.homeui.home.Home;

public class DisplayAds {

    private AdLoader adLoader;
    InterstitialAd interstitialDisplayAd;

    RewardedAd rewardedDisplayAd;

    private final String bannerAdId = "ca-app-pub-3940256099942544/2247696110";
    private final String interstitalAdId = "ca-app-pub-3940256099942544/1033173712";
    private final String rewardedAdId = "ca-app-pub-3940256099942544/5224354917";

    public void loadBannerAds(Context context, Activity activity, int viewId, boolean state){



        adLoader = new AdLoader.Builder(context, bannerAdId)
                .forNativeAd(nativeAd -> {
                    if (state) {
                        nativeAd.destroy();
                    } else {
                        // Show the ad.
                        if (adLoader.isLoading()) {
                            // The AdLoader is still loading ads.
                            // Expect more adLoaded or onAdFailedToLoad callbacks.
                            Log.d("Ads", "Native Ad are getting loaded.");
                        } else {
                            // The AdLoader has finished loading ads.
                            Log.d("Ads", "Native Ad have been loaded.");
                        }


                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(new ColorDrawable(Color.parseColor("#1C2951"))).build();
                        TemplateView templateView = activity.findViewById(viewId);
                        if (templateView != null) {
                            templateView.setStyles(styles);
                            templateView.setNativeAd(nativeAd);
                        }

                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                        // Handle the failure by logging, altering the UI, and so on.
                        Log.e("Ads",adError.toString());
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();

        adLoader.loadAds(new AdRequest.Builder().build(), 5);

    }

    public void loadInterstitalAds(Context context, Activity activity){
        InterstitialAd.load(context, interstitalAdId, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d("Ads","Interstitial Ad failed to load.");
                interstitialDisplayAd=null;
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                Log.d("Ads","Interstitial Ad have been loaded.");

                interstitialDisplayAd=interstitialAd;
                interstitialDisplayAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        interstitialDisplayAd=null;
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                    }
                });
            }
        });

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if(interstitialDisplayAd!=null){
                interstitialDisplayAd.show(activity);
            } else {
                Log.e("Ads","Interstitial Ad failed to load.");
            }

        }, 10000);
    }

    public void loadRewardedAds(Context context, Activity activity){
        RewardedAd.load(context, rewardedAdId, new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d("Ads","Rewarded Ad failed to load.");
                rewardedDisplayAd=null;
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                super.onAdLoaded(rewardedAd);
                Log.d("Ads","Rewarded Ad have been loaded.");
                rewardedDisplayAd=rewardedAd;
                rewardedDisplayAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                    }
                });
            }
        });

        if(rewardedDisplayAd!=null){
            rewardedDisplayAd.show(activity, rewardItem -> {
                Log.d("Ads", "Rewarded Item amount: "+rewardItem.getAmount()+ "Reward Type:"+rewardItem.getType());
                if (activity instanceof Home){
                    Log.d("Ad Reward","Enable Prediction");
                    activity.findViewById(R.id.unlockPredictionCard).setVisibility(View.GONE);
                    activity.findViewById(R.id.no_live_event).setVisibility(View.GONE);
                    activity.findViewById(R.id.predictionLayout).setVisibility(View.VISIBLE);
                }

            });
        }else {
            Toast.makeText(context, "Rewarded Ads failed to load. Please try again later.", Toast.LENGTH_SHORT).show();
        }

    }

}
