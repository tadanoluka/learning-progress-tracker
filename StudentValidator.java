package tracker;

import java.util.regex.Pattern;

public class StudentValidator {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Z]+(?:['-]?[A-Z]+)+$", Pattern.CASE_INSENSITIVE);
    private static final Pattern EMAIL_PATTERN = Pattern.compile("\\w+(?:[-.]*\\w*)*@\\w+\\.\\w+", Pattern.CASE_INSENSITIVE);
    private static final Pattern POINTS_PATTERN = Pattern.compile("^(?:[0-9]+\\s+){3}[0-9]+\\s*?$");

    public static boolean validateName(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }

    public static boolean validateEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean validatePointsFormat(String pointsFormat) {
        return POINTS_PATTERN.matcher(pointsFormat).matches();
    }
}