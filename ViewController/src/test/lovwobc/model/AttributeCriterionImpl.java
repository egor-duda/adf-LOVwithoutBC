package test.lovwobc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.AttributeDescriptor;

public class AttributeCriterionImpl extends AttributeCriterion {
    private AttributeDescriptor attributeDescriptor;
    private List<Object> values;
    
    public AttributeCriterionImpl (AttributeDef attributeDef) {
        if (values == null) {
            values = new ArrayList<Object>();
            values.add ("");
        }
        attributeDescriptor = new AttributeDescriptorImpl(attributeDef);
    }

    public AttributeDescriptor getAttribute() {
        return attributeDescriptor;
    }

    public AttributeDescriptor.Operator getOperator() {
        return null;
    }

    public Map<String, AttributeDescriptor.Operator> getOperators() {
        return Collections.emptyMap();
    }

    public java.util.List<? extends Object> getValues() {
        return values;
    }

    public boolean isRemovable() {
        return false;
    }

    public void setOperator(AttributeDescriptor.Operator operator) {
    }
}
