package app.error;


 public abstract class CustomException extends RuntimeException {
  protected CustomException(String message) {
    super(message);
  }

  public abstract int getStatusCode();
}
