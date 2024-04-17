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
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.doan.Adapter.QuizListAdapter;
import com.example.doan.Model.QuizListModel;
import com.example.doan.R;
import com.example.doan.viewmodel.AuthViewModel;
import com.example.doan.viewmodel.QuizListViewModel;

import java.util.List;


public class ListFragment extends Fragment implements QuizListAdapter.OnItemClickedListner {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private NavController navController;
    private QuizListViewModel viewModel;
    private AuthViewModel viewModel1;
    private QuizListAdapter adapter;

    ImageView btnXH1, btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
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
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogout =view.findViewById(R.id.imageView4);

        btnXH1 = view.findViewById(R.id.imageView2);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel1.signOut();
                navController.navigate(R.id.action_listFragment_to_splashFragment);
            }
        });
//        btnXH.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navController.navigate(R.id.action_listFragment_to_chartsFragment);
//            }
//        });
        btnXH1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_listFragment_to_chartsFragment);
            }
        });
        recyclerView = view.findViewById(R.id.listQuizRecyclerview);
        progressBar = view.findViewById(R.id.quizListProgressbar);
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
        ListFragmentDirections.ActionListFragmentToDetailFragment action =
                ListFragmentDirections.actionListFragmentToDetailFragment();
        action.setPosition(position);
        navController.navigate(action);
    }
}