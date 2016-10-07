package test.lovwobc.model;

import java.util.Collections;
import java.util.Set;

import oracle.adf.view.rich.model.AttributeDescriptor;
import oracle.adf.view.rich.model.ColumnDescriptor;

public class ColumnDescriptorImpl extends ColumnDescriptor {
    private AttributeDef attributeDef;

    public ColumnDescriptorImpl(AttributeDef attributeDef) {
        this.attributeDef = attributeDef;
    }

    @Override
    public String getFormat() {
        return null;
    }

    @Override
    public String getLabel() {
        return attributeDef.getLabel();
    }

    @Override
    public String getName() {
        return attributeDef.getAttributeName();
    }

    @Override
    public Class<?> getType() {
        return String.class;
    }

    @Override
    public String getAlign() {
        return null;
    }

    @Override
    public AttributeDescriptor.ComponentType getComponentType() {
        return AttributeDescriptor.ComponentType.inputText;
    }

    @Override
    public String getDescription() {
        return attributeDef.getDescription();
    }

    @Override
    public String getModel() {
        return null;
    }

    @Override
    public Set<AttributeDescriptor.Operator> getSupportedOperators() {
        return Collections.emptySet();
    }

    @Override
    public int getLength() {
        return attributeDef.getLength();
    }

    @Override
    public int getMaximumLength() {
        return attributeDef.getMaxLength();
    }

    @Override
    public boolean isRequired() {
        return false;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }
}
