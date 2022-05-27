package theshaybi.androidinvertormonitor.ui.screens;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import theshaybi.androidinvertormonitor.R;

public class SecondScreenFragment extends Fragment {

    private SecondScreenViewModel mViewModel;

    public static SecondScreenFragment newInstance() {
        return new SecondScreenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.second_screen_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SecondScreenViewModel.class);
        // TODO: Use the ViewModel
    }

}