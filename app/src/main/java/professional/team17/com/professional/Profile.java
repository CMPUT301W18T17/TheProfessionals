package professional.team17.com.professional;

/**
 * Created by ag on 2018-02-22.
 */

public class Profile {

    private String userName;

    public Profile(String userName){
        this.userName = userName;
    }

    public String getUniqueID() {
        return userName;
    }
}
