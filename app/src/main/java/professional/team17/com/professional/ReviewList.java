package professional.team17.com.professional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//TODO implement for project part 5

/**
 * A custom ArrayList class for handling Review objects.
 */
public class ReviewList {
    private ArrayList<Review> reviews = new ArrayList<Review>();

    public ReviewList(){
        // Nothing
    }

    public boolean hasReview(Review review){
        return reviews.contains(review);
    }


    public void addReview(Review review){
        if (this.hasReview(review) == true){
            throw new  IllegalArgumentException("Duplicate Review - cannot be added");
        }
        reviews.add(review);
   }

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



    public ArrayList<Review> getReviews() {
        //TO DO implement using colletions.sort method to create sort by date list
        //see https://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date
        return reviews;
    }


    public boolean isEmpty(){
        return reviews.size()==0;
    }
}
