package theshaybi.androidinvertormonitor.ui.inverter;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import theshaybi.androidinvertormonitor.R;

public class InverterListFragment extends Fragment {

    private InverterListViewModel mViewModel;

    public static InverterListFragment newInstance() {
        return new InverterListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inverter_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InverterListViewModel.class);
        // TODO: Use the ViewModel
    }

}