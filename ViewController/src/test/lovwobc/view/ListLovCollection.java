package test.lovwobc.view;

import java.util.Collections;
import java.util.List;

import org.apache.myfaces.trinidad.model.CollectionModel;

class ListLovCollection extends CollectionModel {
    private final ProgLOVBean bean;

    ListLovCollection(ProgLOVBean bean) {
        this.bean = bean;
    }

    public Object getRowKey() {
        if (_row != null) {
            return _row.getRowId();
        }
        return null;
    }

    /**
     * Finds the row with the matching key and makes it current
     * @param rowKey the rowKey, previously obtained from {@link #getRowKey}.
     */
    public void setRowKey(Object rowKey) {
        if (rowKey == null) {
            _row = null;
            return;
        }

        int index = -1;
        for (int i = 0; i < bean.getFilteredList().size(); i++) {
            String rowId = (bean.getFilteredList().get(i)).getRowId();
            if (rowId.equals(rowKey)) {
                index = i;
                break;
            }
        }

        setRowIndex(index);
    }

    public void setRowIndex(int rowIndex) {
        int size = bean.getFilteredList().size();
        if (rowIndex < 0 || rowIndex >= size || size == 0) {
            _row = null;
            _rowIndex = -1;
        } else {
            _row = bean.getFilteredList().get(rowIndex);
            _rowIndex = rowIndex;
        }
    }

    public int getRowIndex() {
        return _rowIndex;
    }

    public Object getRowData() {
        return _row;
    }

    public int getRowCount() {
        return bean.getFilteredList().size();
    }

    public boolean isRowAvailable() {
        return (_row != null);
    }

    public Object getRowData(int rowIndex) {
        int oldIndex = getRowIndex();
        try {
            setRowIndex(rowIndex);
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

    EmpDataRow _row = null;
    int _rowIndex = -1;
}
