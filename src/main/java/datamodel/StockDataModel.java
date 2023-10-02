package datamodel;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;

@ApplicationScoped
@Entity
public class StockDataModel {
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    ItemDataModel itemDataModel;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long available_qty = 1;
    private long location_code = 2;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAvailable_qty() {
        return available_qty;
    }

    public void setAvailable_qty(long available_qty) {
        this.available_qty = available_qty;
    }

    public long getLocation_code() {
        return location_code;
    }

    public void setLocation_code(long location_code) {
        this.location_code = location_code;
    }

    public ItemDataModel getItemDataModel() {
        return itemDataModel;
    }

    public void setItemDataModel(ItemDataModel itemDataModel) {
        this.itemDataModel = itemDataModel;
    }
}
