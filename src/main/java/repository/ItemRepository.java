package repository;

import datamodel.ItemDataModel;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import org.inventory.item.CreateItemRequest;
import org.inventory.item.UpdateItemRequest;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemRepository implements PanacheRepository<ItemDataModel> {
    @ReactiveTransactional
    public Uni<ItemDataModel> createItem(CreateItemRequest request) {
        ItemDataModel itemDataModel=new ItemDataModel();
        itemDataModel.setCode( request.getCode());
        itemDataModel.setCategory(request.getCategory());
        itemDataModel.setCompany_id( request.getCompanyId());
        return this.persist(itemDataModel);
    }


    @ReactiveTransactional
    public Uni<ItemDataModel> updateItem( UpdateItemRequest request){
        ItemDataModel itemDataModel=new ItemDataModel();
        itemDataModel.setCode( request.getCode());
        itemDataModel.setCategory(request.getCategory());
        itemDataModel.setCompany_id(request.getCompanyId());
        return this.persist(itemDataModel);

    }

    @ReactiveTransactional
    public Uni<Long> deleteItem(ItemDataModel itemDataModel){
        return this.delete("id", itemDataModel.getUnique_id());
    }
}
