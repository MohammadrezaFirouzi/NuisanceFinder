package com.firouzi.mozahem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import java.util.Random;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;


public class Search extends AppCompatActivity implements SearchDialog.MyDialogEventListener,ShowinfoDialog.MyDialogEventListener{

    private SearchDialog searchDialog;
    private TextInputEditText textInputEditText;

    private ShowinfoDialog showinfoDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        textInputEditText = findViewById(R.id.numberedit);
        Button btn = findViewById(R.id.btn);
        searchDialog = new SearchDialog();
        ShowinfoDialog infoDialog = new ShowinfoDialog();
        ImageView rubika = findViewById(R.id.rubika);
        ImageView telegram = findViewById(R.id.tel);
        ImageView insta = findViewById(R.id.insta);
        RelativeLayout deletenumberbtn = findViewById(R.id.deletenumber);

        rubika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String[] myList = {"rubika://l.rubika.ir/Mohamadreza_firouzim", "rubika://g.rubika.ir/FJBCBBDF0YBLWWZNYCYIXOOOJJLATNJQ", "rubika://l.rubika.ir/api_web","rubika://l.rubika.ir/Source_Cade"};
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getRandomItem(myList)));
                    startActivity(intent);
                }catch (Exception e){
                    Snackbar.make(view, "شما اپلیکیشن روبیکا را نصب نکرده اید", Snackbar.LENGTH_LONG).show();

                }


            }
        });

        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://t.me/Mohammadreza_firouziii");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.instagram.com/mohammadreza_firoouzi");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        deletenumberbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "این قابلیت به زودی اضافه خواهد شد", Snackbar.LENGTH_LONG).show();

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textInputEditText.getText().length() > 0){
                    searchDialog.show(getSupportFragmentManager() , null);
                    MyHttpClient.sendGetRequest("https://phone.liara.run/?num="+textInputEditText.getText(), new MyHttpClient.MyCallback() {
                        @Override
                        public void onSuccess(String response) {
                            try{
                                JSONObject jsonresponse = new JSONObject(response);
                                JSONObject result = jsonresponse.getJSONObject("result");
                                String name = result.getString("name");
                                String last = result.getString("last");
                                searchDialog.dismiss();
                                if (!name.equals("null")) {
                                    if (!last.equals("null")){
                                        String firstAndLast = name +" "+ last ;
                                        infoDialog.showInfo(getSupportFragmentManager(), firstAndLast ,textInputEditText.getText().toString());
                                    }
                                    else{
                                        infoDialog.showInfo(getSupportFragmentManager(), name ,textInputEditText.getText().toString());
                                    }

                                }
                                else {
                                    infoDialog.showInfo(getSupportFragmentManager(), "چیزی یافت نشد" ,textInputEditText.getText().toString());
                                }





                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(Exception e) {
                            e.printStackTrace();
                            Toast.makeText(Search.this, "خطا", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onCancelButtonClicked() {

    }


    private String getRandomItem(String[] array) {
        Random random = new Random();
        int randomIndex = random.nextInt(array.length);
        return array[randomIndex];
    }

}