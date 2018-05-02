package fr.wildcodeschool.wildquizz;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wilder on 28/03/18.
 */

public class DisplayQuizzAdapter extends ArrayAdapter<DisplayQuizzModel> {

    public DisplayQuizzAdapter(Context context, ArrayList<DisplayQuizzModel> trips) {
        super(context, 0, trips);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DisplayQuizzModel display = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_quizz, parent, false);
        }
        TextView tvIdQuizz = convertView.findViewById(R.id.id_quiz);
        TextView tvScore = convertView.findViewById(R.id.text_score_quizz);
        TextView tvNote = convertView.findViewById(R.id.text_note_quizz);
        RatingBar ratingBar = convertView.findViewById(R.id.rating_bar);

        tvIdQuizz.setText(display.getIdQuizz());
        tvScore.setText(String.valueOf(display.getScore()));
        tvNote.setText(String.valueOf(display.getNote()));
        ratingBar.setRating(display.getNote());

        return convertView;

    }



}
