package com.example.doan.views;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doan.Adapter.ChartsAdapter;
import com.example.doan.Model.ChartsModel;
import com.example.doan.Model.QuizListModel;
import com.example.doan.R;
import com.example.doan.viewmodel.QuizListViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChartDetalFragment extends Fragment {
    private TextView title, difficulty, totalQuestions;
    private Button startQuizBtn;
    private NavController navController;
    private int position;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressBar progressBar;
    private QuizListViewModel viewModel;
    private ImageView topicImage;
    private String quizId;
    private long totalQueCount;
    private RecyclerView recyclerView;
    private ArrayList<QueryDocumentSnapshot> documentSnapshots = new ArrayList<>();
    private ChartsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart_detal, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(QuizListViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        title = view.findViewById(R.id.detailFragmentTitle);
        difficulty = view.findViewById(R.id.detailFragmentDifficulty);
        totalQuestions = view.findViewById(R.id.detailFragmentQuestions);
        startQuizBtn = view.findViewById(R.id.startQuizBtn);
        progressBar = view.findViewById(R.id.detailProgressBar);
        topicImage = view.findViewById(R.id.detailFragmentImage);
        navController = Navigation.findNavController(view);
        recyclerView = view.findViewById(R.id.listChartRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext())); // hoặc requireActivity()

        position = ChartDetalFragmentArgs.fromBundle(getArguments()).getPositionC();


        adapter = new ChartsAdapter(getContext(), documentSnapshots);
        recyclerView.setAdapter(adapter);

        viewModel.getQuizListLiveData().observe(getViewLifecycleOwner(), new Observer<List<QuizListModel>>() {
            @Override
            public void onChanged(List<QuizListModel> quizListModels) {
                QuizListModel quiz = quizListModels.get(position);
                title.setText(quiz.getTitle());
                Glide.with(view).load(quiz.getImage()).into(topicImage);

                quizId = quiz.getQuizId();

                db.collection("Quiz").document(quizId).collection("results")
                        .orderBy("correct", Query.Direction.DESCENDING)
                        .orderBy("time", Query.Direction.ASCENDING)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                documentSnapshots.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    documentSnapshots.add(document);
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "Lỗi khi đọc dữ liệu", Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        });
            }

        });
    }
}