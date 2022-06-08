package com.troy.sportsbetting;

import static com.google.firebase.messaging.Constants.TAG;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.PurchaseInfo;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.troy.sportsbetting.PurchaseActivity.*;

public class MainActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler{


    private InterstitialAd interstitialAd;
    private static final String AD_UNIT_ID = "ca-app-pub-3727697994870495/3926105867";
    private final static String GPLAY_LICENSE ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxLz9LnItV5iDVdEPEzxRzFBh4vNnWivmDYFXn6pvd1tf+WksUPBpbPuOAh2/Ih16BbMTTjux4xJZknLnk11pmShYaWuNCnhgabd/hIhvSELv/WpRyKNcBxMtjYDtgs6BNNKV1D2Qf0YfVAhNGSs4VrXxxZOHRaQ0e8wAt0tOm+pnfOwfWrT6wQeddZZQH9DgfT/6tIkFgOLdBbAaAbkaTzfe6gvslZ2Tm6gDPTC/L0t/Ramy0v6SmynKoarG7onCEkFlbCLxVlwugp6meM+c2f4rKKy6zM6XxV/Ymw3kooTsFXxsPIdNG0Bvx/1Y8+AeVNWl7m1BMntYYTvFJbHMNwIDAQAB";
    BillingProcessor bp;
    Button vipButton;
    boolean isPurchaded;
    private View mProgress;
    private boolean initialize;   // храним готовность к покупкам
    private boolean vipStatus;    // храним текущий статус отображения рекламы


    private PreferencesManager prefManager; // класс, который работает с SharedPreferences. Я для себя решил вынести всю логику отдельно
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vipButton = findViewById(R.id.button3);

        bp = new BillingProcessor(this,
                InAppBillingResources.getRsaKey(), InAppBillingResources.getMerchantId(), this);

        prefManager = new PreferencesManager(this); // класс, который работает с `SharedPreferences`
        vipStatus = prefManager.getVipStatus();        // получаем из `SharedPreferences` сохраненное состояние рекламы (ВКЛ / ВЫКЛ)
        resources = this.getResources();            // получаем "доступ" к ресурсам

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        loadAd();

        vipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vipStatus == true) {
                    startActivity(new Intent(MainActivity.this, VipActivity.class));
                }

                if (!vipStatus) {
                    startActivity(new Intent(MainActivity.this, PurchaseActivity.class));
                }
            }
        });
    }



    public void BtnBetClick(View view) {
        showInterstitial();
        startActivity(new Intent(MainActivity.this, BetActivity.class));

    }

    public void BtnStatsClick(View view) {
        startActivity(new Intent(MainActivity.this, StatsActivity.class));
    }

    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                this,
                AD_UNIT_ID,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        MainActivity.this.interstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        MainActivity.this.interstitialAd = null;
                                        Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        MainActivity.this.interstitialAd = null;
                                        Log.d("TAG", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        interstitialAd = null;


                    }
                });

}
    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitialAd != null) {
            interstitialAd.show(this);
        } else {


        }
    }
    private void setupSubscription(boolean isPurchased) {

        if (vipStatus) {
            vipButton.setText("asdasf");
        } else {

        }
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable PurchaseInfo details) {
        // Called when requested PRODUCT ID was successfully purchased
        // Вызывается, когда запрашиваемый PRODUCT ID был успешно куплен
        Toast.makeText(MainActivity.this, "Already purchased", Toast.LENGTH_SHORT).show();
        setupSubscription(true);
        prefManager.setVipStatus(true); // 1. записываем в `SharedPreferences` состояние vip


        if (bp.isPurchased(productId)) {
            prefManager.setVipStatus(true); // 1. записываем в `SharedPreferences` состояние vip

        } else {
            prefManager.setVipStatus(false);

        }
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }



}