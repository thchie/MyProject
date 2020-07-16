package com.my.newproject2;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

import static com.my.newproject2.SharePreferenceUtils.TTS;
import static com.my.newproject2.TrainerTahap_1Activity.adapterStudent_level1;
import static com.my.newproject2.TrainerTahap_1Activity.teacher_et_level1;
import static com.my.newproject2.TrainerTahap_1Activity.teacher_pressed_word_count_level1;
import static com.my.newproject2.TrainerTahap_1Activity.pressed_item_level1;

import static com.my.newproject2.TrainerTahap_2Activity.adapterStudent_level2;
import static com.my.newproject2.TrainerTahap_2Activity.teacher_et_level2;
import static com.my.newproject2.TrainerTahap_2Activity.teacher_pressed_word_count_level2;
import static com.my.newproject2.TrainerTahap_2Activity.pressed_item_level2;

import static com.my.newproject2.TrainerTahap_3Activity.adapterStudent_level3;
import static com.my.newproject2.TrainerTahap_3Activity.teacher_et_level3;
import static com.my.newproject2.TrainerTahap_3Activity.teacher_pressed_word_count_level3;
import static com.my.newproject2.TrainerTahap_3Activity.pressed_item_level3;

public class MyListAdapterTeacher extends RecyclerView.Adapter<MyListAdapterTeacher.ViewHolder>{
    private MyListData[] listdata;
    private TextToSpeech tts;
    private LayoutInflater layoutInflater;
    private View listItem;
    private ViewHolder viewHolder;
    private int level;

    // RecyclerView recyclerView;
    public MyListAdapterTeacher(MyListData[] listdata, TextToSpeech tts, int level) {
        this.listdata = listdata;
        this.tts = tts;
        this.level=level;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final MyListData myListData = listdata[position];
        holder.textView.setText(listdata[position].getDescription());
        holder.imageView.setImageResource(listdata[position].getImgId());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (level==1) {
                    if (!teacher_et_level1.getText().toString().contains(myListData.getDescription()) && teacher_pressed_word_count_level1 < 2) {
                        tts.speak(myListData.getDescription(), TextToSpeech.QUEUE_ADD, null);
                        teacher_et_level1.append(myListData.getDescription() + " ");
                        pressed_item_level1[teacher_pressed_word_count_level1] = position;
                        adapterStudent_level1.notifyItemChanged(position);
                        teacher_pressed_word_count_level1 += 1;
                    }
                }else if (level==2){
                    if (!teacher_et_level2.getText().toString().contains(myListData.getDescription()) && teacher_pressed_word_count_level2 < 3) {
                        tts.speak(myListData.getDescription(), TextToSpeech.QUEUE_ADD, null);
                        teacher_et_level2.append(myListData.getDescription() + " ");
                        pressed_item_level2[teacher_pressed_word_count_level2] = position;
                        adapterStudent_level2.notifyItemChanged(position);
                        teacher_pressed_word_count_level2 += 1;
                    }
                }else if (level==3){
                    if (!teacher_et_level3.getText().toString().contains(myListData.getDescription()) && teacher_pressed_word_count_level3 < 4) {
                        tts.speak(myListData.getDescription(), TextToSpeech.QUEUE_ADD, null);
                        teacher_et_level3.append(myListData.getDescription() + " ");
                        pressed_item_level3[teacher_pressed_word_count_level3] = position;
                        adapterStudent_level3.notifyItemChanged(position);
                        teacher_pressed_word_count_level3 += 1;
                    }
                }
            }
        });
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