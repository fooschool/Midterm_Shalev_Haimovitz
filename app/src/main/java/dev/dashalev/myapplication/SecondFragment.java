package dev.dashalev.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;

import dev.dashalev.myapplication.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private Button back;
    private Button clear;
    private ListView listHistory;
    private ArrayAdapter<Integer> adapter;
    private MultiplicationView viewModel;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(MultiplicationView.class);

        back = binding.back;
        clear = binding.clear;
        listHistory = binding.listHistory;

        ArrayList<Integer> history = viewModel.getHistory();

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, history);
        listHistory.setAdapter(adapter);

        back.setOnClickListener(v ->
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment)
        );
        
        clear.setOnClickListener(v -> {
            viewModel.getHistory().clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(requireContext(), "History cleared", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}