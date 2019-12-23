package com.example.check_in_out;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogBox extends AppCompatDialogFragment {
    EditText reason;
    boolean flageed;
    String flaggedReason;
    ListenerDialogBox listenerDialogBox;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_box, null);
        reason = view.findViewById(R.id.flag_reason);

        builder.setView(view)
                .setTitle("Reason")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        flageed = false;
                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        flageed = true;

                        flaggedReason = reason.getText().toString();
                        if(flaggedReason.trim().isEmpty()){
                            //Toast.makeText(view.getContext(), "Please do provide a reason", Toast.LENGTH_SHORT).show();
                            flageed = false;
                            return;
                        }
                        listenerDialogBox.passText(flageed, flaggedReason);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listenerDialogBox = (ListenerDialogBox) context;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public interface ListenerDialogBox{
        void passText(boolean flag, String rFlag);
    }
}
