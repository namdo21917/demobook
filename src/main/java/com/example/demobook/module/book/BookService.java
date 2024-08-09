package com.example.demobook.module.book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demobook.exception.AppException;
import com.example.demobook.exception.ErrorCode;
import com.example.demobook.module.base.AuditBase;
import com.example.demobook.module.base.BaseService;
import com.example.demobook.module.base.CustomSpecification;
import com.example.demobook.module.base.dto.request.FilterRequest;
import com.example.demobook.module.base.dto.response.PageResponse;
import com.example.demobook.module.base.enums.Action;
import com.example.demobook.module.base.util.SortUtils;
import com.example.demobook.module.book.dto.request.BookInsertRequest;
import com.example.demobook.module.book.dto.request.BookUpdateRequest;
import com.example.demobook.module.book.dto.response.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookService extends BaseService {
    BookRepository bookRepository;
    BookMapper bookMapper;

    //   @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<BookResponse> index(Integer pageNumber, Integer pageSize, String[] sort) {

        List<Sort.Order> sortOrders = SortUtils.getSortOrders(sort);

        try {
            Page<BookEntity> bookEntities = bookRepository.findAll(
                    PageRequest.of(pageNumber <= 0 ? 0 : pageNumber - 1, pageSize, Sort.by(sortOrders)));

            return pageResponse(pageNumber, pageSize, bookEntities);
        } catch (Exception e) {
            throw new AppException(ErrorCode.BOOK_INDEX_FAILED);
        }
    }

    // @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<BookResponse> filter(
            FilterRequest filterRequest, Integer pageNumber, Integer pageSize, String[] sort) {

        List<Class<?>> classes = new ArrayList<>();
        classes.add(BookEntity.class);
        classes.add(AuditBase.class);

        CustomSpecification<BookEntity> customSpecification = new CustomSpecification<>();
        customSpecification.addSearchCriteriaList(classes, filterRequest.getSearchCriteriaList());
        List<Sort.Order> sortOrders = SortUtils.getSortOrders(sort);

        try {
            Page<BookEntity> bookEntities = bookRepository.findAll(
                    customSpecification,
                    PageRequest.of(pageNumber <= 0 ? 0 : pageNumber - 1, pageSize, Sort.by(sortOrders)));

            return pageResponse(pageNumber, pageSize, bookEntities);
        } catch (Exception e) {
            throw new AppException(ErrorCode.BOOK_INDEX_FAILED);
        }
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookInsertRequest create() {
        BookEntity bookEntity = new BookEntity();

        try {

            return bookMapper.toBookInsertRequest(bookEntity);
        } catch (Exception e) {
            throw new AppException(ErrorCode.BOOK_CREATE_FAILED);
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public List<BookInsertRequest> bulkCreate() {

        List<BookInsertRequest> bookInsertRequests = new ArrayList<>();
        bookInsertRequests.add(create());

        return bookInsertRequests;
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookInsertResponse insert(BookInsertRequest bookInsertRequest) {

        try {
            BookEntity bookEntity = bookMapper.fromBookInsertRequest(bookInsertRequest);
            saveAction(bookEntity, Action.INSERT);

            return bookMapper.toBookInsertResponse(bookEntity);
        } catch (Exception e) {
            throw new AppException(ErrorCode.BOOK_INSERT_FAILED);
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public List<BookInsertResponse> bulkInsert(List<BookInsertRequest> bookInsertRequests) {

        try {
            List<BookInsertResponse> bookInsertResponses = new ArrayList<>();
            bookInsertRequests.forEach(e -> bookInsertResponses.add(insert(e)));

            return bookInsertResponses;
        } catch (Exception e) {

            throw new AppException(ErrorCode.BOOK_CREATE_FAILED);
        }
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookUpdateRequest edit(String bookId) {
        BookEntity bookEntity = findById(bookId);

        return bookMapper.toBookUpdateRequest(bookEntity);
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public List<BookUpdateRequest> bulkEdit(List<String> bookIds) {
        try {
            List<BookUpdateRequest> bookUpdateRequests = new ArrayList<>();
            bookIds.forEach(id -> bookUpdateRequests.add(edit(id)));

            return bookUpdateRequests;
        } catch (Exception e) {
            throw new AppException(ErrorCode.BOOK_CREATE_FAILED);
        }
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookUpdateResponse update(String bookId, BookUpdateRequest bookUpdateRequest) {
        BookEntity bookEntity = findById(bookId);

        try {
            bookEntity = bookMapper.fromBookUpdateRequest(bookUpdateRequest, bookEntity);
            saveAction(bookEntity, Action.UPDATE);

            return bookMapper.toBookUpdateResponse(bookEntity);
        } catch (Exception e) {
            throw new AppException(ErrorCode.BOOK_UPDATE_FAILED);
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public BookUpdateResponse insertOrUpdate(String bookId, BookUpdateRequest bookUpdateRequest) {
        BookEntity bookEntity =
                Optional.ofNullable(bookId).map(id -> findById(id)).orElseGet(BookEntity::new);

        return getUpdateResponse(bookUpdateRequest, bookEntity);
    }

    private BookUpdateResponse getUpdateResponse(BookUpdateRequest productUpdateRequest, BookEntity bookEntity) {
        try {
            bookEntity = bookMapper.fromBookUpdateRequest(productUpdateRequest, bookEntity);
            saveAction(bookEntity, Action.UPDATE);

            return bookMapper.toBookUpdateResponse(bookEntity);
        } catch (Exception e) {
            throw new AppException(ErrorCode.BOOK_UPDATE_FAILED);
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public List<BookUpdateResponse> bulkInsertOrUpdate(List<BookUpdateRequest> bookUpdateRequests) {
        try {
            List<BookUpdateResponse> bookUpdateResponses = new ArrayList<>();
            bookUpdateRequests.forEach(e -> bookUpdateResponses.add(insertOrUpdate(e.getId(), e)));

            return bookUpdateResponses;
        } catch (Exception e) {
            throw new AppException(ErrorCode.BOOK_CREATE_FAILED);
        }
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookShowResponse show(String bookId) {
        BookEntity bookEntity = findById(bookId);

        return bookMapper.toBookShowResponse(bookEntity);
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookDeleteResponse delete(String bookId) {
        BookEntity bookEntity = findById(bookId);

        try {
            BookDeleteResponse bookDeleteResponse = bookMapper.toBookDeleteResponse(bookEntity);

            bookRepository.delete(bookEntity);

            return bookDeleteResponse;
        } catch (Exception e) {
            throw new AppException(ErrorCode.BOOK_DELETE_FAILED);
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public List<BookDeleteResponse> bulkDelete(List<String> bookIds) {

        List<BookDeleteResponse> bookDeleteRespons = new ArrayList<>();
        bookIds.forEach(bookId -> bookDeleteRespons.add(delete(bookId)));

        return bookDeleteRespons;
    }

    public BookEntity findById(String bookId) {

        BookEntity bookEntity =
                bookRepository.findById(bookId).orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
        return bookEntity;
    }

    public void saveAction(BookEntity bookEntity, Action action) {
        if (action == Action.INSERT) {
            try {
                bookRepository.save(bookEntity);
            } catch (Exception e) {
                throw new AppException(ErrorCode.BOOK_INSERT_FAILED);
            }
        } else if (action == Action.UPDATE) {
            try {
                bookRepository.save(bookEntity);
            } catch (Exception e) {
                throw new AppException(ErrorCode.BOOK_UPDATE_FAILED);
            }
        }
    }

    public PageResponse<BookResponse> pageResponse(
            Integer pageNumber, Integer pageSize, Page<BookEntity> bookEntities) {

        PageResponse<BookResponse> pageResponse = new PageResponse<>();
        pageResponse.setPageNumber(pageNumber);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalPages(bookEntities.getTotalPages());
        pageResponse.setTotalElements(bookEntities.getTotalElements());
        pageResponse.setRecords(bookEntities.getContent().stream()
                .map(bookMapper::toBookResponse)
                .toList());

        return pageResponse;
    }
}
