package GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PasswordValidator {
    private final boolean atLeastOneNumber;
    private final boolean atLeastOneSpecialChar;
    private final boolean atLeast8Chars;

    private static final Set<Character> caratteriSpeciali = new HashSet<>(Arrays.asList(
            '@', '!', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '-', '='
    ));
    public PasswordValidator(String password) {
        if (password == null) {
            this.atLeast8Chars = false;
            this.atLeastOneNumber = false;
            this.atLeastOneSpecialChar = false;
            return;
        }
        this.atLeast8Chars = password.length() >= 8;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (caratteriSpeciali.contains(c)) {
                hasSpecial = true;
            }
            if (hasNumber && hasSpecial) {
                break;
            }
        }
        this.atLeastOneNumber = hasNumber;
        this.atLeastOneSpecialChar = hasSpecial;
    }
    public boolean isValid() {
        return atLeastOneNumber && atLeastOneSpecialChar && atLeast8Chars;
    }

    public String getErrorMessage() {
        ArrayList<String> errors = new ArrayList<String>();
        if (!atLeast8Chars) errors.add("almeno 8 caratteri");
        if (!atLeastOneNumber) errors.add("almeno un numero");
        if (!atLeastOneSpecialChar) errors.add("almeno un carattere speciale");
        if (errors.isEmpty()) return "";
        return "La password deve contenere: " + String.join(", ", errors) + ".";
    }
}