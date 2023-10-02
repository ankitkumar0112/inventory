package grpc;


import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import org.inventory.details.*;
import repository.ItemDetailsRepository;

import javax.inject.Inject;

@GrpcService
public class ItemDetailsGrpcService implements ItemDetailsGrpc{
    @Inject
    ItemDetailsRepository itemDetailsRepository;

    public ItemDetailsGrpcService(ItemDetailsRepository itemDetailsRepository) {
        this.itemDetailsRepository = itemDetailsRepository;
    }

    @Override
    public Uni<CreateItemDetailsReply> createItemDetails(CreateItemDetailsRequest request) {
        return itemDetailsRepository.createItemDetails(request)
                .onItem()
                .transform(itemDetailsDataModel1 -> CreateItemDetailsReply.newBuilder()
                        .setId(itemDetailsDataModel1.getId())
                        .build());
    }

    @Override
    public Uni<GetItemDetailsReply> getItemDetails(GetItemDetailsRequest request) {
        return itemDetailsRepository.findById(request.getId())
                .onItem()
                .ifNull()
                .failWith(() -> {
            throw new RuntimeException("Invalid ID");})
                .onItem()
                .ifNotNull()
                .transform(itemDetailsDataModel -> GetItemDetailsReply.newBuilder()
                .setDescription(itemDetailsDataModel.getDescription())
                .setName(itemDetailsDataModel.getName())
                .setMedia(itemDetailsDataModel.getMedia())
                        .setSupplierId(itemDetailsDataModel.getSupplier_id())
                .build());
    }

    @Override
    public Uni<UpdateItemDetailsReply> updateItemDetails(UpdateItemDetailsRequest request) {
        return itemDetailsRepository.findById(request.getId())
                .onItem()
                .ifNull()
                .failWith(() -> {throw new RuntimeException("Invalid ID");})
                .onItem()
                .ifNotNull()
                .transformToUni(dataModel -> itemDetailsRepository.updateItemDetails(request)
                        .onItem()
                        .transform(dataModel1 -> UpdateItemDetailsReply.newBuilder()
                                .setId(dataModel.getId()).build()));

    }

//    @Override
//    public Uni<DeleteItemDetailsReply> deleteItemDetails(DeleteItemDetailsRequest request) {
//        return itemDetailsRepository.findById(request.getId())
//                .onItem()
//                .ifNull()
//                .failWith(() -> new Throwable("Invalid ID")).onItem().ifNotNull().transformToUni(itemDetailsDataModel -> itemDetailsRepository.deleteItemDetails(itemDetailsDataModel).onItem().transform(itemDetailsDataModel1 -> DeleteItemDetailsReply.newBuilder().setMessage("Success").build()));
//    }
}
