package com.my.newproject2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import static com.my.newproject2.SharePreferenceUtils.IMG_FILE_PATH_STUDENT;
import static com.my.newproject2.SharePreferenceUtils.IMG_FILE_PATH_TEACHER;
import static com.my.newproject2.SharePreferenceUtils.LANG;
import static com.my.newproject2.SharePreferenceUtils.LEVEL;
import static com.my.newproject2.SharePreferenceUtils.LOGIN_STUDENT_ID;
import static com.my.newproject2.SharePreferenceUtils.LOGIN_STUDENT_NAME;
import static com.my.newproject2.SharePreferenceUtils.TTS;
import static com.my.newproject2.SharePreferenceUtils.USER_NAME;

public class TrainerTahap_1Activity extends Activity {

    DatabaseHelper mDatabaseHelper;

    RecyclerView recyclerViewStudent;
    RecyclerView recyclerViewTeacher;
    public static EditText teacher_et_level1;
    public static EditText student_et_level1;
    public static int teacher_pressed_word_count_level1;
    public static int student_pressed_word_count_level1;
    public static int pressed_item_level1[];
    MyListAdapterTeacher adapterTeacher;
    static MyListAdapterStudent adapterStudent_level1;
    private SharePreferenceUtils sp;

    private TextView teacher_tv;
    private ImageView teacher_img;
    private ImageView teacher_clear_img;
    private ImageView teacher_speak_img;

    private TextView student_tv;
    private ImageView student_img;
    private ImageView student_clear_img;
    private ImageView student_speak_img;

    private Intent intent = new Intent();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.level_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_home:
                intent.setClass(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_logout:
                mDatabaseHelper.updateDataStudent(LOGIN_STUDENT_ID, LEVEL, LANG);
                intent.setClass(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {

        mDatabaseHelper = new DatabaseHelper(this);

        super.onCreate(_savedInstanceState);
        setContentView(R.layout.trainer_tahap_1);
        TTS = new TextToSpeech(getApplicationContext(), null);
        if (LANG.equals("malay")){
            TTS.setLanguage(new Locale("in_ID","ID"));
        } else if (LANG.equals("chinese")){
            TTS.setLanguage(Locale.CHINESE);
        } else if (LANG.equals("tamil")){
            TTS.setLanguage(new Locale("ta_MY","MY"));
        } else {
            TTS.setLanguage(Locale.US);
        }

        pressed_item_level1 = new int[]{-1, -1};

        teacher_img = findViewById(R.id.teacher_img);
        student_img = findViewById(R.id.student_img);
        teacher_tv = findViewById(R.id.teacher_tv);
        student_tv = findViewById(R.id.student_tv);

        teacher_img.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(IMG_FILE_PATH_TEACHER, 200, 200));
        student_img.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(IMG_FILE_PATH_STUDENT, 200, 200));

        teacher_tv.setText(USER_NAME);
        student_tv.setText(LOGIN_STUDENT_NAME);


        MyListData[] myListData = new MyListData[]{
                new MyListData("  " + getString(R.string.level1_body), R.drawable.level1_body),
                new MyListData("  " + getString(R.string.level1_bowl), R.drawable.level1_bowl),
                new MyListData("  " + getString(R.string.level1_boy), R.drawable.level1_boy),
                new MyListData("  " + getString(R.string.level1_brother), R.drawable.level1_brother),
                new MyListData("  " + getString(R.string.level1_cup), R.drawable.level1_cup),
                new MyListData("  " + getString(R.string.level1_father), R.drawable.level1_father),
                new MyListData("  " + getString(R.string.level1_fork), R.drawable.level1_fork),
                new MyListData("  " + getString(R.string.level1_girl), R.drawable.level1_girl),
                new MyListData("  " + getString(R.string.level1_glass), R.drawable.level1_glass),
                new MyListData("  " + getString(R.string.level1_grandfather), R.drawable.level1_grandfather),
                new MyListData("  " + getString(R.string.level1_grandmother), R.drawable.level1_grandmother),
                new MyListData("  " + getString(R.string.level1_head), R.drawable.level1_head),
                new MyListData("  " + getString(R.string.level1_legs), R.drawable.level1_legs),
                new MyListData("  " + getString(R.string.level1_mother), R.drawable.level1_mother),
                new MyListData("  " + getString(R.string.level1_plate), R.drawable.level1_plate),
                new MyListData("  " + getString(R.string.level1_shoe), R.drawable.level1_shoe),
                new MyListData("  " + getString(R.string.level1_singlet), R.drawable.level1_singlet),
                new MyListData("  " + getString(R.string.level1_sister), R.drawable.level1_sister),
                new MyListData("  " + getString(R.string.level1_socks), R.drawable.level1_socks),
                new MyListData("  " + getString(R.string.level1_spoon), R.drawable.level1_spoon),
                new MyListData("  " + getString(R.string.level1_t_shirt), R.drawable.level1_t_shirt),
                new MyListData("  " + getString(R.string.level1_trouser), R.drawable.level1_trouser),
                new MyListData("  " + getString(R.string.level1_underwear), R.drawable.level1_underwear),
                new MyListData("  " + getString(R.string.level1_grandmother), R.drawable.level1_you),
        };

        teacher_et_level1 = findViewById(R.id.teacher_et);
        teacher_clear_img = findViewById(R.id.teacher_clear_img);
        teacher_speak_img = findViewById(R.id.teacher_speak_img);

        teacher_clear_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teacher_et_level1.setText("");
                student_et_level1.setText("");
                teacher_pressed_word_count_level1 = 0;
                student_pressed_word_count_level1 = 0;
                adapterStudent_level1.notifyItemChanged(pressed_item_level1[0]);
                adapterStudent_level1.notifyItemChanged(pressed_item_level1[1]);
                pressed_item_level1[0] = -1;
                pressed_item_level1[1] = -1;
            }
        });

        teacher_speak_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (LANG.equals("malay")){
                    TTS.setLanguage(new Locale("in_ID","ID"));
                } else if (LANG.equals("chinese")){
                    TTS.setLanguage(Locale.CHINESE);
                } else if (LANG.equals("tamil")){
                    TTS.setLanguage(new Locale("ta_MY","MY"));
                } else {
                    TTS.setLanguage(Locale.US);
                }

//                TTS.setLanguage(Locale.CHINESE);
                String speakingText = teacher_et_level1.getText().toString();
                TTS.speak(speakingText, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        student_et_level1 = findViewById(R.id.student_et);
        student_clear_img = findViewById(R.id.student_clear_img);
        student_speak_img = findViewById(R.id.student_speak_img);

        student_clear_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                student_et_level1.setText("");
                student_pressed_word_count_level1 = 0;
            }
        });

        student_speak_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TTS.speak(student_et_level1.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        });

        recyclerViewTeacher = findViewById(R.id.recyclerViewTeacher);
        adapterTeacher = new MyListAdapterTeacher(myListData, TTS, 1);
        recyclerViewTeacher.setHasFixedSize(true);

        recyclerViewTeacher.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerViewTeacher.setAdapter(adapterTeacher);
        recyclerViewStudent = findViewById(R.id.recyclerViewStudent);
        adapterStudent_level1 = new MyListAdapterStudent(myListData, TTS,1);
        recyclerViewStudent.setHasFixedSize(true);
        recyclerViewStudent.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerViewStudent.setAdapter(adapterStudent_level1);

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}
