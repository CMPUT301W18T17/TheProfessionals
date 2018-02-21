package professional.team17.com.professional;

import java.util.ArrayList;

/**
 * Created by Hailan on 2018-02-20.
 */

public class ReviewList {
    private ArrayList<Review> reviews = new ArrayList<Review>();

    public ReviewList(){
        // Nothing
    }

    public boolean hasReview(Review review){
        return reviews.contains(review);
    }

    public void addReview(Review review){ reviews.add(review);}

    public void deleteReview(Review review){
        reviews.remove(review);
    }


    /**
    *
    *    @returns the avg of all the reviews in list as double
    */
    public double getAvg(){
        double sum = 0.0;
        for (int i=0; i<reviews.size(); i++){
            sum = reviews.get(i).getScore() + sum;
        }
    return sum/reviews.size();
}


    /*
    *
    * @returns the avg of all the reviews in list as string
    */
    public String getAvgString(){
       double sum = getAvg();
       String sumText = String.format("%.2f", sum); //format to two decimals
      return sumText;
 }
}
