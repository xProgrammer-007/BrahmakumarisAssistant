package com.bkassistant;

import android.app.ProgressDialog;
import android.content.Context;

public class PDialog {
    private Context activity;
    private ProgressDialog pd;

    public PDialog(Context activity) {
        this.activity = activity;

    }
    public void progressDialog(String message) {
        pd = new ProgressDialog(activity);
        pd.setMessage(message);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();
    }

    public void progressDialog(String title, String message) {
        pd.setTitle(title);
        pd.setMessage(message);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();
    }

    public void dismisss() {
        if (pd != null) {
            if (pd.isShowing()) {
                pd.dismiss();
            }
        }
    }

}
