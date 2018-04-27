package fr.wildcodeschool.wildquizz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by wilder on 09/04/18.
 */

public class ResultsAdapter extends ArrayAdapter<ResultsModel> {

    public ResultsAdapter(Context context, ArrayList<ResultsModel> resultsModels) {
        super(context, 0, resultsModels);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.item_results, parent, false);

        }
        ResultsModel resultsModel = (ResultsModel) getItem(position);

        TextView question =  convertView.findViewById(R.id.question);
        ImageView logoValidate = convertView.findViewById(R.id.logo_validated);
        TextView score = convertView.findViewById(R.id.score_value);

        question.setText(resultsModel.getQuestion());
        score.setText(String.valueOf(resultsModel.getScore()));

        if (resultsModel.isSuccess()){
            Glide.with(getContext()).load(R.drawable.logo_check1).into(logoValidate);
        }
        else {
            Glide.with(getContext()).load(R.drawable.logo_cancel2).into(logoValidate);

        }



        return convertView;
    }
}
