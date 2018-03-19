package professional.team17.com.professional;

/**
 * A mock task object for testing purposes.
 */

public class MockTask extends Task {

    /**
     * Constructs a mock task object.
     * @param profileName Testing username
     * @param name Task name
     * @param description Task description
     * @param location Task location
     * @param date Task date
     */
    public MockTask(String profileName, String name, String description, String location, String date){
        super(profileName, name, description, location, date);
    }
}
