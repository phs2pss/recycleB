package com.selecto.banana2;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class PageTwoFragment extends Fragment {
    View view;
    Button button_gps;
    Animation translateDown;
    LinearLayout page_gps;
    LinearLayout page_info;
    ArrayAdapter spinnerAdapter;

    private Button btnShowLocation;
    private TextView txtAds;
    private boolean isPermission = false;
    Geocoder geocoder;
    List<Address> addresses = null;
    private GpsInfo gps;
    TextView close;
    Animation translateUp;
    Spinner spinner;
    String fulladdr;
    ArrayList<String> guList;
    String gu;
    String[] arrString;
    double latitude;
    double longitude;

    LayoutInflater mInflater;
    LinearLayout mRootLinear ;
    LinearLayout gangnam;
    LinearLayout gangdong;


    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;

    public PageTwoFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            return view;
        } else {
            view = inflater.inflate(R.layout.fragment_page_two, null);
        }

        SlidingAnimationListener listener = new SlidingAnimationListener();
        translateDown = AnimationUtils.loadAnimation(getActivity(), R.anim.translate_down);

        page_gps = view.findViewById(R.id.view_gps);
        page_info = view.findViewById(R.id.view_info);
        button_gps = view.findViewById(R.id.button_gps);
        spinner = view.findViewById(R.id.spinner2);
        spinnerAdapter = new ArrayAdapter(getActivity(), R.layout.spinner_item, getResources().getStringArray(R.array.ward));
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        button_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View b_view) {
                page_gps.setVisibility(View.VISIBLE);
                page_gps.startAnimation(translateDown);
            }
        });

        btnShowLocation = view.findViewById(R.id.button3);
        txtAds = view.findViewById(R.id.textView6);


        translateUp = AnimationUtils.loadAnimation(getActivity(), R.anim.translate_up);
        translateUp.setAnimationListener(listener);

        close = view.findViewById(R.id.textView8);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View childview) {
                page_gps.startAnimation(translateUp);
            }
        });

        geocoder = new Geocoder(getActivity(), Locale.KOREAN);

        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                txtAds.setText("현 위치 찾는 중...");
                if (!isPermission) {
                    callPermission();
                }
                gps = new GpsInfo(getActivity());
                // GPS 사용유무 가져오기
                if (gps.isGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    //latitude = 37.57026;
                    //longitude = 126.97980;
                    Log.d("gpstest", "latitude : " + latitude);
                    Log.d("gpstest", "longitude : " + longitude);
                    try {
                        addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        Log.d("gpstest", "addresses : " + addresses);
                        if(addresses == null || addresses.size() == 0) {
                            Toast.makeText(getActivity(), "주소 없음", Toast.LENGTH_SHORT).show();
                        } else {
                            fulladdr = addresses.get(0).getAddressLine(0);
                            txtAds.setText(fulladdr);
                            Log.d("gpstest", "fulladdr : " + fulladdr);
                            String[] words = fulladdr.split("\\s");
                            Log.d("gpstest", "words -> " + words[2]);
                            gu = words[2];
                            Log.d("gpstest", "gu -> " + gu);
                            button_gps.setText(gu);
                            arrString = getResources().getStringArray(R.array.ward);
                            guList = new ArrayList<String>();

                            for (String s : arrString) {
                                guList.add(s);
                            }

                            if (guList.contains(gu)) {
                                spinner.setSelection(guList.indexOf(gu));
                            } else {
                                spinner.setSelection(0);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // GPS 를 사용할수 없으므로
                    txtAds.setText("GPS를 사용할 수 없습니다.");
                    gps.showSettingsAlert();
                }
            }
        });

        mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootLinear = view.findViewById(R.id.linear_root);
        gangdong = (LinearLayout) mInflater.inflate(R.layout.gangdong, mRootLinear, false);
        gangnam = (LinearLayout) mInflater.inflate(R.layout.gangnam, mRootLinear, false);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("spinner_test", adapterView.getItemAtPosition(position).toString());
                String spinner_selected_gu = adapterView.getItemAtPosition(position).toString();
                button_gps.setText(spinner_selected_gu);
                mRootLinear.removeAllViews();
                if (spinner_selected_gu.equals("강남구")) {
                    mRootLinear.addView(gangdong);
                } else if (spinner_selected_gu.equals("강동구")) {
                    mRootLinear.addView(gangnam);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        return view;
    }

    class SlidingAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            page_gps.setVisibility(View.INVISIBLE);

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }

}
