//package com.example.doan.repository;
//import androidx.annotation.NonNull;
//
//import com.example.doan.Model.ChartsModel;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.List;
//
//public class ChartRepository {
//    private onFirestoreTaskComplete onFirestoreTaskComplete;
//    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
//    private CollectionReference reference = firebaseFirestore.collection("Chart");
//
//    public ChartRepository(ChartRepository.onFirestoreTaskComplete onFirestoreTaskComplete){
//        this.onFirestoreTaskComplete = onFirestoreTaskComplete;
//    }
//    public void getChartData(){
//        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull  Task<QuerySnapshot> task) {
//                if (task.isSuccessful()){
//                    onFirestoreTaskComplete.chartDataLoaded(task.getResult()
//                            .toObjects(ChartsModel.class));
//                }else{
//                    onFirestoreTaskComplete.onError(task.getException());
//                }
//            }
//        });
//    }
//
//    public interface onFirestoreTaskComplete{
//        void chartDataLoaded(List<ChartsModel> chartsModels);
//        void onError(Exception e);
//    }
//}