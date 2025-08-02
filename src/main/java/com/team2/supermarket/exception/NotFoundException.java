package com.team2.supermarket.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException  {

    @Getter
    @Setter
    HttpStatus status ;

    private Object[] orgs;


    public NotFoundException(String massage){
        super(massage);
        this.status=HttpStatus.NOT_FOUND;
    }

    public NotFoundException(String massage , Object[] orgs){
        super(massage);
        this.orgs=orgs;
    }

    @Override
    public String toString() {
        return "CustomException{" +
                "message='" + getMessage() + '\'' +
                ", status=" + status +
                '}';
    }
}
