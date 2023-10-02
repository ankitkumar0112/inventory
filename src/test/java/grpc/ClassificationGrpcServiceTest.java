package grpc;

import datamodel.*;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.smallrye.mutiny.Uni;
import org.inventory.classification.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ClassificationRepository;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
class ClassificationGrpcServiceTest {

    String name = "Electronics";
    String description = "Electronics Item";
    String tag = "Elex";
    long id = 1;


    @GrpcClient
    ClassificationGrpc classificationGrpc;
    @InjectMock
    ClassificationRepository classificationRepository;

    ClassificationDataModel dataModel;
    ItemTypeDataModel itemTypeDataModel;
    ItemDataModel itemDataModel;
    ItemDetailsDataModel itemDetailsDataModel;
    StockDataModel stockDataModel;

    @BeforeEach
    void setUp() {
        dataModel = new ClassificationDataModel();
        itemTypeDataModel = new ItemTypeDataModel();
        itemDataModel = new ItemDataModel();
        itemDetailsDataModel = new ItemDetailsDataModel();
        stockDataModel = new StockDataModel();
    }

    @Test
    void createDetails() {
        dataModel.setId(id);
        dataModel.setName(name);
        when(classificationRepository.createDetails(any()))
                .thenReturn(Uni.createFrom().item(dataModel));
        Uni<CreateClassificationReply> response = classificationGrpc.createDetails(CreateClassificationRequest.newBuilder().setName(name).build());
        Assertions.assertEquals(id, response.await().indefinitely().getId());
    }

    @Test
    void getDetails() {
        dataModel.setId(10L);
        dataModel.setName("Electronics");
        dataModel.setDescription("Electronics Item");
        dataModel.setTag("Elex");
        dataModel.setItemTypeDataModel(itemTypeDataModel);
        itemTypeDataModel.setItemDataModel(itemDataModel);
        itemDataModel.setItemDetailsDataModel(itemDetailsDataModel);
        itemDataModel.setStockDataModel(stockDataModel);


        when(classificationRepository.findById(10L))
                .thenReturn(Uni.createFrom().item(dataModel));

        Uni<GetClassificationReply> response = classificationGrpc.getDetails(GetClassificationRequest.newBuilder().setId(10L).build());
        assertEquals(name, response.await().indefinitely().getName());
        assertEquals(description, response.await().indefinitely().getDescription());
        assertEquals(tag, response.await().indefinitely().getTag());


    }

//    @Test
//    void getAllDetails() {
//        dataModel.setId(10L);
//        dataModel.setName("Electronics");
//        dataModel.setDescription("Electronics Item");
//        dataModel.setTag("Elex");
//        itemDataModel.setCode(10L);
//
//        itemTypeDataModel.setName("Electronics");
//        itemTypeDataModel.setDescription("Electronics Item");
//        itemTypeDataModel.setClassificationTag("Elex");
//        itemDataModel.setCode(10L);
//        itemDetailsDataModel.setMedia("Hard Disk");
//        stockDataModel.setAvailable_qty(2L);
//        dataModel.setItemTypeDataModel(itemTypeDataModel);
//        itemTypeDataModel.setItemDataModel(itemDataModel);
//        itemDataModel.setItemDetailsDataModel(itemDetailsDataModel);
//        itemDataModel.setStockDataModel(stockDataModel);
//        stockDataModel.setItemDataModel(itemDataModel);
//        itemDetailsDataModel.setItemDataModel(itemDataModel);
//        itemDataModel.setItemType(itemTypeDataModel);
//        itemTypeDataModel.setClassificationDataModel(dataModel);
////        itemDataModel.setItemType(dataModel.getItemTypeDataModel());
////        itemDetailsDataModel.setItemDataModel(dataModel.getItemTypeDataModel().getItemDataModel());
//
////        stockDataModel.setItemDataModel(itemDataModel);
////        itemTypeDataModel.setClassificationDataModel(dataModel);
//
//
//        when(classificationRepository.findById(10L)).thenReturn(Uni.createFrom().item(dataModel));
//
//        Uni<GetClassificationReplyNew> response = classificationGrpc.getAllDetails(GetClassificationRequestNew.newBuilder().setId(10L).build());
//
//        assertEquals("Electronics", response.await().indefinitely().getName());
////        assertEquals("Electronics Item", response.await().indefinitely().getDescription());
////        assertEquals("Elex", response.await().indefinitely().getTag());
////        assertEquals("Elex", response.await().indefinitely().getItemType().getClassificationTag());
//
//
//    }

    @Test
    void updateDetails() {
        dataModel.setId(10L);
        dataModel.setName("HealthCare");
        when(classificationRepository.findById(10L)).thenReturn(Uni.createFrom().item(dataModel));

        Uni<UpdateClassificationReply> response = classificationGrpc.updateDetails(UpdateClassificationRequest.newBuilder().setId(10L).setName("HealthCare").setTag("HC").build());
        assertEquals(10L, response.await().indefinitely().getId());

    }

    @Test
    void deleteDetails() {
        dataModel.setId(10L);
        when(classificationRepository.findById(10L)).thenReturn(Uni.createFrom().item(dataModel));
        Uni<DeleteClassificationReply> response = classificationGrpc.deleteDetails(DeleteClassificationRequest.newBuilder()
                .setId(10L)
                .build());
        assertEquals("Success", response.await().indefinitely().getMessage());
    }
}






