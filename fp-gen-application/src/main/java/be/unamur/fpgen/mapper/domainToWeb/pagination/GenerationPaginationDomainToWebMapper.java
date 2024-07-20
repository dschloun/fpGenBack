package be.unamur.fpgen.mapper.domainToWeb.pagination;

import be.unamur.fpgen.mapper.domainToWeb.GenerationDomainToWebMapper;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.model.GenerationsPage;

public class GenerationPaginationDomainToWebMapper {

    public static GenerationsPage map(be.unamur.fpgen.generation.pagination.GenerationsPage domain) {
        return new GenerationsPage()
                .generations(MapperUtil.mapList(domain.getConversationGenerations(), GenerationDomainToWebMapper::map))
                .pagination(PaginationDomainToWebMapper.map(domain.getPagination()));
    }
}
