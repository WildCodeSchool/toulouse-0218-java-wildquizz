package fr.wildcodeschool.wildquizz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.wildcodeschool.wildquizz.R;

/**
 * Created by wilder on 23/04/18.
 */

public class ListQuizzAdapter extends ArrayAdapter<QuizzModel> {


    public ListQuizzAdapter(@NonNull Context context, @NonNull List<QuizzModel> objects) {
        super(context,0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_list_quizz, parent, false);
        }

        QuizzModel QuizzModel = getItem(position);
        TextView id = convertView.findViewById(R.id.txt_id);
        id.setText(QuizzModel.getId());

        return convertView;
    }


}
