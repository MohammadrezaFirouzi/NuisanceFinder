package com.firouzi.mozahem;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;

public class SaveContactUtil {

    public static final int REQUEST_CODE_SAVE_CONTACT = 123;

    public static void saveContact(Activity activity, String name, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber);
        activity.startActivityForResult(intent, REQUEST_CODE_SAVE_CONTACT);
    }
}
