package datamodel;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;

@ApplicationScoped
@Entity
public class ItemDataModel {
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    ItemTypeDataModel itemType;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    ItemDetailsDataModel itemDetailsDataModel;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    StockDataModel stockDataModel;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long unique_id;
    private long code;
    private String category;
    private long company_id;
    private long stock_id;


    public ItemTypeDataModel getItemType() {
        return itemType;
    }

    public void setItemType(ItemTypeDataModel itemType) {
        this.itemType = itemType;
    }

    public ItemDetailsDataModel getItemDetailsDataModel() {
        return itemDetailsDataModel;
    }

    public void setItemDetailsDataModel(ItemDetailsDataModel itemDetailsDataModel) {
        this.itemDetailsDataModel = itemDetailsDataModel;
    }

    public StockDataModel getStockDataModel() {
        return stockDataModel;
    }

    public void setStockDataModel(StockDataModel stockDataModel) {
        this.stockDataModel = stockDataModel;
    }

    public long getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(long unique_id) {
        this.unique_id = unique_id;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(long company_id) {
        this.company_id = company_id;
    }

    public long getStock_id() {
        return stock_id;
    }

    public void setStock_id(long stock_id) {
        this.stock_id = stock_id;
    }
}
