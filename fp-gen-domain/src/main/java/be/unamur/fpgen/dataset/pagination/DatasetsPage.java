package be.unamur.fpgen.dataset.pagination;


import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.exception.pagination.IncompleteDatasetsPageException;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.utils.ViolationHandler;

import java.util.ArrayList;
import java.util.List;

public class DatasetsPage {
    private final Pagination pagination;
    private final List<Dataset> datasetList;

    private DatasetsPage(Builder builder) {
        pagination = builder.pagination;
        datasetList = builder.datasetList;
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public Pagination getPagination() {
        return pagination;
    }

    public List<Dataset> getDatasetList() {
        return datasetList;
    }

    public static final class Builder extends ViolationHandler {
        private Pagination pagination;
        private List<Dataset> datasetList;

        private Builder() {
        }

        public Builder withPagination(Pagination val) {
            pagination = val;
            return this;
        }

        public Builder withDatasetList(List<Dataset> val) {
            datasetList = val;
            return this;
        }

        public DatasetsPage build() {
            // Validation
            List<String> violations = new ArrayList<>();
            violations.addAll(cannotBeNull(pagination, "pagination"));
            violations.addAll(cannotBeNull(datasetList, "datasetList"));

            if (!violations.isEmpty()) {
                throw new IncompleteDatasetsPageException(buildMessage("The dataset page is incomplete because", violations));
            }
            return new DatasetsPage(this);
        }
    }
}
