package com.my.newproject2;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class ProfileActivity extends Activity {

    public final int REQ_CD_CAMERA = 101;
    public final int REQ_CD_GALLERY = 102;

    private Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    private File _file_camera;
    private String _filePath;
    private Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
    private boolean isTeacher = false;

    private EditText name_teacher_et;
    private EditText age_teacher_et;
    private EditText category_teacher_et;

    private EditText name_student_et;
    private EditText age_student_et;
    private EditText category_student_et;

    private ImageView profile_teacher_img;
    private ImageView camera_teacher_img;
    private ImageView photo_teacher_img;

    private ImageView profile_student_img;
    private ImageView camera_student_img;
    private ImageView photo_student_img;

    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.profile);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
            }
        }

        name_teacher_et = findViewById(R.id.name_teacher);
        age_teacher_et = findViewById(R.id.age_teacher);
        category_teacher_et = findViewById(R.id.category_teacher);

        name_student_et = findViewById(R.id.name_student);
        age_student_et = findViewById(R.id.age_student);
        category_student_et = findViewById(R.id.category_student);

        profile_teacher_img = findViewById(R.id.profile_teacher_img);
        camera_teacher_img = findViewById(R.id.camera_teacher_img);
        photo_teacher_img = findViewById(R.id.photo_teacher_img);

        profile_student_img = findViewById(R.id.profile_student_img);
        camera_student_img = findViewById(R.id.camera_student_img);
        photo_student_img = findViewById(R.id.photo_student_img);

        sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);

        name_teacher_et.setText(sp.getString("name_teacher", null));
        age_teacher_et.setText(sp.getString("age_teacher", null));
        category_teacher_et.setText(sp.getString("category_teacher", null));

        name_student_et.setText(sp.getString("name_student", null));
        age_student_et.setText(sp.getString("age_student", null));
        category_student_et.setText(sp.getString("category_student", null));

        if (sp.contains("image_file_path_teacher"))
            profile_teacher_img.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(sp.getString("image_file_path_teacher", null), 200, 200));

        if (sp.contains("image_file_path_student"))
            profile_student_img.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(sp.getString("image_file_path_student", null), 200, 200));


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
        gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);


        camera_teacher_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                isTeacher = true;
                startActivityForResult(camera, REQ_CD_CAMERA);
            }
        });

        photo_teacher_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                isTeacher = true;
                startActivityForResult(gallery, REQ_CD_GALLERY);
            }
        });

        camera_student_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                isTeacher = false;
                startActivityForResult(camera, REQ_CD_CAMERA);
            }
        });

        photo_student_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                isTeacher = false;
                startActivityForResult(gallery, REQ_CD_GALLERY);
            }
        });

    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {
            case REQ_CD_CAMERA:
                if (_resultCode == Activity.RESULT_OK) {
                    _filePath = _file_camera.getAbsolutePath();

                    if (isTeacher) {
                        profile_teacher_img.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath, 200, 200));
                        sp.edit().putString("image_file_path_teacher", _filePath).commit();
                    } else {
                        profile_student_img.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath, 200, 200));
                        sp.edit().putString("image_file_path_student", _filePath).commit();
                    }
                } else {

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
                            }
                        } else {
                            _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
                        }
                    }

                    if (isTeacher) {
                        profile_teacher_img.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath.get(0), 200, 200));
                        sp.edit().putString("image_file_path_teacher", _filePath.get(0)).commit();
                    } else {
                        profile_student_img.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath.get(0), 200, 200));
                        sp.edit().putString("image_file_path_student", _filePath.get(0)).commit();
                    }

                } else {

                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onBackPressed() {

        sp.edit().putString("name_teacher", name_teacher_et.getText().toString()).commit();
        sp.edit().putString("age_teacher", age_teacher_et.getText().toString()).commit();
        sp.edit().putString("category_teacher", category_teacher_et.getText().toString()).commit();

        sp.edit().putString("name_student", name_student_et.getText().toString()).commit();
        sp.edit().putString("age_student", age_student_et.getText().toString()).commit();
        sp.edit().putString("category_student", category_student_et.getText().toString()).commit();

        super.onBackPressed();
    }

    @Deprecated
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }

    @Deprecated
    public int getLocationX(View _v) {
        int _location[] = new int[2];
        _v.getLocationInWindow(_location);
        return _location[0];
    }

    @Deprecated
    public int getLocationY(View _v) {
        int _location[] = new int[2];
        _v.getLocationInWindow(_location);
        return _location[1];
    }

    @Deprecated
    public int getRandom(int _min, int _max) {
        Random random = new Random();
        return random.nextInt(_max - _min + 1) + _min;
    }

    @Deprecated
    public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
        ArrayList<Double> _result = new ArrayList<Double>();
        SparseBooleanArray _arr = _list.getCheckedItemPositions();
        for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
            if (_arr.valueAt(_iIdx))
                _result.add((double) _arr.keyAt(_iIdx));
        }
        return _result;
    }

    @Deprecated
    public float getDip(int _input) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
    }

    @Deprecated
    public int getDisplayWidthPixels() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Deprecated
    public int getDisplayHeightPixels() {
        return getResources().getDisplayMetrics().heightPixels;
    }

}
