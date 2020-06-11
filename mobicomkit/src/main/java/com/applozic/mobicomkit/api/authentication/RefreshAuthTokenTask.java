package com.applozic.mobicomkit.api.authentication;

import android.content.Context;
import android.os.AsyncTask;

import com.applozic.mobicomkit.api.MobiComKitClientService;
import com.applozic.mobicomkit.api.account.register.RegisterUserClientService;
import com.applozic.mobicomkit.api.account.user.MobiComUserPreference;
import com.applozic.mobicomkit.listners.AlCallback;

import java.lang.ref.WeakReference;

public class RefreshAuthTokenTask extends AsyncTask<Void, Void, Boolean> {

    private final String applicationId;
    private final String userId;
    private final WeakReference<Context> context;
    private final AlCallback callback;

    public RefreshAuthTokenTask(Context context, String applicationId, String userId, AlCallback callback) {
        this.context = new WeakReference<>(context);
        this.applicationId = applicationId;
        this.userId = userId;
        this.callback = callback;
    }

    public RefreshAuthTokenTask(Context context, AlCallback callback) {
        this(context, MobiComKitClientService.getApplicationKey(context), MobiComUserPreference.getInstance(context).getUserId(), callback);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return new RegisterUserClientService(context.get()).refreshAuthToken(userId, applicationId);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (callback != null) {
            if (aBoolean) {
                callback.onSuccess(true);
            } else {
                callback.onError(false);
            }
        }
        super.onPostExecute(aBoolean);
    }
}