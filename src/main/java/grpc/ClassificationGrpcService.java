package grpc;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import org.inventory.classification.*;
import org.inventory.details.GetItemDetailsReply;
import org.inventory.item.GetItemReply;
import org.inventory.stock.GetStockReply;
import org.inventory.type.GetItemTypeReply;
import repository.ClassificationRepository;
import javax.inject.Inject;

@GrpcService
public class ClassificationGrpcService implements ClassificationGrpc{

    @Inject
    ClassificationRepository classificationRepository;

    public ClassificationGrpcService(ClassificationRepository classificationRepository) {
        this.classificationRepository = classificationRepository;
    }

    @Override
    public Uni<CreateClassificationReply> createDetails(CreateClassificationRequest request) {
        return classificationRepository.createDetails(request)
                .onItem()
                .transform(classificationDataModel1 -> CreateClassificationReply.newBuilder()
                        .setId(classificationDataModel1.getId()).build());
    }

    @Override
    public Uni<GetClassificationReply> getDetails(GetClassificationRequest request) {
        return classificationRepository.findById(request.getId())
                .onItem()
                .ifNull()
                .failWith(() -> {
                    throw new RuntimeException("Invalid Id");
                })
                .onItem()
                .ifNotNull()
                .transform(classificationDataModel -> GetClassificationReply.newBuilder()
                        .setDescription(classificationDataModel.getDescription())
                        .setTag(classificationDataModel.getTag())
                        .setName(classificationDataModel.getName())
                        .build());
    }

    @Override
    public Uni<GetClassificationReplyNew> getAllDetails(GetClassificationRequestNew requestNew) {
        return classificationRepository.findById(requestNew.getId())
                .onItem()
                .ifNull()
                .failWith(() -> {
                    throw new RuntimeException("Invalid Id");
                })
                .onItem()
                .ifNotNull()
                .transform(classificationDataModel -> GetClassificationReplyNew.newBuilder()
                        .setName(classificationDataModel.getName())
                        .setDescription(classificationDataModel.getDescription())
                        .setTag(classificationDataModel.getTag())
                        .setItemType(GetItemTypeReply.newBuilder()
                                .setName(classificationDataModel.getItemTypeDataModel().getName())
                                .setDescription(classificationDataModel.getItemTypeDataModel().getDescription())
                                .setClassificationTag(classificationDataModel.getItemTypeDataModel().getClassificationTag())
                                .build())
                        .setItemReply(GetItemReply.newBuilder()
                                .setCode(classificationDataModel.getItemTypeDataModel().getItemDataModel().getCode())
                                .setCategory(classificationDataModel.getItemTypeDataModel().getItemDataModel().getCategory())
                                .setCompanyId(classificationDataModel.getItemTypeDataModel().getItemDataModel().getCompany_id())
                                .setStockId(classificationDataModel.getItemTypeDataModel().getItemDataModel().getStock_id())
                                .build())
                        .setItemDetails(GetItemDetailsReply.newBuilder()
                                .setName(classificationDataModel.getItemTypeDataModel().getItemDataModel().getItemDetailsDataModel().getName())
                                .setMedia(classificationDataModel.getItemTypeDataModel().getItemDataModel().getItemDetailsDataModel().getMedia())
                                .setSupplierId(classificationDataModel.getItemTypeDataModel().getItemDataModel().getItemDetailsDataModel().getSupplier_id())
                                .setDescription(classificationDataModel.getItemTypeDataModel().getItemDataModel().getItemDetailsDataModel().getDescription())
                                .build())
                        .setStock(GetStockReply.newBuilder().setAvailableQty(classificationDataModel.getItemTypeDataModel().getItemDataModel().getStockDataModel().getAvailable_qty()).setLocationCode(classificationDataModel.getItemTypeDataModel().getItemDataModel().getStockDataModel().getLocation_code()).build())
                        .build());

    }




    @Override
    public Uni<UpdateClassificationReply> updateDetails(UpdateClassificationRequest request) {
        return classificationRepository.findById( request.getId())
                .onItem()
                .ifNull()
                .failWith(() -> {
                    throw new RuntimeException("Invalid Id");
                })
                .chain(classificationDataModel -> classificationRepository.updateDetails(classificationDataModel, request)
                        .onItem().transform(classificationDataModel1 -> UpdateClassificationReply.newBuilder().setId(classificationDataModel.getId()).build())
                );
    }

    @Override
    public Uni<DeleteClassificationReply> deleteDetails(DeleteClassificationRequest request) {
        return classificationRepository.findById( request.getId())
                .onItem().ifNull().failWith(() -> {
                    throw new RuntimeException("Invalid Id");
                })
                .onItem()
                .ifNotNull()
                .transformToUni(count -> classificationRepository.deleteDetails(count)
                        .onItem()
                        .transform(c -> DeleteClassificationReply.newBuilder().setMessage("Success").build()));
    }


}
