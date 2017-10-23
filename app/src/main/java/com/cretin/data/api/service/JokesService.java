package com.cretin.data.api.service;


import com.cretin.data.api.model.JokesContentModel;
import com.cretin.data.api.model.JokesImgModel;
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
     *
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST( "/jokes/jokesList" )
    Observable<ResultModel<JokesContentModel>> getJokesList(@Field( "page" ) Integer page);

    /**
     * 获取推荐的文字段子
     *
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST( "/jokes/jokesRecList" )
    Observable<ResultModel<JokesContentModel>> getJokesRecList(@Field( "page" ) Integer page);

    /**
     * 获取所有的图片段子
     *
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST( "/jokes/jokesImgList" )
    Observable<ResultModel<JokesImgModel>> getJokesImgList(@Field( "page" ) Integer page);

    /**
     * 获取所有的图片段子
     *
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST( "/jokes/jokesImgRecList" )
    Observable<ResultModel<JokesImgModel>> getJokesImgRecList(@Field( "page" ) Integer page);

    /**
     * 文本段子点赞
     *
     * @return
     */
    @FormUrlEncoded
    @POST( "/jokes/text/like" )
    Observable<ResultModel> likes(@Field( "jokes_id" ) String jokes_id);

    /**
     * 文本段子 取消点赞
     *
     * @return
     */
    @FormUrlEncoded
    @POST( "/jokes/text/unlike" )
    Observable<ResultModel> unlikes(@Field( "jokes_id" ) String jokes_id);

    /**
     * 文本段子点赞
     *
     * @return
     */
    @FormUrlEncoded
    @POST( "/jokes/img/like" )
    Observable<ResultModel> imgLikes(@Field( "jokes_id" ) String jokes_id);

    /**
     * 文本段子 取消点赞
     *
     * @return
     */
    @FormUrlEncoded
    @POST( "/jokes/img/unlike" )
    Observable<ResultModel> imgUnlikes(@Field( "jokes_id" ) String jokes_id);
}


