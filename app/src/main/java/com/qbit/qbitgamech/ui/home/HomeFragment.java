package com.qbit.qbitgamech.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.qbit.qbitgamech.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        String myYouTubeVideoUrl = "https://www.youtube.com/embed/wz-msmYesz8";

        String dataUrl =
                "<html>" +
                        "<body bgcolor=\"1C2951\">" +
                        "<iframe width=\"960\" height=\"600\" src=\""+myYouTubeVideoUrl+"\" frameborder=\"0\" allowfullscreen/>" +
                        "</body>" +
                        "</html>";

        WebView myWebView = binding.ytvideo;
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadData(dataUrl, "text/html", "utf-8");

        TextView prediction = binding.userprediction;
        prediction.setText("1");

        TextView ballResult = binding.ballresult;
        ballResult.setText("2");

        TextView resultMessage = binding.resultmessage;
        resultMessage.setText("Your Prediction was correct. You earned 3 Qbits.");


        return binding.getRoot();



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}