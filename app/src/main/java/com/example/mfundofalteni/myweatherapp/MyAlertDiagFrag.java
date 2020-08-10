package com.example.mfundofalteni.myweatherapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.util.Objects;

public class MyAlertDiagFrag extends DialogFragment {
    public MyAlertDiagFrag(){}

    public static MyAlertDiagFrag newInstance(){
        MyAlertDiagFrag myAlertDiagFrag = new MyAlertDiagFrag();
        return myAlertDiagFrag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        setCancelable(false);
        alertBuilder.setTitle("Unable to connect");
        alertBuilder.setMessage("You're not connected to the Internet. Please check your settings, and try again.");
        alertBuilder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(0);
            }
        });
        alertBuilder.setPositiveButton("Check Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Objects.requireNonNull(getActivity()).startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                System.exit(0);
                //getActivity().finish();
            }
        });

        return alertBuilder.create();
    }
}
