package com.example.doan.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.doan.Adapter.QuizListAdapter;
import com.example.doan.Model.QuestionModel;
import com.example.doan.Model.QuizListModel;
import com.example.doan.R;
import com.example.doan.viewmodel.QuestionViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.List;
import android.media.MediaPlayer;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class Quizragment extends Fragment implements View.OnClickListener {



    private QuestionViewModel viewModel;
    private StorageReference storageReference;
    private NavController navController;
    private ProgressBar progressBar;
    private Button option1Btn , option2Btn , option3Btn , nextQueBtn;
    private TextView questionTv , ansFeedBackTv , questionNumberTv , timerCountTv;
    private ImageView closeQuizBtn;
    private String quizId;
    private long totalQuestions;
    private int currentQueNo = 0;
    private boolean canAnswer = false;
    private long timer;
    private CountDownTimer countDownTimer;
    private int notAnswerd = 0;
    private int correctAnswer = 0;
    private int wrongAnswer = 0;
    private String answer = "";
    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    private long time = 0;
    private Context context;
    private MediaPlayer mediaRight,mediaWrong;
    ImageView qimage;
    private List<QuestionModel> questionModel;


    String email = "ndwadawdwadakiet2708@gmail.com";
    Bundle bundle = getArguments();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(QuestionViewModel.class);


//            email = bundle.getString("email", "");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();
        if (context == null) {
            context = requireContext();
        }

        mediaRight = MediaPlayer.create(getActivity(), R.raw.right);
        mediaWrong = MediaPlayer.create(getActivity(), R.raw.wrong);
        navController = Navigation.findNavController(view);
        qimage = view.findViewById(R.id.imageView4);
        closeQuizBtn = view.findViewById(R.id.imageView3);
        option1Btn = view.findViewById(R.id.option1Btn);
        option2Btn = view.findViewById(R.id.option2Btn);
        option3Btn = view.findViewById(R.id.option3Btn);
        nextQueBtn = view.findViewById(R.id.nextQueBtn);
        ansFeedBackTv = view.findViewById(R.id.ansFeedbackTv);
        questionTv = view.findViewById(R.id.quizQuestionTv);
        timerCountTv = view.findViewById(R.id.countTimeQuiz);
        questionNumberTv = view.findViewById(R.id.quizQuestionsCount);
        progressBar = view.findViewById(R.id.quizCoutProgressBar);

        quizId = QuizragmentArgs.fromBundle(getArguments()).getQuizId();
        totalQuestions = QuizragmentArgs.fromBundle(getArguments()).getTotalQueCount();
        viewModel.setQuizId(quizId);
        viewModel.getQuestions();;

        option1Btn.setOnClickListener(this);
        option2Btn.setOnClickListener(this);
        option3Btn.setOnClickListener(this);
        nextQueBtn.setOnClickListener(this);

        closeQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_quizragment_to_listFragment);
            }
        });

        loadData();
    }

    private void loadData(){
        enableOptions();
        loadQuestions(1);
    }

    private void enableOptions(){
        option1Btn.setVisibility(View.VISIBLE);
        option2Btn.setVisibility(View.VISIBLE);
        option3Btn.setVisibility(View.VISIBLE);

        //enable buttons , hide feedback tv , hide nextQuiz btn

        option1Btn.setEnabled(true);
        option2Btn.setEnabled(true);
        option3Btn.setEnabled(true);

        ansFeedBackTv.setVisibility(View.INVISIBLE);
        nextQueBtn.setVisibility(View.INVISIBLE);
    }

    private void loadQuestions(int i){


        currentQueNo = i;
        viewModel.getQuestionMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<QuestionModel>>() {
            @Override
            public void onChanged(List<QuestionModel> questionModels) {
                questionTv.setText(String.valueOf(currentQueNo) + ") " + questionModels.get(i - 1).getQuestion());
                option1Btn.setText(questionModels.get(i - 1).getOption_a());
                option2Btn.setText(questionModels.get(i - 1).getOption_b());
                option3Btn.setText(questionModels.get(i - 1).getOption_c());
                timer = questionModels.get(i-1).getTimer();
                answer = questionModels.get(i-1).getAnswer();

                storageReference = FirebaseStorage.getInstance().getReference().child(questionModels.get(i-1).getQimage());
                // Tải ảnh từ Firebase Storage vào ImageView bằng Glide và FirebaseImageLoader
                Picasso.get().load(questionModels.get(i-1).getQimage()).into(qimage);
                //todo set current que no, to que number tv
                questionNumberTv.setText(String.valueOf(currentQueNo));
                startTimer();
            }
        });

        canAnswer = true;
    }

    private void startTimer(){
        timerCountTv.setText(String.valueOf(timer));
        progressBar.setVisibility(View.VISIBLE);

        countDownTimer = new CountDownTimer(timer * 1000 , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // update time
                timerCountTv.setText(millisUntilFinished / 1000 + "");

                Long percent = millisUntilFinished/(timer*10);
                progressBar.setProgress(percent.intValue());
            }

            @Override
            public void onFinish() {
                canAnswer = false;
                ansFeedBackTv.setText("Hết Giờ");
                notAnswerd ++;
                showNextBtn();
            }
        }.start();
    }

    private void showNextBtn() {
        if (currentQueNo == totalQuestions){
            nextQueBtn.setText("Submit");
            nextQueBtn.setEnabled(true);
            nextQueBtn.setVisibility(View.VISIBLE);
        }else{
            nextQueBtn.setVisibility(View.VISIBLE);
            nextQueBtn.setEnabled(true);
            ansFeedBackTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.option1Btn) {
            time = time + (timer-Long.parseLong(timerCountTv.getText().toString()));
            verifyAnswer(option1Btn);
        } else if (v.getId() == R.id.option2Btn) {
            time = time + (timer-Long.parseLong(timerCountTv.getText().toString()));
            verifyAnswer(option2Btn);
        } else if (v.getId() == R.id.option3Btn) {
            time = time + (timer-Long.parseLong(timerCountTv.getText().toString()));
            verifyAnswer(option3Btn);
        } else if (v.getId() == R.id.nextQueBtn) {
            time = time + (timer-Long.parseLong(timerCountTv.getText().toString()));
            if (currentQueNo == totalQuestions) {
                submitResults();
            } else {
                currentQueNo++;
                loadQuestions(currentQueNo);
                resetOptions();
            }
        }
    }

    private void resetOptions(){
        ansFeedBackTv.setVisibility(View.INVISIBLE);
        nextQueBtn.setVisibility(View.INVISIBLE);
        nextQueBtn.setEnabled(false);
//        option1Btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#59CFFA")));
        option1Btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A770FF")));;
        option2Btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A770FF")));
        option3Btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A770FF")));
        option1Btn.setBackground(ContextCompat.getDrawable(getContext() , R.drawable.button_bg));
        option2Btn.setBackground(ContextCompat.getDrawable(getContext() , R.drawable.button_bg));
        option3Btn.setBackground(ContextCompat.getDrawable(getContext() , R.drawable.button_bg));
    }

    private void submitResults() {
        HashMap<String , Object> resultMap = new HashMap<>();
        resultMap.put("correct" , correctAnswer);
        resultMap.put("wrong" , wrongAnswer);
        resultMap.put("notAnswered" , notAnswerd);
        resultMap.put("currentUserId" , currentUserId);
        resultMap.put("time" , time);
        viewModel.addResults(resultMap);

        QuizragmentDirections.ActionQuizragmentToResultFragment action =
                QuizragmentDirections.actionQuizragmentToResultFragment();
        action.setQuizId(quizId);
        navController.navigate(action);

    }

    private void verifyAnswer(Button button){
        if (canAnswer){
            if (answer.equals(button.getText())){

                button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF00")));
                correctAnswer++;
                ansFeedBackTv.setText("Correct Answer");

                mediaRight.start();
            }else{
                button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
                wrongAnswer++;
                ansFeedBackTv.setText("Wrong Answer \nCorrect Answer :" + answer);
                mediaWrong.start();
            }
            ansFeedBackTv.setVisibility(View.VISIBLE);
        }
        canAnswer=false;
        countDownTimer.cancel();
        showNextBtn();
    }
}