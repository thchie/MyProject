package com.my.newproject2;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import static com.my.newproject2.SharePreferenceUtils.LOGIN_STUDENT_ID;
import static com.my.newproject2.SharePreferenceUtils.LOGIN_STUDENT_NAME;
import static com.my.newproject2.SharePreferenceUtils.IMG_FILE_PATH_STUDENT;
import static com.my.newproject2.SharePreferenceUtils.LANG;
import static com.my.newproject2.SharePreferenceUtils.LEVEL;

public class CreateStudentAccountActivity extends Activity {

    public final int REQ_CD_CAMERA = 101;
    public final int REQ_CD_GALLERY = 102;

    private Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    private Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
    private File _file_camera;
    private String image_file_path;

    DatabaseHelper mDatabaseHelper;

    private TextView welcome_text;

    private ImageView profile_student_img1;
    private ImageView profile_student_img2;
    private ImageView profile_student_img3;

    private ImageView camera_student_img1;
    private ImageView camera_student_img2;
    private ImageView camera_student_img3;

    private ImageView photo_student_img1;
    private ImageView photo_student_img2;
    private ImageView photo_student_img3;

    private Spinner okuNo_sp1;
    private Spinner okuNo_sp2;
    private Spinner okuNo_sp3;

    private EditText name_et1;
    private EditText name_et2;
    private EditText name_et3;

    private EditText age_et1;
    private EditText age_et2;
    private EditText age_et3;

    private EditText school_et1;
    private EditText school_et2;
    private EditText school_et3;

    private Button add_student_btn1;
    private Button add_student_btn2;
    private Button add_student_btn3;

    private Button login_btn1;
    private Button login_btn2;
    private Button login_btn3;

