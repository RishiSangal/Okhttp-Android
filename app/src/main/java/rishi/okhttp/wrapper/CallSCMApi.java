package rishi.okhttp.wrapper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.Vector;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Rishi.Sangal on 8/1/2016.
 */
public class CallSCMApi {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client;
    ConnectionDetector connection;
    Context context;
    Vector<ProgressDialog> progress;

    public CallSCMApi(Context activity){
        context = activity;
        client = new OkHttpClient();
        progress = new Vector<>();
    }

    Request.Builder request;
    public void postJson(String url, String json, Headers.Builder headerBuilder) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        request = new Request.Builder()
                .url(url).post(body).headers(headerBuilder.build());
        request.build();
        new runApiTask().execute();
    }

    public void get(HttpUrl url, Headers.Builder headerBuilder) throws IOException {
        request = new Request.Builder()
                .url(url).headers(headerBuilder.build());
        request.build();
        new runApiTask().execute();
    }

    public void postWithParameter(String url, FormBody formBuilderPost, Headers.Builder headerBuilder) {
        request = new Request.Builder().url(url)
                .post(formBuilderPost)
                .headers(headerBuilder.build());
        request.build();
        new runApiTask().execute();
    }

    ApiIterfaces.GetResponse response;
    public void setInterface(ApiIterfaces.GetResponse response){
        this.response = response;
    }

    public void showDailoge(String title, String message) {
            progress.add(ProgressDialog.show(context, title, message, true, false));
    }

    public void dismissDialog() {
        if (progress != null)
            for (ProgressDialog prog : progress)
                prog.dismiss();
    }

    public class runApiTask extends AsyncTask<Void, Void, Void> {
        Response apiResponse;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                connection = new ConnectionDetector(context);
                if (!connection.isConnectingToInternet()) {
                    response.noInternetConnection();
                    return null;
                }
                apiResponse = client.newCall(request.build()).execute();
                dismissDialog();
                if (apiResponse.isSuccessful())
                    response.getResponse(apiResponse.body().string());
                else
                    response.getErrorResponse(apiResponse.code(), apiResponse.body().string() );
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
        }
    }
}
