package datamodel;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;

@ApplicationScoped
@Entity
public class ItemTypeDataModel {
    @OneToOne(mappedBy = "itemTypeDataModel", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    ClassificationDataModel classificationDataModel;
    @OneToOne(mappedBy = "itemType", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    ItemDataModel itemDataModel;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String name;
    private String description;
    private String classificationTag;



    public ItemDataModel getItemDataModel() {
        return itemDataModel;
    }

    public void setItemDataModel(ItemDataModel itemDataModel) {
        this.itemDataModel = itemDataModel;
    }

    public String getClassificationTag() {
        return classificationTag;
    }

    public void setClassificationTag(String classificationTag) {
        this.classificationTag = classificationTag;
    }

    public ClassificationDataModel getClassificationDataModel() {
        return classificationDataModel;
    }

    public void setClassificationDataModel(ClassificationDataModel classificationDataModel) {
        this.classificationDataModel = classificationDataModel;
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


}
