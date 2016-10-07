package test.lovwobc.model;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.view.rich.model.ColumnDescriptor;
import oracle.adf.view.rich.model.TableModel;

import org.apache.myfaces.trinidad.model.CollectionModel;

public class TableModelImpl extends TableModel {
    
    private List<? extends AttributeDef> attributes;
    private CollectionModel collectionModel;
    private List<ColumnDescriptor> descriptors;
    
    public TableModelImpl(CollectionModel collectionModel, List<? extends AttributeDef> attributes) {
        assert (collectionModel != null);
        this.attributes = attributes;
        this.collectionModel = collectionModel;
    }

    @Override
    public CollectionModel getCollectionModel() {
        return collectionModel;
    }

    @Override
    public List<ColumnDescriptor> getColumnDescriptors() {
        if (descriptors == null) {
            descriptors = new ArrayList<ColumnDescriptor>(attributes.size());
            for (AttributeDef attr : attributes) {
                descriptors.add(new ColumnDescriptorImpl(attr));
            }
        }
        return descriptors;
    }
}
