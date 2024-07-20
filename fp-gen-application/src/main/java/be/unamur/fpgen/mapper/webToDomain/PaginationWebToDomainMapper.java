package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.pagination.QueryPage;


public class PaginationWebToDomainMapper {
    public static Pagination map(be.unamur.model.Pagination web) {
        return new Pagination.Builder()
                .page(web.getPage())
                .size(web.getSize())
                .totalSize(web.getTotalSize())
                .build();
    }

    public static QueryPage map(be.unamur.model.QueryPage dto) {
        return new QueryPage.Builder()
                .page(dto.getPage())
                .size(dto.getSize())
                .build();
    }
}
