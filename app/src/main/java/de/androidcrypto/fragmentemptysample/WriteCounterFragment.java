package de.androidcrypto.fragmentemptysample;

import static de.androidcrypto.fragmentemptysample.Utils.doVibrate;
import static de.androidcrypto.fragmentemptysample.Utils.printData;

import android.content.Intent;
import android.media.MediaPlayer;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WriteCounterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WriteCounterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "WriteCounterFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    com.google.android.material.textfield.TextInputLayout counter0Layout, counter1Layout, counter2Layout;
    com.google.android.material.textfield.TextInputEditText incrementCounter, counter0, counter1, counter2, resultNfcWriting;
    RadioButton incrementNoCounter, incrementCounter0, incrementCounter1, incrementCounter2;
    private View loadingLayout;

    public WriteCounterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WriteCounterFragment newInstance(String param1, String param2) {
        WriteCounterFragment fragment = new WriteCounterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_write_counter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        incrementCounter = getView().findViewById(R.id.etCounterIncreaseValue);
        counter0 = getView().findViewById(R.id.etCounter0);
        counter1 = getView().findViewById(R.id.etCounter1);
        counter2 = getView().findViewById(R.id.etCounter2);
        incrementNoCounter = getView().findViewById(R.id.rbCounterNoIncrease);
        incrementCounter0 = getView().findViewById(R.id.rbIncreaseCounter0);
        incrementCounter1 = getView().findViewById(R.id.rbIncreraseCounter1);
        incrementCounter2 = getView().findViewById(R.id.rbIncreaseCounter2);
        resultNfcWriting = getView().findViewById(R.id.etMainResult);
        loadingLayout = getView().findViewById(R.id.loading_layout);

    }

    /**
     * shows a progress bar as long as the reading lasts
     *
     * @param isVisible
     */

    private void setLoadingLayoutVisibility(boolean isVisible) {
        getActivity().runOnUiThread(() -> {
            if (isVisible) {
                loadingLayout.setVisibility(View.VISIBLE);
            } else {
                loadingLayout.setVisibility(View.GONE);
            }
        });
    }

    private void showMessage(String message) {
        getActivity().runOnUiThread(() -> {
            Toast.makeText(getContext(),
                    message,
                    Toast.LENGTH_SHORT).show();
            resultNfcWriting.setText(message);
        });
    }

}