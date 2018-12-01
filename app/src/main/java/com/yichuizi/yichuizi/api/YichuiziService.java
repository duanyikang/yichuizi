package com.yichuizi.yichuizi.api;

import com.yichuizi.yichuizi.bean.BookBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * 作者： duanyikang on 2018/11/28.
 * 邮箱： 574281045@qq.com
 * 描述：
 */
public interface YichuiziService {
    @GET("v2/book/1220562")
    Observable<BookBean> getBook();

//    @GET("v2/movie/top250")
//    Observable<Top250Bean> getTop250(@Query("count") int count);

    @GET("v2/book/1220562")
    Observable<String> getBookString();

    /**
     * 上传多个文件  demo
     *
     * @param uploadUrl 地址
     * @param files     文件
     * @return ResponseBody
     */
    @Multipart
    @POST
    Observable<String> uploadFiles(@Url String uploadUrl,
                                   @Part List<MultipartBody.Part> files);
}
