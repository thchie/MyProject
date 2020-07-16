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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class SignUpActivity extends Activity {

    public static final String TAG = "SignUpActivity";

    DatabaseHelper mDatabaseHelper;

    public final int REQ_CD_CAMERA = 101;
    public final int REQ_CD_GALLERY = 102;

    private Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    private Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
    private File _file_camera;
    private String image_file_path;

    private SharedPreferences sp;

    private TextView login_text;
    private ImageView profile_teacher_img;
    private ImageView camera_teacher_img;
    private ImageView photo_teacher_img;
    private EditText name_et;
    private EditText email_et;
    private EditText password_et;
    private EditText verify_password_et;
    private Button sign_up_btn;
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.sign_up);
        initialize(_savedInstanceState);
    }

    private void initialize(Bundle _savedInstanceState) {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
            }
        }

        login_text = findViewById(R.id.login_text);
        profile_teacher_img = findViewById(R.id.profile_teacher_img);
        camera_teacher_img = findViewById(R.id.camera_teacher_img);
        photo_teacher_img = findViewById(R.id.photo_teacher_img);
        name_et = findViewById(R.id.name_et);
        email_et = findViewById(R.id.email_et);
        password_et = findViewById(R.id.password_et);
        verify_password_et = findViewById(R.id.verify_password_et);
        sign_up_btn = findViewById(R.id.sign_up_btn);

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


        camera_teacher_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                startActivityForResult(camera, REQ_CD_CAMERA);
            }
        });

        photo_teacher_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                startActivityForResult(gallery, REQ_CD_GALLERY);
            }
        });

        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intent.setClass(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {

                String name = name_et.getText().toString().trim();
                String email = email_et.getText().toString().trim();
                String password = password_et.getText().toString().trim();
                String verify_password = verify_password_et.getText().toString().trim();


                if (name.length()!=0 && email.length()!=0 && password.length()!=0 && verify_password.length()!=0) {

                    if (!password.equals(verify_password))
                        Toast.makeText(SignUpActivity.this,  getString(R.string.prompt_verify_password_same), Toast.LENGTH_LONG).show();
                    else
                        addData(name, email, password, image_file_path);

                } else {
                    Toast.makeText(SignUpActivity.this, getString(R.string.prompt_fill_all_data), Toast.LENGTH_LONG).show();
                }

            }
        });

        mDatabaseHelper = new DatabaseHelper(this);
        sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);


    }


    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {
            case REQ_CD_CAMERA:
                if (_resultCode == Activity.RESULT_OK) {
                    String _filePath = _file_camera.getAbsolutePath();
//                    String _filePath = "/storage/emulated/0/Android/data/com.my.newproject2/TeacherImage";
//                    /storage/emulated/0/Android/data/com.my.newproject2/files/DCIM/20200511_041437.jpg
                    image_file_path = _filePath;

                    System.out.println("hihi lalala camera : " + _filePath);

                    profile_teacher_img.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath, 200, 200));
//                    sp.edit().putString("image_file_path_teacher", _filePath).commit();

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

//                    /storage/emulated/0/Android/data/com.my.newproject2/files/DCIM/20200511_041437.jpg
//                    /storage/emulated/0/DCIM/Camera/IMG_20200511_032026.jpg
                    System.out.println("hihi lalala gallery : " + _filePath.get(0));

                    image_file_path = _filePath.get(0);
                    profile_teacher_img.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath.get(0), 200, 200));
//                    sp.edit().putString("image_file_path_teacher", "apple").commit();

                }
                break;
            default:
                break;
        }
    }


    public void addData(String name, String email, String password, String image_file_path){
        boolean insertData = mDatabaseHelper.addDataTeacher(name, email, password, image_file_path);

        if (insertData) {
            Toast.makeText(this, getString(R.string.prompt_data_inserted), Toast.LENGTH_SHORT).show();
            intent.setClass(getApplicationContext(), LoginActivity.class);
            startActivity(intent);

        }else
            Toast.makeText(this, getString(R.string.prompt_data_fail_to_insert), Toast.LENGTH_SHORT).show();
    }

}
