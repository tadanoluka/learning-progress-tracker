package tracker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String credentialRegex = "(?i)([-'A-Z]+\\s){2,}[-.@A-Z0-9]+";
    public static String firstNameRegex = "(?i)([A-Z]+[-']?)+[A-Z]";
    public static String lastNameRegex = "(?i)((([A-Z]+[-']?)+[A-Z])\\s?)+";
    public static String emailRegex = "(?i)[-A-Z0-9_.]+@\\w+\\.\\w+";
    public static String emailRegex1 = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    public static Pattern credentialPattern = Pattern.compile(credentialRegex);
    public static Pattern firstNamePattern = Pattern.compile(firstNameRegex);
    public static Pattern lastNamePattern = Pattern.compile(lastNameRegex);
    public static Pattern emailPattern = Pattern.compile(emailRegex);

    public static boolean isCredentialsValid(String studentCredentials) {
        Matcher matcher = credentialPattern.matcher(studentCredentials);
        return matcher.matches();
    }

    public static boolean isFirstNameCorrect(String firstName) {
        Matcher matcher = firstNamePattern.matcher(firstName);
        return matcher.matches();
    }

    public static boolean isLastNameCorrect(String lastName) {
        Matcher matcher = lastNamePattern.matcher(lastName);
        return matcher.matches();
    }

    public static boolean isEmailCorrect(String email) {
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }
}
