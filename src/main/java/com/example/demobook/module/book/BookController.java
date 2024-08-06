package com.example.demobook.module.book;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.demobook.module.base.Constants;
import com.example.demobook.module.base.dto.request.FilterRequest;
import com.example.demobook.module.base.dto.response.ApiResponse;
import com.example.demobook.module.base.dto.response.PageResponse;
import com.example.demobook.module.book.dto.request.BookInsertRequest;
import com.example.demobook.module.book.dto.request.BookUpdateRequest;
import com.example.demobook.module.book.dto.response.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookController {
    BookService bookService;

    @GetMapping("/books/index")
    ApiResponse<PageResponse<BookResponse>> index(
            @RequestParam(defaultValue = Constants.PAGE_NUMBER_DEFAULT) Integer pageNumber,
            @RequestParam(defaultValue = Constants.PAGE_SIZE_DEFAULT) Integer pageSize,
            @RequestParam(defaultValue = Constants.SORT_DEFAULT) String[] sort) {
        return ApiResponse.<PageResponse<BookResponse>>builder()
                .data(bookService.index(pageNumber, pageSize, sort))
                .build();
    }

    @GetMapping("/books/filter")
    ApiResponse<PageResponse<BookResponse>> filter(
            @RequestBody FilterRequest filterRequest,
            @RequestParam(defaultValue = Constants.PAGE_NUMBER_DEFAULT) Integer pageNumber,
            @RequestParam(defaultValue = Constants.PAGE_SIZE_DEFAULT) Integer pageSize,
            @RequestParam(defaultValue = Constants.SORT_DEFAULT) String[] sort) {
        return ApiResponse.<PageResponse<BookResponse>>builder()
                .data(bookService.filter(filterRequest, pageNumber, pageSize, sort))
                .build();
    }

    @GetMapping("/books/create")
    ApiResponse<BookInsertRequest> create() {
        return ApiResponse.<BookInsertRequest>builder()
                .data(bookService.create())
                .build();
    }

    @GetMapping("/books/bulk/create")
    ApiResponse<List<BookInsertRequest>> bulkCreate() {
        return ApiResponse.<List<BookInsertRequest>>builder()
                .data(bookService.bulkCreate())
                .build();
    }

    @PostMapping("/books")
    ApiResponse<BookInsertResponse> insert(@RequestBody BookInsertRequest bookInsertRequest) {
        return ApiResponse.<BookInsertResponse>builder()
                .data(bookService.insert(bookInsertRequest))
                .build();
    }

    @PostMapping("/books/bulk")
    ApiResponse<List<BookInsertResponse>> bulkInsert(@RequestBody @Valid List<BookInsertRequest> bookInsertRequests) {
        return ApiResponse.<List<BookInsertResponse>>builder()
                .data(bookService.bulkInsert(bookInsertRequests))
                .build();
    }

    @GetMapping("/books/{bookId}/edit")
    ApiResponse<BookUpdateRequest> edit(@PathVariable String bookId) {
        return ApiResponse.<BookUpdateRequest>builder()
                .data(bookService.edit(bookId))
                .build();
    }

    @GetMapping("/books/bulk/edit")
    ApiResponse<List<BookUpdateRequest>> bulkEdit(@RequestBody List<String> bookIds) {
        return ApiResponse.<List<BookUpdateRequest>>builder()
                .data(bookService.bulkEdit(bookIds))
                .build();
    }

    @PutMapping("/books/{bookId}")
    ApiResponse<BookUpdateResponse> update(
            @PathVariable String bookId, @RequestBody BookUpdateRequest bookUpdateRequest) {
        return ApiResponse.<BookUpdateResponse>builder()
                .data(bookService.update(bookId, bookUpdateRequest))
                .build();
    }

    @PutMapping("/books/bulk")
    ApiResponse<List<BookUpdateResponse>> bulkInsertOrUpdate(
            @RequestBody @Valid List<BookUpdateRequest> bookUpdateRequests) {
        return ApiResponse.<List<BookUpdateResponse>>builder()
                .data(bookService.bulkInsertOrUpdate(bookUpdateRequests))
                .build();
    }

    @DeleteMapping("/books/{bookId}")
    ApiResponse<BookDeleteResponse> delete(@PathVariable String bookId) {
        return ApiResponse.<BookDeleteResponse>builder()
                .data(bookService.delete(bookId))
                .build();
    }

    @DeleteMapping("/books/bulk")
    ApiResponse<List<BookDeleteResponse>> bulkDelete(@RequestBody List<String> bookIds) {
        return ApiResponse.<List<BookDeleteResponse>>builder()
                .data(bookService.bulkDelete(bookIds))
                .build();
    }

    @GetMapping("/books/{bookId}")
    ApiResponse<BookShowResponse> show(@PathVariable String bookId) {
        return ApiResponse.<BookShowResponse>builder()
                .data(bookService.show(bookId))
                .build();
    }
}
