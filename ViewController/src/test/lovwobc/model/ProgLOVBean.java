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
import org.apache.myfaces.trinidad.model.CollectionModel;

public class ProgLOVBean {
    
    private String _ename;

    private ListOfValuesModel _listOfValuesModel;

    private CollectionModel listModel = new ListLovCollection(this);
    private List<EmpDataRow> _values = new ArrayList<EmpDataRow>();
    private List<EmpDataRow> _filteredList = new ArrayList<EmpDataRow>();
    private static List<String> _attributes = new ArrayList<String>();
    
    public ProgLOVBean() {
        for (int i = 0; i < _DIR_DATA.length; i++) {
            String ename = _DIR_DATA[i];
            String rowID = String.valueOf(i);
            EmpDataRow _curRow = new EmpDataRow (ename, rowID);
            _values.add(_curRow);
        }
        _filteredList.addAll(_values);
    }

    public void setEname(String ename) {
        _ename = ename;
    }
    
    public String getEname() {
        return _ename;
    }

    public void setSelectedValue(EmpDataRow rowData) {
        if (rowData != null) {
            setEname(rowData.getEname());
        }
    }

    public void filterList(String eName) {
        _filteredList.clear();
        if (eName == null) return;
        for (EmpDataRow data : _values) {
            if (data.getEname().startsWith(eName)) {
                _filteredList.add(data);
            }
        }
    }

    public CollectionModel getListModel() {
        return listModel;
    }

    public ListOfValuesModel getListOfValuesModel() {
        if(_listOfValuesModel == null)
            _listOfValuesModel = new ListOfValuesModelImpl(this);
        return _listOfValuesModel;
    }

    public void validate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (facesContext == null || uIComponent == null) return;
        for(Object data : _values) {
            if(((EmpDataRow)data).getEname().equals(object)) {
                return;
            }
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                              "Not a Valid Value",
                                              "Not a Valid Value");
        throw new ValidatorException(message);
    }
  
    public static List<String> getAttributes() {
        return _attributes;
    }

    public List<EmpDataRow> getFilteredList() {
        return _filteredList;
    }

    public List<SelectItem> suggestedItems (String searchString) {
        List<EmpDataRow> values;

        if (searchString == null || searchString.length() == 0) return Collections.emptyList();
        
        filterList(searchString);
        values = _filteredList;

        int size = values.size();
        int maxItems = Math.min(10, size);

        List<SelectItem> items = new ArrayList<SelectItem>();
        for (int i = 0; i < maxItems; i++) {
            SelectItem selectItem = new SelectItem();
            EmpDataRow data = values.get(i);
            String eName = data.getEname();
            selectItem.setLabel(String.format("%-15s", eName));
            selectItem.setValue(eName);
            items.add(selectItem);
        }
        return items;
    }
  
    //Single datasource
    static String _DIR_DATA[] = {
        "Adam",
        "Avance",
        "Abdul",
        "Blake",
        "Bob",
        "Brenta",
        "Bejond",
        "Calvin",
        "Carl",
        "David"
    };

    static {
        _attributes.add("ename");
    }

    public List<SelectItem> getItems() {
        List<SelectItem> retVal = new ArrayList<SelectItem>(_values.size() + 1);
        retVal.add (new SelectItem (null));
        for (EmpDataRow row: _values) {
            retVal.add (new SelectItem (row.getEname()));
        }
        return retVal;
    }
}
