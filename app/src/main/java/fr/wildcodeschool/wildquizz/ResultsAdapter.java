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

        TextView question =  convertView.findViewById(R.id.text_name_QCM);
        TextView answer =  convertView.findViewById(R.id.text_answer);
        ImageView logoValidate = convertView.findViewById(R.id.logo_validated);
        ImageView logoUnvalidate = convertView.findViewById(R.id.logo_unvalidated);
        ImageView arrow = convertView.findViewById(R.id.iv_arrow);
        TextView checkText = convertView.findViewById(R.id.check_text);
        TextView goodAnswer = convertView.findViewById(R.id.text_good_answer);

        question.setText(resultsModel.getQuestion());
        answer.setText(resultsModel.getAnswer());
        logoValidate.setImageResource(resultsModel.getValidateLogo());
        logoUnvalidate.setImageResource(resultsModel.getValidateLogo());

        goodAnswer.setText(resultsModel.getGoodAnswer());


        if (resultsModel.isSuccess()){
            arrow.setVisibility(View.GONE);
            checkText.setVisibility(View.GONE);
            goodAnswer.setVisibility(View.GONE);
            logoUnvalidate.setVisibility(View.GONE);
        }
        else {
            arrow.setVisibility(View.VISIBLE);
            checkText.setVisibility(View.VISIBLE);
            goodAnswer.setVisibility(View.VISIBLE);
            logoValidate.setVisibility(View.GONE);
        }


        return convertView;
    }
}
