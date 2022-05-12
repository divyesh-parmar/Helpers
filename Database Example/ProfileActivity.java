package com.div.videocall;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProfileActivity extends Activity {

    ImageView backy;
    EditText et_name, et_ph;
    ImageView prof_img;
    Button save_btn, logout_btn;
    TextView email_tv;
    PDatabaseHelper mDatabaseHelper;
    Uri xUri;
    byte[] mPic, c_pic;
    String c_name, c_ph, c_email;
    private String selectedcountry;
    int c_country, mPOS = 0;
    TextView selCountry;
    ImageView selFlag;
    LinearLayout spbox;

    //Crop Variable
    ImageView btncrop, backy1, disimg;
    public static Bitmap cropbit;
    CropImageView img;

    public String[] CountryNames = new String[]{"india", "morocco", "saudi arabia", "turkey", "pakistan",
            "egypt", "afghanistan", "albania", "algeria", "american samoa", "andorra", "angola", "anguilla", "antigua and barbuda", "argentina", "armenia",
            "aruba", "austalia", "austria", "azerbaijan", "bahamas", "bahrain", "bangladesh", "barbados", "belarus", "belgium", "belize",
            "benin", "bermuda", "bhutan", "bolivia", "bosnia and herzegovina", "botswana", "brazil", "british indian ocean territory", "british virgin islands", "brunei", "bulgaria",
            "burkina faso", "burundi", "cambodia", "cameroon", "united states of america", "cape verde", "netharlands antiless", "cayman islands", "central african republic", "chad", "chile",
            "china", "colombia", "comoros", "democratic republic of the congo", "congo", "cook islands", "costa rica", "ivory coast", "croatia", "cuba", "curacao",
            "cyprus", "czech republic", "denmark", "djibouti", "dominica", "united states of america", "ecuador", "el salvador", "equatorial guinea", "eritrea", "estonia",
            "ethiopia", "folkland iceland", "faroe islands", "fiji", "finland", "france", "french guiana", "french polynesia", "gabon", "gambia", "georgia",
            "germany", "ghana", "gibraltar", "greece", "greenland", "grenada", "ecuador", "guam", "guatemala", "guinea", "guinea bissau",
            "guyana", "haiti", "honduras", "hong kong", "hungary", "iceland", "indonesia", "iran", "iraq", "republic of ireland", "israel", "italy",
            "jamaica", "japan", "jordan", "kazakhstan", "argentina", "kiribati", "kuwait", "kyrgyzstan", "laos", "latvia", "lebanon",
            "lesotho", "liberia", "libya", "liechtenstein", "lithuania", "luxembourg", "macau", "macedonia ", "madagascar", "malawi", "malaysia",
            "maldives", "mali", "malta", "marshall islands", "martinique", "mauritania", "mauritius", "mexico", "federated states of micronesia", "moldova", "monaco",
            "mongolia", "montenegro", "montserrat", "mozambique", "myanmar", "namibia", "nauru", "nepal", "kingdom of the netherlands", "new caledonia", "new zeland",
            "nicaragua", "niger", "nigeria", "niue", "antartica", "north korea", "northern mariana islands", "norway", "oman", "palau", "state of palestine",
            "panama", "papua new guinea", "paraguay", "peru", "philippines", "poland", "portugal", "qatar", "reunion", "romania", "kazakhstan",
            "rwanda", "saint barthelemy", "saint helena", "saint kitts and nevis", "saint lucia", "saint barthelemy", "saint pierre and miquelon", "saint vincent and the grenadines", "samoa", "san marino", "sao tome and principe",
            "senegal", "serbia", "seychelles", "sierra leone", "singapore", "sint maarten", "slovakia", "slovenia", "solomon islands", "somalia", "south africa",
            "south korea", "south sudan", "spain", "sri lanka", "sudan", "suriname", "eswatini", "sweden", "switzerland", "syria", "taiwan", "tajikistan",
            "tanzania", "thailand", "timor leste", "togo", "tokelau", "tonga", "trinidad and tobago", "tunisia", "turkmenistan", "turks and caicos islands", "tuvalu",
            "u s virgin islands", "uganda", "ukraine", "united arab emirates", "united kingdom", "united states of america", "uruguay", "uzbekistan", "vanuatu", "italy", "venezuela", "vietnam",
            "wallis and futuna", "zambia", "zimbabwe"};

    public int Countrycodeflag[] = {R.drawable.india, R.drawable.morocco, R.drawable.saudiarabia, R.drawable.turkey, R.drawable.pakistan,
            R.drawable.egypt, R.drawable.afghanistan, R.drawable.albania, R.drawable.algeria, R.drawable.americansamoa, R.drawable.andorra, R.drawable.angola, R.drawable.anguilla, R.drawable.antiguaandbarbuda, R.drawable.argentina, R.drawable.armenia,
            R.drawable.aruba, R.drawable.austalia, R.drawable.austria, R.drawable.azerbaijan, R.drawable.bahamas, R.drawable.bahrain, R.drawable.bangladesh, R.drawable.barbados, R.drawable.belarus, R.drawable.belgium, R.drawable.belize,
            R.drawable.benin, R.drawable.bermuda, R.drawable.bhuta, R.drawable.bolivia, R.drawable.bosniaandherzegovina, R.drawable.botswana, R.drawable.brazil, R.drawable.britishindianoceanterritory, R.drawable.british_virgin_island, R.drawable.brunei, R.drawable.bulgaria,
            R.drawable.burkina_faso, R.drawable.burundi, R.drawable.cambodia, R.drawable.cameroon, R.drawable.united_states_of_america, R.drawable.cape_verde, R.drawable.netharlands_antilles, R.drawable.cayman_islands, R.drawable.centralafricanrepublic, R.drawable.chad, R.drawable.chile,
            R.drawable.china, R.drawable.colombia, R.drawable.comoros, R.drawable.democratic_republic_of_the_congo, R.drawable.congo, R.drawable.cook_islands, R.drawable.costarica, R.drawable.ivory_coast, R.drawable.croatia, R.drawable.cuba, R.drawable.curacao,
            R.drawable.cyprus, R.drawable.czech_republic, R.drawable.denmark, R.drawable.djibouti, R.drawable.dominica, R.drawable.united_states_of_america, R.drawable.ecuador, R.drawable.el_salvador, R.drawable.equatorial_guinea, R.drawable.eritrea, R.drawable.estonia,
            R.drawable.ethiopia, R.drawable.folkland_iceland, R.drawable.faroeislands, R.drawable.fiji, R.drawable.finland, R.drawable.france, R.drawable.frenchguiana, R.drawable.frenchpolynesia, R.drawable.gabon, R.drawable.gambia, R.drawable.georgia,
            R.drawable.germany, R.drawable.ghana, R.drawable.gibraltar, R.drawable.greece, R.drawable.greenland, R.drawable.grenada, R.drawable.ecuador, R.drawable.guam, R.drawable.guatemala, R.drawable.guinea, R.drawable.guineabissau,
            R.drawable.guyana, R.drawable.haiti, R.drawable.honduras, R.drawable.hongkong, R.drawable.hungary, R.drawable.iceland, R.drawable.indonesia, R.drawable.iran, R.drawable.iraq, R.drawable.republicofireland, R.drawable.israel, R.drawable.italy,
            R.drawable.jamaica, R.drawable.japan, R.drawable.jordan, R.drawable.kazakhstan, R.drawable.argentina, R.drawable.kiribati, R.drawable.kuwait, R.drawable.kyrgyzstan, R.drawable.laos, R.drawable.latvia, R.drawable.lebanon,
            R.drawable.lesotho, R.drawable.liberia, R.drawable.libya, R.drawable.liechtenstein, R.drawable.lithuania, R.drawable.luxembourg, R.drawable.macau, R.drawable.macedonia, R.drawable.madagascar, R.drawable.malawi, R.drawable.malaysia,
            R.drawable.maldives, R.drawable.mali, R.drawable.malta, R.drawable.marshallislands, R.drawable.martinique, R.drawable.mauritania, R.drawable.mauritius, R.drawable.mexico, R.drawable.federatedstatesofmicronesia, R.drawable.moldova, R.drawable.monaco,
            R.drawable.mongolia, R.drawable.montenegro, R.drawable.montserrat, R.drawable.mozambique, R.drawable.myanmar, R.drawable.namibia, R.drawable.nauru, R.drawable.nepal, R.drawable.kingdomofthenetherlands, R.drawable.newcaledonia, R.drawable.newzeland,
            R.drawable.nicaragua, R.drawable.niger, R.drawable.nigeria, R.drawable.niue, R.drawable.antartica, R.drawable.northkorea, R.drawable.northernmarianaislands, R.drawable.norway, R.drawable.oman, R.drawable.palau, R.drawable.stateofpalestine,
            R.drawable.panama, R.drawable.papuanewguinea, R.drawable.paraguay, R.drawable.peru, R.drawable.philippines, R.drawable.poland, R.drawable.portugal, R.drawable.qatar, R.drawable.reunion, R.drawable.romania, R.drawable.kazakhstan,
            R.drawable.rwanda, R.drawable.saintbarthelemy, R.drawable.sainthelena, R.drawable.saintkittsandnevis, R.drawable.saintlucia, R.drawable.saintbarthelemy, R.drawable.saintpierreandmiquelon, R.drawable.saintvincentandthegrenadines, R.drawable.samoa, R.drawable.sanmarino, R.drawable.saotomeandprincipe,
            R.drawable.senegal, R.drawable.serbia, R.drawable.seychelles, R.drawable.sierraleone, R.drawable.singapore, R.drawable.sintmaarten, R.drawable.slovakia, R.drawable.slovenia, R.drawable.solomonislands, R.drawable.somalia, R.drawable.southafrica,
            R.drawable.southkorea, R.drawable.southsudan, R.drawable.spain, R.drawable.srilanka, R.drawable.sudan, R.drawable.suriname, R.drawable.eswatini, R.drawable.sweden, R.drawable.switzerland, R.drawable.syria, R.drawable.taiwan, R.drawable.tajikistan,
            R.drawable.tanzania, R.drawable.thailand, R.drawable.timorleste, R.drawable.togo, R.drawable.tokelau, R.drawable.tonga, R.drawable.trinidadandtobago, R.drawable.tunisia, R.drawable.turkmenistan, R.drawable.turksandcaicosislands, R.drawable.tuvalu,
            R.drawable.usvirginislands, R.drawable.uganda, R.drawable.ukraine, R.drawable.unitedarabemirates, R.drawable.unitedkingdom, R.drawable.united_states_of_america, R.drawable.uruguay, R.drawable.uzbekistan, R.drawable.vanuatu, R.drawable.italy, R.drawable.venezuela, R.drawable.vietnam,
            R.drawable.wallis__futuna_flag, R.drawable.zambia, R.drawable.zimbabwe};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(1024, 1024);
        mDatabaseHelper = new PDatabaseHelper(ProfileActivity.this);

        backy = findViewById(R.id.backy);
        et_name = findViewById(R.id.et_name);
        et_ph = findViewById(R.id.et_ph);
        prof_img = findViewById(R.id.prof_img);
        save_btn = findViewById(R.id.save_btn);
        logout_btn = findViewById(R.id.logout_btn);
        email_tv = findViewById(R.id.email_tv);
        selFlag = findViewById(R.id.selflag);
        selCountry = findViewById(R.id.selcountry);
        spbox = findViewById(R.id.spbox);

        et_name.setSelection(et_name.getText().length());

        SetUIRelative(selFlag,167,111);

        try {
            mDatabaseHelper.createDatabase();
            mDatabaseHelper.openDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String xmail = Utils.get_STRING(ProfileActivity.this, "email", "danielbryan@gmail.com");
        Cursor zcursor = mDatabaseHelper.GetSpecific_Data(xmail);

        if (zcursor.moveToFirst()) {
            do {
                c_email = zcursor.getString(1);
                c_name = zcursor.getString(3);
                c_ph = zcursor.getString(4);
                c_country = zcursor.getInt(5);
                c_pic = zcursor.getBlob(6);
            }
            while (zcursor.moveToNext());
        }

        et_ph.setText(c_ph);
        et_name.setText(c_name);
        email_tv.setText(c_email);
        Bitmap c_bmp = BitmapFactory.decodeByteArray(c_pic, 0, c_pic.length);
        prof_img.setImageBitmap(c_bmp);

        selCountry.setText(CountryNames[c_country]);
        selFlag.setImageResource(Countrycodeflag[c_country]);

        spbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog1 = new Dialog(ProfileActivity.this);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog1.setContentView(R.layout.dialog_select_country);

                ListView lists=(ListView)dialog1.findViewById(R.id.lists);

                CountryAdapter adapter=new CountryAdapter(ProfileActivity.this,CountryNames,Countrycodeflag);
                lists.setAdapter(adapter);

                lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        mPOS=i;
                        selCountry.setText(CountryNames[mPOS]);
                        selFlag.setImageResource(Countrycodeflag[mPOS]);
                        dialog1.dismiss();
                    }
                });

                dialog1.show();
            }
        });

        prof_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 111);
                } catch (Exception exp) {
                    Log.i("Error", exp.toString());
                }
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String names = et_name.getText().toString();
                String phno = et_ph.getText().toString();
                Log.e("Pos -S-", mPOS + "");

                if (names.equals("") || phno.equals("")) {
                    Toast.makeText(ProfileActivity.this, "Please Fill All Information", Toast.LENGTH_SHORT).show();
                } else {

                    if (phno.equals("") || phno.length() < 10) {
                        Toast.makeText(ProfileActivity.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
                    } else {
                        if (mPic == null) {
                            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.image_bg3);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();

                            String xEmail = Utils.get_STRING(ProfileActivity.this, "email", "danielbryan@gmail.com");
                            mDatabaseHelper.Update(names, phno, mPOS, xEmail, byteArray);
                        } else {
                            String xEmail = Utils.get_STRING(ProfileActivity.this, "email", "danielbryan@gmail.com");
                            mDatabaseHelper.Update(names, phno, mPOS, xEmail, mPic);
                        }
                        onBackPressed();
                    }
                }
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.save_STRING(ProfileActivity.this, "BLK", "0");
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        backy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void SetUIRelative(View mview, int WIDTH, int HEIGHT) {
        RelativeLayout.LayoutParams layoutParamsi = new RelativeLayout.LayoutParams(
                getResources().getDisplayMetrics().widthPixels * WIDTH / 1080,
                getResources().getDisplayMetrics().widthPixels * HEIGHT / 1080);

        mview.setLayoutParams(layoutParamsi);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 111 && resultCode == RESULT_OK && null != data) {
            xUri = data.getData();

            final Dialog dialog1 = new Dialog(ProfileActivity.this, android.R.style.Theme_NoTitleBar_Fullscreen);
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog1.setCancelable(true);
            dialog1.setContentView(R.layout.dialog_crop);

            img = (CropImageView) dialog1.findViewById(R.id.img1);
            img.setFixedAspectRatio(true);
            img.setAspectRatio(1, 1);
            btncrop = (ImageView) dialog1.findViewById(R.id.btncrop);
            disimg = (ImageView) dialog1.findViewById(R.id.disimg);
            backy1 = (ImageView) dialog1.findViewById(R.id.backy1);

            img.setImageUriAsync(xUri);

            backy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog1.dismiss();
                }
            });

            btncrop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog1.dismiss();
                    cropbit = img.getCroppedImage();
                    prof_img.setImageBitmap(cropbit);
                    mPic = getBytesFromBitmap(cropbit);
                }
            });

            dialog1.show();
        }
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ProfileActivity.this, LoadingVideo.class);
        startActivity(intent);
        finish();
    }
}
