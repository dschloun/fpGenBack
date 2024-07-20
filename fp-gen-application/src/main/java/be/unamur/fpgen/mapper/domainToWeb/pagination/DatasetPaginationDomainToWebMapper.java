package be.unamur.fpgen.mapper.domainToWeb.pagination;

import be.unamur.fpgen.mapper.domainToWeb.DatasetDomainToWebMapper;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.model.DatasetsPage;

public class DatasetPaginationDomainToWebMapper {

    public static DatasetsPage map(be.unamur.fpgen.dataset.pagination.DatasetsPage domain) {
        return new DatasetsPage()
                .datasets(MapperUtil.mapList(domain.getConversationDatasetList(), DatasetDomainToWebMapper::mapConversationDataset))
                .pagination(PaginationDomainToWebMapper.map(domain.getPagination()));
    }
}
