package com.example.photocalling;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;


import java.io.File;

public class Ringtonehelper {


    private static int TONE_TYPE;

    public static enum RINGTONE_TYPE {
        RINGTONES, ALRAM, NOTIFICATIONS, CONTACT
    }


    public static void setRingtone(Context mContext, String oPath, RINGTONE_TYPE type) {

        File file = new File(oPath);

        String name = file.getName();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DATA, oPath);
            contentValues.put(MediaStore.MediaColumns.TITLE, name);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
            contentValues.put(MediaStore.MediaColumns.SIZE, oPath.length());
            contentValues.put(MediaStore.Audio.Media.ARTIST, getAppName(mContext));

            if (type == RINGTONE_TYPE.RINGTONES) {

                TONE_TYPE = RingtoneManager.TYPE_RINGTONE;
                contentValues.put(MediaStore.Audio.Media.IS_RINGTONE, true);
                contentValues.put(MediaStore.Audio.Media.IS_ALARM, false);
                contentValues.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
            } else if (type == RINGTONE_TYPE.NOTIFICATIONS) {
                TONE_TYPE = RingtoneManager.TYPE_NOTIFICATION;
                contentValues.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
                contentValues.put(MediaStore.Audio.Media.IS_RINGTONE, false);
                contentValues.put(MediaStore.Audio.Media.IS_ALARM, false);
            } else if (type == RINGTONE_TYPE.ALRAM) {
                TONE_TYPE = RingtoneManager.TYPE_ALARM;
                contentValues.put(MediaStore.Audio.Media.IS_ALARM, true);
                contentValues.put(MediaStore.Audio.Media.IS_RINGTONE, false);
                contentValues.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
            }

            contentValues.put(MediaStore.Audio.Media.IS_MUSIC, false);
            ContentResolver contentResolver = mContext.getContentResolver();
            Uri generalaudiouri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

            contentResolver.delete(generalaudiouri, MediaStore.MediaColumns.DATA + "='" + oPath +
                    "'", null);

            Uri ringtoneuri = contentResolver.insert(generalaudiouri, contentValues);
            RingtoneManager.setActualDefaultRingtoneUri(mContext, TONE_TYPE, ringtoneuri);

