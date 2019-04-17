package com.example.fraku.future_mind;

public class DataObject {

    private String OrderId;
    private String ModificationDate;
    private String Description;
    private String Title;
    private String ImageUrl;
    private String WebUrl;

    public DataObject(String OrderId, String ModificationDate, String Description, String Title, String ImageUrl, String WebUrl){
        this.OrderId = OrderId;
        this.ModificationDate = ModificationDate;
        this.Description = Description;
        this.Title = Title;
        this.ImageUrl = ImageUrl;
        this.WebUrl = WebUrl;
    }

    public String getOrderId() {
        return OrderId;
    }
    public void setOrderId(String OrderId) {
        OrderId = OrderId;
    }

    public String getModificationDate() {
        return ModificationDate;
    }
    public void setModificationDate(String ModificationDate) {
        ModificationDate = ModificationDate;
    }

    public String getDescription() {
        return Description;
    }
    public void setDescription(String Description) {
        Description = Description;
    }

    public String getTitle() {
        return Title;
    }
    public void setTitle(String Title) {
        Title = Title;
    }

    public String getImageUrl() {
        return ImageUrl;
    }
    public void setImageUrl(String ImageUrl) {
        ImageUrl = ImageUrl;
    }

    public String getWebUrl() {
        return WebUrl;
    }
    public void setWebUrl(String WebUrl) {
        WebUrl = WebUrl;
    }
}
