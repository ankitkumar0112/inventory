package step;

import datamodel.ItemTypeDataModel;
import grpc.ItemTypeGrpcService;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.smallrye.mutiny.Uni;
import org.inventory.type.*;
import repository.ItemTypeRepository;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class ItemTypeStep {

    static ItemTypeRepository itemTypeRepository;
    static ItemTypeGrpc itemTypeGrpc;
    ItemTypeDataModel itemTypeDataModel;
    CreateItemTypeRequest createItemTypeRequest;
    GetItemTypeRequest getItemTypeRequest;
    UpdateItemTypeRequest updateItemTypeRequest;
    DeleteItemTypeRequest deleteItemTypeRequest;

    @BeforeAll
    public static void setUp() {
        itemTypeRepository = mock(ItemTypeRepository.class);
        itemTypeGrpc = new ItemTypeGrpcService(itemTypeRepository);
    }

    @Given("User has the permissions to add itemType details")
    public void user_has_the_permissions_to_add_item_type_details() {
    }

    @When("I provide all the details to the inventory for data creation of Item Type:")
    public void i_provide_all_the_details_to_the_inventory_for_data_creation_of_item_type(io.cucumber.datatable.DataTable dataTable) {
        String name = dataTable.asMaps().get(0).get("name");
        String desc = dataTable.asMaps().get(0).get("description");
        String tag = dataTable.asMaps().get(0).get("Classification tag");
        String id = dataTable.asMaps().get(0).get("id");

        createItemTypeRequest = CreateItemTypeRequest.newBuilder().build().toBuilder().setName(name).setClassificationTag(tag).setDescription(desc).build();
        itemTypeDataModel = new ItemTypeDataModel();
        itemTypeDataModel.setId(Long.parseLong(id));
        itemTypeDataModel.setName(name);
        itemTypeDataModel.setDescription(desc);
        itemTypeDataModel.setClassificationTag(tag);
        when(itemTypeRepository.addItemsDetails(any()))
                .thenReturn(Uni.createFrom().item(itemTypeDataModel));
    }

    @Then("The ItemType Service should return the Id:")
    public void the_item_type_service_should_return_the_id(io.cucumber.datatable.DataTable dataTable) {
        Uni<CreateItemTypeReply> response = itemTypeGrpc.createItemType(createItemTypeRequest);
        assertEquals(Long.parseLong(dataTable.asMaps().get(0).get("id")), (int) response.await().indefinitely().getId());

    }

    @Given("User wants to get the details of the Item Type")
    public void user_wants_to_get_the_details_of_the_item_type() {
    }

    @When("I provide the Item Type id for the retrieval:")
    public void i_provide_the_item_type_id_for_the_retrieval(io.cucumber.datatable.DataTable dataTable) {
        String id = dataTable.asMaps().get(0).get("id");
        itemTypeDataModel = new ItemTypeDataModel();
        itemTypeDataModel.setId(Long.parseLong(id));
        itemTypeDataModel.setName("Electronics");
        itemTypeDataModel.setDescription("Electronics Item");
        itemTypeDataModel.setClassificationTag("Elex");
        getItemTypeRequest = GetItemTypeRequest.newBuilder().setId(Long.parseLong(id)).build();
        when(itemTypeRepository.findById(Long.parseLong(id)))
                .thenReturn(Uni.createFrom().item(itemTypeDataModel));
    }

    @Then("The inventory should successfully return the value of Item Type to the user:")
    public void the_inventory_should_successfully_return_the_value_of_item_type_to_the_user(io.cucumber.datatable.DataTable dataTable) {
        Uni<GetItemTypeReply> response = itemTypeGrpc.getItemType(getItemTypeRequest);
        assertEquals(dataTable.asMaps().get(0).get("name"), response.await().indefinitely().getName());
        assertEquals(dataTable.asMaps().get(0).get("description"), response.await().indefinitely().getDescription());
        assertEquals(dataTable.asMaps().get(0).get("Classification tag"), response.await().indefinitely().getClassificationTag());
    }

    @Given("User wants to update the details of the itemType")
    public void user_wants_to_update_the_details_of_the_item_type() {

    }

    @When("I provide the Item Type id and all the details to be updated:")
    public void i_provide_the_item_type_id_and_all_the_details_to_be_updated(io.cucumber.datatable.DataTable dataTable) {

        String name = dataTable.asMaps().get(0).get("name");
        String desc = dataTable.asMaps().get(0).get("description");
        String tag = dataTable.asMaps().get(0).get("Classification tag");
        String id = dataTable.asMaps().get(0).get("id");
        updateItemTypeRequest = UpdateItemTypeRequest.newBuilder().setName(name).setId(Long.parseLong(id)).setDescription(desc).setClassificationTag(tag).build();
        itemTypeDataModel = new ItemTypeDataModel();
        itemTypeDataModel.setName("Electrical");
        itemTypeDataModel.setDescription("Electrical Item");
        itemTypeDataModel.setClassificationTag("Elex");
        itemTypeDataModel.setId(Long.parseLong(id));
        when(itemTypeRepository.findById(Long.parseLong(id))).thenReturn(Uni.createFrom().item(itemTypeDataModel));

    }

    @Then("The inventory should successfully return the Item Type id:")
    public void the_inventory_should_successfully_return_the_item_type_id(io.cucumber.datatable.DataTable dataTable) {
        Uni<UpdateItemTypeReply> response = itemTypeGrpc.updateItemType(updateItemTypeRequest);
        assertEquals(Long.parseLong(dataTable.asMaps().get(0).get("id")), response.await().indefinitely().getId());


    }


    @Given("User wants to delete the Item Type details of the item")
    public void user_wants_to_delete_the_item_type_details_of_the_item() {
    }

    @When("I provide the Item Type id to be deleted:")
    public void i_provide_the_item_type_id_to_be_deleted(io.cucumber.datatable.DataTable dataTable) {
        String id = dataTable.asMaps().get(0).get("id");
        itemTypeDataModel = new ItemTypeDataModel();
        itemTypeDataModel.setId(Long.parseLong(id));
        deleteItemTypeRequest = DeleteItemTypeRequest.newBuilder().setId(Long.parseLong(id)).build();
        when(itemTypeRepository.findById(Long.parseLong(id))).thenReturn(Uni.createFrom().item(itemTypeDataModel));

    }

    @Then("The inventory should successfully return the message for Item Type:")
    public void the_inventory_should_successfully_return_the_message_for_item_type(io.cucumber.datatable.DataTable dataTable) {
        Uni<DeleteItemTypeReply> response = itemTypeGrpc.deleteItemType(deleteItemTypeRequest);
        assertEquals(dataTable.asList().get(1), response.await().indefinitely().getMessage());

    }

}
