package com.ta.sdsofa.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.View;
import android.widget.TextView;

import com.ta.sdsofa.R;

public class UtilMessage {
    private Activity activity;
    private Dialog progressDialog;

    public UtilMessage(Activity activity){this.activity = activity;}

    public void showProgressBar(String text){
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);

        View view = activity.getLayoutInflater().inflate(R.layout.progressbar, null);
        TextView progressText = view.findViewById(R.id.tv_progress_text);

        if (text != null && !text.isEmpty()) {
            progressText.setText(text);
        }
        builder.setView(view);
        builder.setCancelable(false);

        progressDialog = builder.create();
        progressDialog.show();
    }
    public void dismissProgressBar() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
