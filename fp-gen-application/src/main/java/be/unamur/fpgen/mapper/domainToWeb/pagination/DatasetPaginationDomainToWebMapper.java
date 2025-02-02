package be.unamur.fpgen.mapper.domainToWeb.pagination;

import be.unamur.fpgen.mapper.domainToWeb.DatasetDomainToWebMapper;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.model.DatasetType;
import be.unamur.model.DatasetsPage;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class DatasetPaginationDomainToWebMapper {

    public static DatasetsPage map(be.unamur.fpgen.dataset.pagination.DatasetsPage domain, DatasetType datasetType) {
        return new DatasetsPage()
                .datasets(MapperUtil.mapList(domain.getDatasetList(), DatasetDomainToWebMapper::map))
                .pagination(PaginationDomainToWebMapper.map(domain.getPagination()));
    }
}
