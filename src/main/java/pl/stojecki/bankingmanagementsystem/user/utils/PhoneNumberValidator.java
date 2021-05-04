package pl.stojecki.bankingmanagementsystem.user.utils;

public class PhoneNumberValidator {

    public static boolean isValid(String phoneNumber) {
        String regex = "\\d{3}-\\d{3}-\\d{3}";
        return phoneNumber.matches(regex);
    }
}
