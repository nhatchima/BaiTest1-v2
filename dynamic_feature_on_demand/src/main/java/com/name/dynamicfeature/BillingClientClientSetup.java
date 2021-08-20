package com.name.dynamicfeature;

import android.content.Context;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.PurchasesUpdatedListener;

public class BillingClientClientSetup {
    String TAG = BillingClientClientSetup.this.getClass().getSimpleName();

    private static BillingClient instance;

    public static BillingClient getInstance(Context context, PurchasesUpdatedListener listener) {
        return instance == null ? setUpBillingClient(context, listener) : instance;
    }

    private static BillingClient setUpBillingClient(Context context, PurchasesUpdatedListener listener) {
        BillingClient billingClient = BillingClient.newBuilder(context)
                .setListener(listener)
                .enablePendingPurchases()
                .build();

        return billingClient;
    }

}
