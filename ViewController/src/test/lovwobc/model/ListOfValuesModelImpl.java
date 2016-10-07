package test.lovwobc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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

    LinkedHashMap<SourceDataRow, Boolean> recentItems = new LinkedHashMap<SourceDataRow, Boolean> () {
        protected boolean removeEldestEntry(Map.Entry<SourceDataRow, Boolean> eldest) {
            return size() > lov.getMRUSize();
        }
    };
    private QueryDescriptor queryDescriptor;
    private BeanLOV lov;    
    
    public ListOfValuesModelImpl(BeanLOV lov) {
        this.lov = lov;
    }
    
    @Override
    public List<? extends Object> getItems() {
        ArrayList<Object> retVal = new ArrayList<Object> ();
        for (SourceDataRow row: lov.getValues()) {
            Map<String, Object> item = new HashMap<String, Object>(1);
            item.put(lov.getAttributes().get(0).getAttributeName(), row.getValue());
            retVal.add (item);
        }
        return retVal;
    }

    @Override
    public QueryModel getQueryModel() {
        return new QueryModelImpl();
    }

    @Override
    public List<? extends Object> getRecentItems() {
        LinkedList<Object> retVal = new LinkedList<Object> ();
        if (recentItems.size() <= 0) return retVal;
        for (Map.Entry<SourceDataRow, Boolean> item: recentItems.entrySet()) {
            Map<String, Object> retItem = new HashMap<String, Object>(1);
            retItem.put(lov.getAttributes().get(0).getAttributeName(), item.getKey().getValue());
            retVal.addFirst(retItem);
        }
        // work around apparent bug in ADF -- last item in recentItems list is rendered 
        // as separator, has height of 3px and cannot be selected. Add dummy null item to 
        // recentItems list, to serve as separator
        if (retVal.size() > 0) {
            Map<String, Object> retItem = new HashMap<String, Object>(1);
            retItem.put(lov.getAttributes().get(0).getAttributeName(), null);
            retVal.addLast(retItem);
        }
        return retVal;
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
            lov.setSelectedValue (rowData);
            recentItems.remove (rowData); // make last selected item last-inserted
            recentItems.put (rowData,true);
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
        if (selectedRowKey == null) return null;
        if (selectedRowKey instanceof RowKeySet) {
            Iterator<Object> selection = ((RowKeySet) selectedRowKey).iterator();
            while (selection.hasNext()) {
                Object rowKey = selection.next();
                Object oldRowKey = lov.getListModel().getRowKey();
                lov.getListModel().setRowKey(rowKey);
                SourceDataRow rowData = lov.getListModel().getRowData();
                lov.getListModel().setRowKey(oldRowKey);
                return rowData;
            }
        } else if (selectedRowKey instanceof List<?>) {
            Object item = ((List<?>)selectedRowKey).get(0);
            if (item instanceof HashMap<?,?>) {
                HashMap<String,Object> itemMap = (HashMap<String,Object>)item;
                for (SourceDataRow row: lov.getValues()) {
                    if (row.getValue().equals(itemMap.get(lov.getAttributes().get(0).getAttributeName()))) {
                        return row;
                    }
                }
            }
        }
        return null;
    }

    public QueryDescriptor getQueryDescriptor() {
        if(queryDescriptor == null) queryDescriptor = new QueryDescriptorImpl(lov.getAttributes().get(0));
        return queryDescriptor;
    }

}
