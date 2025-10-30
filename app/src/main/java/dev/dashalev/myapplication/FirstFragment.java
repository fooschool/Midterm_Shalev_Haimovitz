package dev.dashalev.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;

import dev.dashalev.myapplication.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    private EditText editText;
    private Button button;
    private Button historyNav;
    private ListView listView;
    private ArrayList<String> multiplication;
    private ArrayAdapter<String> adapter;
    private MultiplicationView viewModel;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = binding.editText;
        button = binding.button;
        historyNav = binding.historyNav;
        listView = binding.listView;

        viewModel = new ViewModelProvider(requireActivity()).get(MultiplicationView.class);

        multiplication = new ArrayList<>();
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, multiplication);
        listView.setAdapter(adapter);
        
        if (!viewModel.getCurrentTable().isEmpty()) {
            multiplication.addAll(viewModel.getCurrentTable());
            adapter.notifyDataSetChanged();
        }

        button.setOnClickListener(v -> generateTable());
        listView.setOnItemClickListener((parent, view1, position, id) -> showDeleteDialog(position));
        historyNav.setOnClickListener(v ->
            NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment)
); 
    }

      private void generateTable() {
        String input = editText.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a number", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int number = Integer.parseInt(input);

            multiplication.clear();

            viewModel.addToHistory(number);

            for (int i = 1; i <= 10; i++) {
                int result = number * i;
                multiplication.add(number + " × " + i + " = " + result);
            }
            
            viewModel.setCurrentTable(multiplication);

            adapter.notifyDataSetChanged();

        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteDialog(int position) {
        String item = multiplication.get(position);

        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Item")
                .setMessage("Do you want to delete this row?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    multiplication.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(requireContext(), "Deleted: " + item, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}