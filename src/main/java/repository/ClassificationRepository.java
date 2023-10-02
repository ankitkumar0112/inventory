package repository;

import datamodel.*;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import org.inventory.classification.CreateClassificationRequest;
import org.inventory.classification.UpdateClassificationRequest;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClassificationRepository implements PanacheRepository<ClassificationDataModel> {

    @ReactiveTransactional
    public Uni<ClassificationDataModel> createDetails(CreateClassificationRequest request) {
        ItemTypeDataModel itemTypeDataModel = new ItemTypeDataModel();
        ClassificationDataModel classificationDataModel = new ClassificationDataModel();
        classificationDataModel.setName(request.getName());
        classificationDataModel.setTag(request.getTag());
        classificationDataModel.setDescription(request.getDescription());


        //ItemType
        itemTypeDataModel.setName(request.getItemType().getName());
        itemTypeDataModel.setDescription(request.getItemType().getDescription());
        itemTypeDataModel.setClassificationTag(request.getItemType().getClassificationTag());

        //Items
        ItemDataModel itemDataModel = new ItemDataModel();
        itemDataModel.setCode(request.getItemType().getItem().getCode());
        itemDataModel.setCategory(request.getItemType().getItem().getCategory());
        itemDataModel.setCompany_id(request.getItemType().getItem().getCompanyId());
//        itemDataModel.setStock_id(request.getItemType().getItem().getStockId());

        //ItemDetails
        ItemDetailsDataModel itemDetailsDataModel = new ItemDetailsDataModel();
        itemDetailsDataModel.setName(request.getItemType().getItem().getItemDetails().getName());
        itemDetailsDataModel.setDescription(request.getItemType().getItem().getItemDetails().getDescription());
        itemDetailsDataModel.setSupplier_id(request.getItemType().getItem().getItemDetails().getSupplierId());
        itemDetailsDataModel.setMedia(request.getItemType().getItem().getItemDetails().getMedia());
        itemDetailsDataModel.setItem_id(itemDataModel.getUnique_id());

        //Stock
        StockDataModel stockDataModel = new StockDataModel();
        stockDataModel.setLocation_code(request.getItemType().getItem().getStock().getLocationCode());
        stockDataModel.setAvailable_qty(request.getItemType().getItem().getStock().getAvailableQty());
        itemDataModel.setStock_id(request.getItemType().getItem().getStockId());


        classificationDataModel.setItemTypeDataModel(itemTypeDataModel);
        itemTypeDataModel.setItemDataModel(itemDataModel);
        itemDataModel.setItemDetailsDataModel(itemDetailsDataModel);
        itemDataModel.setStockDataModel(stockDataModel);
        itemDataModel.setItemType(itemTypeDataModel);
        stockDataModel.setItemDataModel(itemDataModel);
        itemTypeDataModel.setClassificationDataModel(classificationDataModel);
        return this.persist(classificationDataModel);

    }

    @ReactiveTransactional
    public Uni<ClassificationDataModel> updateDetails(ClassificationDataModel classificationDataModel, UpdateClassificationRequest request) {
        classificationDataModel.setName(request.getName());
        classificationDataModel.setDescription(request.getDescription());
        classificationDataModel.setTag(request.getTag());
        return this.persist(classificationDataModel);

    }

    @ReactiveTransactional
    public Uni<Long> deleteDetails(ClassificationDataModel classificationDataModel) {
        return this.delete("id", classificationDataModel.getId());
    }

}
