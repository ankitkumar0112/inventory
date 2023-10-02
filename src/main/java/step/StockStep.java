package step;

import datamodel.StockDataModel;
import grpc.StockGrpcService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.smallrye.mutiny.Uni;
import org.inventory.classification.GetClassificationReply;
import org.inventory.stock.*;
import org.junit.jupiter.api.Assertions;
import repository.StockRepository;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class StockStep {
    static StockRepository stockRepository;
    static StockGrpc stockGrpc;
    CreateStockRequest createStockRequest;
    GetStockRequest getStockRequest;

    UpdateStockRequest updateStockRequest;
    StockDataModel stockDataModel;

    @BeforeAll
    public static void setUp() {
        stockRepository = mock(StockRepository.class);
        stockGrpc = new StockGrpcService(stockRepository);
    }

    @Given("User has the permissions to add Stock")
    public void user_has_the_permissions_to_add_stock() {

    }

    @When("I provide all the details to the inventory for data creation of Stock:")
    public void i_provide_all_the_details_to_the_inventory_for_data_creation_of_stock(DataTable dataTable) {
        String id = dataTable.asMaps().get(0).get("id");
        String locationCode = dataTable.asMaps().get(0).get("locationCode");
        String availableQty = dataTable.asMaps().get(0).get("availableQty");
        createStockRequest=CreateStockRequest.newBuilder().setLocationCode(Long.parseLong(locationCode)).setAvailableQty(Long.parseLong(availableQty)).build();
        stockDataModel = new StockDataModel();
        stockDataModel.setId(Long.parseLong(id));
        stockDataModel.setAvailable_qty(Long.parseLong(availableQty));
        stockDataModel.setLocation_code(Long.parseLong(locationCode));
        when(stockRepository.createStock(any())).thenReturn(Uni.createFrom().item(stockDataModel));

    }

    @Then("The Stock Service should return the Id:")
    public void the_stock_service_should_return_the_id(io.cucumber.datatable.DataTable dataTable) {
        Uni<CreateStockReply> response = stockGrpc.createStock(createStockRequest);
        Assertions.assertEquals(Long.parseLong(dataTable.asList().get(1)),response.await().indefinitely().getId());
    }

    @Given("User wants to get the details of the Stock")
    public void user_wants_to_get_the_details_of_the_stock() {

    }

    @When("I provide the   Stock id for the retrieval:")
    public void i_provide_the_stock_id_for_the_retrieval(io.cucumber.datatable.DataTable dataTable) {
        String id = dataTable.asMaps().get(0).get("id");
        stockDataModel = new StockDataModel();
        stockDataModel.setId(Long.parseLong(id));
        stockDataModel.setAvailable_qty(10);
        stockDataModel.setLocation_code(123);
        getStockRequest = GetStockRequest.newBuilder().setId(Long.parseLong(id)).build();
        when(stockRepository.findById(Long.parseLong(id)))
                .thenReturn(Uni.createFrom().item(stockDataModel));

    }

    @Then("The inventory should successfully return the value of Stock to the user:")
    public void the_inventory_should_successfully_return_the_value_of_stock_to_the_user(io.cucumber.datatable.DataTable dataTable) {
        Uni<GetStockReply> response = stockGrpc.getStock(getStockRequest);
        assertEquals(Long.parseLong(dataTable.asMaps().get(0).get("availableQty")), response.await().indefinitely().getAvailableQty());
        assertEquals(Long.parseLong(dataTable.asMaps().get(0).get("locationCode")), response.await().indefinitely().getLocationCode());
    }

    @Given("User wants to update the details of the Stock")
    public void user_wants_to_update_the_details_of_the_stock() {

    }

    @When("I provide the   Stock id and all the details to be updated:")
    public void i_provide_the_stock_id_and_all_the_details_to_be_updated(io.cucumber.datatable.DataTable dataTable) {
        String id = dataTable.asMaps().get(0).get("id");
        String locationCode = dataTable.asMaps().get(0).get("locationCode");
        String availableQty = dataTable.asMaps().get(0).get("availableQty");
        updateStockRequest=UpdateStockRequest.newBuilder().setLocationCode(Long.parseLong(locationCode)).setAvailableQty(Long.parseLong(availableQty)).setId(Long.parseLong(id)).build();
        stockDataModel = new StockDataModel();
        stockDataModel.setId(Long.parseLong(id));
        stockDataModel.setAvailable_qty(Long.parseLong(availableQty));
        stockDataModel.setLocation_code(Long.parseLong(locationCode));
        when(stockRepository.findById(Long.parseLong(id))).thenReturn(Uni.createFrom().item(stockDataModel));
    }

    @Then("The inventory should successfully return the Stock id:")
    public void the_inventory_should_successfully_return_the_stock_id(io.cucumber.datatable.DataTable dataTable) {
        Uni<UpdateStockReply> response = stockGrpc.updateStock(updateStockRequest);
        Assertions.assertEquals(Long.parseLong(dataTable.asList().get(1)),response.await().indefinitely().getId());

    }

}
