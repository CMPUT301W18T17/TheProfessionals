package professional.team17.com.professional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Task implements Serializable{
    private String profileName;     /* person who requested the task */
    private String name;
    private String description;
    private String location;
    private Date date;
    private String uniqueID;
    private ArrayList<String> photos;
    private BidList bids;
    private String status;

    public Task(String profileName, String name, String description, String location, Date Date, String ID) {
        this.profileName = profileName;
        this.name = name;
        this.description = description;
        this.location = location;
        this.date = null;
        this.uniqueID = ID;
        this.status = "Requested";
        this.bids = new BidList();
    }


    public Task(String profileName, String name, String description, String location, String ID) {
        this.profileName = profileName;
        this.name = name;
        this.description = description;
        this.location = location;
        this.date = null;
        this.uniqueID = ID;
        this.status = "Requested";
        this.bids = new BidList();
    }



    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String ID) {
        this.uniqueID = ID;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    public void addPhoto(String photo){
        this.photos.add(photo);
    }

    public void removePhoto(String photo){
        this.photos.remove(photo);
    }

    public BidList getBids() {
        return bids;
    }


    /**
     *
     * @param bid
     */
    public void addBid(Bid bid){
        //if requested, update to Bidded
        if (isRequested()){
            setBidded();
        }
       this.bids.addBids(bid);
    }

    public void removeBid(Bid bid){
        this.bids.delete(bid);
    }

    public void clearBids(){
        this.bids.clear();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Set status to bidded, with better control of words
     */
    public void setBidded(){
        this.status = "Bidded";
    }

    /**
     *
     * @return boolean value on whether status is Assigned
     */
    public boolean isAssigned() {
        return this.status=="Assigned";
    }

    /**
     *
     * @return boolean value on whether status is Requested
     */
    public boolean isRequested() {
        return this.status=="Requested";
    }

    /**
     *
     * @return boolean value on whether status is Bidded
     */
    public boolean isBidded() {
        return this.status=="Bidded";
    }

    /**
     *
     * @return boolean value on whether status is Done
     */
    public boolean isDone() {
        return this.status=="Done";
    }

    public String toString(){
        return this.name;
    }

}
