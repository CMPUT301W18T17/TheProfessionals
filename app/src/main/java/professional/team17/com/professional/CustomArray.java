package professional.team17.com.professional;

import java.util.ArrayList;

/**
 * Created by Richard on 2018/2/21.
 */

public class CustomArray {
    String acceptBid, taskTitle, status, userName, lowestBid, myBid, searchResult, comment;
    int starNumber;
    private ArrayList<String> NotAssignedArray = new ArrayList<String>();
    private ArrayList<String> AssignedArray = new ArrayList<String>();
    private ArrayList<String> SearchResultArray = new ArrayList<String>();
    private ArrayList<String> MyBiddedTaskArray = new ArrayList<String>();
    private ArrayList<String> MyGotTaskArray = new ArrayList<String>();
    private ArrayList<String> UserReviewArray = new ArrayList<String>();

    public void RequesterAdapter(String taskTitle, String status){}
    public void RequesterAdapter(String taskTitle, String status, String userName, int starNumber, String acceptBid){}
    public void ProviderAdapter(String taskTitle, String status, String userName, int starNumber, String lowestBid, String myBid){}
    public void ProviderAdapter(String taskTitle, String status, String userName, int starNumber, String myBid){}
    public void SearchResultAdapter(String searchResult){}
    public void RatingListAdapter(String userName, String comment, int starNumber){}
}
