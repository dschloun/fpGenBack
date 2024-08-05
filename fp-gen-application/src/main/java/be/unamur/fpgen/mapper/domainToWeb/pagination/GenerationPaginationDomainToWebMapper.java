package be.unamur.fpgen.mapper.domainToWeb.pagination;

import be.unamur.fpgen.generation.pagination.ConversationGenerationsPage;
import be.unamur.fpgen.generation.pagination.InstantMessageGenerationsPage;
import be.unamur.fpgen.mapper.domainToWeb.GenerationDomainToWebMapper;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.model.GenerationsPage;

public class GenerationPaginationDomainToWebMapper {

    public static GenerationsPage map(ConversationGenerationsPage domain) {
        return new GenerationsPage()
                .generations(MapperUtil.mapList(domain.getGenerationList(), GenerationDomainToWebMapper::map))
                .pagination(PaginationDomainToWebMapper.map(domain.getPagination()));
    }

    public static GenerationsPage map(InstantMessageGenerationsPage domain) {
        return new GenerationsPage()
                .generations(MapperUtil.mapList(domain.getGenerationList(), GenerationDomainToWebMapper::map))
                .pagination(PaginationDomainToWebMapper.map(domain.getPagination()));
    }
}
