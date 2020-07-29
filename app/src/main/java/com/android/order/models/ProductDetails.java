package com.android.order.models;

import android.content.Intent;

import com.android.order.ui.product_details.ProductDetailsActivity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetails {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nameInArabic")
    @Expose
    private String nameInArabic;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("heroImage")
    @Expose
    private String heroImage;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("stock")
    @Expose
    private Integer stock;
    @SerializedName("carousels")
    @Expose
    private Object carousels;
    @SerializedName("carouselsUrls")
    @Expose
    private List<String> carouselsUrls = null;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("carouselsImages")
    @Expose
    private Object carouselsImages;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("discount")
    @Expose
    private Double discount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameInArabic() {
        return nameInArabic;
    }

    public void setNameInArabic(String nameInArabic) {
        this.nameInArabic = nameInArabic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getHeroImage() {
        return heroImage;
    }

    public void setHeroImage(String heroImage) {
        this.heroImage = heroImage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Object getCarousels() {
        return carousels;
    }

    public void setCarousels(Object carousels) {
        this.carousels = carousels;
    }

    public List<String> getCarouselsUrls() {
        return carouselsUrls;
    }

    public void setCarouselsUrls(List<String> carouselsUrls) {
        this.carouselsUrls = carouselsUrls;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public Object getCarouselsImages() {
        return carouselsImages;
    }

    public void setCarouselsImages(Object carouselsImages) {
        this.carouselsImages = carouselsImages;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

}