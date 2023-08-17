package app.error;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private int statusCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.statusCode = errorCode.getStatus();


    }


}
