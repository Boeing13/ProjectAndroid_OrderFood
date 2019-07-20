package com.example.project_orderfood.Common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.project_orderfood.R;
import com.example.project_orderfood.User_OrderCart;

public class AddressDialog extends AppCompatDialogFragment {
    EditText txtAdress;
    public  User_OrderCart act;
    DialogListener dialogListener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.address, null);
        txtAdress=view.findViewById(R.id.txtAddress);

        builder.setView(view)
                .setTitle("One More Steps !")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialogListener.sendInfor(txtAdress.getText().toString());
                    }
                });

          return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dialogListener=(DialogListener) context;

    }

    public interface DialogListener{
        void sendInfor(String text);
     }

}
