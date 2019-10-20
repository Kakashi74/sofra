
package com.shanaptech.sofra.Data.postman.model.loginRestaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shanaptech.sofra.Data.postman.model.Generated.GeneratedDataUser;


public class Data {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("user")
    @Expose
    private GeneratedDataUser User ;


    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public GeneratedDataUser getDataUser() {
        return User;
    }

    public void setDataUser(GeneratedDataUser dataUser) {
        User = dataUser;
    }

}
