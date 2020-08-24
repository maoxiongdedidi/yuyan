package com.yueyi.yuyinfanyi.httppager;


import com.yueyi.yuyinfanyi.bean.APPBean;
import com.yueyi.yuyinfanyi.bean.AccessTokenBean;
import com.yueyi.yuyinfanyi.bean.BaseBean;
import com.yueyi.yuyinfanyi.bean.CancelAccountBean;
import com.yueyi.yuyinfanyi.bean.LoginBean;
import com.yueyi.yuyinfanyi.bean.PayChannelBean;
import com.yueyi.yuyinfanyi.bean.PayResultBean;
import com.yueyi.yuyinfanyi.bean.ProductListBean;
import com.yueyi.yuyinfanyi.bean.RecognitionResultBean;
import com.yueyi.yuyinfanyi.bean.TranslateBean;
import com.yueyi.yuyinfanyi.bean.UserBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2019/3/8.
 */

public interface ApiService {


    /* @GET("/api/small/home")
     Observable<BaseBean<SmallHoneBean>> smallhome(@Header("authorization") String apikey, @QueryMap Map<String, Object> map);

     @GET
     @Streaming
     Observable<ResponseBody> showLocation_S(@Url String imgUrl);

     @POST("/api/small/orders/create")
     @FormUrlEncoded
     Observable<BaseBean<XianXiaOrderBean>> ordersCreate(@Header("authorization") String apikey, @FieldMap Map<String, Object> map);

   */

    @POST("/standard/account/sendVerifyCode")
    Observable<BaseBean> sendVerifyCode(@Body Map<String, Object> map);

    @POST("/standard/account/login")
    Observable<BaseBean<LoginBean>> loginByMobile(@Body Map<String, Object> map);

    @POST("/standard/account/visitorLogin")
    Observable<BaseBean<LoginBean>> visitorLogin(@Body Map<String, Object> map);

    @POST("/standard/account/getNewAccountId")
    Observable<BaseBean<String>> getNewAccountId(@Body Map<String, Object> map);

    @POST("/standard/account/getAccountInfo")
    Observable<BaseBean<UserBean>> getAccountInfo(@Body Map<String, Object> map);
    @POST("/standard/product/productList")
    Observable<BaseBean<List<ProductListBean>>> productList(@Body Map<String, Object> map);

    @POST("/standard/product/payChannel")
    Observable<BaseBean<List<PayChannelBean>>> payChannel(@Body Map<String, Object> map);

    @POST("/standard/order/create")
    Observable<BaseBean<String>> create(@Body Map<String, Object> map);

    @POST("/standard/common/addFeedback")
    Observable<BaseBean> addFeedback(@Body Map<String, Object> map);

    @POST("/standard/account/cancelAccount")
    Observable<BaseBean<CancelAccountBean>> cancelAccount(@Body Map<String, Object> map);

    @POST("/standard/order/submitOrder")
    Observable<BaseBean<PayResultBean>> submitOrder(@Body Map<String, Object> map);

    @POST("/standard/common/base")
    Observable<BaseBean<APPBean>> appinfo(@Body Map<String, Object> map);


    @POST("/standard/order/queryPayOrder")
    Observable<BaseBean<PayResultBean>> queryPayOrder(@Body Map<String, Object> map);

    @POST
    Observable<TranslateBean> translate(@Url String imgUrl);


    @POST
    Observable<AccessTokenBean> getaccess_token(@Url String imgUrl, @Body Map<String,Object> map);


    @POST
    @FormUrlEncoded
    Observable<RecognitionResultBean> general_basic(@Url String imgUrl, @Field("image") String image);

}
