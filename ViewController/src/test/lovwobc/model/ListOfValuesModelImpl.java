package test.lovwobc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.ListOfValuesModel;
import oracle.adf.view.rich.model.QueryDescriptor;
import oracle.adf.view.rich.model.QueryModel;
import oracle.adf.view.rich.model.TableModel;

import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;


public class ListOfValuesModelImpl extends ListOfValuesModel {

    private QueryDescriptor queryDescriptor;
    private BeanLOV lov;    
    
    public ListOfValuesModelImpl(BeanLOV lov) {
        this.lov = lov;
    }
    
    /**
     * Not applicable as items are only supported in comboLOV
     * @return
     */
    @Override
    public List<? extends Object> getItems() {
        ArrayList<Object> retVal = new ArrayList<Object> ();
        for (SourceDataRow row: lov.getValues()) {
            Map<String, Object> item = new HashMap<String, Object>(1);
            item.put(lov.getAttributes().get(0), row.getValue());
            retVal.add (item);
        }
        return retVal;
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
        return new ArrayList<Object>();
    }

    @Override
    public TableModel getTableModel() {
        return new TableModelImpl (lov.getListModel(), lov.getAttributes());
    }

    @Override
    public boolean isAutoCompleteEnabled() {
        return true;
    }

    public void performQuery(QueryDescriptor qd) {
        AttributeCriterion criterion = (AttributeCriterion) qd.getConjunctionCriterion().getCriterionList().get(0);
        String value = (String) criterion.getValues().get(0);
        lov.filterList (value);
    }

    public List<Object> autoCompleteValue(Object value) {
        // wierd way of filtering and accessing _filteredList but for now its ok
        lov.filterList((String)value);
        if (lov.getFilteredList().size() == 1) {
            List<Object> returnList = new ArrayList<Object>();
            SourceDataRow rowData = lov.getFilteredList().get(0);
            Object rowKey = rowData.getId();
            RowKeySet rowKeySet = new RowKeySetImpl();
            rowKeySet.add(rowKey);
            returnList.add(rowKeySet);
            return returnList;
        }
        return Collections.emptyList();
    }

    @Override
    public void valueSelected(Object value) {
        SourceDataRow rowData = getRowData(value);
        if(rowData != null) {
            lov.setSelectedValue(rowData);
        }
    }

    @Override
    public Object getValueFromSelection(Object selectedRowKey) {
        SourceDataRow rowData = getRowData(selectedRowKey);
        if(rowData != null) {
            return rowData.getValue();
        }
        return null;
    }

    private SourceDataRow getRowData(Object selectedRowKey) {
        if (selectedRowKey != null && selectedRowKey instanceof RowKeySet) {
            Iterator<Object> selection = ((RowKeySet) selectedRowKey).iterator();
            while (selection.hasNext()) {
                Object rowKey = selection.next();
                Object oldRowKey = lov.getListModel().getRowKey();
                lov.getListModel().setRowKey(rowKey);
                SourceDataRow rowData = lov.getListModel().getRowData();
                lov.getListModel().setRowKey(oldRowKey);
                return rowData;
            }
        }
        return null;
    }

    public QueryDescriptor getQueryDescriptor() {
        if(queryDescriptor == null) queryDescriptor = new QueryDescriptorImpl(lov.getAttributes().get(0));
        return queryDescriptor;
    }

}
