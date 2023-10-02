package repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import datamodel.StockDataModel;
import org.inventory.stock.CreateStockRequest;
import org.inventory.stock.UpdateStockRequest;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StockRepository implements PanacheRepository<StockDataModel> {
    @ReactiveTransactional
    public Uni<StockDataModel> createStock(CreateStockRequest request) {
        StockDataModel stockDataModel=new StockDataModel();
        stockDataModel.setLocation_code( request.getLocationCode());
        stockDataModel.setAvailable_qty(request.getAvailableQty());
        return this.persist(stockDataModel);
    }

    @ReactiveTransactional
    public Uni<StockDataModel> updateStock(UpdateStockRequest request){
        StockDataModel stockDataModel=new StockDataModel();
        stockDataModel.setLocation_code(request.getLocationCode());
        stockDataModel.setAvailable_qty( request.getAvailableQty());
        return this.persist(stockDataModel);

    }

    @ReactiveTransactional
    public Uni<Long> deleteStock(StockDataModel stockDataModel){
        return this.delete("id", stockDataModel.getId());
    }
}
