package com.example.skycastle.UnivReveiw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.skycastle.R;

import java.util.zip.Inflater;

public class WebViewFragment extends Fragment {
    private WebView webView;
    private WebSettings webSettings;
    private String url = null;

    public WebViewFragment(String url) {
        this.url = url;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_webview, container, false);

        webView = (WebView) rootView.findViewById(R.id.review_webview);
        webView.setWebViewClient(new WebViewClient());
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if (url == null) {
            Toast.makeText(getContext(), "WebViewFg url miss", Toast.LENGTH_SHORT).show();
        } else {
            webView.loadUrl(url);
        }

        return rootView;
    }
}
