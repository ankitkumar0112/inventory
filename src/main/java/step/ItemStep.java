package step;

import datamodel.ClassificationDataModel;
import datamodel.ItemDataModel;
import grpc.ClassificationGrpcService;
import grpc.ItemGrpcService;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.hu.De;
import io.smallrye.mutiny.Uni;
import org.inventory.classification.*;
import org.inventory.item.*;
import org.junit.jupiter.api.Assertions;
import repository.ClassificationRepository;
import repository.ItemRepository;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class ItemStep {
    static ItemRepository itemRepository;
    static ItemGrpc itemGrpc;
    ItemDataModel itemDataModel;
    CreateItemRequest createItemRequest;
    GetItemRequest getItemRequest;
    UpdateItemRequest updateItemRequest;
    DeleteItemRequest deleteItemRequest;

    @BeforeAll
    public static void setUp() {
        itemRepository = mock(ItemRepository.class);
        itemGrpc = new ItemGrpcService(itemRepository);
    }


    @Given("User has the permissions to add Item")
    public void user_has_the_permissions_to_add_item() {

    }
    @When("I provide all the details to the inventory for data creation of   Item:")
    public void i_provide_all_the_details_to_the_inventory_for_data_creation_of_item(io.cucumber.datatable.DataTable dataTable) {
        String companyId = dataTable.asMaps().get(0).get("company_id");
        String code = dataTable.asMaps().get(0).get("code");
        String category = dataTable.asMaps().get(0).get("category");
        String id = dataTable.asMaps().get(0).get("id");

        createItemRequest = CreateItemRequest.newBuilder().build().toBuilder().setCategory(category).setCode(Long.parseLong(code)).setCompanyId(Long.parseLong(companyId)).build();
        itemDataModel = new ItemDataModel();
        itemDataModel.setUnique_id(Long.parseLong(id));
        itemDataModel.setCode(Long.parseLong(code));
        itemDataModel.setCategory(category);
        itemDataModel.setCompany_id(Long.parseLong(companyId));
        when(itemRepository.createItem(any()))
                .thenReturn(Uni.createFrom().item(itemDataModel));
    }
    @Then("The Item Service should return the Id:")
    public void the_item_service_should_return_the_id(io.cucumber.datatable.DataTable dataTable) {
        Uni<CreateItemReply> response = itemGrpc.createItem(createItemRequest);
        assertEquals(Long.parseLong(dataTable.asMaps().get(0).get("id")),response.await().indefinitely().getId());

    }




    @Given("User wants to get the details of the Item")
    public void user_wants_to_get_the_details_of_the_item() {

    }
    @When("I provide the Item id for the retrieval:")
    public void i_provide_the_item_id_for_the_retrieval(io.cucumber.datatable.DataTable dataTable) {
        String id = dataTable.asMaps().get(0).get("id");

        getItemRequest = GetItemRequest.newBuilder().build().toBuilder().setId(Long.parseLong(id)).build();
        itemDataModel = new ItemDataModel();
        itemDataModel.setUnique_id(1);
        itemDataModel.setCode(123);
        itemDataModel.setCategory("Electronics Item");
        itemDataModel.setCompany_id(123);
        when(itemRepository.findById(Long.parseLong(id)))
                .thenReturn(Uni.createFrom().item(itemDataModel));
    }
    @Then("The inventory should successfully return the value of Item to the user:")
    public void the_inventory_should_successfully_return_the_value_of_item_to_the_user(io.cucumber.datatable.DataTable dataTable) {
    Uni<GetItemReply> response=itemGrpc.getItem(getItemRequest);
    assertEquals(Long.parseLong(dataTable.asMaps().get(0).get("code")),response.await().indefinitely().getCode());
        assertEquals(Long.parseLong(dataTable.asMaps().get(0).get("company_id")),response.await().indefinitely().getCompanyId());
        assertEquals(dataTable.asMaps().get(0).get("category"),response.await().indefinitely().getCategory());

    }




    @Given("User wants to update the details of the Item")
    public void user_wants_to_update_the_details_of_the_item() {

    }
    @When("I provide the   Item id and all the details to be updated:")
    public void i_provide_the_item_id_and_all_the_details_to_be_updated(io.cucumber.datatable.DataTable dataTable) {
        String companyId = dataTable.asMaps().get(0).get("company_id");
        String code = dataTable.asMaps().get(0).get("code");
        String category = dataTable.asMaps().get(0).get("category");
        String id = dataTable.asMaps().get(0).get("id");

        updateItemRequest = UpdateItemRequest.newBuilder().build().toBuilder().setCategory(category).setCode(Long.parseLong(code)).setCompanyId(Long.parseLong(companyId)).setUniqueId(Long.parseLong(id)).build();
        itemDataModel = new ItemDataModel();
        itemDataModel.setUnique_id(Long.parseLong(id));
        itemDataModel.setCode(Long.parseLong(code));
        itemDataModel.setCategory(category);
        itemDataModel.setCompany_id(Long.parseLong(companyId));
        when(itemRepository.createItem(any()))
                .thenReturn(Uni.createFrom().item(itemDataModel));
    }
    @Then("The inventory should successfully return the Item id:")
    public void the_inventory_should_successfully_return_the_item_id(io.cucumber.datatable.DataTable dataTable) {
        Uni<UpdateItemReply> response = itemGrpc.updateItem(updateItemRequest);
        assertEquals(Long.parseLong(dataTable.asMaps().get(0).get("id")),response.await().indefinitely().getId());
    }



    @Given("User wants to delete the   Item of the item")
    public void user_wants_to_delete_the_item_of_the_item() {

    }
    @When("I provide the   Item id to be deleted:")
    public void i_provide_the_item_id_to_be_deleted(io.cucumber.datatable.DataTable dataTable) {
        String id = dataTable.asMaps().get(0).get("id");
        itemDataModel = new ItemDataModel();
        itemDataModel.setUnique_id(Long.parseLong(id));
        deleteItemRequest = DeleteItemRequest.newBuilder().setId(Long.parseLong(id)).build();
        when(itemRepository.findById(Long.parseLong(id))).thenReturn(Uni.createFrom().item(itemDataModel));
    }
    @Then("The inventory should successfully return the message for Item:")
    public void the_inventory_should_successfully_return_the_message_for_item(io.cucumber.datatable.DataTable dataTable) {
        Uni<DeleteItemReply> response = itemGrpc.deleteItem(deleteItemRequest);
        assertEquals(dataTable.asList().get(1), response.await().indefinitely().getMessage());
    }



}
