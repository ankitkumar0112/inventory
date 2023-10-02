package repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import datamodel.ItemTypeDataModel;
import org.inventory.type.CreateItemTypeRequest;
import org.inventory.type.UpdateItemTypeRequest;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemTypeRepository implements PanacheRepository<ItemTypeDataModel> {
    @ReactiveTransactional
    public Uni<ItemTypeDataModel> addItemsDetails(CreateItemTypeRequest request){
        ItemTypeDataModel itemTypeDataModel=new ItemTypeDataModel();
        itemTypeDataModel.setName(request.getName());
        itemTypeDataModel.setClassificationTag(request.getClassificationTag());
        itemTypeDataModel.setDescription(request.getDescription());
        return this.persist(itemTypeDataModel);
    }

    @ReactiveTransactional
    public Uni<ItemTypeDataModel> updateDetails(UpdateItemTypeRequest request){
        ItemTypeDataModel itemTypeDataModel=new ItemTypeDataModel();
        itemTypeDataModel.setName(request.getName());
        itemTypeDataModel.setClassificationTag(request.getClassificationTag());
        itemTypeDataModel.setDescription(request.getDescription());
        return this.persist(itemTypeDataModel);
    }

    @ReactiveTransactional
    public Uni<Long> deleteItemDetails(ItemTypeDataModel itemTypeDataModel){
        return this.delete("id",itemTypeDataModel.getId());
    }
}
