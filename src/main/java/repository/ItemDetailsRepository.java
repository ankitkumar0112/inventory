package repository;

import datamodel.ItemDetailsDataModel;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import org.inventory.details.CreateItemDetailsRequest;
import org.inventory.details.UpdateItemDetailsRequest;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemDetailsRepository implements PanacheRepository<ItemDetailsDataModel> {
    @ReactiveTransactional
    public Uni<ItemDetailsDataModel> createItemDetails( CreateItemDetailsRequest request) {
        ItemDetailsDataModel itemDetailsDataModel=new ItemDetailsDataModel();
        itemDetailsDataModel.setName(request.getName());
        itemDetailsDataModel.setMedia(request.getMedia());
        itemDetailsDataModel.setDescription(request.getDescription());
        itemDetailsDataModel.setSupplier_id( request.getSupplierId());
        return this.persist(itemDetailsDataModel);
    }


    @ReactiveTransactional
    public Uni<ItemDetailsDataModel> updateItemDetails( UpdateItemDetailsRequest request){
        ItemDetailsDataModel itemDetailsDataModel=new ItemDetailsDataModel();
        itemDetailsDataModel.setName(request.getName());
        itemDetailsDataModel.setDescription(request.getDescription());
        itemDetailsDataModel.setMedia(request.getMedia());
        itemDetailsDataModel.setSupplier_id(request.getSupplierId());
        return this.persist(itemDetailsDataModel);

    }

    @ReactiveTransactional
    public Uni<Long> deleteItemDetails(ItemDetailsDataModel itemDetailsDataModel){
        return this.delete("id", itemDetailsDataModel.getId());
    }
}
