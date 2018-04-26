package fr.wildcodeschool.wildquizz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

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
        ImageView logoUnvalidate = convertView.findViewById(R.id.logo_unvalidated);
        TextView score = convertView.findViewById(R.id.score_value);

        question.setText(resultsModel.getQuestion());
        logoValidate.setImageResource(resultsModel.getValidateLogo(R.drawable.logo_check1));
        logoUnvalidate.setImageResource(resultsModel.getUnvalidateLogo(R.drawable.logo_cancel2));
        score.setText(String.valueOf(resultsModel.getScore()));



        if (resultsModel.isSuccess()){
            logoUnvalidate.setVisibility(View.GONE);
        }
        else {
            logoValidate.setVisibility(View.GONE);
        }


        return convertView;
    }
}
