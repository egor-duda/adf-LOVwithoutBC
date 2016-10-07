package test.lovwobc.model;

import java.util.HashMap;
import java.util.Map;

import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.ConjunctionCriterion;
import oracle.adf.view.rich.model.QueryDescriptor;

public class QueryDescriptorImpl extends QueryDescriptor {
    
    private ConjunctionCriterion conjCriterion;
    
    public QueryDescriptorImpl (AttributeDef attributeDef) {
        conjCriterion = new ConjunctionCriterionImpl(attributeDef);
    }

    public void addCriterion(String name) {
    }

    public void changeMode(QueryDescriptor.QueryMode mode) {
    }

    public ConjunctionCriterion getConjunctionCriterion() {
        return conjCriterion;
    }

    public void setConjunctionCriterion(ConjunctionCriterion criterion) {
        conjCriterion = criterion;
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
}
