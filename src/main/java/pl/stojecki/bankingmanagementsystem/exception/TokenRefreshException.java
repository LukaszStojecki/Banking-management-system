package pl.stojecki.bankingmanagementsystem.exception;


public class TokenRefreshException extends RuntimeException{

    public TokenRefreshException(String token, String message) {
        super(String.format("Couldn't refresh token for [%s]: [%s])", token, message));
    }

}
