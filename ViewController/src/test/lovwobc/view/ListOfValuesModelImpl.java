package test.lovwobc.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.ListOfValuesModel;
import oracle.adf.view.rich.model.QueryDescriptor;
import oracle.adf.view.rich.model.QueryModel;
import oracle.adf.view.rich.model.TableModel;

import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class ListOfValuesModelImpl extends ListOfValuesModel {
    
    public ListOfValuesModelImpl(ProgLOVBean bean) {
      _bean = bean;
    }
    
    /**
     * Not applicable as items are only supported in comboLOV
     * @return
     */
    @Override
    public List<? extends Object> getItems() {
      return Collections.emptyList();
    }

    /**
     * Returns null for now.
     * @return
     */
    @Override
    public QueryModel getQueryModel() {
      return new QueryModelImpl();
    }

    /**
     * Not applicable as items are only supported in comboLOV
     * @return
     */
    @Override
    public List<? extends Object> getRecentItems() {
      return Collections.emptyList();
    }

    @Override
    public TableModel getTableModel() {
      return new TableModelImpl(_bean.getListModel());
    }

    @Override
    public boolean isAutoCompleteEnabled() {
      return false;
    }

    public void performQuery(QueryDescriptor qd) {
      AttributeCriterion criterion = (AttributeCriterion) qd.getConjunctionCriterion().getCriterionList().get(0);
      String ename = (String) criterion.getValues().get(0);
      _bean.filterList(ename);
    }

    public List<Object> autoCompleteValue(Object value) {
      // wierd way of filtering and accessing _filteredList but for now its ok
      _bean.filterList((String)value);
      if (_bean.getFilteredList().size() == 1) {
        List<Object> returnList = new ArrayList<Object>();
        EmpDataRow rowData = _bean.getFilteredList().get(0);
        Object rowKey = rowData.getRowId();
        RowKeySet rowKeySet = new RowKeySetImpl();
        rowKeySet.add(rowKey);
        returnList.add(rowKeySet);
        return returnList;
      }
      return Collections.emptyList();
    }

    public void valueSelected(Object value) {
      EmpDataRow rowData = _getRowData(value);
      if(rowData != null) {
        _bean.setValues(rowData);
      }
    }

    @Override
    public Object getValueFromSelection(Object selectedRowKey) {
        EmpDataRow rowData = _getRowData(selectedRowKey);
        if(rowData != null) {
          return rowData.getEname();
        }
        return null;
    }

    private EmpDataRow _getRowData(Object selectedRowKey) {
      if (selectedRowKey != null && selectedRowKey instanceof RowKeySet) {
        Iterator<Object> selection = ((RowKeySet) selectedRowKey).iterator();
        while (selection.hasNext()) {
          Object rowKey = selection.next();
          Object oldRowKey = _bean.getListModel().getRowKey();
          _bean.getListModel().setRowKey(rowKey);
          EmpDataRow rowData = (EmpDataRow)_bean.getListModel().getRowData();
          _bean.getListModel().setRowKey(oldRowKey);
          return rowData;
        }
      }
      return null;
    }

    public QueryDescriptor getQueryDescriptor() {
      if(_queryDescriptor == null)
        _queryDescriptor = new QueryDescriptorImpl();
      return _queryDescriptor;
    }

    private QueryDescriptor _queryDescriptor;
    private ProgLOVBean _bean;    
}
