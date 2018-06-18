package com.viatorfortis.bebaker.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {
    private static final String RECIPE_LIST_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private static final String TAG = "NetworkUtilsLog";

    public static String getRecipeListUrl()
            throws IOException {
        URL url = buildRecipeListUrl();
        return getResponseFromHttpUrl(url);
    }

    private static URL buildRecipeListUrl() {
        Uri uri = Uri.parse(RECIPE_LIST_URL);

        URL url = null;

        try {
            url = new URL(uri.toString() );
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage() );
        }

        return url;
    }

    private static String getResponseFromHttpUrl(URL url)
            throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
