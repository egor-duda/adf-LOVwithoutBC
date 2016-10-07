package test.lovwobc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.model.ListOfValuesModel;

public abstract class BeanLOV {
    
    private Object id;
    private String value;
    
    private ListOfValuesModel listOfValuesModel;

    private ListLovCollection listModel = new ListLovCollection(this);
    private List<SourceDataRow> filteredList = new ArrayList<SourceDataRow>();

    public abstract List<String> getAttributes();    
    public abstract List<? extends SourceDataRow> getValues();

    public void setSelectedValue(SourceDataRow rowData) {
        id = rowData.getId();
        value = rowData.getValue();
    };
    
    public List<SourceDataRow> getFilteredList() {
        return filteredList;
    }
    
    public ListOfValuesModel getListOfValuesModel() {
        if(listOfValuesModel == null)
            listOfValuesModel = new ListOfValuesModelImpl(this);
        return listOfValuesModel;
    }
    
    public ListLovCollection getListModel () {
        return listModel;
    }
    
    
    public void filterList(String aValue) {
        filteredList.clear();
        if (aValue == null) return;
        for (SourceDataRow row : getValues()) {
            if (row.getValue().startsWith(aValue)) {
                filteredList.add (row);
            }
        }
    }

    public void validate (FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (facesContext == null || uIComponent == null) return;
        for (SourceDataRow row: getValues()) {
            if(row.getValue().equals(object)) {
                return;
            }
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                 "Not a Valid Value",
                                                 "Not a Valid Value");
        throw new ValidatorException (message);
    }
    
    
    public List<SelectItem> suggestedItems (String searchString) {
        if (searchString == null || searchString.length() == 0) return Collections.emptyList();
        
        filterList(searchString);

        int size = filteredList.size();
        int maxItems = Math.min(10, size);

        List<SelectItem> items = new ArrayList<SelectItem>();
        for (int i = 0; i < maxItems; i++) {
            SelectItem selectItem = new SelectItem();
            SourceDataRow data = filteredList.get(i);
            String value = data.getValue();
            selectItem.setLabel(String.format("%-15s", value));
            selectItem.setValue(value);
            items.add(selectItem);
        }
        return items;   
    }
    
    public List<SelectItem> getSelectItems () {
        List<SelectItem> retVal = new ArrayList<SelectItem>(getValues().size() + 1);
        retVal.add (new SelectItem (null));
        for (SourceDataRow row: getValues()) {
            retVal.add (new SelectItem (row.getValue()));
        }
        return retVal;
    }

    public Object getId() {
        return id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
