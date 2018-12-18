package com.tablayoutrv.bean;

/**
 * Created by HARRY on 2017/3/23 0023.
 */

public class RvData {

    private ParentCategory parentcategory;
    private CategoryBean category;
    private Product product;

    public ParentCategory getParentcategory() {
        return parentcategory;
    }

    public void setParentcategory(ParentCategory parentcategory) {
        this.parentcategory = parentcategory;
    }

    public CategoryBean getCategory() {
        return category;
    }

    public void setCategory(CategoryBean category) {
        this.category = category;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public static class CategoryBean {
        /**
         * id : 105617
         * name : Cable & Charger & Adapter
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
