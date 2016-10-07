package test.lovwobc.model;

import java.util.Collections;
import java.util.List;

import org.apache.myfaces.trinidad.model.CollectionModel;

class ListLovCollection extends CollectionModel {
    private final BeanLOV lov;
    
    private SourceDataRow row = null;
    private int rowIndex = -1;

    ListLovCollection(BeanLOV beanLOV) {
        this.lov = beanLOV;
    }

    public Object getRowKey() {
        return (row != null ? row.getId() : null);
    }

    public void setRowKey(Object rowKey) {
        int index = -1;
        for (int i = 0; i < lov.getFilteredList().size(); i++) {
            Object rowId = (lov.getFilteredList().get(i)).getId();
            if (rowKey == null && rowId == null || rowKey != null && rowKey.equals(rowId)) {
                index = i;
                break;
            }
        }
        setRowIndex(index);
    }

    public void setRowIndex(int index) {
        int size = lov.getFilteredList().size();
        if (index < 0 || index >= size || size == 0) {
            row = null;
            rowIndex = -1;
        } else {
            row = lov.getFilteredList().get(index);
            rowIndex = index;
        }
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public SourceDataRow getRowData() {
        return row;
    }

    public int getRowCount() {
        return lov.getFilteredList().size();
    }

    public boolean isRowAvailable() {
        return (row != null);
    }

    public SourceDataRow getRowData(int index) {
        int oldIndex = getRowIndex();
        try {
            setRowIndex(index);
            return getRowData();
        } finally {
            setRowIndex(oldIndex);
        }
    }

    public boolean isSortable(String property) {
        return false;
    }

    
    public @SuppressWarnings ( "rawtypes" ) List getSortCriteria() {
        return Collections.EMPTY_LIST;
    }

    public Object getWrappedData() {
        return ListLovCollection.this;
    }

    public void setWrappedData(Object data) {
        throw new UnsupportedOperationException();
    }
}
