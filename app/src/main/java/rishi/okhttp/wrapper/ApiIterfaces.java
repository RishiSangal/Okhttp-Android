package rishi.okhttp.wrapper;

/**
 * Created by Rishi.Sangal on 8/5/2016.
 */
public class ApiIterfaces {

    // Interface that give response of the fragemnt or activity
    public interface GetResponse{
        void getResponse(String response);
        void getErrorResponse(int code, String errorResponse);
    }

}
