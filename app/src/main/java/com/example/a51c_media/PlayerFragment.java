package com.example.a51c_media;

import android.net.Uri;import android.os.Bundle;

import androidx.fragment.app.Fragment;import androidx.navigation.Navigation;

import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.webkit.WebChromeClient;import android.webkit.WebSettings;import android.webkit.WebView;import android.webkit.WebViewClient;import android.widget.Button;import android.widget.Toast;

public class PlayerFragment extends Fragment {

    private WebView youtubeWebView;
    private Button btnBackToStream;

    private String youtubeUrl = "";
    private String currentUsername = "";

    public PlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_player, container, false);

        youtubeWebView = view.findViewById(R.id.youtubeWebView);
        btnBackToStream = view.findViewById(R.id.btnBackToStream);

        Bundle bundle = getArguments();
        if (bundle != null) {
            youtubeUrl = bundle.getString("youtubeUrl", "");
            currentUsername = bundle.getString("currentUsername", "");
        }

        String videoId = extractYoutubeVideoId(youtubeUrl);

        if (videoId == null || videoId.isEmpty()) {
            Toast.makeText(requireContext(), "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).popBackStack();
            return view;
        }

        setupYoutubeWebView(videoId);

        btnBackToStream.setOnClickListener(v -> {
            Navigation.findNavController(view).popBackStack();
        });

        return view;
    }

    private void setupYoutubeWebView(String videoId) {
        WebSettings webSettings = youtubeWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        youtubeWebView.setWebChromeClient(new WebChromeClient());

        String html =
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                        "<meta name=\"referrer\" content=\"strict-origin-when-cross-origin\">" +
                        "<style>" +
                        "html, body {" +
                        "   margin: 0;" +
                        "   padding: 0;" +
                        "   width: 100%;" +
                        "   height: 100%;" +
                        "   background-color: black;" +
                        "   overflow: hidden;" +
                        "}" +

                        ".video-container {" +
                        "   position: relative;" +
                        "   width: 100%;" +
                        "   height: 100%;" +
                        "   background-color: black;" +
                        "}" +

                        ".video-container iframe {" +
                        "   position: absolute;" +
                        "   top: 0;" +
                        "   left: 0;" +
                        "   width: 100%;" +
                        "   height: 100%;" +
                        "   border: 0;" +
                        "}" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "<div class=\"video-container\">" +
                        "<iframe " +
                        "src=\"https://www.youtube.com/embed/" + videoId + "?enablejsapi=1&playsinline=1&rel=0&origin=https://com.example.a51c_media\" " +
                        "title=\"YouTube video player\" " +
                        "frameborder=\"0\" " +
                        "allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" " +
                        "referrerpolicy=\"strict-origin-when-cross-origin\" " +
                        "allowfullscreen>" +
                        "</iframe>" +
                        "</div>" +
                        "</body>" +
                        "</html>";

        youtubeWebView.loadDataWithBaseURL(
                "https://com.example.a51c_media/",
                html,
                "text/html",
                "UTF-8",
                null
        );
    }

    private String extractYoutubeVideoId(String url) {
        try {
            Uri uri = Uri.parse(url);

            String host = uri.getHost();
            if (host == null) {
                return null;
            }

            host = host.toLowerCase();

            if (host.contains("youtu.be")) {
                return uri.getLastPathSegment();
            }

            if (host.contains("youtube.com")) {
                String videoId = uri.getQueryParameter("v");
                if (videoId != null && !videoId.isEmpty()) {
                    return videoId;
                }

                String path = uri.getPath();
                if (path != null && path.startsWith("/embed/")) {
                    return path.replace("/embed/", "");
                }

                if (path != null && path.startsWith("/shorts/")) {
                    return path.replace("/shorts/", "");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}