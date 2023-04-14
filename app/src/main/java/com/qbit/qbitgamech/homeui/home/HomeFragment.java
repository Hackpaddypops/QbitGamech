package com.qbit.qbitgamech.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.qbit.qbitgamech.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment{

    private FragmentHomeBinding binding;
    LottieAnimationView noball;
    LottieAnimationView dotBall;
    LottieAnimationView wide;
    LottieAnimationView one;
    LottieAnimationView two;
    LottieAnimationView three;
    LottieAnimationView four;
    LottieAnimationView six;
    LottieAnimationView out;

    Group predictionGroup;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        noball = binding.noball;
        dotBall = binding.dotball;
        wide = binding.wide;
        one = binding.one;
        two = binding.tworun;
        three = binding.threerun;
        four = binding.four;
        six = binding.six;
        out = binding.out;
        predictionGroup= binding.predictionGroup;



        youtubeVideo();
        clicks();
        String predict="1";
        prediction(predict);
        String ballRes="2";
        ballResult(ballRes);


        TextView resultMessage = binding.resultmessage;
        resultMessage.setText("Your Prediction was correct. You earned 3 Qbits.");


        return binding.getRoot();



    }

    private void ballResult(String ballRes) {
        TextView ballResult = binding.ballresult;
        ballResult.setText(ballRes);
    }

    private void prediction(String predict) {
        TextView prediction = binding.userprediction;
        prediction.setText(predict);
    }

    private void clicks() {
        noball.setOnClickListener(view -> {

            Toast.makeText(getContext(), "You selected no Ball", Toast.LENGTH_SHORT).show();
            prediction("No Ball");
            stopLottie(noball);
        });

        dotBall.setOnClickListener(view -> {
            Toast.makeText(getContext(), "You selected dot Ball", Toast.LENGTH_SHORT).show();
            prediction("Dot Ball");
            stopLottie(dotBall);
        });

        wide.setOnClickListener(view -> {
            Toast.makeText(getContext(), "You selected Wide Ball", Toast.LENGTH_SHORT).show();
            prediction("Wide Ball");
            stopLottie(wide);
        });

        one.setOnClickListener(view -> {
            Toast.makeText(getContext(), "You selected one run.", Toast.LENGTH_SHORT).show();
            prediction("1");
            stopLottie(one);
        });

        two.setOnClickListener(view -> {
            Toast.makeText(getContext(), "You selected two run.", Toast.LENGTH_SHORT).show();
            prediction("2");
            stopLottie(two);
        });

        three.setOnClickListener(view -> {
            Toast.makeText(getContext(), "You selected three run.", Toast.LENGTH_SHORT).show();
            prediction("3");
            stopLottie(three);
        });

        four.setOnClickListener(view -> {
            Toast.makeText(getContext(), "You selected four.", Toast.LENGTH_SHORT).show();
            prediction("4");
            stopLottie(noball);
        });

        six.setOnClickListener(view -> {
            Toast.makeText(getContext(), "You selected sixer.", Toast.LENGTH_SHORT).show();
            prediction("6");
            stopLottie(noball);
        });

        out.setOnClickListener(view -> {
            Toast.makeText(getContext(), "You selected wicket", Toast.LENGTH_SHORT).show();
            prediction("Out");
            stopLottie(out);
        });
    }

    private void stopLottie(LottieAnimationView lottieNotStop) {
        List<LottieAnimationView> userPredictionList = new ArrayList<>();
        userPredictionList.add(noball);
        userPredictionList.add(wide);
        userPredictionList.add(dotBall);
        userPredictionList.add(one);
        userPredictionList.add(three);
        userPredictionList.add(two);
        userPredictionList.add(four);
        userPredictionList.add(six);
        userPredictionList.add(out);


        for (LottieAnimationView lotties:userPredictionList) {

            if(lotties==lottieNotStop){
                continue;
            }
            lotties.pauseAnimation();
            lotties.setClickable(false);

        }

    }

    private void youtubeVideo() {
        String myYouTubeVideoUrl = "wz-msmYesz8";

        YouTubePlayerView myYouTubePlayerView = binding.ytvideo;
        getLifecycle().addObserver(myYouTubePlayerView);
        myYouTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);

                youTubePlayer.cueVideo(myYouTubeVideoUrl,0);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }






}