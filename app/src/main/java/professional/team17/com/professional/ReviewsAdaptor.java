/*
 * ReviewsAdaptor
 *
 * March 12, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */
package professional.team17.com.professional;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * ReviewsAdaptor is used when looking at the profiles of both current user and other users.
 * @version 2.0 Last updated: Mar. 12, 2018
 * @see ReviewList
 */
public class ReviewsAdaptor extends ArrayAdapter {
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private ReviewList reviewList;

    /**
     * Initialize adaptor
     * @param context  - context
     * @param resource - resource
     * @param reviewList - review list of current profile
     */
    public ReviewsAdaptor(Context context, int resource, ReviewList reviewList) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.reviewList = reviewList;
    }

    /**
     * Mandatory getCount method
     * @return number of reviews
     */
    public int getCount() {
        return reviewList.getReviews().size();
    }

    /**
     * Place each review in corresponding (and currently viewable) boxes in listView
     *
     * @param position position of box
     * @param convertView default
     * @param parent default
     * @return - convertView
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = layoutInflater.inflate(layoutResource, parent, false);
        }

//        TextView subTitle = (TextView) convertView.findViewById(R.id.subTitle);
        TextView subProfile = (TextView) convertView.findViewById(R.id.subProfile);
        RatingBar subRatingBar = (RatingBar) convertView.findViewById(R.id.subRatingBar);
        TextView subComment = (TextView) convertView.findViewById(R.id.subComment);

        Review currentReview = reviewList.getReviews().get(position);

//        subTitle.setText(currentReview.getReviewTitle());
        subProfile.setText(currentReview.getProfileName());
        float score = currentReview.getScore();
//        float scoreInFloat = (float)score;
        subRatingBar.setRating(score);
        subComment.setText(currentReview.getComment());

        return convertView;

    }
}
