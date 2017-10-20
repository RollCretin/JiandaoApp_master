package com.cretin.data.api.service;


import com.cretin.data.api.model.GetCodeModel;
import com.cretin.data.api.model.InfoModel;
import com.cretin.data.api.model.JokesContentModel;
import com.cretin.data.api.model.JokesImgModel;
import com.cretin.data.api.model.LoginModel;
import com.cretin.data.api.model.ResultModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by grubber on 2017/1/6.
 */
public interface JokesService {
    /**
     * 获取所有的文字段子
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST( "/jokes/jokesList" )
    Observable<ResultModel<JokesContentModel>> getJokesList(@Field( "page" ) Integer page);

    /**
     * 获取所有的文字段子
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST( "/jokes/jokesImgList" )
    Observable<ResultModel<JokesImgModel>> getJokesImgList(@Field( "page" ) Integer page);

    /**
     * 测试
     * @return
     */
    @POST( "/weixin/app/active" )
    Observable<ResultModel<InfoModel>> list();

    /**
     * 用户登录
     *
     * @param userName
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST( "/login/doLogin" )
    Observable<LoginModel> login(@Field( "userName" ) String userName, @Field( "password" ) String password);

    /**
     * 获取验证码
     *
     * @param mobilePhone
     * @return
     */
    @FormUrlEncoded
    @POST( "/reg/getCheckNumber" )
    Observable<GetCodeModel> getCode(@Field( "mobilePhone" ) String mobilePhone);

    /**
     * 注册
     *
     * @param mobilePhone
     * @return
     */
    @FormUrlEncoded
    @POST( "/reg/save" )
    Observable<GetCodeModel> register(@Field( "mobilePhone" ) String mobilePhone,
                                      @Field( "password" ) String password,
                                      @Field( "checkNumber" ) String checkNumber);
}
