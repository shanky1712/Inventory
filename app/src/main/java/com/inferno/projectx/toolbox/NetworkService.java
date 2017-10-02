package com.inferno.projectx.toolbox;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.inferno.projectx.toolbox.ServerConstants.ASSIGN_WORK;
import static com.inferno.projectx.toolbox.ServerConstants.VIEW_ALL;

/**
 * Created by saravana.subramanian on 9/8/17.
 */

public interface NetworkService {

    @GET(VIEW_ALL)
    Call<ResponseBody> getAllResources();


    @GET(ASSIGN_WORK)
    Call<ResponseBody> getAssignWork(@Query("reqData") String params);

}
