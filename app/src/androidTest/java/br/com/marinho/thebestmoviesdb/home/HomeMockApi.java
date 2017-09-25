package br.com.marinho.thebestmoviesdb.home;

import android.content.Context;

import br.com.marinho.thebestmoviesdb.RestServiceTestHelper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * Created by Marinho on 25/09/17.
 */

public class HomeMockApi {

    private final MockWebServer server;
    private Context context;

    public HomeMockApi(MockWebServer server, Context context){
        this.server = server;
        this.context = context;
    }

    public HomeMockApi responseNowPlayingMovie() throws Exception{
        server.enqueue(new MockResponse()
        .setResponseCode(200)
        .setBody(RestServiceTestHelper.getStringFromFile(context, "response_nowplaying")));

        return this;
    }
}
//        String fileName = "quote_200_ok_response.json";
//        server.enqueue(new MockResponse()
//                .setResponseCode(200)
//                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));