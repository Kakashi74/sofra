package com.shanaptech.sofra.Retrofit;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://ipda3-tech.com/sofra-v2/api/v2/";

    public static ApiPostman getAPIService() {

        return RetrofitClient.getClient().create(ApiPostman.class);
    }
}
