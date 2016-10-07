package test.lovwobc.model;

public class AttributeDef {
    private String attributeName;
    
    public AttributeDef (String attributeName) {
        this.attributeName = attributeName;
    }
    
    public String getAttributeName () {
        return attributeName;
    }
    
    public String getLabel () { 
        return getAttributeName (); 
    }
    
    public String getDescription () {
        return getAttributeName();
    }
    
    public int getLength() { 
        return 0; 
    }
    
    public int getMaxLength() { 
        return 0; 
    }
}
