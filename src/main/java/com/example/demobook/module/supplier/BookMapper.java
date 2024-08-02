package com.example.demobook.module.supplier;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.demobook.module.supplier.dto.request.BookInsertRequest;
import com.example.demobook.module.supplier.dto.request.BookUpdateRequest;
import com.example.demobook.module.supplier.dto.response.*;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookEntity fromBookInsertRequest(BookInsertRequest bookInsertRequest);

    BookEntity fromBookUpdateRequest(
            BookUpdateRequest bookUpdateRequest, @MappingTarget BookEntity entity);

    BookInsertRequest toBookInsertRequest(BookEntity entity);

    BookInsertResponse toBookInsertResponse(BookEntity entity);

    BookResponse toBookResponse(BookEntity entity);

    BookUpdateRequest toBookUpdateRequest(BookEntity entity);

    BookShowResponse toBookShowResponse(BookEntity entity);

    BookUpdateResponse toBookUpdateResponse(BookEntity entity);

    BookDeleteResponse toBookDeleteResponse(BookEntity entity);
}
