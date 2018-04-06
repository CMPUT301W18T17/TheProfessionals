package professional.team17.com.professional;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationController {
    private EmailValidator emailValidator;
    private Pattern pattern;
    private Matcher matcher;
    String PHONE_STRING = "[0-9]{10}";

    public ValidationController() {
        pattern = pattern.compile(PHONE_STRING);
    };

    /**
     * Uses Apache's EmailValidator
     * https://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/routines/EmailValidator.html
     * Compares the string email to the regular expression
     * @param email string email the user entered
     * @return true or false if it is valid or not
     */
    public boolean validateEmail(String email){
        emailValidator = emailValidator.getInstance();
        return emailValidator.isValid(email);
    }

    /**
     * Validates phone number
     * @param phoneNumber - phone number entered by the user
     * @return - boolean - whether it is a valid phone number
     */
    public boolean validatePhoneNumber(String phoneNumber){
        matcher = pattern.matcher(phoneNumber);
        if (phoneNumber.length() >= 10) {
            if (android.util.Patterns.PHONE.matcher(phoneNumber).matches()) {
                return true;
            } else if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
