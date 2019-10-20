package com.shanaptech.sofra.Retrofit;



import com.shanaptech.sofra.Data.postman.model.UpdateOffer.UpdateOffer;
import com.shanaptech.sofra.Data.postman.model.acceptOrder.AcceptOrder;
import com.shanaptech.sofra.Data.postman.model.addContact.AddContact;
import com.shanaptech.sofra.Data.postman.model.allRestaurants.AllRestaurants;
import com.shanaptech.sofra.Data.postman.model.clientRegister.ClientRegister;
import com.shanaptech.sofra.Data.postman.model.commissions.Commissions;
import com.shanaptech.sofra.Data.postman.model.deleteItem.DeleteItem;
import com.shanaptech.sofra.Data.postman.model.getItems.MyItems;
import com.shanaptech.sofra.Data.postman.model.getOffersClient.GetOffersClient;
import com.shanaptech.sofra.Data.postman.model.getcategories.Categories;

import com.shanaptech.sofra.Data.postman.model.loginClient.ClientLogin;
import com.shanaptech.sofra.Data.postman.model.loginRestaurant.LoginRestaurant;
import com.shanaptech.sofra.Data.postman.model.myItems.ITems;

import com.shanaptech.sofra.Data.postman.model.cities.GetCities;
import com.shanaptech.sofra.Data.postman.model.myOrders.MyOrders;
import com.shanaptech.sofra.Data.postman.model.newPassword.NewPassword;
import com.shanaptech.sofra.Data.postman.model.notifications.Notifications;
import com.shanaptech.sofra.Data.postman.model.notifyToken.NotifyToken;
import com.shanaptech.sofra.Data.postman.model.profile.Profile;
import com.shanaptech.sofra.Data.postman.model.regions.Regions;
import com.shanaptech.sofra.Data.postman.model.rejectOrder.RejectOrder;
import com.shanaptech.sofra.Data.postman.model.resetPassword.ResetPassword;
import com.shanaptech.sofra.Data.postman.model.restaurantRegister.RestauranttRegister;
import com.shanaptech.sofra.Data.postman.model.showOrder.ShowOrder;
import com.shanaptech.sofra.Data.postman.model.updateItem.UpdateItem;
import com.shanaptech.sofra.Data.postman.model.updateProfileClient.ProfileClient;


