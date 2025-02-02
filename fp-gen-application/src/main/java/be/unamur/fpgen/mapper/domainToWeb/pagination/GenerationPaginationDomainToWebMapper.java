package be.unamur.fpgen.mapper.domainToWeb.pagination;

import be.unamur.fpgen.generation.pagination.GenerationPage;
import be.unamur.fpgen.mapper.domainToWeb.GenerationDomainToWebMapper;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.model.GenerationsPage;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class GenerationPaginationDomainToWebMapper {

    public static GenerationsPage map(GenerationPage domain) {
        return new GenerationsPage()
                .generations(MapperUtil.mapList(domain.getGenerationList(), GenerationDomainToWebMapper::map))
                .pagination(PaginationDomainToWebMapper.map(domain.getPagination()));
    }
}
