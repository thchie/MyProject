package com.my.newproject2;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.my.newproject2.TrainerTahap_1Activity.student_et_level1;
import static com.my.newproject2.TrainerTahap_1Activity.student_pressed_word_count_level1;
import static com.my.newproject2.TrainerTahap_1Activity.pressed_item_level1;

import static com.my.newproject2.TrainerTahap_2Activity.student_et_level2;
import static com.my.newproject2.TrainerTahap_2Activity.student_pressed_word_count_level2;
import static com.my.newproject2.TrainerTahap_2Activity.pressed_item_level2;

import static com.my.newproject2.TrainerTahap_3Activity.student_et_level3;
import static com.my.newproject2.TrainerTahap_3Activity.student_pressed_word_count_level3;
import static com.my.newproject2.TrainerTahap_3Activity.pressed_item_level3;

public class MyListAdapterStudent extends RecyclerView.Adapter<MyListAdapterStudent.ViewHolder> {
    private MyListData[] listdata;
    private TextToSpeech tts;
    private LayoutInflater layoutInflater;
    private View listItem;
    private ViewHolder viewHolder;
    private int level;

    // RecyclerView recyclerView;
    public MyListAdapterStudent(MyListData[] listdata, TextToSpeech tts, int level) {
        this.listdata = listdata;
        this.tts = tts;
        this.level=level;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final MyListData myListData = listdata[position];
        holder.textView.setText(listdata[position].getDescription());
        holder.imageView.setImageResource(listdata[position].getImgId());

        if (level == 1) {
            if ((pressed_item_level1[0] > -1 && pressed_item_level1[0] == position) || (pressed_item_level1[1] > -1 && pressed_item_level1[1] == position)) {

                holder.imageView.setColorFilter(null);
                holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!student_et_level1.getText().toString().contains(myListData.getDescription()) && student_pressed_word_count_level1 < 2) {
                            tts.speak(myListData.getDescription(), TextToSpeech.QUEUE_ADD, null);
                            student_et_level1.append(myListData.getDescription() + " ");
                            student_pressed_word_count_level1 += 1;
                        }
                    }
                });

            } else {

                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);
                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                holder.imageView.setColorFilter(filter);
                holder.relativeLayout.setOnClickListener(null);

            }
        } else if (level == 2) {
            if ((pressed_item_level2[0] > -1 && pressed_item_level2[0] == position) || (pressed_item_level2[1] > -1 && pressed_item_level2[1] == position)
                    ||(pressed_item_level2[2] > -1 && pressed_item_level2[2] == position)){
                holder.imageView.setColorFilter(null);
                holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!student_et_level2.getText().toString().contains(myListData.getDescription()) && student_pressed_word_count_level2 < 3) {
                            tts.speak(myListData.getDescription(), TextToSpeech.QUEUE_ADD, null);
                            student_et_level2.append(myListData.getDescription() + " ");
                            student_pressed_word_count_level2 += 1;
                        }
                    }
                });

            } else {

                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);
                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                holder.imageView.setColorFilter(filter);
                holder.relativeLayout.setOnClickListener(null);

            }
        }else if (level == 3) {
            if ((pressed_item_level3[0] > -1 && pressed_item_level3[0] == position) || (pressed_item_level3[1] > -1 && pressed_item_level3[1] == position)
                    ||(pressed_item_level3[2] > -1 && pressed_item_level3[2] == position) ||(pressed_item_level3[3] > -1 && pressed_item_level3[3] == position)){
                holder.imageView.setColorFilter(null);
                holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!student_et_level3.getText().toString().contains(myListData.getDescription()) && student_pressed_word_count_level3 < 4) {
                            tts.speak(myListData.getDescription(), TextToSpeech.QUEUE_ADD, null);
                            student_et_level3.append(myListData.getDescription() + " ");
                            student_pressed_word_count_level3 += 1;
                        }
                    }
                });

            } else {

                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);
                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                holder.imageView.setColorFilter(filter);
                holder.relativeLayout.setOnClickListener(null);

            }
        }
    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.textView = itemView.findViewById(R.id.textView);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }

}