import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiPostman {

    @POST("restaurant/login")
    @FormUrlEncoded
    Call<LoginRestaurant> Login(@Field("email") String phone, @Field("password") String password);

    @Multipart
    @POST("restaurant/register")
    Call<RestauranttRegister> getRestaurantRegister(@Part("name") RequestBody name, @Part("email") RequestBody email
            , @Part("password") RequestBody password, @Part("password_confirmation") RequestBody password_confirmation
            , @Part("phone") RequestBody phone, @Part("whatsapp") RequestBody whatsapp, @Part("region_id") RequestBody region_id
            , @Part("categories[]") List<RequestBody> categories, @Part("minimum_charger") RequestBody minimum_charger, @Part("delivery_cost") RequestBody delivery_cost,
                                                    @Part MultipartBody.Part image);

    @POST("restaurant/reset-password")
    @FormUrlEncoded
    Call<ResetPassword> resetPassword(@Field("email") String email);

    @POST("restaurant/new-password")
    @FormUrlEncoded
    Call<NewPassword> newPassword(@Field("password") String password, @Field("password_confirmation") String password_confirmation
            , @Field("code") String pin_code);


    @GET("restaurant/my-items")
    Call<ITems> getOrders(@Query("api_token") String Api_Token  , @Query("page") int page );

    @GET("cities")
    Call<GetCities> getCities();

    @GET("categories")
    Call<Categories> getCategories();

    @GET("restaurant/my-items")
    Call<MyItems> getItemRestaurant(@Query("api_token") String api_token, @Query("page") int page);

    @Multipart
    @POST("restaurant/update-item")
    Call<UpdateItem> UpdateItems(@Part("description") RequestBody description, @Part("price") RequestBody price
            , @Part("preparing_time") RequestBody preparing_time, @Part("name") RequestBody name
            , @Part("item_id") RequestBody item_id, @Part("offer_price") RequestBody offer_price, @Part("api_token") RequestBody api_token,
                                 @Part MultipartBody.Part image);

    @Multipart
    @POST("restaurant/new-item")
    Call<UpdateItem> NewItem(@Part("description") RequestBody description, @Part("price") RequestBody price
            , @Part("offer_price") RequestBody offer_price
            , @Part("preparing_time") RequestBody preparing_time, @Part("name") RequestBody name
            , @Part("api_token") RequestBody api_token,
                             @Part MultipartBody.Part image);

    @Multipart
    @POST("restaurant/update-offer")
    Call<UpdateOffer> updateOffer(@Part("description") RequestBody description, @Part("price") RequestBody price
            , @Part("starting_at") RequestBody starting_at, @Part("name") RequestBody name,
                                  @Part MultipartBody.Part image, @Part("ending_at") RequestBody ending_at,
                                  @Part("api_token") RequestBody api_token, @Part("offer_id") RequestBody offer_id);



    @POST("restaurant/delete-item")
    @FormUrlEncoded
    Call<DeleteItem> DeleteItemRestaurant(@Field("api_token") String api_token, @Field("item_id") int item_id);

    @GET("regions")
    Call<Regions> getRegions(@Query("city_id") int city_id);


    @POST("restaurant/profile")
    @FormUrlEncoded
    Call<Profile> getProfile(@Field("api_token") String api_token);


    @Multipart
    @POST("restaurant/profile")
    Call<RestauranttRegister> getEditRestaurantRegister(@Part("name") RequestBody name, @Part("email") RequestBody email
            , @Part("password") RequestBody password, @Part("password_confirmation") RequestBody password_confirmation
            , @Part("phone") RequestBody phone, @Part("whatsapp") RequestBody whatsapp, @Part("region_id") RequestBody region_id
            , @Part("categories[]") List<RequestBody> categories, @Part("minimum_charger") RequestBody minimum_charger
            , @Part("delivery_cost") RequestBody delivery_cost, @Part MultipartBody.Part image,
                                                        @Part("api_token") RequestBody api_token,@Part("availability") RequestBody availability);
    @GET("restaurant/commissions")
    Call<Commissions> myShowOrder(@Query("api_token") String api_token);


    @POST("restaurant/accept-order")
    @FormUrlEncoded
    Call<AcceptOrder> acceptOrder(@Field("api_token") String api_token, @Field("order_id") int order_id);


    @POST("restaurant/reject-order")
    @FormUrlEncoded
    Call<RejectOrder> rejectOrder(@Field("api_token") String api_token, @Field("order_id") int order_id);


    @POST("restaurant/confirm-order")
    @FormUrlEncoded
    Call<RejectOrder> confirmOrder(@Field("api_token") String api_token, @Field("order_id") int order_id);


    @GET("restaurant/show-order")
    Call<ShowOrder> myShowOrder(@Query("api_token") String api_token, @Query("order_id") int order_id);

    @GET("restaurant/my-orders")
    Call<MyOrders> getMyOrders(@Query("api_token") String api_token, @Query("state") String state, @Query("page") int page);



///////////////////////////////////////////////////////////////////////////////////

    @POST("client/login")
    @FormUrlEncoded
    Call<ClientLogin> onLogin(@Field("email") String phone, @Field("password") String password);

    @Multipart
    @POST("client/sign-up")
    Call<ClientRegister> addClientRegister(@Part("name") RequestBody name, @Part("email") RequestBody email
            , @Part("password") RequestBody password, @Part("password_confirmation") RequestBody password_confirmation
            , @Part("phone") RequestBody phone, @Part("address") RequestBody address, @Part("region_id") RequestBody region_id
            , @Part MultipartBody.Part image);

    @POST("client/register-token")
    @FormUrlEncoded
    Call<NotifyToken>RegisterToken(@Field("token") String token
            , @Field("api_token") String api_token, @Field("type") String type);

    @Multipart
    @POST("client/profile")
    Call<ProfileClient> editProfileClient(@Part("api_token") RequestBody api_token, @Part("name") RequestBody name, @Part("email") RequestBody email
            , @Part("password") RequestBody password, @Part("password_confirmation") RequestBody password_confirmation
            , @Part("phone") RequestBody phone, @Part("address") RequestBody address, @Part("region_id") RequestBody region_id
            , @Part MultipartBody.Part image);

    @POST("client/profile")
    @FormUrlEncoded
    Call<ProfileClient> getProfileClient(@Field("api_token") String api_token );


    @POST("client/remove-token")
    @FormUrlEncoded
    Call<NotifyToken>RemoveToken(@Field("token") String token
            , @Field("api_token") String api_token);


    @GET("offers")
    Call<GetOffersClient> getOffers(@Query("page") int page);

    @GET("client/notifications")
    Call<Notifications> getNotifications(@Query("api_token") String api_token, @Query("page") int page);

    @POST("contact")
    @FormUrlEncoded
    Call<AddContact>addContact(@Field("name") String name, @Field("email") String email, @Field("phone") String phone
            , @Field("type") String type, @Field("content") String content);


    @GET("restaurants")
    Call<AllRestaurants> getAllRestaurant(@Query("page")int page);

    @GET("items")
    Call<MyItems> getItemRestaurantClient(@Query("restaurant_id") Integer restaurant_id, @Query("page") int page);

    @GET("restaurants")
    Call<AllRestaurants> getAllRestaurantFilter(@Query("keywork")String keywork,@Query("region_id")int region_id,@Query("page")int page);


}
