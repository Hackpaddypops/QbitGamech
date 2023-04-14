package com.qbit.qbitgamech.homeui.home;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.qbit.qbitgamech.DisplayAds;
import com.qbit.qbitgamech.R;
import com.qbit.qbitgamech.SessionManager;
import com.qbit.qbitgamech.databinding.FragmentHomeBinding;
import com.qbit.qbitgamech.firebase.FCM;
import com.qbit.qbitgamech.firebase.FirebaseDB;
import com.qbit.qbitgamech.model.CoinHistory;
import com.qbit.qbitgamech.model.Prediction;

import org.json.JSONArray;
import org.json.JSONException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class HomeFragment extends Fragment {

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
    DisplayAds displayAds;
    String userPredict;

    TextView ballResult;
    TextView resultMessage;
    FCM fcm;
    CardView unlockPredictionCard;
    Button unlockPrediction;
    SessionManager sessionManager;
    BroadcastReceiver broadcastReceiver;
    LocalDateTime userPredictionTime;
    TextView qbitScore;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        MobileAds.initialize(requireContext(), initializationStatus -> Log.d("Ads","Ads initialised in home activity."));
        displayAds = new DisplayAds();
        displayAds.loadBannerAds(requireContext(),getActivity(),R.id.my_template,false);
        displayAds.loadInterstitalAds(requireContext(),getActivity());


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        unlockPrediction=binding.unlockPrediction;
        unlockPredictionCard=binding.unlockPredictionCard;
        qbitScore = requireActivity().findViewById(R.id.qbitScore);
        noball = binding.noball;
        dotBall = binding.dotball;
        wide = binding.wide;
        one = binding.one;
        two = binding.tworun;
        three = binding.threerun;
        four = binding.four;
        six = binding.six;
        out = binding.out;
        resultMessage = binding.resultmessage;
        ballResult = binding.ballresult;
        sessionManager = new SessionManager(requireContext());
        qbitScore.setText(String.valueOf(sessionManager.getQbits()));


        clicks();
        getYtVideoId();
        unlockPrediction.setOnClickListener(view -> {
            getBallResult();
            displayAds.loadRewardedAds(requireContext(), getActivity());
        });



        return binding.getRoot();



    }


    private void updateQbit(String ballRes) {
        int qbit;
        switch (ballRes){
            case "1" : qbit = 2;
            break;
            case "Dot Ball" : qbit = 1;
            break;
            case "No Ball" : qbit = 8;
                break;
            case "Wide Ball" : qbit = 9;
                break;
            case "2" : qbit = 3;
                break;
            case "3" : qbit = 4;
                break;
            case "4" : qbit = 5;
                break;
            case "6" : qbit = 6;
                break;
            case "Out" : qbit = 10;
                break;
            default: qbit = 0;
        }
        sessionManager = new SessionManager(requireContext());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseDB firebaseDB = new FirebaseDB();
        Prediction predictionObject = new Prediction();
        predictionObject.setUserInput(userPredict);
        predictionObject.setUserInputTime(userPredictionTime);
        predictionObject.setResult(ballRes);
        predictionObject.setResultTime(LocalDateTime.now());
        predictionObject.setMatched(true);
        predictionObject.setQbitsCredited(qbit);
        CoinHistory coinHistory = new CoinHistory(LocalDateTime.now(),qbit);
        db.collection("User").document(sessionManager.getSessionDetails("key_session_email"))
                .update("coinHistory", FieldValue.arrayUnion(coinHistory));
        db.collection("User").document(sessionManager.getSessionDetails("key_session_email"))
                .update("predictions", FieldValue.arrayUnion(predictionObject));
        if (sessionManager.getQbits()==0){
            sessionManager.updateQbits(qbit);
            Map<String,Object> map = new HashMap<>();
            map.put("qbits",qbit);
            firebaseDB.updateUser(map,sessionManager.getSessionDetails("key_session_email"));
            qbitScore.setText(String.valueOf(qbit));
        } else {
            qbit = sessionManager.getQbits() + qbit;
            sessionManager.updateQbits(qbit);
            Map<String,Object> map = new HashMap<>();
            map.put("qbits",qbit);
            firebaseDB.updateUser(map,sessionManager.getSessionDetails("key_session_email"));
            qbitScore.setText(String.valueOf(qbit));
        }
    }

    private void prediction(String predict) {
        TextView prediction = binding.userprediction;
        userPredict = predict;
        prediction.setText(predict);
        userPredictionTime = LocalDateTime.now();
    }

    private void clicks() {
        noball.setOnClickListener(view -> {

            Toast.makeText(getContext(), "You selected no Ball", Toast.LENGTH_SHORT).show();
            prediction("No Ball");
            stopLottie(noball,false);
        });

        dotBall.setOnClickListener(view -> {
            Toast.makeText(getContext(), "You selected dot Ball", Toast.LENGTH_SHORT).show();
            prediction("Dot Ball");
            stopLottie(dotBall,false);
        });

        wide.setOnClickListener(view -> {
            Toast.makeText(getContext(), "You selected Wide Ball", Toast.LENGTH_SHORT).show();
            prediction("Wide Ball");
            stopLottie(wide,false);
        });

        one.setOnClickListener(view -> {
            Toast.makeText(getContext(), "You selected one run.", Toast.LENGTH_SHORT).show();
            prediction("1");
            stopLottie(one,false);
        });

        two.setOnClickListener(view -> {
            Toast.makeText(getContext(), "You selected two run.", Toast.LENGTH_SHORT).show();
            prediction("2");
            stopLottie(two,false);
        });

        three.setOnClickListener(view -> {
            Toast.makeText(getContext(), "You selected three run.", Toast.LENGTH_SHORT).show();
            prediction("3");
            stopLottie(three,false);
        });

        four.setOnClickListener(view -> {
            Toast.makeText(getContext(), "You selected four.", Toast.LENGTH_SHORT).show();
            prediction("4");
            stopLottie(four,false);
        });

        six.setOnClickListener(view -> {
            Toast.makeText(getContext(), "You selected sixer.", Toast.LENGTH_SHORT).show();
            prediction("6");
            stopLottie(six,false);
        });

        out.setOnClickListener(view -> {
            Toast.makeText(getContext(), "You selected wicket", Toast.LENGTH_SHORT).show();
            prediction("Out");
            stopLottie(out,false);
        });
    }

    private void stopLottie(LottieAnimationView lottieNotStop, boolean playPause) {
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

        if (playPause){
            for (LottieAnimationView lotties:userPredictionList) {
                lotties.playAnimation();
                lotties.setClickable(true);
            }
        } else {
            for (LottieAnimationView lotties:userPredictionList) {

                if(lotties==lottieNotStop){
                    continue;
                }
                lotties.pauseAnimation();
                lotties.setClickable(false);
            }
        }



    }



    private void youtubeVideo(String ytVideoId) {

        YouTubePlayerView myYouTubePlayerView = binding.ytvideo;
        getLifecycle().addObserver(myYouTubePlayerView);
        myYouTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                youTubePlayer.cueVideo(ytVideoId,0);

            }
        });
    }

    private void getYtVideoId() {
        AtomicBoolean isConditionMet = new AtomicBoolean(true);
        RequestQueue requestQueuePlaylist = Volley.newRequestQueue(requireContext());
        String playListItemUrl = "https://youtube.googleapis.com/youtube/v3/playlistItems?part=contentDetails&playlistId=UUphZiShLYpa-nIygo-TxepA&access_token=AIzaSyBt-ZkjA32LWzEuctMJrrpLgR4Xa8P3A0M&key=AIzaSyBt-ZkjA32LWzEuctMJrrpLgR4Xa8P3A0M";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, playListItemUrl, null, response -> {
            try {
                JSONArray itemsArray=response.getJSONArray("items");
                Log.d("PlayListItem", response.toString());
                for (int i = 0; i < itemsArray.length(); i++) {
                    String videoListUrl = "https://youtube.googleapis.com/youtube/v3/videos?part=liveStreamingDetails&id=" +
                    itemsArray.getJSONObject(i).getJSONObject("contentDetails").get("videoId") +
                            "&access_token=AIzaSyBt-ZkjA32LWzEuctMJrrpLgR4Xa8P3A0M&key=AIzaSyBt-ZkjA32LWzEuctMJrrpLgR4Xa8P3A0M";
                    RequestQueue requestQueueVideo  = Volley.newRequestQueue(requireContext());
                    JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, videoListUrl, null, response1 -> {
                        Log.d("VideoIdList", response1.toString());

                        try {
                            if (isConditionMet.get()){
                            if(response1.getJSONArray("items").getJSONObject(0).getJSONObject("liveStreamingDetails").has("concurrentViewers")){
                                youtubeVideo(response1.getJSONArray("items").getJSONObject(0).getString("id"));
                                sessionManager.updateSessionDetails("key_yt_video_id",response1.getJSONArray("items").getJSONObject(0).getString("id"));
                                unlockPredictionCard.setVisibility(View.VISIBLE);
                                isConditionMet.set(false);
                            } else if (response1.getJSONArray("items").getJSONObject(0).getJSONObject("liveStreamingDetails").has("actualEndTime")){
                                youtubeVideo(response1.getJSONArray("items").getJSONObject(0).getString("id"));
                                sessionManager.updateSessionDetails("key_yt_video_id",response1.getJSONArray("items").getJSONObject(0).getString("id"));
                                isConditionMet.set(false);
                            }
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }, error -> Log.e("YTVideoId", "Failed to get playlistItem." + error.getMessage()));
                    requestQueueVideo.add(jsonObjectRequest1);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> Log.e("YTVideoId", "Failed to get playlistItem." + error.getMessage()));
        requestQueuePlaylist.add(jsonObjectRequest);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requireContext().unregisterReceiver(broadcastReceiver);
        fcm = new FCM();
        fcm.unsubscribe("predictionResult");
        displayAds.loadBannerAds(requireContext(),getActivity(),R.id.my_template,true);
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        SessionManager sessionManager = new SessionManager(requireContext());
        if (sessionManager.getSessionDetails("key_yt_video_id")!=null){
            youtubeVideo(sessionManager.getSessionDetails("key_yt_video_id"));
        }

        displayAds.loadBannerAds(requireContext(),getActivity(),R.id.my_template,false);
    }

    @Override
    public void onPause() {
        super.onPause();
        displayAds.loadBannerAds(requireContext(),getActivity(),R.id.my_template,true);
    }


    private void ballResult(String result) {
        stopLottie(null,true);

        ballResult.setText(result);
        if(result.equals(userPredict)){
            resultMessage.setText("Your Prediction was correct.");
            updateQbit(result);
            userPredict = null;
        } else {
            resultMessage.setText("Your Prediction was wrong.");
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            sessionManager = new SessionManager(requireContext());
            Prediction predictionObject = new Prediction();
            predictionObject.setUserInput(userPredict);
            predictionObject.setUserInputTime(userPredictionTime);
            predictionObject.setResult(result);
            predictionObject.setResultTime(LocalDateTime.now());
            predictionObject.setMatched(false);
            predictionObject.setQbitsCredited(0);
            db.collection("User").document(sessionManager.getSessionDetails("key_session_email"))
                    .update("predictions", FieldValue.arrayUnion(predictionObject));
            userPredict = null;
        }

    }

    private void getBallResult(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("result");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String result = dataSnapshot.getValue(String.class);
                Log.d("Prediction Result", "Value is: " + result);
                if (result != null) {
                    ballResult(result);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Prediction Result", "Failed to read value.", error.toException());
            }
        });
    }
}