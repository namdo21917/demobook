package com.example.demobook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),

    // SUPPLIER
    BOOK_INDEX_FAILED(1012, "Retrieving the list of books failed", HttpStatus.INTERNAL_SERVER_ERROR),
    BOOK_CREATE_FAILED(1013, "Book creation failed", HttpStatus.INTERNAL_SERVER_ERROR),
    BOOK_INSERT_FAILED(1014, "Book insert failed", HttpStatus.INTERNAL_SERVER_ERROR),
    BOOK_EDIT_FAILED(1015, "Book editing failed", HttpStatus.INTERNAL_SERVER_ERROR),
    BOOK_UPDATE_FAILED(1016, "Book update failed", HttpStatus.INTERNAL_SERVER_ERROR),
    BOOK_DELETE_FAILED(1017, "Deleting the book failed", HttpStatus.INTERNAL_SERVER_ERROR),
    BOOK_SHOW_FAILED(1018, "Retrieving book information failed", HttpStatus.INTERNAL_SERVER_ERROR),
    BOOK_NOT_FOUND(1020, "Book not found", HttpStatus.INTERNAL_SERVER_ERROR),
    BOOK_NAME_EXISTS(1009, "Book name already exists", HttpStatus.BAD_REQUEST),
    BOOK_EMAIL_EXISTS(1010, "Email already exists", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
