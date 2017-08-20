package com.jiandao.data.api.service;


import com.jiandao.data.api.model.InfoModel;
import com.jiandao.data.api.model.ResultModel;

import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by grubber on 2017/1/6.
 */
public interface TopService {
    @POST("/weixin/app/active")
    Observable<ResultModel<InfoModel>> list();
}
