package com.firouzi.mozahem;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class ShowinfoDialog extends DialogFragment {
    private MyDialogEventListener eventListener;
    private String infoText; // متغیری برای نگهداری متن اطلاعات
    private String number;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        eventListener = (MyDialogEventListener) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setWindowAnimations(R.style.DialogAnimation);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setWindowAnimations(0);
        }
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_info, null, false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }


        TextView infoTextView = view.findViewById(R.id.textview);
        TextView numberTextView = view.findViewById(R.id.textview_number);
        infoTextView.setText(infoText);
        numberTextView.setText(number);

        builder.setView(view);

        Button okBtn = view.findViewById(R.id.btn_save);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                SaveContactUtil.saveContact(getActivity(), infoText, number);
            }
        });

        ImageView imageView = view.findViewById(R.id.exit);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ImageView call = view.findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);
            }
        });

        return builder.create();
    }

    public void showInfo(FragmentManager manager, String infoText ,String number) {
        this.infoText = infoText;
        this.number = number;
        show(manager, "info_dialog");
    }

    public interface MyDialogEventListener {
        void onCancelButtonClicked();
    }
}