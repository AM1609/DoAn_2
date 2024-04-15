package com.example.doan.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.doan.Adapter.QuizListAdapter;
import com.example.doan.Model.QuizListModel;
import com.example.doan.R;
import com.example.doan.viewmodel.AuthViewModel;
import com.example.doan.viewmodel.QuizListViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class ChartsFragment extends Fragment implements QuizListAdapter.OnItemClickedListner {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private NavController navController;
    private QuizListViewModel viewModel;
    private AuthViewModel viewModel1;
    private QuizListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_charts, container, false);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(QuizListViewModel.class);
        viewModel1 = new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AuthViewModel.class);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.listChartRecyclerview);
        progressBar = view.findViewById(R.id.ChartProgressbar);
        navController = Navigation.findNavController(view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new QuizListAdapter(this);

        recyclerView.setAdapter(adapter);

        viewModel.getQuizListLiveData().observe(getViewLifecycleOwner(), new Observer<List<QuizListModel>>() {
            @Override
            public void onChanged(List<QuizListModel> quizListModels) {
                progressBar.setVisibility(View.GONE);
                adapter.setQuizListModels(quizListModels);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        ChartsFragmentDirections.ActionChartsFragmentToChartDetalFragment action =
                ChartsFragmentDirections.actionChartsFragmentToChartDetalFragment();
        action.setPositionC(position);
        navController.navigate(action);
    }
}