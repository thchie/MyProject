package com.my.newproject2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class TrainerTahap_3Activity extends Activity {

    RecyclerView recyclerViewStudent;
    RecyclerView recyclerViewTeacher;
    public static EditText teacher_et_level3;
    public static EditText student_et_level3;
    public static int teacher_pressed_word_count_level3;
    public static int student_pressed_word_count_level3;
    public static int pressed_item_level3[];
    MyListAdapterTeacher adapterTeacher_level3;
    static MyListAdapterStudent adapterStudent_level3;
    private TextToSpeech tts;
    private SharedPreferences sp;

    private TextView teacher_tv;
    private ImageView teacher_img;
    private ImageView teacher_clear_img;
    private ImageView teacher_speak_img;

    private TextView student_tv;
    private ImageView student_img;
    private ImageView student_clear_img;
    private ImageView student_speak_img;

    @Override
    public void onCreate(Bundle _savedInstanceState) {

        super.onCreate(_savedInstanceState);
        setContentView(R.layout.trainer_tahap_1);
        tts = new TextToSpeech(getApplicationContext(), null);
        tts.setLanguage(Locale.US);

        pressed_item_level3= new int[]{-1, -1, -1, -1};

        teacher_img = findViewById(R.id.teacher_img);
        student_img = findViewById(R.id.student_img);
        teacher_tv = findViewById(R.id.teacher_tv);
        student_tv = findViewById(R.id.student_tv);

        sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
        if (sp.contains("image_file_path_teacher"))
            teacher_img.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(sp.getString("image_file_path_teacher", null), 200, 200));

        if (sp.contains("image_file_path_student"))
            student_img.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(sp.getString("image_file_path_student", null), 200, 200));

        teacher_tv.setText(getString(R.string.label_iam_teacher) + sp.getString("name_teacher", null));
        student_tv.setText(getString(R.string.label_iam_student) + sp.getString("name_student", null));

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
        teacher_et_level3 = findViewById(R.id.teacher_et);
        teacher_clear_img = findViewById(R.id.teacher_clear_img);
        teacher_speak_img = findViewById(R.id.teacher_speak_img);

        teacher_clear_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teacher_et_level3.setText("");
                student_et_level3.setText("");
                teacher_pressed_word_count_level3 = 0;
                student_pressed_word_count_level3 = 0;
                adapterStudent_level3.notifyItemChanged(pressed_item_level3[0]);
                adapterStudent_level3.notifyItemChanged(pressed_item_level3[1]);
                adapterStudent_level3.notifyItemChanged(pressed_item_level3[2]);
                adapterStudent_level3.notifyItemChanged(pressed_item_level3[3]);
                pressed_item_level3[0] = -1;
                pressed_item_level3[1] = -1;
                pressed_item_level3[2] = -1;
                pressed_item_level3[3] = -1;

            }
        });

        teacher_speak_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak(teacher_et_level3.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        });

        student_et_level3 = findViewById(R.id.student_et);
        student_clear_img = findViewById(R.id.student_clear_img);
        student_speak_img = findViewById(R.id.student_speak_img);

        student_clear_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                student_et_level3.setText("");
                student_pressed_word_count_level3 = 0;
            }
        });

        student_speak_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak(student_et_level3.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        });

        recyclerViewTeacher = findViewById(R.id.recyclerViewTeacher);
        adapterTeacher_level3 = new MyListAdapterTeacher(myListData, tts,3);
        recyclerViewTeacher.setHasFixedSize(true);

        recyclerViewTeacher.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerViewTeacher.setAdapter(adapterTeacher_level3);
        recyclerViewStudent = findViewById(R.id.recyclerViewStudent);
        adapterStudent_level3 = new MyListAdapterStudent(myListData, tts,3);
        recyclerViewStudent.setHasFixedSize(true);
        recyclerViewStudent.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerViewStudent.setAdapter(adapterStudent_level3);

    }

}
