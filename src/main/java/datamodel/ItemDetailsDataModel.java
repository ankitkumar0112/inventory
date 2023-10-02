package datamodel;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;

@ApplicationScoped
@Entity
public class ItemDetailsDataModel {
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    ItemDataModel itemDataModel;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private long item_id;
    private String description;
    private String media;
    private long supplier_id;


    public ItemDataModel getItemDataModel() {
        return itemDataModel;
    }

    public void setItemDataModel(ItemDataModel itemDataModel) {
        this.itemDataModel = itemDataModel;
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

    public long getItem_id() {
        return item_id;
    }

    public void setItem_id(long item_id) {
        this.item_id = item_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public long getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(long supplier_id) {
        this.supplier_id = supplier_id;
    }
}
