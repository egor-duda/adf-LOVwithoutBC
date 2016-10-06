package test.lovwobc.model;

import java.util.HashMap;
import java.util.Map;

import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.ConjunctionCriterion;
import oracle.adf.view.rich.model.QueryDescriptor;

public class QueryDescriptorImpl extends QueryDescriptor {
    public QueryDescriptorImpl() {
        _conjCriterion = new ConjunctionCriterionImpl();
    }

    public void addCriterion(String name) {
    }

    public void changeMode(QueryDescriptor.QueryMode mode) {
    }

    public ConjunctionCriterion getConjunctionCriterion() {
        return _conjCriterion;
    }

    public void setConjunctionCriterion(ConjunctionCriterion criterion) {
        _conjCriterion = criterion;
    }

    public String getName() {
        return null;
    }

    public Map<String, Object> getUIHints() {
        return new HashMap<String, Object>();
    }

    public void removeCriterion(oracle.adf.view.rich.model.Criterion object) {
    }

    public AttributeCriterion getCurrentCriterion() {
        return null;
    }

    public void setCurrentCriterion(AttributeCriterion attrCriterion) {
    }

    ConjunctionCriterion _conjCriterion;
}
