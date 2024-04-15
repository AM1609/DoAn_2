package com.example.doan.views;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressBar progressBar;
    private QuizListViewModel viewModel;
    private ImageView topicImage;
    private String quizId;
    private long totalQueCount;
    ListView lv;
        ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter adapter;
    ArrayList<QueryDocumentSnapshot> documentSnapshots = new ArrayList<>();


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
        lv = view.findViewById(R.id.lv);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(adapter);

        position = ChartDetalFragmentArgs.fromBundle(getArguments()).getPositionC();

        viewModel.getQuizListLiveData().observe(getViewLifecycleOwner(), new Observer<List<QuizListModel>>() {
            @Override

            public void onChanged(List<QuizListModel> quizListModels) {

                QuizListModel quiz = quizListModels.get(position);
//                difficulty.setText(quiz.getDifficulty());
                title.setText(quiz.getTitle());
//                totalQuestions.setText(String.valueOf(quiz.getCorrect()));
                Glide.with(view).load(quiz.getImage()).into(topicImage);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                }, 2000);

                totalQueCount = quiz.getQuestions();
                quizId = quiz.getQuizId();
//                Toast.makeText(getActivity(), quizId,Toast.LENGTH_LONG).show();
                db.collection("Quiz").document(quizId).collection("results")
                        .orderBy("correct", Query.Direction.DESCENDING)
//                        .orderBy("wrong", Query.Direction.ASCENDING)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                arrayList.clear();
                                documentSnapshots.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    documentSnapshots.add(document);
                                    String item = "Email: " + document.getString("currentUserId") + "\n" +
                                            "Correct: " + document.getLong("correct") +
                                            "      Time: "+ document.getLong("time")+"s";
                                    arrayList.add(item);
                                }
//                                Collections.sort(arrayList, new Comparator<String>() {
//                                    @Override
//                                    public int compare(String o1, String o2) {
//                                        String[] parts1 = o1.split("Wrong: ");
//                                        String[] parts2 = o2.split("Wrong: ");
//
//                                        // Kiểm tra xem chuỗi có chứa "Wrong: " không
//                                        if (parts1.length < 2 || parts2.length < 2) {
//                                            // Trả về 0 nếu không chứa "Wrong: ", vì không có gì để so sánh
//                                            return 0;
//                                        }
//
//                                        // Lấy phần số từ phần tử thứ hai của mảng
//                                        long wrong1 = Long.parseLong(parts1[1].trim());
//                                        long wrong2 = Long.parseLong(parts2[1].trim());
//                                        return Long.compare(wrong1, wrong2);
//                                    }
//                                });


                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "Lỗi khi đọc dữ liệu", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

//        startQuizBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DetailFragmentDirections.ActionDetailFragmentToQuizragment action =
//                        DetailFragmentDirections.actionDetailFragmentToQuizragment();
//
//                action.setQuizId(quizId);
//                action.setTotalQueCount(totalQueCount);
//                navController.navigate(action);
//            }
//        });
    }
}