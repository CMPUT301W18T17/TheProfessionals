/*
 * ReviewList
 *
 * February 20, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */
package professional.team17.com.professional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
        if (this.hasReview(review)){
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
    public float getAvg(){
        float sum = 0;
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
       float sum = getAvg();
        return String.format("%.2f", sum);
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