            String msg = "";
            if (type == RINGTONE_TYPE.RINGTONES) {
                msg = name + " set as ringtone successfully";
            } else if (type == RINGTONE_TYPE.NOTIFICATIONS) {
                msg = name + " set as notification tone successfully";
            } else if (type == RINGTONE_TYPE.ALRAM) {
                msg = name + " set as alarm tone successfully";
            }
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            Log.i("EROORRRR", "doInBackground: " + e.toString());
            e.printStackTrace();
        }


    }

    public static String getAppName(Context mContext) {
        return mContext.getString(R.string.app_name);
    }



    public static void Set_Ringtone(Context mContext, String filepath) {

        String filename = filepath.substring(filepath.lastIndexOf("/") + 1);
        String Ringtonename = filename.substring(0, filename.lastIndexOf("."));

        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, filepath);
        values.put(MediaStore.MediaColumns.TITLE, Ringtonename);
        values.put(MediaStore.MediaColumns.SIZE, filepath.length());
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
        values.put(MediaStore.Audio.Media.ARTIST, mContext.getString(R.string.app_name));
        // values.put(MediaStore.Audio.Media.DURATION, 230);
        values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
        values.put(MediaStore.Audio.Media.IS_ALARM, false);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);

        try {
            ContentResolver cr = mContext.getContentResolver();
            Uri uri = MediaStore.Audio.Media.getContentUriForPath(filepath);
            cr.delete(uri, MediaStore.MediaColumns.DATA + "=\"" + filepath + "\"", null);
            Uri newUri = cr.insert(uri, values);
            Log.e("", "=====Enter ====" + newUri);
            RingtoneManager.setActualDefaultRingtoneUri(mContext, RingtoneManager.TYPE_RINGTONE,
                    newUri);
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("", "Error" + e.getMessage());
        }
    }

    public static void Set_AssignContact(Context mContext, String filepath, Uri contactData) {

        String filename = filepath.substring(filepath.lastIndexOf("/") + 1);
        String Ringtonename = filename.substring(0, filename.lastIndexOf("."));

        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, filepath);
        values.put(MediaStore.MediaColumns.TITLE, Ringtonename);
        values.put(MediaStore.MediaColumns.SIZE, filepath.length());
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
        values.put(MediaStore.Audio.Media.ARTIST, mContext.getString(R.string.app_name));
        // values.put(MediaStore.Audio.Media.DURATION, 230);
        values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
        values.put(MediaStore.Audio.Media.IS_ALARM, false);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);

        try {

            ContentResolver cr = mContext.getContentResolver();
            Uri uri = MediaStore.Audio.Media.getContentUriForPath(filepath);
            cr.delete(uri, MediaStore.MediaColumns.DATA + "=\"" + filepath + "\"", null);
            Uri newUri = mContext.getContentResolver().insert(uri, values);

            Log.e("=====Enter ====", "" + newUri);
            try {
                assignRingtoneToContact(mContext, newUri, contactData);
            } catch (Exception e) {
                Log.e("RintonrMaker", "Couldn't open Choose Contact window");
            }

        } catch (Exception e) {

            // TODO: handle exception

            Log.e("", "Error" + e.getMessage());
        }
    }


    private static void assignRingtoneToContact(Context mContext, Uri newUri, Uri contactData) {
        ContentResolver cr = mContext.getContentResolver();
        String[] PROJECTION = new String[]{Contacts._ID, Contacts.DISPLAY_NAME, Contacts
                .HAS_PHONE_NUMBER,};
        Cursor c = cr.query(contactData, PROJECTION, null, null, null);
        c.moveToFirst();
        int dataIndex = c.getColumnIndexOrThrow(Contacts._ID);
        String contactId = c.getString(dataIndex);
        dataIndex = c.getColumnIndexOrThrow(Contacts.DISPLAY_NAME);
        String displayName = c.getString(dataIndex);
        Uri uri = Uri.withAppendedPath(Contacts.CONTENT_URI, contactId);
        ContentValues values = new ContentValues();
        values.put(ContactsContract.Data.CUSTOM_RINGTONE, newUri.toString());
        int temp = cr.update(uri, values, null, null);
        String message = null;
        if (temp > 0) {
            message = "Successfully assigned ringtone to:" + " " + displayName;
        } else {
            message = "UNSuccessfull";
        }
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

    }


    public static void setCustomRingtone(Context mContext, String path, Uri contactUri) {

        Uri newUri = null;

        String filename = path.substring(path.lastIndexOf("/") + 1);
        String Ringtonename = filename.substring(0, filename.lastIndexOf("."));

        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, path);
        values.put(MediaStore.MediaColumns.TITLE, Ringtonename);
        values.put(MediaStore.MediaColumns.SIZE, path.length());
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
        values.put(MediaStore.Audio.Media.ARTIST, mContext.getString(R.string.app_name));
        // values.put(MediaStore.Audio.Media.DURATION, 230);
        values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
        values.put(MediaStore.Audio.Media.IS_ALARM, false);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);

        try {

            ContentResolver cr = mContext.getContentResolver();
            Uri uri = MediaStore.Audio.Media.getContentUriForPath(path);
            cr.delete(uri, MediaStore.MediaColumns.DATA + "=\"" + path + "\"", null);
            newUri = mContext.getContentResolver().insert(uri, values);


        } catch (Exception e) {

            // TODO: handle exception

            Log.e("", "Error" + e.getMessage());
        }


        try {

            String contactId = contactUri.getLastPathSegment();
            String[] PROJECTION = new String[]{Contacts._ID,
                    Contacts.DISPLAY_NAME, Contacts.HAS_PHONE_NUMBER,};
            Cursor localCursor = mContext.getContentResolver().query(contactUri, PROJECTION,
                    null, null, null);
            localCursor.moveToFirst();
            //--> use moveToFirst instead of this:  localCursor.move(Integer.valueOf(contactId));
            // /*CONTACT ID NUMBER*/
            String contactDisplayName = localCursor.getString(localCursor.getColumnIndexOrThrow
                    ("display_name"));

            Uri localUri = Uri.withAppendedPath(Contacts.CONTENT_URI, contactId);
            localCursor.close();
            ContentValues localContentValues = new ContentValues();

            localContentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contactId);
            localContentValues.put(ContactsContract.Data.CUSTOM_RINGTONE, newUri.toString());
            mContext.getContentResolver().update(localUri, localContentValues, null, null);

            Toast.makeText(mContext, "Ringtone  assigned to: " + contactDisplayName + " " +
                    "Successfully", Toast.LENGTH_LONG).show();

        } catch (Exception ex) {

            Log.i("EROORRRR", "CONTACT: " + ex.toString());

            Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public static void setTone(Context mContext, String path, Uri contact) {


        ContentValues values = new ContentValues();

        ContentResolver resolver = mContext.getContentResolver();

        File file = new File(path);
        if (file.exists()) {


            String contact_number = null;
            String contactDisplayName = null;

            Cursor c = mContext.getContentResolver().query(contact, null, null, null, null);
            if (c.moveToFirst()) {
                int phoneIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                contact_number = c.getString(phoneIndex);

                int nameIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone
                        .DISPLAY_NAME);
                contactDisplayName = c.getString(nameIndex);
            }
            c.close();


            Uri oldUri = MediaStore.Audio.Media.getContentUriForPath(file.getAbsolutePath());
            resolver.delete(oldUri, MediaStore.MediaColumns.DATA + "=\"" + file.getAbsolutePath()
                    + "\"", null);
            Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                    contact_number);

            // The columns used for `Contacts.getLookupUri`
            String[] projection = new String[]{Contacts._ID,
                    Contacts.LOOKUP_KEY};

            Cursor data = mContext.getContentResolver().query(lookupUri, projection, null, null,
                    null);

            if (data != null && data.moveToFirst()) {
                data.moveToFirst();
                // Get the contact lookup Uri
                long contactId = data.getLong(0);
                String lookupKey = data.getString(1);
                Uri contactUri = Contacts.getLookupUri(contactId, lookupKey);

                values.put(MediaStore.MediaColumns.DATA, file.getAbsolutePath());
                values.put(MediaStore.MediaColumns.TITLE, file.getName());
                values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
                values.put(MediaStore.Audio.Media.IS_RINGTONE, true);

                Uri uri = MediaStore.Audio.Media.getContentUriForPath(file.getAbsolutePath());
                Uri newUri = resolver.insert(uri, values);

                if (newUri != null) {
                    String uriString = newUri.toString();
                    values.put(Contacts.CUSTOM_RINGTONE, uriString);
                    Log.e("Uri String for " + Contacts.CONTENT_URI, uriString);
                    long updated = resolver.update(contactUri, values, null, null);

                    Toast.makeText(mContext, "Ringtone  assigned to: " + contactDisplayName + " "
                            + "Successfully", Toast.LENGTH_LONG).show();
                }

                data.close();
            }


        } else {
            Toast.makeText(mContext, "File does not exist", Toast.LENGTH_LONG).show();
        }

    }
}
