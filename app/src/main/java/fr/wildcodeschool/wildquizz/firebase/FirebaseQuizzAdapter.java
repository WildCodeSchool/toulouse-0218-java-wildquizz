package fr.wildcodeschool.wildquizz.firebase;

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
 * Created by wilder on 19/04/18.
 */

public class FirebaseQuizzAdapter extends ArrayAdapter<FirebaseQuizzModel> {


    public FirebaseQuizzAdapter(@NonNull Context context, @NonNull List<FirebaseQuizzModel> objects) {
        super(context,0, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {

            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_firebase_quizz, parent, false);
        }
        FirebaseQuizzModel firebaseQuizzModel = getItem(position);
        TextView id = convertView.findViewById(R.id.quizz_id);
        id.setText(firebaseQuizzModel.getId());
        return convertView;
    }

}
