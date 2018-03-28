package professional.team17.com.professional;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddReviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddReviewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ConstraintLayout reviewLayout;
    private RatingBar rating;
    private EditText commentBox;

    private int score;
    private String comment;
    private String profile;
    private String reviewer;

    private OnFragmentInteractionListener mListener;

    public AddReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddReviewFragment newInstance(String param1, String param2) {
        AddReviewFragment fragment = new AddReviewFragment();
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

        profile = getArguments().getString("name");

        SharedPreferences sharedpreferences = getContext().getSharedPreferences("MyPref",
                Context.MODE_PRIVATE);
        reviewer = sharedpreferences.getString("username", "error");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        profile = getArguments().getString("name");
        return inflater.inflate(R.layout.fragment_add_review, container, false);
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    public void seeReviewBox(View v){
        reviewLayout = v.findViewById(R.id.reviewBox);
        reviewLayout.setVisibility(v.VISIBLE);

    }

    //TODO: Set method to close fragment
    public void closePrompt(View v){

    }

    public void onSubmit(View v){
        rating = v.findViewById(R.id.rating);
        commentBox =v.findViewById(R.id.commentBox);

        score = rating.getNumStars();

        if ( commentBox.getText().length() != 0) {
            comment = commentBox.getText().toString();
        }
        
        Review review = new Review(score, profile, comment, reviewer);
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
