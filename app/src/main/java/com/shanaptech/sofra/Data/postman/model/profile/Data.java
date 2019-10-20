
package com.shanaptech.sofra.Data.postman.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shanaptech.sofra.Data.postman.model.Generated.GeneratedDataUser;

public class Data {

    @SerializedName("user")
    @Expose
    private GeneratedDataUser user;

    public GeneratedDataUser getUser() {
        return user;
    }

    public void setUser(GeneratedDataUser user) {
        this.user = user;
    }

}
