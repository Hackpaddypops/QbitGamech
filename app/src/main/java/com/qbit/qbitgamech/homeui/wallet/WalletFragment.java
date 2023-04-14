package com.qbit.qbitgamech.homeui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.qbit.qbitgamech.Earn;
import com.qbit.qbitgamech.FAQ;
import com.qbit.qbitgamech.History;
import com.qbit.qbitgamech.databinding.FragmentWalletBinding;

public class WalletFragment extends Fragment {

    private Button faqButton;
    CardView transactionsCard;
    CardView historyCard;
    CardView earnCard;
    CardView redeemCard;
    private FragmentWalletBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WalletViewModel walletViewModel =
                new ViewModelProvider(this).get(WalletViewModel.class);

        binding = FragmentWalletBinding.inflate(inflater, container, false);
        faqButton=binding.faqbutton;
        transactionsCard=binding.walletTransactionsCard;
        historyCard=binding.walletHistoryCard;
        redeemCard=binding.walletRedeemCard;
        earnCard=binding.walletEarnCard;
        historyCard.setOnClickListener(view -> startActivity(new Intent(getContext(), History.class)));
        redeemCard.setOnClickListener(view -> startActivity(new Intent(getContext(), Redeem.class)));
        earnCard.setOnClickListener(view -> startActivity(new Intent(getContext(), Earn.class)));
        transactionsCard.setOnClickListener(view -> startActivity(new Intent(getContext(), Transactions.class)));
        faqButton.setOnClickListener(view -> startActivity(new Intent(getContext(), FAQ.class)));
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}