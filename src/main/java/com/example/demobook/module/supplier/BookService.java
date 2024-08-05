package com.example.demobook.module.supplier;

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
import com.example.demobook.module.supplier.dto.request.BookInsertRequest;
import com.example.demobook.module.supplier.dto.request.BookUpdateRequest;
import com.example.demobook.module.supplier.dto.response.*;

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
            Page<BookEntity> supplierEntities = bookRepository.findAll(
                    PageRequest.of(pageNumber <= 0 ? 0 : pageNumber - 1, pageSize, Sort.by(sortOrders)));

            PageResponse<BookResponse> pageResponse = pageResponse(pageNumber, pageSize, supplierEntities);
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
            Page<BookEntity> supplierEntities = bookRepository.findAll(
                    customSpecification,
                    PageRequest.of(pageNumber <= 0 ? 0 : pageNumber - 1, pageSize, Sort.by(sortOrders)));

            PageResponse<BookResponse> pageResponse = pageResponse(pageNumber, pageSize, supplierEntities);
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

            log.info("[BookService.create] supplierInsertRequest {}", bookInsertRequest);
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

        log.info("[BookService.bulkCreate] supplierInsertRequests {}", bookInsertRequests);

        return bookInsertRequests;
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookInsertResponse insert(BookInsertRequest bookInsertRequest) {
        log.info("[BookService.insert]  supplierInsertRequest {}", bookInsertRequest);

        try {
            BookEntity bookEntity = bookMapper.fromBookInsertRequest(bookInsertRequest);
            saveAction(bookEntity, Action.INSERT);

            BookInsertResponse bookInsertResponse = bookMapper.toBookInsertResponse(bookEntity);
            log.info("[BookService.insert] supplierInsertResponse {}", bookInsertResponse);
            return bookInsertResponse;
        } catch (Exception e) {
            log.info("[BookService.insert] Exception {}", e.getMessage());
            throw new AppException(ErrorCode.BOOK_INSERT_FAILED);
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public List<BookInsertResponse> bulkInsert(List<BookInsertRequest> bookInsertRequests) {
        log.info("[BookService.bulkInsert]  supplierInsertRequests {}", bookInsertRequests);

        try {
            List<BookInsertResponse> bookInsertRespons = new ArrayList<>();
            bookInsertRequests.forEach(e -> bookInsertRespons.add(insert(e)));

            log.info("[BookService.bulkInsert] supplierInsertResponses {}", bookInsertRespons);

            return bookInsertRespons;
        } catch (Exception e) {
            log.info("[BookService.bulkInsert] Exception {}", e.getMessage());

            throw new AppException(ErrorCode.BOOK_CREATE_FAILED);
        }
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookUpdateRequest edit(String supplierId) {
        log.info("[BookService.edit]  supplierId {}", supplierId);
        BookEntity bookEntity = findById(supplierId);

        BookUpdateRequest bookUpdateRequest = bookMapper.toBookUpdateRequest(bookEntity);
        log.info("[BookService.edit] supplierUpdateRequest {}", bookUpdateRequest);
        return bookUpdateRequest;
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public List<BookUpdateRequest> bulkEdit(List<String> supplierIds) {
        log.info("[BookService.bulkEdit]  supplierIds {}", supplierIds);

        try {
            List<BookUpdateRequest> bookUpdateRequests = new ArrayList<>();
            supplierIds.forEach(id -> bookUpdateRequests.add(edit(id)));

            log.info("[BookService.bulkEdit] supplierUpdateRequests {}", bookUpdateRequests);

            return bookUpdateRequests;
        } catch (Exception e) {
            log.info("[BookService.bulkEdit] Exception {}", e.getMessage());

            throw new AppException(ErrorCode.BOOK_CREATE_FAILED);
        }
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookUpdateResponse update(String supplierId, BookUpdateRequest bookUpdateRequest) {
        log.info("[BookService.update]  supplierId {} supplierUpdateRequest {}", supplierId, bookUpdateRequest);

        BookEntity bookEntity = findById(supplierId);

        try {
            bookEntity = bookMapper.fromBookUpdateRequest(bookUpdateRequest, bookEntity);
            saveAction(bookEntity, Action.UPDATE);

            BookUpdateResponse bookUpdateResponse = bookMapper.toBookUpdateResponse(bookEntity);
            log.info("[BookService.update] supplierUpdateResponse {}", bookUpdateResponse);
            return bookUpdateResponse;
        } catch (Exception e) {
            log.info("[BookService.update] Exception {}", e.getMessage());
            throw new AppException(ErrorCode.BOOK_UPDATE_FAILED);
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public BookUpdateResponse insertOrUpdate(String supplierId, BookUpdateRequest bookUpdateRequest) {
        log.info("[BookService.insertOrUpdate]  supplierId {} supplierUpdateRequest {}", supplierId, bookUpdateRequest);

        BookEntity bookEntity =
                Optional.ofNullable(supplierId).map(id -> findById(id)).orElseGet(BookEntity::new);

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
        log.info("[BookService.bulkInsertOrUpdate]  supplierUpdateRequests {}", bookUpdateRequests);

        try {
            List<BookUpdateResponse> bookUpdateRespons = new ArrayList<>();
            bookUpdateRequests.forEach(e -> bookUpdateRespons.add(insertOrUpdate(e.getId(), e)));

            log.info("[BookService.bulkInsertOrUpdate] supplierUpdateResponses {}", bookUpdateRespons);

            return bookUpdateRespons;
        } catch (Exception e) {
            throw new AppException(ErrorCode.BOOK_CREATE_FAILED);
        }
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookShowResponse show(String supplierId) {
        log.info("[BookService.show]  supplierId {}", supplierId);
        BookEntity bookEntity = findById(supplierId);

        BookShowResponse bookShowResponse = bookMapper.toBookShowResponse(bookEntity);
        log.info("[BookService.show] supplierShowResponse {}", bookShowResponse);
        return bookShowResponse;
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookDeleteResponse delete(String supplierId) {
        log.info("[BookService.delete]  supplierId {}", supplierId);
        BookEntity bookEntity = findById(supplierId);

        try {
            BookDeleteResponse bookDeleteResponse = bookMapper.toBookDeleteResponse(bookEntity);

            bookRepository.delete(bookEntity);

            log.info("[BookService.delete] supplierDeleteResponse {}", bookDeleteResponse);
            return bookDeleteResponse;
        } catch (Exception e) {
            log.info("[BookService.delete] Exception {}", e.getMessage());
            throw new AppException(ErrorCode.BOOK_DELETE_FAILED);
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public List<BookDeleteResponse> bulkDelete(List<String> supplierIds) {
        log.info("[BookService.bulkDelete]  supplierIds {}", supplierIds);

        List<BookDeleteResponse> bookDeleteRespons = new ArrayList<>();
        supplierIds.forEach(supplierId -> bookDeleteRespons.add(delete(supplierId)));

        log.info("[BookService.bulkDelete] supplierDeleteResponses {}", bookDeleteRespons);

        return bookDeleteRespons;
    }

    public BookEntity findById(String supplierId) {
        log.info("[BookService.findById] supplierId {}", supplierId);

        BookEntity bookEntity =
                bookRepository.findById(supplierId).orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
        log.info("[BookService.findById] supplierEntity {}", bookEntity);
        return bookEntity;
    }

    public void saveAction(BookEntity bookEntity, Action action) {
        log.info("[BookService.saveAction] supplierEntity {} action {}", bookEntity, action);
        if (action == Action.INSERT) {
            try {
                bookRepository.save(bookEntity);
                log.info("[BookService.saveAction(INSERT)] supplierEntity {}", bookEntity);
            } catch (Exception e) {
                log.info("[BookService.saveAction(INSERT)] Exception {}", e.getMessage());
                throw new AppException(ErrorCode.BOOK_INSERT_FAILED);
            }
        } else if (action == Action.UPDATE) {
            try {
                bookRepository.save(bookEntity);
                log.info("[BookService.saveAction(UPDATE)] supplierEntity {}", bookEntity);
            } catch (Exception e) {
                log.info("[BookService.saveAction(UPDATE)] Exception {}", e.getMessage());
                throw new AppException(ErrorCode.BOOK_UPDATE_FAILED);
            }
        }
    }

    public PageResponse<BookResponse> pageResponse(
            Integer pageNumber, Integer pageSize, Page<BookEntity> supplierEntities) {
        log.info(
                "[BookService.pageResponse] pageNumber {} pageSize {} supplierEntities {}",
                pageNumber,
                pageSize,
                supplierEntities);
        PageResponse<BookResponse> pageResponse = new PageResponse<>();
        pageResponse.setPageNumber(pageNumber);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalPages(supplierEntities.getTotalPages());
        pageResponse.setTotalElements(supplierEntities.getTotalElements());
        pageResponse.setRecords(supplierEntities.getContent().stream()
                .map(bookMapper::toBookResponse)
                .toList());

        log.info("[BookService.pageResponse] pageResponse {}", pageResponse);
        return pageResponse;
    }
}
