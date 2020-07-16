package com.my.newproject2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;

import java.util.Locale;

import static com.my.newproject2.SharePreferenceUtils.LANG;
import static com.my.newproject2.SharePreferenceUtils.LEVEL;
import static com.my.newproject2.SharePreferenceUtils.LOGIN_STUDENT_ID;

public class MainActivity extends Activity {

    DatabaseHelper mDatabaseHelper;

    private Button level1_btn;
    private Button level2_btn;
    private Button level3_btn;
    private Button change_language_btn;
    private Button change_level_btn;
    private Button about_btn;

    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.main);
        initialize(_savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
//            case R.id.menu_myAccount:
//                intent.setClass(getApplicationContext(), ProfileActivity.class);
//                startActivity(intent);
//                return true;
            case R.id.menu_logout:
                mDatabaseHelper.updateDataStudent(LOGIN_STUDENT_ID, LEVEL, LANG);
                intent.setClass(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void initialize(Bundle _savedInstanceState) {

        mDatabaseHelper = new DatabaseHelper(this);

//        if (LANG.equals("english")){
//            setAppLocale("en");
//        } else if (LANG.equals("malay")){
//            setAppLocale("ms");
//        } else if (LANG.equals("chinese")){
//            setAppLocale("zh");
//        } else if (LANG.equals("tamil")){
//            setAppLocale("ta");
//        }

        level1_btn = findViewById(R.id.level1_btn);
        level2_btn = findViewById(R.id.level2_btn);
        level3_btn = findViewById(R.id.level3_btn);
        change_language_btn = findViewById(R.id.change_language_btn);
        change_level_btn = findViewById(R.id.change_level_btn);
        about_btn = findViewById(R.id.about_btn);

//        changeLanguage(false);
//        changeLanguage(true);
        changeLevel();


        level1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intent.setClass(getApplicationContext(), TrainerTahap_1Activity.class);
                startActivity(intent);
            }
        });

        level2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intent.setClass(getApplicationContext(), TrainerTahap_2Activity.class);
                startActivity(intent);
            }
        });

        level3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intent.setClass(getApplicationContext(), TrainerTahap_3Activity.class);
                startActivity(intent);
            }
        });

        change_language_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.change_language);
                dialog.show();

                final RadioButton english_rb = dialog.findViewById(R.id.english_rb);
                final RadioButton malay_rb = dialog.findViewById(R.id.malay_rb);
                final RadioButton chinese_rb = dialog.findViewById(R.id.chinese_rb);
                final RadioButton tamil_rb = dialog.findViewById(R.id.tamil_rb);

                if (LANG.equals("english")) {
                    english_rb.setChecked(true);
                } else if (LANG.equals("malay")){
                    malay_rb.setChecked(true);
                } else if (LANG.equals("chinese")){
                    chinese_rb.setChecked(true);
                } else if (LANG.equals("tamil")){
                    tamil_rb.setChecked(true);
                }

                Button cancel_btn = dialog.findViewById(R.id.cancel_btn);
                Button ok_btn = dialog.findViewById(R.id.ok_btn);
                cancel_btn.setOnClickListener(this);
                ok_btn.setOnClickListener(this);


                dialog.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                       dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.ok_btn).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        if (english_rb.isChecked()){
                            LANG = "english";
                            setAppLocale("en");
                        } else if (malay_rb.isChecked()){
                            LANG = "malay";
                            setAppLocale("ms");
                        } else if (chinese_rb.isChecked()){
                            LANG = "chinese";
                            setAppLocale("zh");
                        } else if (tamil_rb.isChecked()){
                            LANG = "tamil";
                            setAppLocale("ta");
                        }

//                        changeLanguage();
                    }
                });
            }
        });

        change_level_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {

                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.change_level);
                final RadioButton level1_rb = dialog.findViewById(R.id.level1_rb);
                final RadioButton level2_rb = dialog.findViewById(R.id.level2_rb);
                final RadioButton level3_rb = dialog.findViewById(R.id.level3_rb);

                if (LEVEL.equals("1")) {
                    level1_rb.setChecked(true);
                } else if (LEVEL.equals("2")){
                    level2_rb.setChecked(true);
                } else if (LEVEL.equals("3")){
                    level3_rb.setChecked(true);
                }
                changeLevel();

                Button cancel_btn = dialog.findViewById(R.id.cancel_btn);
                Button ok_btn = dialog.findViewById(R.id.ok_btn);
                cancel_btn.setOnClickListener(this);
                ok_btn.setOnClickListener(this);
                dialog.show();

                dialog.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.ok_btn).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        if (level1_rb.isChecked())
                            LEVEL = "1";
                        else if (level2_rb.isChecked())
                            LEVEL = "2";
                        else if (level3_rb.isChecked())
                            LEVEL = "3";
                        dialog.dismiss();
                    }
                });
            }
        });




        about_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intent.setClass(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
            }
        });




    }

    public void changeLevel(){

        if (LEVEL.equals("1")){
            level1_btn.setClickable(true);
            level2_btn.setClickable(false);
            level3_btn.setClickable(false);
            level1_btn.setTextColor(Color.BLACK);
            level2_btn.setTextColor(Color.GRAY);
            level3_btn.setTextColor(Color.GRAY);
        }else if (LEVEL.equals("2")){
            level1_btn.setClickable(true);
            level2_btn.setClickable(true);
            level3_btn.setClickable(false);
            level1_btn.setTextColor(Color.BLACK);
            level2_btn.setTextColor(Color.BLACK);
            level3_btn.setTextColor(Color.GRAY);
            level2_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }else if (LEVEL.equals("3")){
            level1_btn.setClickable(true);
            level2_btn.setClickable(true);
            level3_btn.setClickable(true);
            level1_btn.setTextColor(Color.BLACK);
            level2_btn.setTextColor(Color.BLACK);
            level3_btn.setTextColor(Color.BLACK);
            level2_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            level3_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    public void changeLanguage(){

        if (LANG.equals("english")){
            setAppLocale("en");
        } else if (LANG.equals("malay")){
            setAppLocale("ms");
        } else if (LANG.equals("chinese")){
            setAppLocale("zh");
        } else if (LANG.equals("tamil")) {
            setAppLocale("ta");
        }
    }

    private void setAppLocale(String localeCode){
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(new Locale(localeCode.toLowerCase()));
        resources.updateConfiguration(configuration, displayMetrics);
        configuration.locale = new Locale(localeCode.toLowerCase());
        resources.updateConfiguration(configuration, displayMetrics);
        recreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



}
