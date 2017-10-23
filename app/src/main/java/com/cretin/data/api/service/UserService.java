package com.cretin.data.api.service;


import com.cretin.data.api.model.ResultModel;
import com.cretin.data.api.model.UserModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by grubber on 2017/1/6.
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param userName
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST( "/user/login" )
    Observable<ResultModel<UserModel>> login(@Field( "username" ) String userName, @Field( "password" ) String password);

    /**
     * 用户退出登录
     *
     * @return
     */
    @POST( "/user/logout" )
    Observable<ResultModel<UserModel>> logout();

    /**
     * 获取验证码
     *
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST( "/user/reg/sendcode" )
    Observable<ResultModel> getCode(@Field( "phone" ) String phone);

    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST( "/user/reg/register" )
    Observable<ResultModel> register(@Field( "phone" ) String phone,
                                      @Field( "password" ) String password,
                                      @Field( "code" ) String code);
}
