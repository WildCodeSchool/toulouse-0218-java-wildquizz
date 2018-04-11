package fr.wildcodeschool.wildquizz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wilder on 04/04/18.
 */

public class QcmAdapter extends ArrayAdapter {

    public QcmAdapter(@NonNull Context context, int resource, @NonNull ArrayList<QcmModel> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qcm, parent, false);
        }
        TextView tvName = convertView.findViewById(R.id.text_qcm_number);

        QcmModel qcmModel = (QcmModel) getItem(position);
        if (qcmModel != null) {
            tvName.setText(qcmModel.getName());
        }

        return convertView;
    }
}
