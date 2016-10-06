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
import org.apache.myfaces.trinidad.model.RowKeySetImpl;


public class ProgLOVBean
{
    public ProgLOVBean() {
        for (int i = 0; i < _DIR_DATA.length; i++) {
            String ename = _DIR_DATA[i];
            String rowID = String.valueOf(i);
            FileData _curRow = new FileData (ename, rowID );
            _values.add(_curRow);
        }
        _filteredList.addAll(_values);
    }

    private String _ename;
    public void setEname(String ename) {
        _ename = ename;
    }
    
    public String getEname() {
        return _ename;
    }

    public void setValues(FileData rowData) {
        if ( rowData != null) {
            setEname(rowData.getEname());
        }
    }

    public void filterList(String eName) {
        _filteredList.clear();
        if (eName == null) return;
        for (FileData data : _values) {
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

  private ListOfValuesModel _listOfValuesModel;

  public void validate(FacesContext facesContext, UIComponent uIComponent,
                       Object object)
  {
      if (facesContext == null || uIComponent == null) return;
      for(Object data : _values)
      {
        if(((FileData)data).getEname().equals(object))
        {
          return;
        }
      }
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                              "Not a Valid Value",
                                              "Not a Valid Value");
    throw new ValidatorException(message);
  }
  
  public FileData _getRowData(Object selectedRowKey)
  {
    if (selectedRowKey != null && selectedRowKey instanceof RowKeySet)
    {
      Iterator selection = ((RowKeySet) selectedRowKey).iterator();
      while (selection.hasNext())
      {
        Object rowKey = selection.next();
        Object oldRowKey = listModel.getRowKey();
        listModel.setRowKey(rowKey);
        FileData rowData = (FileData)listModel.getRowData();
        listModel.setRowKey(oldRowKey);
        return rowData;
      }
    }
    return null;
  }

    public static List<String> getAttributes() {
        return _attributes;
    }

    public List<ProgLOVBean.FileData> getFilteredList() {
        return _filteredList;
    }

    /**
     * The CollectionModel implementation for the table in the Search popup
     */
  class ListLovCollection
    extends CollectionModel
  {
    public Object getRowKey()
    {
      if (_row != null)
      {
        return _row.getRowId();
      }
      return null;
    }

    /**
     * Finds the row with the matching key and makes it current
     * @param rowKey the rowKey, previously obtained from {@link #getRowKey}.
     */
    public void setRowKey(Object rowKey)
    {
      if (rowKey == null)
      {
        _row = null;
        return;
      }

      int index = -1;
      for (int i = 0; i < _filteredList.size(); i++)
      {
        String rowId = (_filteredList.get(i)).getRowId();
        if (rowId.equals(rowKey))
        {
          index = i;
          break;
        }
      }

      setRowIndex(index);
    }

    public void setRowIndex(int rowIndex)
    {
      int size = _filteredList.size();
      if (rowIndex < 0 || rowIndex >= size || size == 0)
      {
        _row = null;
        _rowIndex = -1;
      }
      else
      {
        _row = _filteredList.get(rowIndex);
        _rowIndex = rowIndex;
      }
    }

    public int getRowIndex()
    {
      return _rowIndex;
    }

    public Object getRowData()
    {
      return _row;
    }

    public int getRowCount()
    {
      return _filteredList.size();
    }

    public boolean isRowAvailable()
    {
      return (_row != null);
    }

    public Object getRowData(int rowIndex)
    {
      int oldIndex = getRowIndex();
      try
      {
        setRowIndex(rowIndex);
        return getRowData();
      }
      finally
      {
        setRowIndex(oldIndex);
      }
    }

    public boolean isSortable(String property)
    {
      return false;
    }

    public List getSortCriteria()
    {
      return Collections.EMPTY_LIST;
    }

    public Object getWrappedData()
    {
      return ListLovCollection.this;
    }

    public void setWrappedData(Object data)
    {
      throw new UnsupportedOperationException();
    }

    FileData _row = null;
    int _rowIndex = -1;
  }

  public static class ListOfValuesModelImpl extends ListOfValuesModel
  {
    public ListOfValuesModelImpl(ProgLOVBean bean)
    {
      _bean = bean;
    }

    /**
     * Not applicable as items are only supported in comboLOV
     * @return
     */
    @Override
    public List<? extends Object> getItems()
    {
      return Collections.emptyList();
    }

    /**
     * Returns null for now.
     * @return
     */
    @Override
    public QueryModel getQueryModel()
    {
      return new QueryModelImpl();
    }

    /**
     * Not applicable as items are only supported in comboLOV
     * @return
     */
    @Override
    public List<? extends Object> getRecentItems()
    {
      return Collections.emptyList();
    }

    @Override
    public TableModel getTableModel()
    {
      return new TableModelImpl(_bean.getListModel());
    }

    @Override
    public boolean isAutoCompleteEnabled()
    {
      return false;
    }

    public void performQuery(QueryDescriptor qd)
    {
      AttributeCriterion criterion = (AttributeCriterion) qd.getConjunctionCriterion().getCriterionList().get(0);
      String ename = (String) criterion.getValues().get(0);
      _bean.filterList(ename);
    }

    public List<Object> autoCompleteValue(Object value)
    {
      // wierd way of filtering and accessing _filteredList but for now its ok
      _bean.filterList((String)value);
      if (_bean._filteredList.size() == 1)
      {
        List<Object> returnList = new ArrayList<Object>();
        FileData rowData = _bean._filteredList.get(0);
        Object rowKey = rowData.getRowId();
        RowKeySet rowKeySet = new RowKeySetImpl();
        rowKeySet.add(rowKey);
        returnList.add(rowKeySet);
        return returnList;
      }
      return Collections.emptyList();
    }

    public void valueSelected(Object value)
    {
      FileData rowData = _getRowData(value);
      if(rowData != null)
      {
        _bean.setValues(rowData);
      }
    }

    @Override
    public Object getValueFromSelection(Object selectedRowKey)
    {
        FileData rowData = _getRowData(selectedRowKey);
        if(rowData != null)
        {
          return rowData.getEname();
        }
        return null;
    }

    private FileData _getRowData(Object selectedRowKey)
    {
      if (selectedRowKey != null && selectedRowKey instanceof RowKeySet)
      {
        Iterator selection = ((RowKeySet) selectedRowKey).iterator();
        while (selection.hasNext())
        {
          Object rowKey = selection.next();
          Object oldRowKey = _bean.listModel.getRowKey();
          _bean.listModel.setRowKey(rowKey);
          FileData rowData = (FileData)_bean.listModel.getRowData();
          _bean.listModel.setRowKey(oldRowKey);
          return rowData;
        }
      }
      return null;
    }

    public QueryDescriptor getQueryDescriptor()
    {
      if(_queryDescriptor == null)
        _queryDescriptor = new QueryDescriptorImpl();
      return _queryDescriptor;
    }

    private QueryDescriptor _queryDescriptor;
    private ProgLOVBean _bean;

  }

  // Simple implementation of the QueryDescriptor classs to display one inputText
  // field to filter the data in the table inside dialog based on the Ename
  // For now return a void implementation for the querymodel to show a simple query component
  // such that the Search... link will also be displayed in the dropdown
  public static class QueryModelImpl extends QueryModel {
    public QueryDescriptor create(String name, QueryDescriptor qdBase) {return null; }

    public void delete(QueryDescriptor qd) {
    }

    public List<AttributeDescriptor> getAttributes() {
      return Collections.emptyList();
    }

    public List<QueryDescriptor> getSystemQueries() {
      return Collections.emptyList();
    }

    public List<QueryDescriptor> getUserQueries() {
      return Collections.emptyList();
    }

    public void reset(QueryDescriptor qd)
    {
    }

    public void setCurrentDescriptor(QueryDescriptor qd)
    {
    }

    public void update(QueryDescriptor qd, Map<String, Object> uiHints)
    {
    }
  }

  public static class QueryDescriptorImpl
    extends QueryDescriptor
  {
    public QueryDescriptorImpl()
    {
      _conjCriterion = new ConjunctionCriterionImpl();
    }

    public void addCriterion(String name)
    {
    }

    public void changeMode(QueryDescriptor.QueryMode mode)
    {
    }

    public ConjunctionCriterion getConjunctionCriterion()
    {
      return _conjCriterion;
    }

    public void setConjunctionCriterion(ConjunctionCriterion criterion)
    {
      _conjCriterion = criterion;
    }

    public String getName()
    {
      return null;
    }

    public Map<String, Object> getUIHints()
    {
      return new HashMap<String, Object>();
    }

    public void removeCriterion(oracle.adf.view.rich.model.Criterion object)
    {
    }

    public AttributeCriterion getCurrentCriterion()
    {
      return null;
    }

    public void setCurrentCriterion(AttributeCriterion attrCriterion)
    {
    }

    ConjunctionCriterion _conjCriterion;
  }

  public static class AttributeDescriptorImpl
    extends AttributeDescriptor
  {

    public AttributeDescriptor.ComponentType getComponentType()
    {
      return AttributeDescriptor.ComponentType.inputText;
    }

    public String getDescription()
    {
      return null;
    }

    public String getFormat()
    {
      return null;
    }

    public String getLabel()
    {
      return "Ename";
    }

    public int getLength()
    {
      return 0;
    }

    public int getMaximumLength()
    {
      return 0;
    }

    public Object getModel()
    {
      return null;
    }

    public String getName()
    {
      return null;
    }

    public Set<AttributeDescriptor.Operator> getSupportedOperators()
    {
      return Collections.emptySet();    }

    public Class getType()
    {
      return null;
    }

    public boolean isReadOnly()
    {
      return false;
    }

    public boolean isRequired()
    {
      return false;
    }
  }

  public static class ConjunctionCriterionImpl
    extends ConjunctionCriterion
  {
    public ConjunctionCriterionImpl()
    {
      _criterionList = new ArrayList<Criterion>();
      _criterionList.add(new AttributeCriterionImpl());
    }

    public ConjunctionCriterion.Conjunction getConjunction()
    {
      return ConjunctionCriterion.Conjunction.NONE;
    }

    public List<oracle.adf.view.rich.model.Criterion> getCriterionList()
    {
      return _criterionList;
    }

    public Object getKey(oracle.adf.view.rich.model.Criterion criterion)
    {
      return Integer.toString(0);
    }

    public Criterion getCriterion(Object key)
    {
      assert(_criterionList != null);
      return _criterionList.get(0);
    }

    public void setConjunction(ConjunctionCriterion.Conjunction conjunction)
    {
    }
    List<Criterion> _criterionList;
  }

  public static class AttributeCriterionImpl extends AttributeCriterion
  {
    public AttributeCriterionImpl()
    {
      if(_values == null)
      {
        _values = new ArrayList<Object>();
        _values.add("A");
      }
    }

    public AttributeDescriptor getAttribute()
    {
      return new AttributeDescriptorImpl();
    }

    public AttributeDescriptor.Operator getOperator()
    {
      return null;
    }

    public Map<String, AttributeDescriptor.Operator> getOperators()
    {
      return Collections.emptyMap();
    }

    public List<? extends Object> getValues()
    {
      return _values;
    }

    public boolean isRemovable()
    {
      return false;
    }

    public void setOperator(AttributeDescriptor.Operator operator)
    {
    }

    List<Object> _values;
  }

  public static class TableModelImpl extends TableModel
  {
    public TableModelImpl(CollectionModel collectionModel)
    {
      assert(collectionModel != null);
      _collectionModel = collectionModel;
    }
    @Override
    public CollectionModel getCollectionModel()
    {
      return _collectionModel;
    }

    @Override
    public List<ColumnDescriptor> getColumnDescriptors()
    {
      if (_descriptors == null)
      {
        _descriptors = new ArrayList<ColumnDescriptor>(_attributes.size());
        for (String attr : _attributes)
        {
          _descriptors.add (new ColumnDescriptorImpl (attr));
        }
      }
      return _descriptors;
    }

    public static class ColumnDescriptorImpl extends ColumnDescriptor {
        private String _name;
        public ColumnDescriptorImpl(String name) {
            _name = name;
        }
        @Override public String getFormat() { return null; }
        @Override public String getLabel() { return _name.toUpperCase(); }
        @Override public String getName() { return _name; }
        @Override public Class getType() { return String.class; }
        @Override public String getAlign() { return null; }
        @Override public AttributeDescriptor.ComponentType getComponentType() { return AttributeDescriptor.ComponentType.inputText; }
        @Override public String getDescription() { return null; }
        @Override public String getModel() { return null; }
        @Override public Set<AttributeDescriptor.Operator> getSupportedOperators() { return Collections.emptySet(); }
        @Override public int getLength() { return 0; }
        @Override public int getMaximumLength() { return 0; }
        @Override public boolean isRequired() { return false; }
        @Override public int getWidth() { return 0; }
        @Override public boolean isReadOnly() { return true; }
    }

    private CollectionModel _collectionModel;
    private static List<ColumnDescriptor> _descriptors;
  }

  public class FileData
  {
    private String ename;
    private String rowId;

    FileData(String ename, String rowID) {
        this.ename = ename;
        this.rowId = rowID;
    }

    public String getEname()
    {
      return ename;
    }
    public String getRowId()
    {
      return rowId;
    }
  }

  // return a pre defined list as the smart list
  public List<SelectItem> smartList()
  {
    List<SelectItem> items = new ArrayList<SelectItem>();
    for (int i=0; i < _DIR_DATA.length; i++)
    {
      SelectItem selectItem = new SelectItem();
      FileData data = _values.get(i);
      String eName = data.getEname();
      selectItem.setLabel(String.format("%-15s", eName));
      selectItem.setValue(eName);
      items.add(selectItem);
    }
    return items;
  }

  public List<SelectItem> suggestedItems(String searchString)
  {
    List<FileData> values;

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
      FileData data = values.get(i);
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
    List<FileData> values;
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
      FileData data = values.get(i);
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

  private CollectionModel listModel = new ListLovCollection();
  private List<FileData> _values = new ArrayList<FileData>();
  private List<FileData> _filteredList = new ArrayList<FileData>();
  private static List<String> _attributes = new ArrayList<String>();

  static
  {
    _attributes.add("ename");
  }
}
