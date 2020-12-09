package com.xut.service;

import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.stereotype.Service;


@Service
public class QiniuService {
    private static final String ACCESS_KEY = "IuF2PwCImQoqaUEKIkYVGAZ9_fGogcgVU1dRaJJD";
    private static final String SECRET_KEY = "HILO_gqtLkUJXUW51aD9dQPWhEuG3bLcv6rwps2H";
    private static final String BUCKET_NAME = "xxxuxonezero";

    public String getToken() {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(BUCKET_NAME, null, expireSeconds, putPolicy);
        return upToken;
    }
}
