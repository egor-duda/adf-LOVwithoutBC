package test.lovwobc.model;

public class LabelledAttributeDef extends AttributeDef {
    private String attributeLabel;
    public LabelledAttributeDef(String attributeName, String attributeLabel) {
        super(attributeName);
        this.attributeLabel = attributeLabel;
    }

    public String getLabel() {
        return attributeLabel;
    }
}
