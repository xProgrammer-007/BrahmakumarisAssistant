package com.bkassistant;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

import java.util.ArrayList;
import java.util.List;
public class HomePageSlider {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final HomeActivity homeActivity;
    GlideImageLoadingService glideImageLoadingService;

    public HomePageSlider(final HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
        glideImageLoadingService = new GlideImageLoadingService(homeActivity);

        db.collection("slides").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                 List<String> imgUrls = new ArrayList<String>();
                for (DocumentSnapshot documentSnapshot :queryDocumentSnapshots.getDocuments()) {
                    if(documentSnapshot.get("imageUrl") != null){
                        String imgUrl = documentSnapshot.get("imageUrl").toString();
                        System.out.println(imgUrl);
                        imgUrls.add(imgUrl);
                    }
                }
                SliderHomeAdapter sliderHomePage = new SliderHomeAdapter(homeActivity, imgUrls);
                Slider.init(    glideImageLoadingService    );
                Slider slider = homeActivity.findViewById(R.id.banner_slider1);
                slider.setAdapter(sliderHomePage);
                slider.setAnimateIndicators(true);
                slider.setLoopSlides(true);
                slider.setInterval(5000);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.getMessage());
            }
        });



    }

}




class SliderHomeAdapter extends SliderAdapter{

    private final HomeActivity homeActivity;
    private final List<String> slides;

    public SliderHomeAdapter(HomeActivity homeActivity, List<String> slides) {
        this.homeActivity = homeActivity;
        this.slides = slides;
    }


    @Override
    public int getItemCount() {
        return slides.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        imageSlideViewHolder.bindImageSlide(slides.get(position));
    }

}