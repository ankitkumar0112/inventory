package grpc;

import datamodel.ItemDetailsDataModel;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.smallrye.mutiny.Uni;
import org.inventory.classification.UpdateClassificationReply;
import org.inventory.classification.UpdateClassificationRequest;
import org.inventory.details.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ItemDetailsRepository;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
class ItemDetailsGrpcServiceTest {
    String name = "Electronics";
    String description = "Electronics Item";
    String media = "Videos";
    long id = 10L;

    @GrpcClient
    ItemDetailsGrpc itemDetailsGrpc;
    @InjectMock
    ItemDetailsRepository itemDetailsRepository;

    ItemDetailsDataModel itemDetailsDataModel;

    @BeforeEach
    void setUp() {
        itemDetailsDataModel = new ItemDetailsDataModel();

    }

    @Test
    void createItemDetails() {
        itemDetailsDataModel.setId(10L);
        itemDetailsDataModel.setName(name);
        when(itemDetailsRepository.createItemDetails(any()))
                .thenReturn(Uni.createFrom().item(itemDetailsDataModel));
        Uni<CreateItemDetailsReply> response = itemDetailsGrpc.createItemDetails(CreateItemDetailsRequest.newBuilder().setName(name).build());
        Assertions.assertEquals(10L, response.await().indefinitely().getId());
    }

    @Test
    void getItemDetails() {
        itemDetailsDataModel.setId(10L);
        itemDetailsDataModel.setName("Laptop");
        itemDetailsDataModel.setDescription("Hard Disk");
        itemDetailsDataModel.setMedia("Videos");

        when(itemDetailsRepository.findById(10L)).thenReturn(Uni.createFrom().item(itemDetailsDataModel));

        Uni<GetItemDetailsReply> response = itemDetailsGrpc.getItemDetails(GetItemDetailsRequest.newBuilder().setId(10L).build());

        assertEquals("Laptop", response.await().indefinitely().getName());

    }

    @Test
    void updateItemDetails() {

        itemDetailsDataModel.setId(10L);
        itemDetailsDataModel.setMedia("Videos");
        when(itemDetailsRepository.findById(id)).thenReturn(Uni.createFrom().item(itemDetailsDataModel));
        Uni<UpdateItemDetailsReply> response = itemDetailsGrpc.updateItemDetails(UpdateItemDetailsRequest.newBuilder().setId(10L).setMedia("Videos").build());
        assertEquals(10L,response.await().indefinitely().getId());

    }
}