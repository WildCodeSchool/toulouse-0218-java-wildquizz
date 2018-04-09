package fr.wildcodeschool.wildquizz;

import android.content.Context;
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

public class ResultatsAdapter extends ArrayAdapter<ResultatsModel> {

    public ResultatsAdapter(Context context, ArrayList<ResultatsModel> resultatsModels) {
        super(context, 0, resultatsModels);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.item_resultats, parent, false);

        }
        ResultatsModel resultatsModel = (ResultatsModel) getItem(position);

        TextView nomQCM = (TextView) convertView.findViewById(R.id.text_nom_QCM);
        TextView reponse = (TextView) convertView.findViewById(R.id.text_reponse);
        ImageButton imageLogoValid = (ImageButton) convertView.findViewById(R.id.imgbtn_logo_validation);
        ImageView chevron = (ImageView) convertView.findViewById(R.id.iv_chevron);
        TextView textCorresction = convertView.findViewById(R.id.text_correction);
        TextView reponseCorrecte = (TextView) convertView.findViewById(R.id.text_reponse_correcte);

        nomQCM.setText(resultatsModel.getNomQCM());
        reponse.setText(resultatsModel.getReponse());
        imageLogoValid.setImageResource(resultatsModel.getLogoValidation());
        reponseCorrecte.setText(resultatsModel.getVraiReponse());

        if (resultatsModel.isSuccess()){
            chevron.setVisibility(View.GONE);
            textCorresction.setVisibility(View.GONE);
            reponseCorrecte.setVisibility(View.GONE);
        }
        else {
            chevron.setVisibility(View.VISIBLE);
            textCorresction.setVisibility(View.VISIBLE);
            reponseCorrecte.setVisibility(View.VISIBLE);
        }


        return convertView;
    }
}
