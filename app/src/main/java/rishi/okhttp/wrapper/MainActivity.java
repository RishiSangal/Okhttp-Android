package rishi.okhttp.wrapper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    CreateRequestApi createRequestApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createRequestApi = new CreateRequestApi.CreateBuilder(MainActivity.this).addRequestType
                (CreateRequestApi.REQUEST_TYPE.GET)
                .addHeader("X-AUTHKEY","E6C1A952987CECAE5E2E982E87B56421")
                .addHeader("X-APPKEY","sasefyweadfkdhaecommstreetinfoservicessiowedaflsdfjs")
                .addRequestType(CreateRequestApi.REQUEST_TYPE.GET)
                .addParams("key","value")
                .build(respose);
        createRequestApi.showDialog();
        createRequestApi.runApi("http://s171227279.onlinehome.us/ecom/nkcc/public/index.php/api/v1/member/getFeaturedMembers");
    }

    private ApiIterfaces.GetResponse respose = new ApiIterfaces.GetResponse() {
        @Override
        public void getResponse(String response) {
            String getResponse = response;
        }

        @Override
        public void getErrorResponse(int code, String errorResponse) {

        }

        @Override
        public void noInternetConnection() {

        }
    };
}
