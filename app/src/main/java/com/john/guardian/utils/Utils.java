package com.john.guardian.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by john on 7/13/2017.
 */

public class Utils
{
    private static Utils instance;
    public static Utils getInstance()
    {
        if(instance == null)
            instance = new Utils();

        return instance;
    }

    private Utils()
    {

    }


    public String reformatDate(String date)
    {
        date = date.replace("T", " ");
        date = date.replace("Z", "");

        return date;
    }

    public static void startActivity(Context context, Class<?> className, Bundle b)
    {
        Intent intent = new Intent(context, className);
        intent.putExtras(b);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class<?> className)
    {
        Intent intent = new Intent(context, className);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void createYesNoDialog(Activity context, String title, String message,
                                  String positiveText, String negativeText, DialogInterface.OnClickListener positiveListener)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveText, positiveListener)
                .setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
