package com.example.iftaproject;

import static java.lang.Character.isDigit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.iftaproject.databinding.FragmentSecondBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    String uriString = "";
    TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);




    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA
            },
                    100);
        }

        binding.ReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });

        binding.buttonExtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(uriString); // This captures the string of the image
                _extractTextFromUri(uri); // Uses method defined below to extract data using the ML KIT.
            }
        });


        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        binding.buttonCurrentQuarter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_currentQuarterFragment);
            }
        });

    }




    public void _extractTextFromUri(Uri _uri){
        try{
            InputImage image = InputImage.fromFilePath(getContext(), _uri);
            //InputImage image = InputImage.fromBitmap(captureImage, 0);
            Task<Text> result =
                    recognizer.process(image)
                            .addOnSuccessListener(new OnSuccessListener<Text>() {
                                @Override
                                public void onSuccess(Text visionText) {
                                    // Below gets the segment of the string that has the gallons data and takes the about of gallons //
                                    String data = visionText.getText();

                                    String stateData = visionText.getText().substring(0, 70);
                                    String[] statesAb = {"AK", "AL", "AR", "AS", "AZ", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "GU", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MP", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UM", "UT", "VA", "VI", "VT", "WA", "WI", "WV", "WY"};
                                    int positionState = stateData.indexOf(",");;
                                    String state = ""+stateData.charAt(positionState+1)+stateData.charAt(positionState+2)+stateData.charAt(positionState+3);

                                    int position = data.indexOf("Gallons:")+8; // May need to add extra code just incase the receipt says "gal:" instead of "gallons:" //
                                    String gallons = ""+data.charAt(position)+data.charAt(position+1)+data.charAt(position+2)+data.charAt(position+3)+data.charAt(position+4)+data.charAt(position+5)+data.charAt(position+6)+data.charAt(position+7);
                                    String totalGallons = gallons.replaceAll("[a-zA-Z]", "");


                                    binding.editTextTextPersonName3.setText(totalGallons);
                                    binding.textViewState.setText(state);
                                    binding.textViewTextResults.setText(totalGallons);

                                }

                            })
                            .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            binding.textViewTextResults.setText("Not Found");

                                        }
                                    });
        } catch (IOException e){
            e.printStackTrace();
        }

    }



    ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    binding.imageViewReceipt.setImageURI(result);
                    uriString = result.toString();
                }
            });

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}