package step;

import datamodel.ClassificationDataModel;
import grpc.ClassificationGrpcService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.smallrye.mutiny.Uni;
import org.inventory.classification.*;
import org.junit.jupiter.api.Assertions;
import repository.ClassificationRepository;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;


public class ClassificationStep {
    static ClassificationRepository classificationRepository;
    static ClassificationGrpc classificationGrpc;
    ClassificationDataModel dataModel;
    CreateClassificationRequest classificationRequest;
    GetClassificationRequest getClassificationRequest;
    UpdateClassificationRequest updateClassificationRequest;
    DeleteClassificationRequest deleteClassificationRequest;

    @BeforeAll
    public static void setUp() {
        classificationRepository = mock(ClassificationRepository.class);
        classificationGrpc = new ClassificationGrpcService(classificationRepository);
    }

    @Given("User has the permissions to add classification details")
    public void user_has_the_permissions_to_add_classification_details() {
    }

    @When("I provide all the details to the inventory for data creation:")
    public void i_provide_all_the_details_to_the_inventory_for_data_creation(DataTable dataTable) {
        String name = dataTable.asMaps().get(0).get("name");
        String desc = dataTable.asMaps().get(0).get("description");
        String tag = dataTable.asMaps().get(0).get("tag");
        String id = dataTable.asMaps().get(0).get("id");

        classificationRequest = CreateClassificationRequest.newBuilder().build().toBuilder().setName(name).setTag(tag).setDescription(desc).build();
        dataModel = new ClassificationDataModel();
        dataModel.setId(Long.parseLong(id));
        dataModel.setName(name);
        dataModel.setDescription(desc);
        dataModel.setTag(tag);
        when(classificationRepository.createDetails(any()))
                .thenReturn(Uni.createFrom().item(dataModel));
    }

    @Then("The Classification Service should return the Classification Id:")
    public void the_classification_service_should_return_the_classification_id(io.cucumber.datatable.DataTable dataTable) {
        Uni<CreateClassificationReply> response = classificationGrpc.createDetails(classificationRequest);
        Assertions.assertEquals(Long.parseLong(dataTable.asMaps().get(0).get("id")), (int) response.await().indefinitely().getId());
    }

    @Given("User wants to get the details of the item")
    public void user_wants_to_get_the_details_of_the_item() {

    }

    @When("I provide the item id for the retrieval:")
    public void i_provide_the_item_id_for_the_retrieval(DataTable dataTable) {
        String id = dataTable.asMaps().get(0).get("id");
        dataModel = new ClassificationDataModel();
        dataModel.setId(Long.parseLong(id));
        dataModel.setName("Electronics");
        dataModel.setDescription("Electronics Item");
        dataModel.setTag("Elex");
        getClassificationRequest = GetClassificationRequest.newBuilder().setId(Long.parseLong(id)).build();
        when(classificationRepository.findById(Long.parseLong(id)))
                .thenReturn(Uni.createFrom().item(dataModel));
    }

    @Then("The inventory should successfully return the value to the user:")
    public void the_inventory_should_successfully_return_the_value_to_the_user(DataTable dataTable) {
        Uni<GetClassificationReply> response = classificationGrpc.getDetails(getClassificationRequest);
        assertEquals(dataTable.asMaps().get(0).get("name"), response.await().indefinitely().getName());
        assertEquals(dataTable.asMaps().get(0).get("description"), response.await().indefinitely().getDescription());
        assertEquals(dataTable.asMaps().get(0).get("tag"), response.await().indefinitely().getTag());
    }


    @Given("User wants to update the details of the item")
    public void user_wants_to_update_the_details_of_the_item() {

    }

    @When("I provide the item id and all the details to be updated:")
    public void i_provide_the_item_id_and_all_the_details_to_be_updated(io.cucumber.datatable.DataTable dataTable) {
        String name = dataTable.asMaps().get(0).get("name");
        String desc = dataTable.asMaps().get(0).get("description");
        String tag = dataTable.asMaps().get(0).get("tag");
        String id = dataTable.asMaps().get(0).get("id");
        updateClassificationRequest = UpdateClassificationRequest.newBuilder().setName(name).setId(Long.parseLong(id)).setDescription(desc).setTag(tag).build();
        dataModel = new ClassificationDataModel();
        dataModel.setName(name);
        dataModel.setDescription(desc);
        dataModel.setTag(tag);
        dataModel.setId(Long.parseLong(id));
        when(classificationRepository.findById(Long.parseLong(id))).thenReturn(Uni.createFrom().item(dataModel));
    }

    @Then("The inventory should successfully return the Classification id:")
    public void the_inventory_should_successfully_return_the_classification_id(io.cucumber.datatable.DataTable dataTable) {
        Uni<UpdateClassificationReply> response = classificationGrpc.updateDetails(updateClassificationRequest);
        assertEquals(Long.parseLong(dataTable.asMaps().get(0).get("id")), response.await().indefinitely().getId());

    }

    @Given("User wants to delete the details of the item")
    public void user_wants_to_delete_the_details_of_the_item() {
    }

    @When("I provide the classification id to be deleted:")
    public void i_provide_the_classification_id_to_be_deleted(io.cucumber.datatable.DataTable dataTable) {
        String id = dataTable.asMaps().get(0).get("id");
        dataModel = new ClassificationDataModel();
        dataModel.setId(Long.parseLong(id));
        deleteClassificationRequest = DeleteClassificationRequest.newBuilder().setId(Long.parseLong(id)).build();
        when(classificationRepository.findById(Long.parseLong(id))).thenReturn(Uni.createFrom().item(dataModel));
    }

    @Then("The inventory should successfully return the message:")
    public void the_inventory_should_successfully_return_the_message(io.cucumber.datatable.DataTable dataTable) {
        Uni<DeleteClassificationReply> response = classificationGrpc.deleteDetails(deleteClassificationRequest);
        assertEquals(dataTable.asList().get(1), response.await().indefinitely().getMessage());
    }

}
