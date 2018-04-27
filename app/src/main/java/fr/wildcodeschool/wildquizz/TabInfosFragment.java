package fr.wildcodeschool.wildquizz;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabInfosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabInfosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabInfosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private ImageView mImageProfile;
    private TextView mScoreValueProfile;
    private String mUid;
    public TabInfosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabInfosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabInfosFragment newInstance(String param1, String param2) {
        TabInfosFragment fragment = new TabInfosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab1, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mImageProfile = getView().findViewById(R.id.iv_profile);
        mScoreValueProfile = getView().findViewById(R.id.tv_score_profile);
        //Affichage du profil dans la nav bar :
        mDatabase = FirebaseDatabase.getInstance();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //TODO : faire pareil pour le pseudo
        DatabaseReference pathID = mDatabase.getReference("Users").child(mUid);

        pathID.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if ((dataSnapshot.child("avatar").getValue() != null)) {
                    String url = dataSnapshot.child("avatar").getValue(String.class);
                    Glide.with(TabInfosFragment.this).load(url).apply(RequestOptions.circleCropTransform()).into(mImageProfile);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        final ImageView mMedalBronze = getView().findViewById(R.id.iv_medal2);
        final ImageView mMedalRed = getView().findViewById(R.id.iv_medal1);
        final ImageView mMedalSilver = getView().findViewById(R.id.iv_medal3);
        final ImageView mMedalGold = getView().findViewById(R.id.iv_medal4);


        //Affichage du score dans la ratingBar et médailles :
        DatabaseReference scoreId = mDatabase.getReference("Users").child(mUid);
        scoreId.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);

                int scoreUser = userModel.getScore();
                mScoreValueProfile.setText(String.format(getString(R.string.scoretext), String.valueOf(scoreUser)));
                int nbQcm = userModel.getNbQcm();
                if (nbQcm > 0) {
                    float scoreUserFloat = scoreUser / nbQcm;
                    RatingBar ratingBar = getView().findViewById(R.id.rating_bar);
                    ratingBar.setFocusable(false);
                    ratingBar.setRating(scoreUserFloat);
                }

                switch (ScoreClass.getMedal(scoreUser)) {
                    case 1:
                        mMedalRed.setAlpha(1.0f);
                        break;

                    case 2:
                        mMedalRed.setAlpha(1.0f);
                        mMedalBronze.setAlpha(1.0f);
                        break;

                    case 3:
                        mMedalRed.setAlpha(1.0f);
                        mMedalBronze.setAlpha(1.0f);
                        mMedalSilver.setAlpha(1.0f);
                        break;

                    case 4:
                        mMedalRed.setAlpha(1.0f);
                        mMedalBronze.setAlpha(1.0f);
                        mMedalSilver.setAlpha(1.0f);
                        mMedalGold.setAlpha(1.0f);
                        break;


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
