package test.lovwobc.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.AttributeDescriptor;
import oracle.adf.view.rich.model.AutoSuggestUIHints;
import oracle.adf.view.rich.model.ColumnDescriptor;
import oracle.adf.view.rich.model.ConjunctionCriterion;
import oracle.adf.view.rich.model.Criterion;
import oracle.adf.view.rich.model.ListOfValuesModel;
import oracle.adf.view.rich.model.QueryDescriptor;
import oracle.adf.view.rich.model.QueryModel;
import oracle.adf.view.rich.model.TableModel;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

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

    public void setValues(EmpDataRow rowData) {
        if ( rowData != null) {
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
  
    public EmpDataRow _getRowData(Object selectedRowKey) {
        if (selectedRowKey != null && selectedRowKey instanceof RowKeySet) {
            Iterator<Object> selection = ((RowKeySet) selectedRowKey).iterator();
            while (selection.hasNext()) {
                Object rowKey = selection.next();
                Object oldRowKey = listModel.getRowKey();
                listModel.setRowKey(rowKey);
                EmpDataRow rowData = (EmpDataRow)listModel.getRowData();
                listModel.setRowKey(oldRowKey);
                return rowData;
            }
        }
        return null;
    }

    public static List<String> getAttributes() {
        return _attributes;
    }

    public List<EmpDataRow> getFilteredList() {
        return _filteredList;
    }


    // return a pre defined list as the smart list
  public List<SelectItem> smartList()
  {
    List<SelectItem> items = new ArrayList<SelectItem>();
    for (int i=0; i < _DIR_DATA.length; i++)
    {
      SelectItem selectItem = new SelectItem();
      EmpDataRow data = _values.get(i);
      String eName = data.getEname();
      selectItem.setLabel(String.format("%-15s", eName));
      selectItem.setValue(eName);
      items.add(selectItem);
    }
    return items;
  }

  public List<SelectItem> suggestedItems(String searchString)
  {
    List<EmpDataRow> values;

    if (searchString == null || searchString.length() == 0)
    {
      // This code should never get executed as we don't queue the event on the client
      // when searchString length is 0
      return Collections.emptyList();
    }
    else
    {
      filterList(searchString);
      values = _filteredList;
    }

    int size = values.size();
    int maxItems = Math.min(10, size);

    List<SelectItem> items = new ArrayList<SelectItem>();
    for (int i = 0; i < maxItems; i++)
    {
      SelectItem selectItem = new SelectItem();
      EmpDataRow data = values.get(i);
      String eName = data.getEname();
      selectItem.setLabel(String.format("%-15s", eName));
      selectItem.setValue(eName);
      items.add(selectItem);
    }
    return items;
  }
  
  public List<SelectItem> suggestItems(FacesContext context, AutoSuggestUIHints hints) {
      if (context == null) {}
    String searchString = hints.getSubmittedValue();
    List<EmpDataRow> values;
    int maxSuggestedItems = hints.getMaxSuggestedItems();
    
    if (searchString == null || searchString.length() == 0)
    {
      // This code should never get executed as we don't queue the event on the client
      // when searchString length is 0
      return Collections.emptyList();
    }
    else
    {
      filterList(searchString);
      values = _filteredList;
    }

    int size = values.size();
    if(maxSuggestedItems == -1)
      maxSuggestedItems = size;
    else
      maxSuggestedItems = Math.min(maxSuggestedItems, size);
    
//    int maxItems = Math.min(10, maxSuggestedItems);

   List<SelectItem> items = new ArrayList<SelectItem>();
    for (int i = 0; i < maxSuggestedItems; i++)
    {
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
  static String _DIR_DATA[] =
  {
    "Adam",
    "Avance",
    "Abdul",
    "Blake",
    "Bob",
    "Brenta",
    "Bejond",
    "Calvin",
    "Carl"
  };

  static
  {
    _attributes.add("ename");
  }
}