    String stu_id1;
    String stu_id2;
    String stu_id3;
    String level1;
    String level2;
    String level3;
    String language1;
    String language2;
    String language3;
    String img_file_path1;
    String img_file_path2;
    String img_file_path3;
    int buttonPressed = 0;

    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.create_student_account);
        initialize(_savedInstanceState);
    }

    @Override
    public void onBackPressed(){
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {
            case REQ_CD_CAMERA:
                if (_resultCode == Activity.RESULT_OK) {
                    String _filePath = _file_camera.getAbsolutePath();
                    image_file_path = _filePath;

                    if (buttonPressed == 1) {
                        img_file_path1 = _filePath;
                        profile_student_img1.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath, 200, 200));

                    } else if (buttonPressed == 2){
                        img_file_path2 = _filePath;
                        profile_student_img2.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath, 200, 200));

                    } else if (buttonPressed == 3){
                        img_file_path3 = _filePath;
                        profile_student_img3.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath, 200, 200));
                    }
                }
                break;

            case REQ_CD_GALLERY:
                if (_resultCode == Activity.RESULT_OK) {
                    ArrayList<String> _filePath = new ArrayList<>();
                    if (_data != null) {
                        if (_data.getClipData() != null) {
                            for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
                                ClipData.Item _item = _data.getClipData().getItemAt(_index);
                                _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
                                _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
                            }
                        } else {
                            _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
                        }
                    }
                    image_file_path = _filePath.get(0);

                    if (buttonPressed == 1) {
                        img_file_path1 = _filePath.get(0);
                        profile_student_img1.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath.get(0), 200, 200));

                    } else if (buttonPressed == 2){
                        img_file_path2 = _filePath.get(0);
                        profile_student_img2.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath.get(0), 200, 200));

                    } else if (buttonPressed == 3){
                        img_file_path3 = _filePath.get(0);
                        profile_student_img3.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath.get(0), 200, 200));
                    }
                }
                break;
            default:
                break;
        }
    }

    private void initialize(Bundle _savedInstanceState) {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
            }
        }

        _file_camera = FileUtil.createNewPictureFile(getApplicationContext());
        Uri _uri_camera = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            _uri_camera = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", _file_camera);
        } else {
            _uri_camera = Uri.fromFile(_file_camera);
        }

        camera.putExtra(MediaStore.EXTRA_OUTPUT, _uri_camera);
        camera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        gallery.setType("image/*");
        gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);

        mDatabaseHelper = new DatabaseHelper(this);

        welcome_text = findViewById(R.id.welcome_text);
        welcome_text.setText(getString(R.string.label_welcome_teacher) + SharePreferenceUtils.USER_NAME);

        profile_student_img1 = findViewById(R.id.profile_student_img1);
        profile_student_img2 = findViewById(R.id.profile_student_img2);
        profile_student_img3 = findViewById(R.id.profile_student_img3);

        camera_student_img1 = findViewById(R.id.camera_student_img1);
        camera_student_img2 = findViewById(R.id.camera_student_img2);
        camera_student_img3 = findViewById(R.id.camera_student_img3);

        photo_student_img1 = findViewById(R.id.photo_student_img1);
        photo_student_img2 = findViewById(R.id.photo_student_img2);
        photo_student_img3 = findViewById(R.id.photo_student_img3);

        okuNo_sp1 = findViewById(R.id.okuNo_sp1);
        okuNo_sp2 = findViewById(R.id.okuNo_sp2);
        okuNo_sp3 = findViewById(R.id.okuNo_sp3);

        name_et1 = findViewById(R.id.name_et1);
        name_et2 = findViewById(R.id.name_et2);
        name_et3 = findViewById(R.id.name_et3);

        age_et1 = findViewById(R.id.age_et1);
        age_et2 = findViewById(R.id.age_et2);
        age_et3 = findViewById(R.id.age_et3);

        school_et1 = findViewById(R.id.school_et1);
        school_et2 = findViewById(R.id.school_et2);
        school_et3 = findViewById(R.id.school_et3);

        add_student_btn1 = findViewById(R.id.add_student_btn1);
        add_student_btn2 = findViewById(R.id.add_student_btn2);
        add_student_btn3 = findViewById(R.id.add_student_btn3);

        login_btn1 = findViewById(R.id.login_btn1);
        login_btn2 = findViewById(R.id.login_btn2);
        login_btn3 = findViewById(R.id.login_btn3);


        camera_student_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                buttonPressed = 1;
                startActivityForResult(camera, REQ_CD_CAMERA);
            }
        });

        camera_student_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                buttonPressed = 2;
                startActivityForResult(camera, REQ_CD_CAMERA);
            }
        });

        camera_student_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                buttonPressed = 3;
                startActivityForResult(camera, REQ_CD_CAMERA);
            }
        });

        photo_student_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                buttonPressed = 1;
                startActivityForResult(gallery, REQ_CD_GALLERY);
            }
        });

        photo_student_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                buttonPressed = 2;
                startActivityForResult(gallery, REQ_CD_GALLERY);
            }
        });

        photo_student_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                buttonPressed = 3;
                startActivityForResult(gallery, REQ_CD_GALLERY);
            }
        });


        login_btn1.setOnClickListener(listener);
        login_btn2.setOnClickListener(listener);
        login_btn3.setOnClickListener(listener);
        add_student_btn1.setOnClickListener(listener);
        add_student_btn2.setOnClickListener(listener);
        add_student_btn3.setOnClickListener(listener);

        Cursor data = mDatabaseHelper.getDataStudent(getIntent().getStringExtra("student1"));
        Cursor data2 = mDatabaseHelper.getDataStudent(getIntent().getStringExtra("student2"));
        Cursor data3 = mDatabaseHelper.getDataStudent(getIntent().getStringExtra("student3"));

        while (data.moveToNext()) {

            stu_id1 = data.getString(0);
            okuNo_sp1.setEnabled(false);
            name_et1.setText(data.getString(1));
            name_et1.setEnabled(false);
            age_et1.setText(data.getString(2));
            age_et1.setEnabled(false);
            school_et1.setText(data.getString(3));
            school_et1.setEnabled(false);
            level1 = data.getString(4);
            language1 = data.getString(5);
            img_file_path1 = data.getString(6);

            profile_student_img1.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(img_file_path1, 200, 200));

            add_student_btn1.setVisibility(View.GONE);
            login_btn1.setVisibility(View.VISIBLE);
        }

        while (data2.moveToNext()) {

            stu_id2 = data.getString(0);
            okuNo_sp2.setEnabled(false);
            name_et2.setText(data2.getString(1));
            name_et2.setEnabled(false);
            age_et2.setText(data2.getString(2));
            age_et2.setEnabled(false);
            school_et2.setText(data2.getString(3));
            school_et2.setEnabled(false);
            level2 = data2.getString(4);
            language2 = data.getString(5);
            img_file_path2 = data.getString(6);

            profile_student_img2.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(img_file_path2, 200, 200));

            add_student_btn2.setVisibility(View.GONE);
            login_btn2.setVisibility(View.VISIBLE);
        }

        while (data3.moveToNext()) {

            stu_id3 = data.getString(0);
            okuNo_sp3.setEnabled(false);
            name_et3.setText(data3.getString(1));
            name_et3.setEnabled(false);
            age_et3.setText(data3.getString(2));
            age_et3.setEnabled(false);
            school_et3.setText(data3.getString(3));
            school_et3.setEnabled(false);
            level3 = data3.getString(4);
            language3 = data.getString(5);
            img_file_path3 = data.getString(6);

            profile_student_img3.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(img_file_path3, 200, 200));

            add_student_btn3.setVisibility(View.GONE);
            login_btn3.setVisibility(View.VISIBLE);
        }

    }

    public boolean addData(String id, String name, String age, String school, String imgPath){
        return mDatabaseHelper.addDataStudent(id, name, age, school, imgPath);
    }

    View.OnClickListener listener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            String name;
            String age;
            String school;
            String stu_id = "" + (mDatabaseHelper.getStudentCount()+1);

            switch (v.getId())
            {
                case R.id.login_btn1:
                    intent.putExtra("student", 1);
                    intent.putExtra("level", level1);
                    intent.putExtra("language", language1);
                    intent.putExtra("imag_file_path", img_file_path1);
                    LOGIN_STUDENT_ID = stu_id1;
                    LOGIN_STUDENT_NAME = name_et1.getText().toString();
                    LANG = language1;
                    LEVEL = level1;
                    IMG_FILE_PATH_STUDENT = img_file_path1;
                    intent.setClass(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.login_btn2:
                    intent.putExtra("student", 2);
                    intent.putExtra("level", level2);
                    intent.putExtra("language", language2);
                    intent.putExtra("imag_file_path", img_file_path2);
                    LOGIN_STUDENT_ID = stu_id2;
                    LOGIN_STUDENT_NAME = name_et2.getText().toString();
                    LANG = language2;
                    LEVEL = level2;
                    IMG_FILE_PATH_STUDENT = img_file_path2;
                    intent.setClass(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.login_btn3:
                    intent.putExtra("student", 3);
                    intent.putExtra("level", level3);
                    intent.putExtra("language", language3);
                    intent.putExtra("imag_file_path", img_file_path3);
                    LOGIN_STUDENT_ID = stu_id3;
                    LOGIN_STUDENT_NAME = name_et3.getText().toString();
                    LANG = language3;
                    LEVEL = level3;
                    IMG_FILE_PATH_STUDENT = img_file_path3;
                    intent.setClass(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.add_student_btn1:
                    name = name_et1.getText().toString().trim();
                    age = age_et1.getText().toString().trim();
                    school = school_et1.getText().toString().trim();

                    if (addData(stu_id,name,age,school,img_file_path1)){
                        Toast.makeText(getApplicationContext(), getString(R.string.prompt_data_inserted), Toast.LENGTH_SHORT).show();

                        okuNo_sp1.setEnabled(false);
                        name_et1.setEnabled(false);
                        age_et1.setEnabled(false);
                        school_et1.setEnabled(false);

                        onRestart();
                        add_student_btn1.setVisibility(View.GONE);
                        login_btn1.setVisibility(View.VISIBLE);

                        mDatabaseHelper.updateDataTeacher(""+SharePreferenceUtils.USER_ID, "student1", stu_id);

                    }else{
                        Toast.makeText(getApplicationContext(), getString(R.string.prompt_data_fail_to_insert), Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.add_student_btn2:
                    name = name_et2.getText().toString().trim();
                    age = age_et2.getText().toString().trim();
                    school = school_et2.getText().toString().trim();

                    if (addData(stu_id,name,age,school,img_file_path2)){
                        Toast.makeText(getApplicationContext(), getString(R.string.prompt_data_inserted), Toast.LENGTH_SHORT).show();

                        okuNo_sp2.setEnabled(false);
                        name_et2.setEnabled(false);
                        age_et2.setEnabled(false);
                        school_et2.setEnabled(false);

                        onRestart();
                        add_student_btn2.setVisibility(View.GONE);
                        login_btn2.setVisibility(View.VISIBLE);

                        mDatabaseHelper.updateDataTeacher(""+SharePreferenceUtils.USER_ID, "student2", stu_id);

                    }else{
                        Toast.makeText(getApplicationContext(), getString(R.string.prompt_data_fail_to_insert), Toast.LENGTH_SHORT).show();
                    }

                    mDatabaseHelper.updateDataTeacher(""+SharePreferenceUtils.USER_ID, "student2", stu_id);
                    break;
                case R.id.add_student_btn3:
                    name = name_et3.getText().toString().trim();
                    age = age_et3.getText().toString().trim();
                    school = school_et3.getText().toString().trim();

                    if (addData(stu_id,name,age,school,img_file_path3)){
                        Toast.makeText(getApplicationContext(), getString(R.string.prompt_data_inserted), Toast.LENGTH_SHORT).show();

                        okuNo_sp3.setEnabled(false);
                        name_et3.setEnabled(false);
                        age_et3.setEnabled(false);
                        school_et3.setEnabled(false);

                        onRestart();
                        add_student_btn3.setVisibility(View.GONE);
                        login_btn3.setVisibility(View.VISIBLE);

                        mDatabaseHelper.updateDataTeacher(""+SharePreferenceUtils.USER_ID, "student3", stu_id);

                    }else{
                        Toast.makeText(getApplicationContext(), getString(R.string.prompt_data_fail_to_insert), Toast.LENGTH_SHORT).show();
                    }

                    mDatabaseHelper.updateDataTeacher(""+SharePreferenceUtils.USER_ID, "student3", stu_id);
                    break;
                default:
                    break;
            }

        }
    };


}
