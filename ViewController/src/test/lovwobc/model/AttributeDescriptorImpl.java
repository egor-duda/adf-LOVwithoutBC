package test.lovwobc.model;

import java.util.Collections;
import java.util.Set;

import oracle.adf.view.rich.model.AttributeDescriptor;

public class AttributeDescriptorImpl extends AttributeDescriptor {
    
    private AttributeDef attributeDef;

    public AttributeDescriptorImpl(AttributeDef attributeDef) {
        super();
        this.attributeDef = attributeDef;
    }

    public AttributeDescriptor.ComponentType getComponentType() {
        return AttributeDescriptor.ComponentType.inputText;
    }

    public String getDescription() {
        return attributeDef.getDescription();
    }

    public String getFormat() {
        return null;
    }

    public String getLabel() {
        return attributeDef.getLabel();
    }

    public int getLength() {
        return attributeDef.getLength();
    }

    public int getMaximumLength() {
        return attributeDef.getMaxLength();
    }

    public Object getModel() {
        return null;
    }

    public String getName() {
        return null;
    }

    public Set<AttributeDescriptor.Operator> getSupportedOperators() {
        return Collections.emptySet();
    }

    public Class<?> getType() {
        return null;
    }

    public boolean isReadOnly() {
        return false;
    }

    public boolean isRequired() {
        return false;
    }
}
