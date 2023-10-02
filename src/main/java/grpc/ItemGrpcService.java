package grpc;

import datamodel.ItemDataModel;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import org.inventory.item.*;
import repository.ItemRepository;
import javax.inject.Inject;

@GrpcService
public class ItemGrpcService implements ItemGrpc {
    @Inject
    ItemRepository itemRepository;

    public ItemGrpcService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Uni<CreateItemReply> createItem(CreateItemRequest request) {
        return itemRepository.createItem(request)
                .onItem()
                .transform(itemDataModel -> CreateItemReply.newBuilder()
                        .setId(itemDataModel.getUnique_id())
                        .build());


    }

    @Override
    public Uni<GetItemReply> getItem(GetItemRequest request) {
        return itemRepository.findById(request.getId())
                .onItem()
                .ifNull()
                .failWith(() -> {throw new RuntimeException("Invalid Id");})
                .onItem()
                .ifNotNull()
                .transform(itemDataModel ->
                        GetItemReply.newBuilder()
                                .setCategory(itemDataModel.getCategory())
                                .setCompanyId(itemDataModel.getCompany_id())
                                .setStockId(itemDataModel.getStock_id())
                                .setCode(itemDataModel.getCode())
                                .build());

    }

    @Override
    public Uni<UpdateItemReply> updateItem(UpdateItemRequest request) {
        return itemRepository.findById(request.getUniqueId())
                .onItem().ifNull()
                .failWith(() -> {throw new RuntimeException("Invalid Id");})
                .onItem()
                .ifNotNull()
                .transformToUni(dataModel -> itemRepository.updateItem(request)
                .onItem().
                transform(dataModel1 ->
                        UpdateItemReply.newBuilder()
                                .setId(dataModel.getUnique_id()).build()));
    }

    @Override
    public Uni<DeleteItemReply> deleteItem(DeleteItemRequest request) {
        return itemRepository.findById(request.getId())
                .onItem()
                .ifNull()
                .failWith(() -> new Throwable("Invalid Id"))
                .onItem()
                .transformToUni(itemDataModel -> itemRepository.deleteItem(itemDataModel)
                        .onItem()
                        .transform(aLong -> DeleteItemReply.newBuilder()
                                .setMessage("Success")
                                .build()));
    }
}
