package com.project.stopdistraction.model;

public class category_row {
    String cid;
    String categoryName;
    String productCount;

    public category_row(String cid, String categoryName, String productCount) {
        this.cid = cid;
        this.categoryName = categoryName;
        this.productCount = productCount;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }
}
