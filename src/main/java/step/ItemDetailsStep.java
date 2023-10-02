package step;

import datamodel.ItemDetailsDataModel;
import datamodel.StockDataModel;
import grpc.ItemDetailsGrpcService;
import grpc.StockGrpcService;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.smallrye.mutiny.Uni;
import org.inventory.details.*;
import org.inventory.stock.*;
import org.junit.jupiter.api.Assertions;
import repository.ItemDetailsRepository;
import repository.StockRepository;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;

public class ItemDetailsStep {

    static ItemDetailsGrpc itemDetailsGrpc;
    static ItemDetailsRepository itemDetailsRepository;
    CreateItemDetailsRequest createItemDetailsRequest;
    GetItemDetailsRequest getItemDetailsRequest;

    UpdateItemDetailsRequest updateItemDetailsRequest;

    ItemDetailsDataModel itemDetailsDataModel;

    @BeforeAll
    public static void setUp() {
        itemDetailsRepository = mock(ItemDetailsRepository.class);
        itemDetailsGrpc = new ItemDetailsGrpcService(itemDetailsRepository);
    }

    @Given("User has the permissions to add   Item Details details")
    public void user_has_the_permissions_to_add_item_details_details() {

    }
    @When("I provide all the details to the inventory for data creation of   Item Details:")
    public void i_provide_all_the_details_to_the_inventory_for_data_creation_of_item_details(io.cucumber.datatable.DataTable dataTable) {
        String id = dataTable.asMaps().get(0).get("id");
        String name = dataTable.asMaps().get(0).get("name");
        String description = dataTable.asMaps().get(0).get("description");
        String media = dataTable.asMaps().get(0).get("media");
        String supplierId = dataTable.asMaps().get(0).get("supplier_id");
        createItemDetailsRequest=CreateItemDetailsRequest.newBuilder().setName(name).setSupplierId(Long.parseLong(supplierId)).setDescription(description).setMedia(media).build();
        itemDetailsDataModel=new ItemDetailsDataModel();
        itemDetailsDataModel.setId(Long.parseLong(id));
        itemDetailsDataModel.setSupplier_id(Long.parseLong(supplierId));
        itemDetailsDataModel.setDescription(description);
        itemDetailsDataModel.setMedia(media);
        itemDetailsDataModel.setName(name);
        when(itemDetailsRepository.createItemDetails(any())).thenReturn(Uni.createFrom().item(itemDetailsDataModel));
    }
    @Then("The   Item Details Service should return the Id:")
    public void the_item_details_service_should_return_the_id(io.cucumber.datatable.DataTable dataTable) {
        Uni<CreateItemDetailsReply> response = itemDetailsGrpc.createItemDetails(createItemDetailsRequest);
        Assertions.assertEquals(Long.parseLong(dataTable.asList().get(1)),response.await().indefinitely().getId());
    }


    @Given("User wants to get the details of the   Item Details")
    public void user_wants_to_get_the_details_of_the_item_details() {

    }
    @When("I provide the   Item Details id for the retrieval:")
    public void i_provide_the_item_details_id_for_the_retrieval(io.cucumber.datatable.DataTable dataTable) {
        String id = dataTable.asMaps().get(0).get("id");
        getItemDetailsRequest=GetItemDetailsRequest.newBuilder().setId(Long.parseLong(id)).build();
        itemDetailsDataModel=new ItemDetailsDataModel();
        itemDetailsDataModel.setId(Long.parseLong(id));
        itemDetailsDataModel.setSupplier_id(123);
        itemDetailsDataModel.setDescription("Electronics Item");
        itemDetailsDataModel.setMedia("Videos");
        itemDetailsDataModel.setItem_id(12);
        itemDetailsDataModel.setName("Electronics");
        when(itemDetailsRepository.findById(Long.parseLong(id))).thenReturn(Uni.createFrom().item(itemDetailsDataModel));
    }
    @Then("The inventory should successfully return the value of   Item Details to the user:")
    public void the_inventory_should_successfully_return_the_value_of_item_details_to_the_user(io.cucumber.datatable.DataTable dataTable) {
        Uni<GetItemDetailsReply> response = itemDetailsGrpc.getItemDetails(getItemDetailsRequest);
        Assertions.assertEquals(dataTable.asMaps().get(0).get("name"),response.await().indefinitely().getName());
        Assertions.assertEquals(dataTable.asMaps().get(0).get("description"),response.await().indefinitely().getDescription());
        Assertions.assertEquals(dataTable.asMaps().get(0).get("media"),response.await().indefinitely().getMedia());
        Assertions.assertEquals(Long.parseLong(dataTable.asMaps().get(0).get("supplier_id")),response.await().indefinitely().getSupplierId());
    }


    @Given("User wants to update the details of the   Item Details")
    public void user_wants_to_update_the_details_of_the_item_details() {

    }
    @When("I provide the   Item Details id and all the details to be updated:")
    public void i_provide_the_item_details_id_and_all_the_details_to_be_updated(io.cucumber.datatable.DataTable dataTable) {
        String id = dataTable.asMaps().get(0).get("id");
        String name = dataTable.asMaps().get(0).get("name");
        String description = dataTable.asMaps().get(0).get("description");
        String media = dataTable.asMaps().get(0).get("media");
        String supplierId = dataTable.asMaps().get(0).get("supplier_id");
        updateItemDetailsRequest=UpdateItemDetailsRequest.newBuilder().setName(name).setSupplierId(Long.parseLong(supplierId)).setDescription(description).setMedia(media).setId(Long.parseLong(id)).build();
        itemDetailsDataModel=new ItemDetailsDataModel();
        itemDetailsDataModel.setId(Long.parseLong(id));
        itemDetailsDataModel.setSupplier_id(Long.parseLong(supplierId));
        itemDetailsDataModel.setDescription(description);
        itemDetailsDataModel.setMedia(media);
        itemDetailsDataModel.setName(name);
        when(itemDetailsRepository.findById(Long.parseLong(id))).thenReturn(Uni.createFrom().item(itemDetailsDataModel));
    }
    @Then("The inventory should successfully return the   Item Details id:")
    public void the_inventory_should_successfully_return_the_item_details_id(io.cucumber.datatable.DataTable dataTable) {
        Uni<UpdateItemDetailsReply> response = itemDetailsGrpc.updateItemDetails(updateItemDetailsRequest);
        Assertions.assertEquals(Long.parseLong(dataTable.asList().get(1)),response.await().indefinitely().getId());
    }

}
