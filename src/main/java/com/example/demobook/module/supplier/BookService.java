package com.example.demobook.module.supplier;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import com.example.demobook.module.base.dto.response.fe.FEResponse;
import com.example.demobook.module.base.dto.response.fe.form.FormFieldResponse;
import com.example.demobook.module.base.dto.response.fe.form.OptionResponse;
import com.example.demobook.module.base.dto.response.fe.table.ColumnResponse;
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

    //    @PreAuthorize("hasRole('ADMIN')")
    public FEResponse fe() {
        log.info("[SupplierService.columns] ");

        List<ColumnResponse> columnResponses = new ArrayList<>();
        Integer index = 1;
        for (Field field : BookResponse.class.getDeclaredFields()) { // CHỈ ĐỊNH CLASS
            ColumnResponse columnResponse = new ColumnResponse();
            columnResponse.setTitle(field.getName());
            columnResponse.setDataIndex(field.getName());
            columnResponse.setSlotName(field.getName());
            columnResponse.setIndex(index);
            index++;

            columnResponses.add(columnResponse);
        }

        ColumnResponse columnResponse = new ColumnResponse();
        columnResponse.setTitle("Action");
        columnResponse.setSlotName("action");
        columnResponse.setAlign("center");
        columnResponse.setWidth(220);
        columnResponse.setIndex(index);
        columnResponses.add(columnResponse);

        List<FormFieldResponse> formFieldResponses = new ArrayList<>();
        for (Field field : BookInsertRequest.class.getDeclaredFields()) { // CHỈ ĐỊNH CLASS
            FormFieldResponse formFieldResponse = new FormFieldResponse();

            String type = field.getGenericType().getTypeName();
            if (Objects.equals(type, "java.lang.String")) {
                formFieldResponse.setType("input");
            } else if (Objects.equals(type, "java.lang.Integer")) {
                formFieldResponse.setType("input-number");
            } else if (Objects.equals(type, "java.time.Instant")) {
                formFieldResponse.setType("date-picker");
            } else if (Objects.equals(type, "java.lang.Enum")) { // TODO: CHECK ENUM TYPE
                formFieldResponse.setType("select");
                List<OptionResponse> optionResponses = new ArrayList<>();
                // TODO:
                formFieldResponse.setOptions(optionResponses);
            }
            formFieldResponse.setLabel(field.getName());
            formFieldResponse.setField(field.getName());

            formFieldResponses.add(formFieldResponse);
        }

        FormFieldResponse formFieldResponse = new FormFieldResponse();
        formFieldResponse.setField("btns");
        formFieldResponse.setSpan(24);

        formFieldResponses.add(formFieldResponse);

        FEResponse feResponse = new FEResponse();
        feResponse.setColumns(columnResponses);
        feResponse.setFormFields(formFieldResponses);

        return feResponse;
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<BookResponse> index(Integer pageNumber, Integer pageSize, String[] sort) {
        log.info("[SupplierService.index]  pageNumber {} pageSize {} sort {}", pageNumber, pageSize, sort);

        List<Sort.Order> sortOrders = SortUtils.getSortOrders(sort);

        try {
            Page<BookEntity> supplierEntities = bookRepository.findAll(
                    PageRequest.of(pageNumber <= 0 ? 0 : pageNumber - 1, pageSize, Sort.by(sortOrders)));

            PageResponse<BookResponse> pageResponse = pageResponse(pageNumber, pageSize, supplierEntities);
            log.info("[SupplierService.index] pageResponse {}", pageResponse);
            return pageResponse;
        } catch (Exception e) {
            log.info("[SupplierService.index] Exception {}", e.getMessage());
            throw new AppException(ErrorCode.SUPPLIER_INDEX_FAILED);
        }
    }

    // @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<BookResponse> filter(
            FilterRequest filterRequest, Integer pageNumber, Integer pageSize, String[] sort) {
        log.info(
                "[SupplierService.filter]  filterRequest {} pageNumber {} pageSize {} sort {}",
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
            log.info("[SupplierService.filter] pageResponse {}", pageResponse);
            return pageResponse;
        } catch (Exception e) {
            log.info("[SupplierService.filter] Exception {}", e.getMessage());
            throw new AppException(ErrorCode.SUPPLIER_INDEX_FAILED);
        }
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookInsertRequest create() {
        log.info("[SupplierService.create] ");
        BookEntity bookEntity = new BookEntity();

        try {
            BookInsertRequest bookInsertRequest = bookMapper.toBookInsertRequest(bookEntity);

            log.info("[SupplierService.create] supplierInsertRequest {}", bookInsertRequest);
            return bookInsertRequest;
        } catch (Exception e) {
            log.info("[SupplierService.create] Exception {}", e.getMessage());
            throw new AppException(ErrorCode.SUPPLIER_CREATE_FAILED);
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public List<BookInsertRequest> bulkCreate() {
        log.info("[SupplierService.bulkCreate] ");

        List<BookInsertRequest> bookInsertRequests = new ArrayList<>();
        bookInsertRequests.add(create());

        log.info("[SupplierService.bulkCreate] supplierInsertRequests {}", bookInsertRequests);

        return bookInsertRequests;
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookInsertResponse insert(BookInsertRequest bookInsertRequest) {
        log.info("[SupplierService.insert]  supplierInsertRequest {}", bookInsertRequest);

        try {
            BookEntity bookEntity = bookMapper.fromBookInsertRequest(bookInsertRequest);
            saveAction(bookEntity, Action.INSERT);

            BookInsertResponse bookInsertResponse = bookMapper.toBookInsertResponse(bookEntity);
            log.info("[SupplierService.insert] supplierInsertResponse {}", bookInsertResponse);
            return bookInsertResponse;
        } catch (Exception e) {
            log.info("[SupplierService.insert] Exception {}", e.getMessage());
            throw new AppException(ErrorCode.SUPPLIER_INSERT_FAILED);
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public List<BookInsertResponse> bulkInsert(List<BookInsertRequest> bookInsertRequests) {
        log.info("[SupplierService.bulkInsert]  supplierInsertRequests {}", bookInsertRequests);

        try {
            List<BookInsertResponse> bookInsertRespons = new ArrayList<>();
            bookInsertRequests.forEach(e -> bookInsertRespons.add(insert(e)));

            log.info("[SupplierService.bulkInsert] supplierInsertResponses {}", bookInsertRespons);

            return bookInsertRespons;
        } catch (Exception e) {
            log.info("[SupplierService.bulkInsert] Exception {}", e.getMessage());

            throw new AppException(ErrorCode.SUPPLIER_CREATE_FAILED);
        }
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookUpdateRequest edit(String supplierId) {
        log.info("[SupplierService.edit]  supplierId {}", supplierId);
        BookEntity bookEntity = findById(supplierId);

        BookUpdateRequest bookUpdateRequest = bookMapper.toBookUpdateRequest(bookEntity);
        log.info("[SupplierService.edit] supplierUpdateRequest {}", bookUpdateRequest);
        return bookUpdateRequest;
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public List<BookUpdateRequest> bulkEdit(List<String> supplierIds) {
        log.info("[SupplierService.bulkEdit]  supplierIds {}", supplierIds);

        try {
            List<BookUpdateRequest> bookUpdateRequests = new ArrayList<>();
            supplierIds.forEach(id -> bookUpdateRequests.add(edit(id)));

            log.info("[SupplierService.bulkEdit] supplierUpdateRequests {}", bookUpdateRequests);

            return bookUpdateRequests;
        } catch (Exception e) {
            log.info("[SupplierService.bulkEdit] Exception {}", e.getMessage());

            throw new AppException(ErrorCode.SUPPLIER_CREATE_FAILED);
        }
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookUpdateResponse update(String supplierId, BookUpdateRequest bookUpdateRequest) {
        log.info("[SupplierService.update]  supplierId {} supplierUpdateRequest {}", supplierId, bookUpdateRequest);

        BookEntity bookEntity = findById(supplierId);

        try {
            bookEntity = bookMapper.fromBookUpdateRequest(bookUpdateRequest, bookEntity);
            saveAction(bookEntity, Action.UPDATE);

            BookUpdateResponse bookUpdateResponse = bookMapper.toBookUpdateResponse(bookEntity);
            log.info("[SupplierService.update] supplierUpdateResponse {}", bookUpdateResponse);
            return bookUpdateResponse;
        } catch (Exception e) {
            log.info("[SupplierService.update] Exception {}", e.getMessage());
            throw new AppException(ErrorCode.SUPPLIER_UPDATE_FAILED);
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public BookUpdateResponse insertOrUpdate(String supplierId, BookUpdateRequest bookUpdateRequest) {
        log.info(
                "[SupplierService.insertOrUpdate]  supplierId {} supplierUpdateRequest {}",
                supplierId,
                bookUpdateRequest);

        BookEntity bookEntity =
                Optional.ofNullable(supplierId).map(id -> findById(id)).orElseGet(BookEntity::new);

        return getUpdateResponse(bookUpdateRequest, bookEntity);
    }

    private BookUpdateResponse getUpdateResponse(
            BookUpdateRequest productUpdateRequest, BookEntity bookEntity) {
        try {
            bookEntity = bookMapper.fromBookUpdateRequest(productUpdateRequest, bookEntity);
            saveAction(bookEntity, Action.UPDATE);

            BookUpdateResponse productUpdateResponse = bookMapper.toBookUpdateResponse(bookEntity);

            log.info("[SupplierService.update] productUpdateResponse {}", productUpdateResponse);

            return productUpdateResponse;
        } catch (Exception e) {
            log.info("[SupplierService.update] Exception {}", e.getMessage());

            throw new AppException(ErrorCode.SUPPLIER_UPDATE_FAILED);
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public List<BookUpdateResponse> bulkInsertOrUpdate(List<BookUpdateRequest> bookUpdateRequests) {
        log.info("[SupplierService.bulkInsertOrUpdate]  supplierUpdateRequests {}", bookUpdateRequests);

        try {
            List<BookUpdateResponse> bookUpdateRespons = new ArrayList<>();
            bookUpdateRequests.forEach(e -> bookUpdateRespons.add(insertOrUpdate(e.getId(), e)));

            log.info("[SupplierService.bulkInsertOrUpdate] supplierUpdateResponses {}", bookUpdateRespons);

            return bookUpdateRespons;
        } catch (Exception e) {
            throw new AppException(ErrorCode.SUPPLIER_CREATE_FAILED);
        }
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookShowResponse show(String supplierId) {
        log.info("[SupplierService.show]  supplierId {}", supplierId);
        BookEntity bookEntity = findById(supplierId);

        BookShowResponse bookShowResponse = bookMapper.toBookShowResponse(bookEntity);
        log.info("[SupplierService.show] supplierShowResponse {}", bookShowResponse);
        return bookShowResponse;
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    public BookDeleteResponse delete(String supplierId) {
        log.info("[SupplierService.delete]  supplierId {}", supplierId);
        BookEntity bookEntity = findById(supplierId);

        try {
            BookDeleteResponse bookDeleteResponse = bookMapper.toBookDeleteResponse(bookEntity);

            bookRepository.delete(bookEntity);

            log.info("[SupplierService.delete] supplierDeleteResponse {}", bookDeleteResponse);
            return bookDeleteResponse;
        } catch (Exception e) {
            log.info("[SupplierService.delete] Exception {}", e.getMessage());
            throw new AppException(ErrorCode.SUPPLIER_DELETE_FAILED);
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public List<BookDeleteResponse> bulkDelete(List<String> supplierIds) {
        log.info("[SupplierService.bulkDelete]  supplierIds {}", supplierIds);

        List<BookDeleteResponse> bookDeleteRespons = new ArrayList<>();
        supplierIds.forEach(supplierId -> bookDeleteRespons.add(delete(supplierId)));

        log.info("[SupplierService.bulkDelete] supplierDeleteResponses {}", bookDeleteRespons);

        return bookDeleteRespons;
    }

    public BookEntity findById(String supplierId) {
        log.info("[SupplierService.findById] supplierId {}", supplierId);

        BookEntity bookEntity = bookRepository
                .findById(supplierId)
                .orElseThrow(() -> new AppException(ErrorCode.SUPPLIER_NOT_FOUND));
        log.info("[SupplierService.findById] supplierEntity {}", bookEntity);
        return bookEntity;
    }

    public void saveAction(BookEntity bookEntity, Action action) {
        log.info("[SupplierService.saveAction] supplierEntity {} action {}", bookEntity, action);
        if (action == Action.INSERT) {
            try {
                bookRepository.save(bookEntity);
                log.info("[SupplierService.saveAction(INSERT)] supplierEntity {}", bookEntity);
            } catch (Exception e) {
                log.info("[SupplierService.saveAction(INSERT)] Exception {}", e.getMessage());
                throw new AppException(ErrorCode.SUPPLIER_INSERT_FAILED);
            }
        } else if (action == Action.UPDATE) {
            try {
                bookRepository.save(bookEntity);
                log.info("[SupplierService.saveAction(UPDATE)] supplierEntity {}", bookEntity);
            } catch (Exception e) {
                log.info("[SupplierService.saveAction(UPDATE)] Exception {}", e.getMessage());
                throw new AppException(ErrorCode.SUPPLIER_UPDATE_FAILED);
            }
        }
    }

    public PageResponse<BookResponse> pageResponse(
            Integer pageNumber, Integer pageSize, Page<BookEntity> supplierEntities) {
        log.info(
                "[SupplierService.pageResponse] pageNumber {} pageSize {} supplierEntities {}",
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

        log.info("[SupplierService.pageResponse] pageResponse {}", pageResponse);
        return pageResponse;
    }
}
