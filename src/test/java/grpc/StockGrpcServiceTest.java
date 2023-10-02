package grpc;

import datamodel.StockDataModel;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.smallrye.mutiny.Uni;
import org.inventory.stock.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.StockRepository;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
class StockGrpcServiceTest {

    @GrpcClient
    StockGrpc stockGrpc;

    @InjectMock
    StockRepository stockRepository;

    StockDataModel stockDataModel;

    long id=10L;

    @BeforeEach
    void setUp(){
        stockDataModel=new StockDataModel();
    }
    @Test
    void createStock() {
        stockDataModel.setId(10L);
        stockDataModel.setAvailable_qty(20);
        stockDataModel.setLocation_code(1234);
        when(stockRepository.createStock(any())).thenReturn(Uni.createFrom().item(stockDataModel));
        Uni<CreateStockReply> response = stockGrpc.createStock(CreateStockRequest.newBuilder().setAvailableQty(20).setLocationCode(1234).build());
        Assertions.assertEquals(10L,response.await().indefinitely().getId());
    }

    @Test
    void getStock() {
        stockDataModel.setId(10L);
        stockDataModel.setAvailable_qty(20);
        stockDataModel.setLocation_code(1234);
        when(stockRepository.findById(id)).thenReturn(Uni.createFrom().item(stockDataModel));
        Uni<GetStockReply> response = stockGrpc.getStock(GetStockRequest.newBuilder().setId(10L).build());
        Assertions.assertEquals(1234,response.await().indefinitely().getLocationCode());
        Assertions.assertEquals(20,response.await().indefinitely().getAvailableQty());

    }

    @Test
    void updateStock() {
        stockDataModel.setId(10L);
        stockDataModel.setItemDataModel(stockDataModel.getItemDataModel());
        stockDataModel.setLocation_code(1234);
        stockDataModel.setAvailable_qty(20);
        when(stockRepository.findById(id)).thenReturn(Uni.createFrom().item(stockDataModel));
        Uni<UpdateStockReply> response = stockGrpc.updateStock(UpdateStockRequest.newBuilder().setAvailableQty(20).setLocationCode(1234).setId(10L).build());
        Assertions.assertEquals(10L,response.await().indefinitely().getId());
    }
}