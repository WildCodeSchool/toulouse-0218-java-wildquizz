package fr.wildcodeschool.wildquizz;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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

        TextView nameQcm =  convertView.findViewById(R.id.text_name_QCM);
        TextView answer =  convertView.findViewById(R.id.text_answer);
        ImageButton logoValidate = convertView.findViewById(R.id.imgbtn_validate_logo);
        ImageView arrow = convertView.findViewById(R.id.iv_arrow);
        TextView checkText = convertView.findViewById(R.id.check_text);
        TextView goodAnswer = convertView.findViewById(R.id.text_good_answer);

        nameQcm.setText(resultsModel.getNameQcm());
        answer.setText(resultsModel.getAnswer());
        logoValidate.setImageResource(resultsModel.getValidateLogo());
        goodAnswer.setText(resultsModel.getGoodAnswer());


        if (resultsModel.isSuccess()){
            arrow.setVisibility(View.GONE);
            checkText.setVisibility(View.GONE);
            goodAnswer.setVisibility(View.GONE);
        }
        else {
            arrow.setVisibility(View.VISIBLE);
            checkText.setVisibility(View.VISIBLE);
            goodAnswer.setVisibility(View.VISIBLE);
        }


        return convertView;
    }
}
