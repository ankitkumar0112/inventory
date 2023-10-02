package grpc;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import org.inventory.type.*;
import repository.ItemTypeRepository;
import javax.inject.Inject;

@GrpcService
public class ItemTypeGrpcService implements ItemTypeGrpc {
    @Inject
    ItemTypeRepository itemTypeRepository;

    public ItemTypeGrpcService(ItemTypeRepository itemTypeRepository) {
        this.itemTypeRepository = itemTypeRepository;
    }

    @Override
    public Uni<CreateItemTypeReply> createItemType(CreateItemTypeRequest request) {
        return itemTypeRepository.addItemsDetails(request)
                .onItem()
                .transform(itemTypeDataModel1 -> CreateItemTypeReply.newBuilder()
                        .setId(itemTypeDataModel1.getId())
                        .build());
    }

    @Override
    public Uni<GetItemTypeReply> getItemType(GetItemTypeRequest request) {
        return itemTypeRepository.findById( request.getId())
                .onItem()
                .ifNull()
                .failWith(() -> {throw new RuntimeException("Invalid ID");})
                .onItem()
                .ifNotNull()
                .transform(itemTypeDataModel -> GetItemTypeReply.newBuilder()
                        .setClassificationTag(itemTypeDataModel.getClassificationTag())
                        .setDescription(itemTypeDataModel.getDescription())
                        .setName(itemTypeDataModel.getName())
                        .build());

    }
    @Override
    public Uni<GetItemTypeReply1> getItemTypeNew(GetItemTypeRequest request) {
        return itemTypeRepository.findById( request.getId())
                .onItem()
                .ifNull()
                .failWith(() -> {
                    throw new RuntimeException("Invalid ID");
                })
                .onItem()
                .ifNotNull()
                .transform(itemTypeDataModel -> GetItemTypeReply1.newBuilder()
                        .setClassificationTag(itemTypeDataModel.getClassificationTag())
                        .setDescription(itemTypeDataModel.getDescription())
                        .setName(itemTypeDataModel.getName())
                        .setClassificationTag1(itemTypeDataModel.getClassificationDataModel().getTag())
                        .setName1(itemTypeDataModel.getClassificationDataModel().getName())
                        .setDescription1(itemTypeDataModel.getClassificationDataModel().getDescription())
                        .build());
    }


    @Override
    public Uni<UpdateItemTypeReply> updateItemType(UpdateItemTypeRequest request) {
        return itemTypeRepository.findById( request.getId())
                .onItem()
                .ifNull()
                .failWith(() -> {
            throw new RuntimeException("Invalid Id");})
                .onItem()
                .transformToUni(itemTypeDataModel -> itemTypeRepository.updateDetails(request)
                        .onItem()
                        .transform(itemTypeDataModel1 -> UpdateItemTypeReply.newBuilder()
                                .setId(itemTypeDataModel.getId())
                                .build()));

    }

    @Override
    public Uni<DeleteItemTypeReply> deleteItemType(DeleteItemTypeRequest request) {
        return itemTypeRepository.findById(request.getId())
                .onItem()
                .ifNull()
                .failWith(() -> {throw new RuntimeException("Invalid Id");})
                .onItem()
                .transformToUni(count -> itemTypeRepository.deleteItemDetails(count)
                        .onItem()
                        .transform(c -> DeleteItemTypeReply.newBuilder().setMessage("Success").build()));
    }
}
