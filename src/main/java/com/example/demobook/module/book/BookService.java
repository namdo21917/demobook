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
        log.info("[BookService.index]  pageNumber {} pageSize {} sort {}", pageNumber, pageSize, sort);

        List<Sort.Order> sortOrders = SortUtils.getSortOrders(sort);

        try {
            Page<BookEntity> bookEntities = bookRepository.findAll(
                    PageRequest.of(pageNumber <= 0 ? 0 : pageNumber - 1, pageSize, Sort.by(sortOrders)));

            PageResponse<BookResponse> pageResponse = pageResponse(pageNumber, pageSize, bookEntities);
            log.info("[BookService.index] pageResponse {}", pageResponse);
            return pageResponse;
        } catch (Exception e) {
            log.info("[BookService.index] Exception {}", e.getMessage());
            throw new AppException(ErrorCode.BOOK_INDEX_FAILED);
        }
    }

    // @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<BookResponse> filter(
            FilterRequest filterRequest, Integer pageNumber, Integer pageSize, String[] sort) {
        log.info(
                "[BookService.filter]  filterRequest {} pageNumber {} pageSize {} sort {}",
                filterRequest,
                pageNumber,
                pageSize,
                sort);

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

            PageResponse<BookResponse> pageResponse = pageResponse(pageNumber, pageSize, bookEntities);
            log.info("[BookService.filter] pageResponse {}", pageResponse);
            return pageResponse;
        } catch (Exception e) {
            log.info("[BookService.filter] Exception {}", e.getMessage());
            throw new AppException(ErrorCode.BOOK_INDEX_FAILED);
        }
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookInsertRequest create() {
        log.info("[BookService.create] ");
        BookEntity bookEntity = new BookEntity();

        try {
            BookInsertRequest bookInsertRequest = bookMapper.toBookInsertRequest(bookEntity);

            log.info("[BookService.create] bookInsertRequest {}", bookInsertRequest);
            return bookInsertRequest;
        } catch (Exception e) {
            log.info("[BookService.create] Exception {}", e.getMessage());
            throw new AppException(ErrorCode.BOOK_CREATE_FAILED);
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public List<BookInsertRequest> bulkCreate() {
        log.info("[BookService.bulkCreate] ");

        List<BookInsertRequest> bookInsertRequests = new ArrayList<>();
        bookInsertRequests.add(create());

        log.info("[BookService.bulkCreate] bookInsertRequests {}", bookInsertRequests);

        return bookInsertRequests;
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookInsertResponse insert(BookInsertRequest bookInsertRequest) {
        log.info("[BookService.insert]  bookInsertRequest {}", bookInsertRequest);

        try {
            BookEntity bookEntity = bookMapper.fromBookInsertRequest(bookInsertRequest);
            saveAction(bookEntity, Action.INSERT);

            BookInsertResponse bookInsertResponse = bookMapper.toBookInsertResponse(bookEntity);
            log.info("[BookService.insert] bookInsertResponse {}", bookInsertResponse);
            return bookInsertResponse;
        } catch (Exception e) {
            log.info("[BookService.insert] Exception {}", e.getMessage());
            throw new AppException(ErrorCode.BOOK_INSERT_FAILED);
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public List<BookInsertResponse> bulkInsert(List<BookInsertRequest> bookInsertRequests) {
        log.info("[BookService.bulkInsert]  bookInsertRequests {}", bookInsertRequests);

        try {
            List<BookInsertResponse> bookInsertRespons = new ArrayList<>();
            bookInsertRequests.forEach(e -> bookInsertRespons.add(insert(e)));

            log.info("[BookService.bulkInsert] bookInsertResponses {}", bookInsertRespons);

            return bookInsertRespons;
        } catch (Exception e) {
            log.info("[BookService.bulkInsert] Exception {}", e.getMessage());

            throw new AppException(ErrorCode.BOOK_CREATE_FAILED);
        }
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookUpdateRequest edit(String bookId) {
        log.info("[BookService.edit]  bookId {}", bookId);
        BookEntity bookEntity = findById(bookId);

        BookUpdateRequest bookUpdateRequest = bookMapper.toBookUpdateRequest(bookEntity);
        log.info("[BookService.edit] bookUpdateRequest {}", bookUpdateRequest);
        return bookUpdateRequest;
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public List<BookUpdateRequest> bulkEdit(List<String> bookIds) {
        log.info("[BookService.bulkEdit]  bookIds {}", bookIds);

        try {
            List<BookUpdateRequest> bookUpdateRequests = new ArrayList<>();
            bookIds.forEach(id -> bookUpdateRequests.add(edit(id)));

            log.info("[BookService.bulkEdit] bookUpdateRequests {}", bookUpdateRequests);

            return bookUpdateRequests;
        } catch (Exception e) {
            log.info("[BookService.bulkEdit] Exception {}", e.getMessage());

            throw new AppException(ErrorCode.BOOK_CREATE_FAILED);
        }
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookUpdateResponse update(String bookId, BookUpdateRequest bookUpdateRequest) {
        log.info("[BookService.update]  bookId {} bookUpdateRequest {}", bookId, bookUpdateRequest);

        BookEntity bookEntity = findById(bookId);

        try {
            bookEntity = bookMapper.fromBookUpdateRequest(bookUpdateRequest, bookEntity);
            saveAction(bookEntity, Action.UPDATE);

            BookUpdateResponse bookUpdateResponse = bookMapper.toBookUpdateResponse(bookEntity);
            log.info("[BookService.update] bookUpdateResponse {}", bookUpdateResponse);
            return bookUpdateResponse;
        } catch (Exception e) {
            log.info("[BookService.update] Exception {}", e.getMessage());
            throw new AppException(ErrorCode.BOOK_UPDATE_FAILED);
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public BookUpdateResponse insertOrUpdate(String bookId, BookUpdateRequest bookUpdateRequest) {
        log.info("[BookService.insertOrUpdate]  bookId {} bookUpdateRequest {}", bookId, bookUpdateRequest);

        BookEntity bookEntity =
                Optional.ofNullable(bookId).map(id -> findById(id)).orElseGet(BookEntity::new);

        return getUpdateResponse(bookUpdateRequest, bookEntity);
    }

    private BookUpdateResponse getUpdateResponse(BookUpdateRequest productUpdateRequest, BookEntity bookEntity) {
        try {
            bookEntity = bookMapper.fromBookUpdateRequest(productUpdateRequest, bookEntity);
            saveAction(bookEntity, Action.UPDATE);

            BookUpdateResponse productUpdateResponse = bookMapper.toBookUpdateResponse(bookEntity);

            log.info("[BookService.update] productUpdateResponse {}", productUpdateResponse);

            return productUpdateResponse;
        } catch (Exception e) {
            log.info("[BookService.update] Exception {}", e.getMessage());

            throw new AppException(ErrorCode.BOOK_UPDATE_FAILED);
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public List<BookUpdateResponse> bulkInsertOrUpdate(List<BookUpdateRequest> bookUpdateRequests) {
        log.info("[BookService.bulkInsertOrUpdate]  bookUpdateRequests {}", bookUpdateRequests);

        try {
            List<BookUpdateResponse> bookUpdateResponses = new ArrayList<>();
            bookUpdateRequests.forEach(e -> bookUpdateResponses.add(insertOrUpdate(e.getId(), e)));

            log.info("[BookService.bulkInsertOrUpdate] bookUpdateResponses {}", bookUpdateResponses);

            return bookUpdateResponses;
        } catch (Exception e) {
            throw new AppException(ErrorCode.BOOK_CREATE_FAILED);
        }
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookShowResponse show(String bookId) {
        log.info("[BookService.show]  bookId {}", bookId);
        BookEntity bookEntity = findById(bookId);

        BookShowResponse bookShowResponse = bookMapper.toBookShowResponse(bookEntity);
        log.info("[BookService.show] bookShowResponse {}", bookShowResponse);
        return bookShowResponse;
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookDeleteResponse delete(String bookId) {
        log.info("[BookService.delete]  bookId {}", bookId);
        BookEntity bookEntity = findById(bookId);

        try {
            BookDeleteResponse bookDeleteResponse = bookMapper.toBookDeleteResponse(bookEntity);

            bookRepository.delete(bookEntity);

            log.info("[BookService.delete] bookDeleteResponse {}", bookDeleteResponse);
            return bookDeleteResponse;
        } catch (Exception e) {
            log.info("[BookService.delete] Exception {}", e.getMessage());
            throw new AppException(ErrorCode.BOOK_DELETE_FAILED);
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public List<BookDeleteResponse> bulkDelete(List<String> bookIds) {
        log.info("[BookService.bulkDelete]  bookIds {}", bookIds);

        List<BookDeleteResponse> bookDeleteRespons = new ArrayList<>();
        bookIds.forEach(bookId -> bookDeleteRespons.add(delete(bookId)));

        log.info("[BookService.bulkDelete] bookDeleteResponses {}", bookDeleteRespons);

        return bookDeleteRespons;
    }

    public BookEntity findById(String bookId) {
        log.info("[BookService.findById] bookId {}", bookId);

        BookEntity bookEntity =
                bookRepository.findById(bookId).orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
        log.info("[BookService.findById] bookEntity {}", bookEntity);
        return bookEntity;
    }

    public void saveAction(BookEntity bookEntity, Action action) {
        log.info("[BookService.saveAction] bookEntity {} action {}", bookEntity, action);
        if (action == Action.INSERT) {
            try {
                bookRepository.save(bookEntity);
                log.info("[BookService.saveAction(INSERT)] bookEntity {}", bookEntity);
            } catch (Exception e) {
                log.info("[BookService.saveAction(INSERT)] Exception {}", e.getMessage());
                throw new AppException(ErrorCode.BOOK_INSERT_FAILED);
            }
        } else if (action == Action.UPDATE) {
            try {
                bookRepository.save(bookEntity);
                log.info("[BookService.saveAction(UPDATE)] bookEntity {}", bookEntity);
            } catch (Exception e) {
                log.info("[BookService.saveAction(UPDATE)] Exception {}", e.getMessage());
                throw new AppException(ErrorCode.BOOK_UPDATE_FAILED);
            }
        }
    }

    public PageResponse<BookResponse> pageResponse(
            Integer pageNumber, Integer pageSize, Page<BookEntity> bookEntities) {
        log.info(
                "[BookService.pageResponse] pageNumber {} pageSize {} bookEntities {}",
                pageNumber,
                pageSize,
                bookEntities);
        PageResponse<BookResponse> pageResponse = new PageResponse<>();
        pageResponse.setPageNumber(pageNumber);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalPages(bookEntities.getTotalPages());
        pageResponse.setTotalElements(bookEntities.getTotalElements());
        pageResponse.setRecords(bookEntities.getContent().stream()
                .map(bookMapper::toBookResponse)
                .toList());

        log.info("[BookService.pageResponse] pageResponse {}", pageResponse);
        return pageResponse;
    }
}
