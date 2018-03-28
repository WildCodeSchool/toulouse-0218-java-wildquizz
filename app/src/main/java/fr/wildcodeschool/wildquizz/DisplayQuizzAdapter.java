package fr.wildcodeschool.wildquizz;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wilder on 28/03/18.
 */

public class DisplayQuizzAdapter extends ArrayAdapter<DisplayQuizzModel> {

    public DisplayQuizzAdapter(Context context, ArrayList<DisplayQuizzModel> displayQuizz) {
        super(context,0,displayQuizz);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DisplayQuizzModel display = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_quizz, parent, false);
        }
        TextView tvId = convertView.findViewById(R.id.id_quiz);
        TextView tvScore = convertView.findViewById(R.id.text_score_quizz);
        TextView tvNote = convertView.findViewById(R.id.text_note_quizz);

        String idValue = "# " + String.valueOf(display.getIdQuizz());
        String scoreValue = String.valueOf(display.getScore());
        String noteValue = String.valueOf(display.getNote());





        return convertView;

    }



}
