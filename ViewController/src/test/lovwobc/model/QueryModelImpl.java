package test.lovwobc.model;

import java.util.List;
import java.util.Map;

import oracle.adf.view.rich.model.AttributeDescriptor;
import oracle.adf.view.rich.model.QueryDescriptor;
import oracle.adf.view.rich.model.QueryModel;

public class QueryModelImpl extends QueryModel {
    public QueryDescriptor create(String name, QueryDescriptor qdBase) {
        return null;
    }

    public void delete(QueryDescriptor qd) {
    }

    public List<AttributeDescriptor> getAttributes() {
        return null;
    }

    public List<QueryDescriptor> getSystemQueries() {
        return null;
    }

    public List<QueryDescriptor> getUserQueries() {
        return null;
    }

    public void reset(QueryDescriptor qd) {
    }

    public void setCurrentDescriptor(QueryDescriptor qd) {
    }

    public void update(QueryDescriptor qd, Map<String, Object> uiHints) {
    }
}
