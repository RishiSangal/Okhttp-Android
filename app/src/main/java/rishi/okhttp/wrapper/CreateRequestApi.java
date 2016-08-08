package rishi.okhttp.wrapper;

import android.app.ProgressDialog;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Rishi.Sangal on 8/2/2016.
 */
public final class CreateRequestApi {

    LinkedHashMap<String, String> params;
    JSONObject object;
    CallSCMApi scmApi;
    Headers.Builder headerBuilder;
    MediaMultipartBulider mediaMultipartBulider;
    private boolean isJson;
    String jsonToPost;
    Context activity;
    enum REQUEST_TYPE {GET, POST};
    REQUEST_TYPE requestedApiType;
    ApiIterfaces.GetResponse responseObject;

    /// Constructer to initalize Objects
    private CreateRequestApi(CreateBuilder createBuilder, ApiIterfaces.GetResponse response1){
        params = createBuilder.params;
        requestedApiType = createBuilder.request_type;
        headerBuilder = createBuilder.headers;
        this.isJson = createBuilder.isJson;
        this.jsonToPost = createBuilder.jsonToPost;
        this.activity = createBuilder.activity;
        responseObject = response1;
        createApiToCall();
    }

    public CreateRequestApi(MediaMultipartBulider mediaMultipartBulider, ApiIterfaces.GetResponse response1) {
        this.mediaMultipartBulider = mediaMultipartBulider;
        requestedApiType = REQUEST_TYPE.POST;
        headerBuilder = mediaMultipartBulider.headers;
        this.activity = mediaMultipartBulider.activity;
        responseObject = response1;
        createApiToCall();
    }

    private void createApiToCall() {
        scmApi = new CallSCMApi(activity);
        setInterface(responseObject);
    }

    // To Return The object
    public JSONObject getJsonToSend(){
        object = new JSONObject();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                try {
                    object.put(entry.getKey(),entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return object;
    }

    // called if we have anyother value to Json object
    public void runApi(String url) {
        try {
            if (mediaMultipartBulider == null){
                switch (requestedApiType){
                    case GET:
                        scmApi.get(getUrlWithParameters(url), headerBuilder);
                        break;

                    case POST:
                        if (isJson)
                            scmApi.postJson(url, jsonToPost, headerBuilder);
                        else
                            scmApi.postWithParameter(url, getFormBuilderPost(), headerBuilder);
                        break;
                }
            }
            else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FormBody getFormBuilderPost() {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    public HttpUrl getUrlWithParameters(String url){
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        return urlBuilder.build();
    }
    // set interface so that response can reach to required class
    private void setInterface(ApiIterfaces.GetResponse response1){
        scmApi.setInterface(response1);
    }

    public static class CreateBuilder{

        LinkedHashMap<String, String> params;
        REQUEST_TYPE request_type;
        Headers.Builder headers;
        boolean isJson;
        String jsonToPost;
        Context activity;

        public CreateBuilder(Context mainActivity){
            params = new LinkedHashMap<>();
            headers = new Headers.Builder();
            activity = mainActivity;
        }

        public CreateBuilder addRequestType(REQUEST_TYPE request_type){
            this.request_type = request_type;
            return this;
        }

        public CreateBuilder addHeader(String key, String value){
            headers.add(key, value);
            return this;
        }

        public CreateBuilder addParams(String key, String value){
            params.put(key, value);
            return this;
        }

        public CreateBuilder addJsonParam(String key, JSONObject json){
            if (!isJson)
                isJson = true;
            params.put(key, json.toString());
            return this;
        }
        public CreateBuilder addJsonForPostApi(JSONObject object){
            jsonToPost = object.toString();
            return this;
        }

        public CreateRequestApi build(ApiIterfaces.GetResponse response1) {
            return new CreateRequestApi(this, response1);
        }

    }

    public static class MediaMultipartBulider{

        MultipartBody.Builder mediaFileBuilder;
        Headers.Builder headers;
        Context activity;

        public MediaMultipartBulider(Context activity){
            mediaFileBuilder = new MultipartBody.Builder();
            mediaFileBuilder.setType(MultipartBody.FORM);
            headers = new Headers.Builder();
            this.activity = activity;
        }

        public MediaMultipartBulider addHeader(String key, String value){
            headers.add(key, value);
            return this;
        }

        public MediaMultipartBulider addMultipartImageFile(String key, String fileName, String filePath){
            mediaFileBuilder.addFormDataPart(key, fileName, RequestBody.create(
                    MediaType.parse("image/jpeg"), new File(filePath)));
            return this;
        }

        public MediaMultipartBulider addMultipartVideoFile(String key, String fileName, String filePath){
            mediaFileBuilder.addFormDataPart(key, fileName, RequestBody.create(
                    MediaType.parse("video/mp4"), new File(filePath)));
            return this;
        }

        public void MediaMultipartBulider(String name, String fileName, String filePath) {
            mediaFileBuilder.addFormDataPart(name, fileName, RequestBody.create(
                    MediaType.parse("text/plain"), new File(filePath)));
        }

        public CreateRequestApi build(ApiIterfaces.GetResponse response1) {
            return new CreateRequestApi(this, response1);
        }
    }

    public void showDialog() {
        showDialog("Loading please wait");
    }
    public void showDialog(String message) {
        showDialog(null, message);
    }

    public void showDialog(String title, String message) {
        scmApi.showDailoge(title, message);
    }
}