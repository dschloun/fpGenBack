package be.unamur.fpgen.mapper.webToDomain.pagination;


import be.unamur.fpgen.dataset.pagination.DatasetQuery;
import be.unamur.fpgen.dataset.pagination.PagedDatasetsQuery;
import be.unamur.fpgen.mapper.webToDomain.DatasetTypeWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.PaginationWebToDomainMapper;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.PagedDatasetQuery;

import java.util.Objects;

/**
 * This class is a mapper which map the web object to the domain object
 */
public class DatasetPaginationWebToDomainMapper {

    public static DatasetQuery map(be.unamur.model.DatasetQuery web) {
        return DatasetQuery.newBuilder()
                .withStartDate(DateUtil.convertLocalDateToOffsetDateTime(web.getStartDate()))
                .withEndDate(DateUtil.convertLocalDateToOffsetDateTime(web.getEndDate()))
                .withAuthorTrigram(web.getAuthorTrigram())
                .withName(web.getName())
                .withVersion(Objects.nonNull(web.getVersion()) ? Integer.valueOf(web.getVersion()) : null) //todo correct api put integer instead of string
                .withComment(web.getComment())
                .withDescription(web.getDescription())
                .withType(DatasetTypeWebToDomainMapper.map(web.getDatasetType()))
                .build();
    }

    public static PagedDatasetsQuery map(PagedDatasetQuery web) {
        return PagedDatasetsQuery.newBuilder()
                .withDatasetQuery(map(web.getQuery()))
                .withQueryPage(PaginationWebToDomainMapper.map(web.getPage()))
                .build();
    }
}
