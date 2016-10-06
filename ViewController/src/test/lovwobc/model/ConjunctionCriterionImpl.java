package test.lovwobc.model;

import java.util.ArrayList;

import oracle.adf.view.rich.model.ConjunctionCriterion;

public class ConjunctionCriterionImpl extends ConjunctionCriterion {
    public ConjunctionCriterionImpl() {
        _criterionList = new ArrayList<oracle.adf.view.rich.model.Criterion>();
        _criterionList.add(new AttributeCriterionImpl());
    }

    public ConjunctionCriterion.Conjunction getConjunction() {
        return ConjunctionCriterion.Conjunction.NONE;
    }

    public java.util.List<oracle.adf.view.rich.model.Criterion> getCriterionList() {
        return _criterionList;
    }

    public Object getKey(oracle.adf.view.rich.model.Criterion criterion) {
        return Integer.toString(0);
    }

    public oracle.adf.view.rich.model.Criterion getCriterion(Object key) {
        assert (_criterionList != null);
        return _criterionList.get(0);
    }

    public void setConjunction(ConjunctionCriterion.Conjunction conjunction) {
    }
    java.util.List<oracle.adf.view.rich.model.Criterion> _criterionList;
}
