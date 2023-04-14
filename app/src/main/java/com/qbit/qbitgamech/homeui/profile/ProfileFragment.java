package com.qbit.qbitgamech.homeui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.qbit.qbitgamech.DisplayAds;
import com.qbit.qbitgamech.GiveFeedback;
import com.qbit.qbitgamech.ReferAndEarn;
import com.qbit.qbitgamech.SessionManager;
import com.qbit.qbitgamech.databinding.FragmentProfileBinding;
import com.qbit.qbitgamech.leaderboard.Leaderboard;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    CardView leaderboardCard;
    CardView feedbackCard;
    CardView referAndEarnCard;
    CardView updateProfileCard;
    CardView watchVideo;
    ImageView profilePic;
    TextView name;
    TextView email;
    Button logout;
    SessionManager sessionManager;
    DisplayAds displayAds;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        ProfileViewModel notificationsViewModel =
//                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sessionManager = new SessionManager(requireContext());
        displayAds= new DisplayAds();
        leaderboardCard= binding.learderboardCard;
        referAndEarnCard = binding.referAndEarn;
        updateProfileCard=binding.updateProfileCard;
        feedbackCard =binding.feedbackCard;
        profilePic=binding.profilepic;
        name= binding.name;
        email=binding.emailid;
        logout=binding.logoutButton;
        watchVideo=binding.watchEarnCard;
        leaderboardCard.setOnClickListener(view -> startActivity(new Intent(getContext(), Leaderboard.class)));
        referAndEarnCard.setOnClickListener(view -> startActivity(new Intent(getContext(), ReferAndEarn.class)));
        updateProfileCard.setOnClickListener(view -> startActivity(new Intent(getContext(), UpdateProfile.class)));
        feedbackCard.setOnClickListener(view -> startActivity(new Intent(getContext(), GiveFeedback.class)));
        logout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            sessionManager.googleSignIn(requireActivity()).signOut();
            sessionManager.logout();
        });
        name.setText(sessionManager.getSessionDetails("key_session_name"));
        email.setText(sessionManager.getSessionDetails("key_session_email"));
        Picasso.get().load(sessionManager.getSessionDetails("key_session_photo_uri")).into(profilePic);
        watchVideo.setOnClickListener(view -> displayAds.loadRewardedAds(getContext(),getActivity()));
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        name.setText(sessionManager.getSessionDetails("key_session_name"));
        email.setText(sessionManager.getSessionDetails("key_session_email"));
        Picasso.get().load(sessionManager.getSessionDetails("key_session_photo_uri")).into(profilePic);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}