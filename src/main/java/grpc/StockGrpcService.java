package grpc;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import org.inventory.stock.*;
import repository.StockRepository;

import javax.inject.Inject;

@GrpcService
public class StockGrpcService implements StockGrpc {
    @Inject
    StockRepository stockRepository;

    public StockGrpcService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Uni<CreateStockReply> createStock(CreateStockRequest request) {
        return stockRepository.createStock(request).onItem().transform(stockDataModel1 -> CreateStockReply.newBuilder().setId(stockDataModel1.getId()).build());
    }

    @Override
    public Uni<GetStockReply> getStock(GetStockRequest request) {
        return stockRepository.findById(request.getId())
                .onItem()
                .ifNull()
                .failWith(() -> {throw new RuntimeException("Invalid Id");})
                .onItem()
                .ifNotNull().transform(stockDataModel -> GetStockReply.newBuilder()
                        .setAvailableQty(stockDataModel.getAvailable_qty())
                        .setLocationCode(stockDataModel.getLocation_code())
                        .build());

    }

    @Override
    public Uni<UpdateStockReply> updateStock(UpdateStockRequest request) {
        return stockRepository.findById(request.getId())
                .onItem()
                .ifNull()
                .failWith(() -> {throw new RuntimeException("Invalid Id");})
                .onItem()
                .ifNotNull()
                .transformToUni(stockDataModel -> stockRepository.updateStock(request)
                .onItem()
                .transform(stockDataModel1 -> UpdateStockReply.newBuilder()
                        .setId(stockDataModel.getId())
                        .build()));


    }

//    @Override
//    public Uni<DeleteStockReply> deleteStock(DeleteStockRequest request) {
//
//        return stockRepository.findById(request.getId()).onItem().ifNull().failWith(() -> {
//            throw new RuntimeException("Invalid Id");
//        }).onItem().transformToUni(stockDataModel -> stockRepository.deleteStock(stockDataModel).onItem().transform(aLong -> DeleteStockReply.newBuilder().setMessage("Success").build()));
//    }
}
