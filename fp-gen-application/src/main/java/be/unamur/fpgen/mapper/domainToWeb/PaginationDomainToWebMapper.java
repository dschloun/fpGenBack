package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.model.Pagination;
import be.unamur.model.QueryPage;

public class PaginationDomainToWebMapper {
    public static Pagination map(be.unamur.fpgen.pagination.Pagination domain) {
        return new Pagination()
                .page(domain.getPage())
                .size(domain.getSize())
                .totalSize(domain.getTotalSize());
    }

    public static QueryPage map(be.unamur.fpgen.pagination.QueryPage domain) {
        return new QueryPage()
                .page(domain.getPage())
                .size(domain.getSize());
    }
}
