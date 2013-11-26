package com.spacescout.spacescout_android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 *
 * Created by ajay alfred on 11/5/13.
 */
public class CustomDialogFragment extends DialogFragment {

    private View titleView;
    public String[] arrToDisplay;
    public boolean[] arrSelectBool;
    public String dialogType;
    public String dialogSelect;
    public int singleSelect;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();

        //intialize custom dialog title view
        titleView = inflater.inflate(R.layout.dialog_custom_title, null);
        TextView title = (TextView) titleView.findViewById(R.id.dialogTitle);
        title.setText(getArguments().getString("dialogTitle"));

        //get array and bool to display
        arrToDisplay = getArguments().getStringArray("arrayToDisplay");

        //get type of dialog
        dialogType = getArguments().getString("dialogType");

        if (dialogType.equalsIgnoreCase("SpaceType")) {
            arrSelectBool = getArguments().getBooleanArray("arrSpaceTypeBool");
        } else if (dialogType.equalsIgnoreCase("SpaceLoc")) {
            singleSelect = getArguments().getInt("singleSelect");
        } else if (dialogType.equalsIgnoreCase("SpaceNoise")) {
            arrSelectBool = getArguments().getBooleanArray("arrSpaceNoiseBool");
        }

        //get dialog select type
        dialogSelect = getArguments().getString("dialogSelect");


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getActivity(), R.style.popupStyle);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setCustomTitle(titleView);

        if (dialogSelect.equalsIgnoreCase("multi")) {
            alertDialogBuilder.setMultiChoiceItems(arrToDisplay, arrSelectBool, new DialogInterface.OnMultiChoiceClickListener() {
                public void onClick(DialogInterface dialogInterface, int item, boolean isChecked) {
                    if (isChecked) {
                        Log.i("INFO", "checked item -> " + item);
                    }
                    if (!isChecked) {
                        Log.i("INFO", "unchecked -> " + item);
                    }


                }
            })
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // if this button is clicked, close
                    // current activity
                    dialog.dismiss();
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // if this button is clicked, just close
                    // the dialog box and do nothing
                    dialog.cancel();
                }
            });
        }
        else
        {
            alertDialogBuilder.setSingleChoiceItems(arrToDisplay, singleSelect, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    Log.i("INFO", "selected -> " + item);
                    dialog.dismiss();
                }
            });
        }


        // create alert dialog
        AlertDialog dialog = alertDialogBuilder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = ((AlertDialog) dialog)
                        .getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setBackgroundResource(R.drawable.custom_filter_action_button);
                positiveButton.setTextColor(getResources().getColor(R.color.white));

                Button negativeButton = ((AlertDialog) dialog)
                        .getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setBackgroundResource(R.drawable.custom_filter_action_button);
                negativeButton.setTextColor(getResources().getColor(R.color.white));
            }
        });

        return dialog;
    }

    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }
}
