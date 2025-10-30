package dev.dashalev.myapplication;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

public class MultiplicationView extends ViewModel {
    private ArrayList<Integer> history = new ArrayList<>();
    private ArrayList<String> currentTable = new ArrayList<>();

    public void addToHistory(int number) {
        if (!history.contains(number)) {
            history.add(number);
        }
    }

    public ArrayList<Integer> getHistory() {
        return history;
    }

    public void setCurrentTable(ArrayList<String> table) {
        this.currentTable = new ArrayList<>(table);
    }

    public ArrayList<String> getCurrentTable() {
        return currentTable;
    }
}
