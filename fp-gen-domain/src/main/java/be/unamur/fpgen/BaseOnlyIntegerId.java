package be.unamur.fpgen;

import java.io.Serial;
import java.io.Serializable;

public abstract class BaseOnlyIntegerId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1079657084325275787L;

    protected Integer id;

    public BaseOnlyIntegerId() {
    }

    public BaseOnlyIntegerId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    protected abstract static class AbstractBaseOnlyIntegerIdBuilder<T> {
        private Integer id;

        public Integer getId() {
            return id;
        }

        protected abstract T self();

        public T withId(Integer id) {
            this.id = id;
            return self();
        }
    }
}
