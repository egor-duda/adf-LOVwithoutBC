package test.lovwobc.model;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.view.rich.model.ColumnDescriptor;
import oracle.adf.view.rich.model.TableModel;

import org.apache.myfaces.trinidad.model.CollectionModel;

public class TableModelImpl extends TableModel {
    public TableModelImpl(CollectionModel collectionModel) {
        assert (collectionModel != null);
        _collectionModel = collectionModel;
    }

    @Override
    public CollectionModel getCollectionModel() {
        return _collectionModel;
    }

    @Override
    public List<ColumnDescriptor> getColumnDescriptors() {
        if (_descriptors == null) {
            _descriptors = new ArrayList<ColumnDescriptor>(ProgLOVBean.getAttributes().size());
            for (String attr : ProgLOVBean.getAttributes()) {
                _descriptors.add(new ColumnDescriptorImpl(attr));
            }
        }
        return _descriptors;
    }

    private CollectionModel _collectionModel;
    private static List<ColumnDescriptor> _descriptors;
}
