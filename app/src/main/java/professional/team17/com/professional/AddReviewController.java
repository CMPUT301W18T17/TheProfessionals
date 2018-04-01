package professional.team17.com.professional;


public class AddReviewController {

    private final ServerHelper serverHelper = new ServerHelper();

    private AddReviewController(){}

    public void setReview(String profileName, int rating, String comment, String reviewer) {
        Review review = new Review(rating, reviewer, comment);
        Profile profile = serverHelper.getProfile(profileName);
        ReviewList list = profile.getReviewList();
        list.addReview(review);
        profile.setReviewList(list);

        serverHelper.addProfile(profile);
    }
}
