package test.lovwobc.model;

import java.util.Collections;
import java.util.Set;

import oracle.adf.view.rich.model.AttributeDescriptor;

public class AttributeDescriptorImpl extends AttributeDescriptor {
    
    private String label;

    public AttributeDescriptorImpl(String label) {
        super();
        this.label = label;
    }

    public AttributeDescriptor.ComponentType getComponentType() {
        return AttributeDescriptor.ComponentType.inputText;
    }

    public String getDescription() {
        return null;
    }

    public String getFormat() {
        return null;
    }

    public String getLabel() {
        return label;
    }

    public int getLength() {
        return 0;
    }

    public int getMaximumLength() {
        return 0;
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
