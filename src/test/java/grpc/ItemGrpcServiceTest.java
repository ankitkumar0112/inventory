package grpc;

import datamodel.ItemDataModel;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.smallrye.mutiny.Uni;
import org.inventory.item.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ItemRepository;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
class ItemGrpcServiceTest {

    @GrpcClient
    ItemGrpc itemGrpc;

    @InjectMock
    ItemRepository itemRepository;

    long id=10L;

    ItemDataModel itemDataModel;
    @BeforeEach
    void setUp(){
       itemDataModel=new ItemDataModel();
    }

    @Test
    void createItem() {
        itemDataModel.setCompany_id(20L);
        itemDataModel.setUnique_id(id);
        when(itemRepository.createItem(any())).thenReturn(Uni.createFrom().item(itemDataModel));
        Uni<CreateItemReply> response = itemGrpc.createItem(CreateItemRequest.newBuilder().setCompanyId(20L).build());
        assertEquals(id,response.await().indefinitely().getId());

    }

    @Test
    void getItem() {
        itemDataModel.setUnique_id(id);
        itemDataModel.setCategory("Goods");
        when(itemRepository.findById(id)).thenReturn(Uni.createFrom().item(itemDataModel));

        Uni<GetItemReply> response = itemGrpc.getItem(GetItemRequest.newBuilder()
                .setId(id)
                .build());
        assertEquals("Goods",response.await().indefinitely().getCategory());
    }

    @Test
    void updateItem() {
        itemDataModel.setUnique_id(10L);
        itemDataModel.setCategory("Videos");
        when(itemRepository.findById(id)).thenReturn(Uni.createFrom().item(itemDataModel));
        Uni<UpdateItemReply> response = itemGrpc.updateItem(UpdateItemRequest.newBuilder().setCompanyId(10L).setUniqueId(10L).build());
        assertEquals(10L,response.await().indefinitely().getId());
    }

    @Test
    void deleteItem() {
        itemDataModel.setUnique_id(10L);
        when(itemRepository.findById(id)).thenReturn(Uni.createFrom().item(itemDataModel));
        Uni<DeleteItemReply> response = itemGrpc.deleteItem(DeleteItemRequest.newBuilder().setId(10L).build());
        assertEquals("Success",response.await().indefinitely().getMessage());

    }
}