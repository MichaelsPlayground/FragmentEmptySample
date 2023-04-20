package de.androidcrypto.fragmentemptysample;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private final String TAG = "HomeFrame";
    Button licenses;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // toolbar
        Toolbar myToolbar = (Toolbar) getView().findViewById(R.id.main_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        setHasOptionsMenu(true);

        licenses = getView().findViewById(R.id.btnLicenses);
        licenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayLicensesAlertDialog();
            }
        });

        boolean nxpChipAvailable = deviceSupportsMifareClassic(view.getContext());
        String messageTrue = "Congratulations ! Your device IS supporting Mifare Ultralight and/or Mifare Classic NFC tags.";
        String messageFalse = "I'm sorry, but your device is NOT supporting Mifare Ultralight and/or Mifare Classic NFC tags.";
        TextView textViewTrue = getView().findViewById(R.id.textViewTrue);
        TextView textViewFalse = getView().findViewById(R.id.textViewFalse);
        if (nxpChipAvailable) {
            textViewTrue.setVisibility(View.VISIBLE);
            textViewTrue.setText(messageTrue);
            textViewFalse.setVisibility(View.GONE);
        } else {
            // todo disable read & write buttons
            textViewTrue.setVisibility(View.GONE);
            textViewFalse.setText(messageFalse);
            textViewFalse.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    /**
     * options menu show licenses
     */

    // run: displayLicensesAlertDialog();
    // display licenses dialog see: https://bignerdranch.com/blog/open-source-licenses-and-android/
    private void displayLicensesAlertDialog() {
        WebView view = (WebView) LayoutInflater.from(getContext()).inflate(R.layout.dialog_licenses, null);
        view.loadUrl("file:///android_asset/open_source_licenses.html");
        android.app.AlertDialog mAlertDialog = new android.app.AlertDialog.Builder(getActivity()).create();
        mAlertDialog = new android.app.AlertDialog.Builder(getContext(), R.style.Theme_NfcNdefExample)
                .setTitle(getString(R.string.action_licenses))
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    public boolean deviceSupportsMifareClassic(Context context) {
        FeatureInfo[] info = context.getPackageManager().getSystemAvailableFeatures();
        for (FeatureInfo i : info) {
            String name = i.name;
            if (name != null && name.equals("com.nxp.mifare")) {
                return true;
            }
        }
        return false;
    }

    /**
     * section for OptionsMenu
     */

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.menu_activity_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.action_copy_data) {
            ClipboardManager clipboard = (ClipboardManager)
                    getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("BasicNfcEmvReader", "abcd");
            clipboard.setPrimaryClip(clip);
            // show toast only on Android versions < 13
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
                Toast.makeText(getContext(), "copied", Toast.LENGTH_SHORT).show();
            //return false;
            return (true);
        } else if (menuItem.getItemId() == R.id.action_licenses) {
            Log.i(TAG, "mLicenses");
            displayLicensesAlertDialog();
            return true;
        } else if (menuItem.getItemId() == R.id.action_about) {
            Log.i(TAG, "mAbout");
            Intent intent = new Intent(getActivity(), AboutTheAppActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(menuItem);
    }

}