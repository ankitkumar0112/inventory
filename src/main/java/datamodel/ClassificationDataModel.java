package datamodel;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;

@ApplicationScoped
@Entity
public class ClassificationDataModel {

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    ItemTypeDataModel itemTypeDataModel;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String tag;


    public ItemTypeDataModel getItemTypeDataModel() {
        return itemTypeDataModel;
    }

    public void setItemTypeDataModel(ItemTypeDataModel itemTypeDataModel) {
        this.itemTypeDataModel = itemTypeDataModel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
