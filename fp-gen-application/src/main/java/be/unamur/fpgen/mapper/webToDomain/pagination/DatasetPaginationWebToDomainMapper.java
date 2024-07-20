package be.unamur.fpgen.mapper.webToDomain.pagination;


import be.unamur.fpgen.dataset.pagination.DatasetQuery;
import be.unamur.fpgen.dataset.pagination.PagedDatasetsQuery;
import be.unamur.fpgen.mapper.webToDomain.PaginationWebToDomainMapper;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.PagedDatasetQuery;

public class DatasetPaginationWebToDomainMapper {

    public static DatasetQuery map(be.unamur.model.DatasetQuery web) {
        return DatasetQuery.newBuilder()
                .withStartDate(DateUtil.convertLocalDateToOffsetDateTime(web.getStartDate()))
                .withEndDate(DateUtil.convertLocalDateToOffsetDateTime(web.getEndDate()))
                .withAuthorTrigram(web.getAuthorTrigram())
                .withName(web.getName())
                .withVersion(web.getVersion())
                .withComment(web.getComment())
                .withDescription(web.getDescription())
                .build();
    }

    public static PagedDatasetsQuery map(PagedDatasetQuery web) {
        return PagedDatasetsQuery.newBuilder()
                .withDatasetQuery(map(web.getQuery()))
                .withQueryPage(PaginationWebToDomainMapper.map(web.getPage()))
                .build();
    }
}
