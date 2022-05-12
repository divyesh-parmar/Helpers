package com.div.videocall;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class LoginActivity extends Activity {

    Button male, female, go;
    EditText nick_name, pass_name;
    private PDatabaseHelper mDBHelper;
    private String l_email, l_pass;
    ImageView show;
    LinearLayout forgotlay;
    boolean xBoolean = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(1024, 1024);

        mDBHelper = new PDatabaseHelper(LoginActivity.this);

        try {
            mDBHelper.createDatabase();
            mDBHelper.openDatabase();
        } catch (IOException e) {
            Log.e("Checkdb", e.toString());
        }

        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        go = findViewById(R.id.go);
        nick_name = findViewById(R.id.nick_name);
        pass_name = findViewById(R.id.pass_name);
        show = findViewById(R.id.show);
        forgotlay = findViewById(R.id.forgotlay);

        if (Utils.get_STRING(LoginActivity.this, "gender", "0").equals("0")) {
            male.setBackgroundColor(Color.parseColor("#F81806"));
            female.setBackgroundColor(Color.parseColor("#C6C6C6"));
        } else {
            female.setBackgroundColor(Color.parseColor("#F81806"));
            male.setBackgroundColor(Color.parseColor("#C6C6C6"));
        }

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (xBoolean) {
                    pass_name.setTransformationMethod(null);
                    pass_name.setSelection(pass_name.length());
                    xBoolean = false;
                } else {
                    pass_name.setTransformationMethod(new PasswordTransformationMethod());
                    pass_name.setSelection(pass_name.length());
                    xBoolean = true;
                }
            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.save_STRING(LoginActivity.this, "gender", "0");
                male.setBackgroundColor(Color.parseColor("#F81806"));
                female.setBackgroundColor(Color.parseColor("#C6C6C6"));
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.save_STRING(LoginActivity.this, "gender", "1");
                female.setBackgroundColor(Color.parseColor("#F81806"));
                male.setBackgroundColor(Color.parseColor("#C6C6C6"));
            }
        });

        forgotlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog1 = new Dialog(LoginActivity.this);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog1.setContentView(R.layout.dialog_forgot_password);

                final EditText et_mail = (EditText) dialog1.findViewById(R.id.et_mail);
                final EditText pass1 = (EditText) dialog1.findViewById(R.id.pass1);
                final EditText pass2 = (EditText) dialog1.findViewById(R.id.pass2);
                Button CK = (Button) dialog1.findViewById(R.id.check);
                Button OK = (Button) dialog1.findViewById(R.id.ok);
                final TextView invalid = (TextView) dialog1.findViewById(R.id.invalid);
                final RelativeLayout valid = (RelativeLayout) dialog1.findViewById(R.id.valid);

                CK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String cmail = et_mail.getText().toString();

                        Cursor zcursor = mDBHelper.GetSpecific_Data(cmail);
//                    Cursor zcursor = myHelper.database
//                            .rawQuery("SELECT * FROM 'profile_table' WHERE email=?",
//                                    new String[]{email});
                        if (zcursor != null) {
                            if (zcursor.moveToFirst()) {
                                do {
                                    l_email = zcursor.getString(1);
                                    l_pass = zcursor.getString(2);
                                }
                                while (zcursor.moveToNext());
                            }

                            zcursor.close();

                        }

                        if (l_email != null && l_email.equals(cmail)) {
                            invalid.setVisibility(View.GONE);
                            valid.setVisibility(View.VISIBLE);

                        } else {
                            invalid.setVisibility(View.VISIBLE);
                            valid.setVisibility(View.GONE);
                        }
                    }
                });

                OK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String fpass = pass1.getText().toString();
                        String spass = pass2.getText().toString();
                        String jmail = et_mail.getText().toString();

                        if (fpass.equals(spass)) {
                            mDBHelper.UpdatePass(fpass, jmail);
                            Toast.makeText(LoginActivity.this, "Password Update Successfully", Toast.LENGTH_SHORT).show();
                            dialog1.dismiss();
                        } else {
                            Toast.makeText(LoginActivity.this, "Retype password not match", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog1.show();
            }
        });

        go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = nick_name.getText().toString();
                String pass = pass_name.getText().toString();

                if (email.equals("") || pass.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please Enter User Name", Toast.LENGTH_SHORT).show();
                } else {

                    Cursor zcursor = mDBHelper.GetSpecific_Data(email);
//                    Cursor zcursor = myHelper.database
//                            .rawQuery("SELECT * FROM 'profile_table' WHERE email=?",
//                                    new String[]{email});
                    if (zcursor != null) {
                        if (zcursor.moveToFirst()) {
                            do {
                                l_email = zcursor.getString(1);
                                l_pass = zcursor.getString(2);
                            }
                            while (zcursor.moveToNext());
                        }

                        zcursor.close();

                    }

                    if (l_email != null && l_email.equals(email)) {

                        if (l_pass.equals(pass)) {
                            Utils.save_STRING(LoginActivity.this, "BLK", "1");

                            Utils.save_STRING(LoginActivity.this, "email", email);

                            Intent intent = new Intent(LoginActivity.this, LoadingVideo.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Utils.save_STRING(LoginActivity.this, "BLK", "1");
                        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.image_bg3);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        Utils.save_STRING(LoginActivity.this, "email", email);

                        mDBHelper.insertData(email, pass, "", "", "", byteArray);
                        Intent intent = new Intent(LoginActivity.this, LoadingVideo.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
