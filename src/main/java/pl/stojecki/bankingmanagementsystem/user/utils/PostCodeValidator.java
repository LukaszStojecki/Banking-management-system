package pl.stojecki.bankingmanagementsystem.user.utils;

public class PostCodeValidator {

    public static boolean PostCodeValidator(String postCode) {
        String pattern = "[\\d]{2}-[\\d]{3}";
        return postCode.matches(pattern);
    }
}
