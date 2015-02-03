package cz.cesnet.cloud.occi.api.http;

import cz.cesnet.cloud.occi.api.exception.CommunicationException;
import java.io.IOException;
import java.net.URI;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(HTTPHelper.class);

    public static HttpGet prepareGet(String uri, Header[] headers) {
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeaders(headers);
        return httpGet;
    }

    public static HttpGet prepareGet(String uri) {
        return prepareGet(uri, null);
    }

    public static HttpHead prepareHead(String uri, Header[] headers) {
        HttpHead httpHead = new HttpHead(uri);
        httpHead.setHeaders(headers);
        return httpHead;
    }

    public static HttpHead prepareHead(String uri) {
        return prepareHead(uri, null);
    }

    public static HttpGet prepareGet(URI uri, Header[] headers) {
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeaders(headers);
        return httpGet;
    }

    public static HttpGet prepareGet(URI uri) {
        return prepareGet(uri, null);
    }

    public static HttpHead prepareHead(URI uri, Header[] headers) {
        HttpHead httpHead = new HttpHead(uri);
        httpHead.setHeaders(headers);
        return httpHead;
    }

    public static HttpHead prepareHead(URI uri) {
        return prepareHead(uri, null);
    }

    public static HttpDelete prepareDelete(String uri, Header[] headers) {
        HttpDelete httpDelete = new HttpDelete(uri);
        httpDelete.setHeaders(headers);
        return httpDelete;
    }

    public static HttpDelete prepareDelete(String uri) {
        return prepareDelete(uri, null);
    }

    public static HttpDelete prepareDelete(URI uri, Header[] headers) {
        HttpDelete httpDelete = new HttpDelete(uri);
        httpDelete.setHeaders(headers);
        return httpDelete;
    }

    public static HttpDelete prepareDelete(URI uri) {
        return prepareDelete(uri, null);
    }

    public static HttpPost preparePost(String uri, Header[] headers) {
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeaders(headers);
        return httpPost;
    }

    public static HttpPost preparePost(String uri) {
        return preparePost(uri, null);
    }

    public static HttpPost preparePost(URI uri, Header[] headers) {
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeaders(headers);
        return httpPost;
    }

    public static HttpPost preparePost(URI uri) {
        return preparePost(uri, null);
    }

    public static String runRequestReturnResponseBody(HttpRequest httpRequest, HttpHost target, CloseableHttpClient client, HttpContext context, int status
    ) throws CommunicationException {
        LOGGER.debug("Running request...");
        try {
            LOGGER.debug("Executing request {} to target {}", httpRequest.getRequestLine(), target);
            try (CloseableHttpResponse response = client.execute(target, httpRequest, context)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                LOGGER.debug("Response: {}\nHeaders: {}\nBody: {}", response.getStatusLine().toString(), response.getAllHeaders(), responseBody);
                if (response.getStatusLine().getStatusCode() != status) {
                    throw new CommunicationException(response.getStatusLine().toString());
                }

                return responseBody;
            }
        } catch (IOException ex) {
            throw new CommunicationException(ex);
        }
    }

    public static String runRequestReturnResponseBody(HttpRequest httpRequest, HttpHost target, CloseableHttpClient client, HttpContext context
    ) throws CommunicationException {
        return runRequestReturnResponseBody(httpRequest, target, client, context, HttpStatus.SC_OK);
    }

    public static boolean runRequestReturnStatus(HttpRequest httpRequest, HttpHost target, CloseableHttpClient client, HttpContext context, int status
    ) throws CommunicationException {
        LOGGER.debug("Running request...");
        try {
            LOGGER.debug("Executing request {} to target {}", httpRequest.getRequestLine(), target);
            try (CloseableHttpResponse response = client.execute(target, httpRequest, context)) {
                LOGGER.debug("Response: {}\nHeaders: {}", response.getStatusLine().toString(), response.getAllHeaders());

                return response.getStatusLine().getStatusCode() == status;
            }
        } catch (IOException ex) {
            throw new CommunicationException(ex);
        }
    }

    public static boolean runRequestReturnStatus(HttpRequest httpRequest, HttpHost target, CloseableHttpClient client, HttpContext context) throws CommunicationException {
        return runRequestReturnStatus(httpRequest, target, client, context, HttpStatus.SC_OK);
    }
}