package com.bookingsystemfortutoring;

import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private MyProgressDialog myProgressDialog;

    public void showProgressDialog() {
        myProgressDialog = new MyProgressDialog(BaseActivity.this);
        if (!myProgressDialog.isShowing()) {
            myProgressDialog.setColor(getResources().getColor(R.color.colorAccent));
            myProgressDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (myProgressDialog != null && myProgressDialog.isShowing()) {
            myProgressDialog.dismiss();
        }
    }

}
