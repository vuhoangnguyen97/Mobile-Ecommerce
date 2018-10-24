package com.example.legia.mobileweb;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class comment extends AppCompatActivity {
    WebView webComment;
    private FrameLayout mContainer;
    private ProgressBar progressBar;
    boolean isLoading;
    private static final int NUMBER_OF_COMMENTS = 5;
    private String postUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        this.setTitle("Bình luận");
        webComment = findViewById(R.id.commentsView);

        mContainer =  findViewById(R.id.webview_frame);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        postUrl = getIntent().getStringExtra("url");

        if (TextUtils.isEmpty(postUrl)) {
            Toast.makeText(getApplicationContext(), "The web url shouldn't be empty", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        setLoading(true);
        loadComments();
    }

    private void loadComments() {
        webComment.setWebViewClient(new UriWebViewClient());
        webComment.setWebChromeClient(new UriChromeClient());
        webComment.getSettings().setJavaScriptEnabled(true);
        webComment.getSettings().setAppCacheEnabled(true);
        webComment.getSettings().setDomStorageEnabled(true);
        webComment.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webComment.getSettings().setSupportMultipleWindows(true);
        webComment.getSettings().setSupportZoom(false);
        webComment.getSettings().setBuiltInZoomControls(false);
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            webComment.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager.getInstance().setAcceptThirdPartyCookies(webComment, true);
        }

        // facebook comment widget including the article url
        String html = "<!doctype html> <html lang=\"en\"> <head></head> <body> " +
                "<div id=\"fb-root\"></div> <script>(function(d, s, id) { var js, fjs = d.getElementsByTagName(s)[0]; if (d.getElementById(id)) return; js = d.createElement(s); js.id = id; js.src = \"//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.6\"; fjs.parentNode.insertBefore(js, fjs); }(document, 'script', 'facebook-jssdk'));</script> " +
                "<div class=\"fb-comments\" data-href=\"" + postUrl + "\" " +
                "data-numposts=\"" + NUMBER_OF_COMMENTS + "\" data-order-by=\"reverse_time\">" +
                "</div> </body> </html>";

        webComment.loadDataWithBaseURL("http://www.nothing.com", html, "text/html", "UTF-8", null);
        webComment.setMinimumHeight(200);
    }

    private void setLoading(boolean isLoading) {
        this.isLoading = isLoading;

        if (isLoading)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);

        invalidateOptionsMenu();
    }

    private class UriWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            String host = Uri.parse(url).getHost();

            return !host.equals("m.facebook.com");

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String host = Uri.parse(url).getHost();
            setLoading(false);
            if (url.contains("/plugins/close_popup.php?reload")) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        mContainer.removeView(webComment);
                        loadComments();
                    }
                }, 600);
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            setLoading(false);
        }
    }

    class UriChromeClient extends WebChromeClient {

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog,
                                      boolean isUserGesture, Message resultMsg) {
            webComment = new WebView(getApplicationContext());
            webComment.setVerticalScrollBarEnabled(false);
            webComment.setHorizontalScrollBarEnabled(false);
            webComment.setWebViewClient(new UriWebViewClient());
            webComment.setWebChromeClient(this);
            webComment.getSettings().setJavaScriptEnabled(true);
            webComment.getSettings().setDomStorageEnabled(true);
            webComment.getSettings().setSupportZoom(false);
            webComment.getSettings().setBuiltInZoomControls(false);
            webComment.getSettings().setSupportMultipleWindows(true);
            webComment.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            mContainer.addView(webComment);
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(webComment);
            resultMsg.sendToTarget();

            return true;
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage cm) {
            //Log.i(TAG, "onConsoleMessage: " + cm.message());
            return true;
        }


    }


}
