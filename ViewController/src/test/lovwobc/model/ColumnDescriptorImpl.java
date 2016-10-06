package test.lovwobc.model;

import java.util.Collections;
import java.util.Set;

import oracle.adf.view.rich.model.AttributeDescriptor;
import oracle.adf.view.rich.model.ColumnDescriptor;

public class ColumnDescriptorImpl extends ColumnDescriptor {
    private String _name;

    public ColumnDescriptorImpl(String name) {
        _name = name;
    }

    @Override
    public String getFormat() {
        return null;
    }

    @Override
    public String getLabel() {
        return _name.toUpperCase();
    }

    @Override
    public String getName() {
        return _name;
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
        return null;
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
        return 0;
    }

    @Override
    public int getMaximumLength() {
        return 0;
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
