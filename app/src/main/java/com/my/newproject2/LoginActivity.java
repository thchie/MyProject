package com.my.newproject2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class LoginActivity extends Activity {

    DatabaseHelper mDatabaseHelper;

    private TextView sign_up_text;
    private EditText email_et;
    private EditText password_et;
    private Button login_btn;
    private Cursor data;
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.login);
        initialize(_savedInstanceState);
    }

    private void initialize(Bundle _savedInstanceState) {

        mDatabaseHelper = new DatabaseHelper(this);
        email_et = findViewById(R.id.email_et);
        password_et = findViewById(R.id.password_et);
        sign_up_text = findViewById(R.id.sign_up_text);
        login_btn = findViewById(R.id.login_btn);

        sign_up_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {

                intent.setClass(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {

                data = mDatabaseHelper.login(email_et.getText().toString().trim(), password_et.getText().toString().trim());
                boolean flag = false;

                while (data.moveToNext()){
                    int user_id = Integer.parseInt(data.getString(0));
                    String name_db = data.getString(1);
                    String email_db = data.getString(2);
                    String password_db = data.getString(3);
                    String student1 = data.getString(4);
                    String student2 = data.getString(5);
                    String student3 = data.getString(6);
                    String img_file_path_teacher = data.getString(7);

                    if (email_db.equals(email_et.getText().toString()) && password_db.equals(password_et.getText().toString())){

                        SharePreferenceUtils.USER_ID = user_id;
                        SharePreferenceUtils.USER_NAME = name_db;
                        SharePreferenceUtils.IMG_FILE_PATH_TEACHER = img_file_path_teacher;

                        intent.setClass(getApplicationContext(), CreateStudentAccountActivity.class);
                        intent.putExtra("student1", student1);
                        intent.putExtra("student2", student2);
                        intent.putExtra("student3", student3);
                        startActivity(intent);
                        flag = true;
                        break;
                    }
                }

                if (!flag){
                    Toast.makeText(getApplicationContext(),getString(R.string.prompt_no_account), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
