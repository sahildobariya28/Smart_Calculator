package com.pragatiya.smartcalculator.ads;

import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardItem;

public interface RewardAdCallback {
    void onUserEarnedReward(RewardItem reward);
    void onAdLoadFailed(LoadAdError adError);
}