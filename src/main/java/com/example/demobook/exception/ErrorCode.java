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
    SUPPLIER_INDEX_FAILED(1012, "Retrieving the list of suppliers failed", HttpStatus.INTERNAL_SERVER_ERROR),
    SUPPLIER_CREATE_FAILED(1013, "Supplier creation failed", HttpStatus.INTERNAL_SERVER_ERROR),
    SUPPLIER_INSERT_FAILED(1014, "Supplier insert failed", HttpStatus.INTERNAL_SERVER_ERROR),
    SUPPLIER_EDIT_FAILED(1015, "Supplier editing failed", HttpStatus.INTERNAL_SERVER_ERROR),
    SUPPLIER_UPDATE_FAILED(1016, "Supplier update failed", HttpStatus.INTERNAL_SERVER_ERROR),
    SUPPLIER_DELETE_FAILED(1017, "Deleting the supplier failed", HttpStatus.INTERNAL_SERVER_ERROR),
    SUPPLIER_SHOW_FAILED(1018, "Retrieving supplier information failed", HttpStatus.INTERNAL_SERVER_ERROR),
    SUPPLIER_NOT_FOUND(1020, "Supplier not found", HttpStatus.INTERNAL_SERVER_ERROR),
    SUPPLIER_NAME_EXISTS(1009, "Supplier name already exists", HttpStatus.BAD_REQUEST),
    SUPPLIER_EMAIL_EXISTS(1010, "Email already exists", HttpStatus.BAD_REQUEST),

    // CATEGORY
    CATEGORY_INDEX_FAILED(1012, "Retrieving the list of categories failed", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_CREATE_FAILED(1013, "Category creation failed", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_INSERT_FAILED(1014, "Category insert failed", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_EDIT_FAILED(1015, "Category editing failed", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_UPDATE_FAILED(1016, "Category update failed", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_DELETE_FAILED(1017, "Deleting the category failed", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_SHOW_FAILED(1018, "Retrieving category information failed", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_NOT_FOUND(1020, "Category not found", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_NAME_EXISTS(1009, "App name already exists", HttpStatus.BAD_REQUEST),
    CATEGORY_DRAG_FAILED(1028, "Department drag failed", HttpStatus.INTERNAL_SERVER_ERROR),

    // CATEGORY_PRODUCT
    CATEGORY_PRODUCT_INDEX_FAILED(
            1012, "Retrieving the list of category products failed", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_PRODUCT_CREATE_FAILED(1013, "Category product creation failed", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_PRODUCT_INSERT_FAILED(1014, "Category product insert failed", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_PRODUCT_EDIT_FAILED(1015, "Category product editing failed", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_PRODUCT_UPDATE_FAILED(1016, "Category product update failed", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_PRODUCT_DELETE_FAILED(1017, "Deleting the category product failed", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_PRODUCT_SHOW_FAILED(
            1018, "Retrieving category product information failed", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_PRODUCT_NOT_FOUND(1020, "Category product not found", HttpStatus.INTERNAL_SERVER_ERROR),

    // ATTRIBUTE
    ATTRIBUTE_INDEX_FAILED(1012, "Retrieving the list of attributes failed", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTRIBUTE_CREATE_FAILED(1013, "Attribute creation failed", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTRIBUTE_INSERT_FAILED(1014, "Attribute insert failed", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTRIBUTE_EDIT_FAILED(1015, "Attribute editing failed", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTRIBUTE_UPDATE_FAILED(1016, "Attribute update failed", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTRIBUTE_DELETE_FAILED(1017, "Deleting the attribute failed", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTRIBUTE_SHOW_FAILED(1018, "Retrieving attribute information failed", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTRIBUTE_NOT_FOUND(1020, "Attribute not found", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTRIBUTE_NAME_EXISTS(1009, "Attribute name already exists", HttpStatus.BAD_REQUEST),

    // ATTRIBUTE_OPTION
    ATTRIBUTE_OPTION_INDEX_FAILED(
            1012, "Retrieving the list of attribute options failed", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTRIBUTE_OPTION_CREATE_FAILED(1013, "Attribute option creation failed", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTRIBUTE_OPTION_INSERT_FAILED(1014, "Attribute option insert failed", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTRIBUTE_OPTION_EDIT_FAILED(1015, "Attribute option editing failed", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTRIBUTE_OPTION_UPDATE_FAILED(1016, "Attribute option update failed", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTRIBUTE_OPTION_DELETE_FAILED(1017, "Deleting the attribute option failed", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTRIBUTE_OPTION_SHOW_FAILED(
            1018, "Retrieving attribute option information failed", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTRIBUTE_OPTION_NOT_FOUND(1020, "Attribute option not found", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTRIBUTE_OPTION_NAME_EXISTS(1009, "Attribute option name already exists", HttpStatus.BAD_REQUEST),

    // PRODUCT
    PRODUCT_INDEX_FAILED(1012, "Retrieving the list of products failed", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_CREATE_FAILED(1013, "Product creation failed", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_INSERT_FAILED(1014, "Product insert failed", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_EDIT_FAILED(1015, "Product editing failed", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_UPDATE_FAILED(1016, "Product update failed", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_DELETE_FAILED(1017, "Deleting the product failed", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_SHOW_FAILED(1018, "Retrieving product information failed", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_NOT_FOUND(1020, "Product not found", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_NAME_EXISTS(1009, "Product name already exists", HttpStatus.BAD_REQUEST),

    // VARIANT
    VARIANT_INDEX_FAILED(1012, "Retrieving the list of variants failed", HttpStatus.INTERNAL_SERVER_ERROR),
    VARIANT_CREATE_FAILED(1013, "Variant creation failed", HttpStatus.INTERNAL_SERVER_ERROR),
    VARIANT_INSERT_FAILED(1014, "Variant insert failed", HttpStatus.INTERNAL_SERVER_ERROR),
    VARIANT_EDIT_FAILED(1015, "Variant editing failed", HttpStatus.INTERNAL_SERVER_ERROR),
    VARIANT_UPDATE_FAILED(1016, "Variant update failed", HttpStatus.INTERNAL_SERVER_ERROR),
    VARIANT_DELETE_FAILED(1017, "Deleting the Variant failed", HttpStatus.INTERNAL_SERVER_ERROR),
    VARIANT_SHOW_FAILED(1018, "Retrieving Variant information failed", HttpStatus.INTERNAL_SERVER_ERROR),
    VARIANT_NOT_FOUND(1020, "Variant not found", HttpStatus.INTERNAL_SERVER_ERROR),
    VARIANT_NAME_EXISTS(1009, "Variant name already exists", HttpStatus.BAD_REQUEST),

    // PRODUCT_ATTRIBUTE
    PRODUCT_ATTRIBUTE_INDEX_FAILED(
            1012, "Retrieving the list of product attributes failed", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_ATTRIBUTE_CREATE_FAILED(1013, "Product attribute creation failed", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_ATTRIBUTE_INSERT_FAILED(1014, "Product attribute insert failed", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_ATTRIBUTE_EDIT_FAILED(1015, "Product attribute editing failed", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_ATTRIBUTE_UPDATE_FAILED(1016, "Product attribute update failed", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_ATTRIBUTE_DELETE_FAILED(1017, "Deleting the product attribute failed", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_ATTRIBUTE_SHOW_FAILED(
            1018, "Retrieving product attribute information failed", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_ATTRIBUTE_NOT_FOUND(1020, "Product attribute not found", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_ATTRIBUTE_NAME_EXISTS(1009, "Product attribute name already exists", HttpStatus.BAD_REQUEST),

    // MEDIA
    MEDIA_INDEX_FAILED(1012, "Retrieving the list of medias failed", HttpStatus.INTERNAL_SERVER_ERROR),
    MEDIA_CREATE_FAILED(1013, "Media creation failed", HttpStatus.INTERNAL_SERVER_ERROR),
    MEDIA_INSERT_FAILED(1014, "Media insert failed", HttpStatus.INTERNAL_SERVER_ERROR),
    MEDIA_EDIT_FAILED(1015, "Media editing failed", HttpStatus.INTERNAL_SERVER_ERROR),
    MEDIA_UPDATE_FAILED(1016, "Media update failed", HttpStatus.INTERNAL_SERVER_ERROR),
    MEDIA_DELETE_FAILED(1017, "Deleting the media failed", HttpStatus.INTERNAL_SERVER_ERROR),
    MEDIA_SHOW_FAILED(1018, "Retrieving media information failed", HttpStatus.INTERNAL_SERVER_ERROR),
    MEDIA_NOT_FOUND(1020, "Media not found", HttpStatus.INTERNAL_SERVER_ERROR),
    MEDIA_NAME_EXISTS(1009, "Media name already exists", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
