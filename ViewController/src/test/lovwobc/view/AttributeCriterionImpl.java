package test.lovwobc.view;

import java.util.ArrayList;
import java.util.Map;

import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.AttributeDescriptor;

public class AttributeCriterionImpl extends AttributeCriterion {
    public AttributeCriterionImpl() {
        if (_values == null) {
            _values = new ArrayList<Object>();
            _values.add ("");
        }
    }

    public AttributeDescriptor getAttribute() {
        return new AttributeDescriptorImpl();
    }

    public AttributeDescriptor.Operator getOperator() {
        return null;
    }

    public Map<String, AttributeDescriptor.Operator> getOperators() {
        return null;
    }

    public java.util.List<? extends Object> getValues() {
        return _values;
    }

    public boolean isRemovable() {
        return false;
    }

    public void setOperator(AttributeDescriptor.Operator operator) {
    }

    java.util.List<Object> _values;
}
