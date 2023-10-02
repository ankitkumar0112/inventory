package grpc;

import datamodel.ClassificationDataModel;
import datamodel.ItemDataModel;
import datamodel.ItemTypeDataModel;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.smallrye.mutiny.Uni;
import org.inventory.type.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ItemTypeRepository;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
class ItemTypeGrpcServiceTest {

    @GrpcClient
    ItemTypeGrpc itemTypeGrpc;

    @InjectMock
    ItemTypeRepository itemTypeRepository;

    ItemTypeDataModel itemTypeDataModel;
    ClassificationDataModel classificationDataModel;
    ItemDataModel itemDataModel;

    long id=10L;
    String name="Dell";

    @BeforeEach
    void setUp(){
       itemTypeDataModel=new ItemTypeDataModel();
       itemDataModel=new ItemDataModel();
       classificationDataModel=new ClassificationDataModel();
    }

    @Test
    void createItemType() {
        itemTypeDataModel.setId(10L);
        itemTypeDataModel.setName("Hp");
        when(itemTypeRepository.addItemsDetails(any())).thenReturn(Uni.createFrom().item(itemTypeDataModel));
        Uni<CreateItemTypeReply> response = itemTypeGrpc.createItemType(CreateItemTypeRequest.newBuilder().setName(name).build());
        Assertions.assertEquals(10L,response.await().indefinitely().getId());

    }

    @Test
    void getItemType() {
        itemTypeDataModel.setId(10L);
        itemTypeDataModel.setName("Hp");
        itemTypeDataModel.setClassificationTag("Abc");
        itemTypeDataModel.setDescription("Description");
        classificationDataModel.setName("Abc");
        itemTypeDataModel.setItemDataModel(itemTypeDataModel.getItemDataModel());
        itemTypeDataModel.setClassificationDataModel(itemTypeDataModel.getClassificationDataModel());
        when(itemTypeRepository.findById(10L)).thenReturn(Uni.createFrom().item(itemTypeDataModel));

        Uni<GetItemTypeReply> response = itemTypeGrpc.getItemType(GetItemTypeRequest.newBuilder()
                .setId(id)
                .build());
        assertEquals("Hp",response.await().indefinitely().getName());
        assertEquals("Abc",response.await().indefinitely().getClassificationTag());
        assertEquals("Description",response.await().indefinitely().getDescription());
    }

//    @Test
//    void getItemTypeNew() {
//        itemTypeDataModel.setId(10L);
//        itemTypeDataModel.setName("Hp");
//        itemTypeDataModel.setDescription("Description");
//        itemTypeDataModel.setClassificationTag("Tag");
//        itemTypeDataModel.setItemDataModel(itemTypeDataModel.getItemDataModel());
//        itemTypeDataModel.setClassificationDataModel(itemTypeDataModel.getClassificationDataModel());
//
//        when(itemTypeRepository.findById(id)).thenReturn(Uni.createFrom().item(itemTypeDataModel));
//        Uni<GetItemTypeReply1> response = itemTypeGrpc.getItemTypeNew(GetItemTypeRequest.newBuilder().setId(10L).build());
//        Assertions.assertEquals("Hp",response.await().indefinitely().getName());
//        Assertions.assertEquals("Description",response.await().indefinitely().getDescription());
//    }

    @Test
    void updateItemType() {
        itemTypeDataModel.setId(10L);
        itemTypeDataModel.setName("Hp");
        when(itemTypeRepository.findById(10L)).thenReturn(Uni.createFrom().item(itemTypeDataModel));
        Uni<UpdateItemTypeReply> response = itemTypeGrpc.updateItemType(UpdateItemTypeRequest.newBuilder().setName(name).setId(10L).build());
        Assertions.assertEquals(10L,response.await().indefinitely().getId());
    }

    @Test
    void deleteItemType() {
        when(itemTypeRepository.findById(id)).thenReturn(Uni.createFrom().item(itemTypeDataModel));
        Uni<DeleteItemTypeReply> response = itemTypeGrpc.deleteItemType(DeleteItemTypeRequest.newBuilder().setId(10L).build());
        assertEquals("Success",response.await().indefinitely().getMessage());
    }
}