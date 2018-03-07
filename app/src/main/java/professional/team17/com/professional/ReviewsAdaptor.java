package professional.team17.com.professional;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class ReviewsAdaptor extends ArrayAdapter {
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private ReviewList reviewList;

    public ReviewsAdaptor(Context context, int resource, ReviewList reviewList) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.reviewList = reviewList;
    }

    public int getCount() {
        return reviewList.getReviews().size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = layoutInflater.inflate(layoutResource, parent, false);
        }

        TextView subTitle = (TextView) convertView.findViewById(R.id.subTitle);
        TextView subProfile = (TextView) convertView.findViewById(R.id.subProfile);
        RatingBar subRatingBar = (RatingBar) convertView.findViewById(R.id.subRatingBar);
        TextView subComment = (TextView) convertView.findViewById(R.id.subComment);

        Review currentReview = reviewList.getReviews().get(position);

        subTitle.setText(currentReview.getReviewTitle());
        subProfile.setText(currentReview.getProfileName());
        double score = currentReview.getScore();
        float scoreInFloat = (float)score;
        subRatingBar.setRating(scoreInFloat);
        subComment.setText(currentReview.getComment());

        return convertView;

    }
}
