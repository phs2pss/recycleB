package com.selecto.banana2;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.github.chrisbanes.photoview.PhotoView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PageTwoFragment extends Fragment {
    View view;
    Button button_gps;
    Animation translateDown;
    LinearLayout page_gps;
    LinearLayout page_info;
    ArrayAdapter spinnerAdapter;
    Button btnShowLocation;
    TextView txtAds;
    Geocoder geocoder;
    List<Address> addresses = null;
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
    ScrollView gangnam;
    ScrollView gangdong;

    PhotoView photoView;
    Dialog dialog;


    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    private GpsInfo gps;


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

        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                txtAds.setText("기기를 살짝 이동 후 버튼을 다시 눌러주세요.");
                if (!isPermission) {
                    callPermission();
                    return;
                }
                gps = new GpsInfo(getActivity());

                if (gps.isGetLocation) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    setAddresses();
                } else {
                    gps.showSettingsAlert();
                }
                gps.stopUsingGPS();
            }
        });
        callPermission();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View s_view, int position, long l) {
                mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                mRootLinear = view.findViewById(R.id.linear_root);
                Log.d("spinner_test", adapterView.getItemAtPosition(position).toString());
                String spinner_selected_gu = adapterView.getItemAtPosition(position).toString();
                if (!spinner_selected_gu.equals("행정구역 선택")) {
                    button_gps.setText(spinner_selected_gu + " 배출 정보");
                }
                mRootLinear.removeAllViews();

                dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_dialog);
                photoView = dialog.findViewById(R.id.childImage);
                photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);

                if (spinner_selected_gu.equals("강남구")) {
                    gangnam = (ScrollView) mInflater.inflate(R.layout.gangnam, mRootLinear, false);
                    mRootLinear.addView(gangnam);
                    Button furniture = (Button) gangnam.findViewById(R.id.button6);
                    Button appliance = (Button) gangnam.findViewById(R.id.button5);
                    Button etc = (Button) gangnam.findViewById(R.id.button4);
                    Button  agent = (Button) gangnam.findViewById(R.id.button7);

                    furniture.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            photoView.setImageResource(R.drawable.furniture);
                            dialog.show();
                        }
                    });

                    appliance.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            photoView.setImageResource(R.drawable.appliance);
                            dialog.show();
                        }
                    });

                    etc.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            photoView.setImageResource(R.drawable.etc);
                            dialog.show();
                        }
                    });

                    agent.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            photoView.setImageResource(R.drawable.agent);
                            dialog.show();
                        }
                    });
                } else if (spinner_selected_gu.equals("강동구")) {
                    gangdong = (ScrollView) mInflater.inflate(R.layout.gangdong, mRootLinear, false);
                    mRootLinear.addView(gangdong);
                    Button furniture = gangdong.findViewById(R.id.button_gandong1);
                    Button appliance = gangdong.findViewById(R.id.button_gandong2);
                    Button etc = gangdong.findViewById(R.id.button_gandong3);
                    Button free = gangdong.findViewById(R.id.button_gandong4);

                    furniture.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            photoView.setImageResource(R.drawable.gangdong1);
                            dialog.show();
                        }
                    });

                    appliance.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            photoView.setImageResource(R.drawable.gangdong2);
                            dialog.show();
                        }
                    });

                    etc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            photoView.setImageResource(R.drawable.gangdong3);
                            dialog.show();
                        }
                    });

                    free.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            photoView.setImageResource(R.drawable.gangdong4);
                            dialog.show();
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
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

    // 전화번호 권한 요청
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


    public void setAddresses() {
        geocoder = new Geocoder(getActivity(), Locale.KOREAN);
        Log.d("gpstest", "lat2 ->" + latitude);
        Log.d("gpstest", "lng2 ->" + longitude);
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if(addresses == null || addresses.size() == 0) {
                txtAds.setText("기기를 살짝 이동 후 버튼을 다시 눌러주세요.");
            } else {
                fulladdr = addresses.get(0).getAddressLine(0);
                txtAds.setText(fulladdr);
                Log.d("gpstest", "fulladdr : " + fulladdr);
                String[] words = fulladdr.split("\\s");
                Log.d("gpstest", "words -> " + words[2]);
                gu = words[2];
                Log.d("gpstest", "gu -> " + gu);
                button_gps.setText(gu + " 배출 정보");
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



}
