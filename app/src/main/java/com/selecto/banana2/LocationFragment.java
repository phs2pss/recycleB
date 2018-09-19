package com.selecto.banana2;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class LocationFragment extends Fragment {

    View view;
    private Button btnShowLocation;
    private TextView txtAds;
    private TextView txtTitle;
    private boolean isPermission = false;
    Geocoder geocoder;
    List<Address> addresses = null;
    private GpsInfo gps;

    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;


    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        if (view != null) {
            return view;
        } else {
            view = inflater.inflate(R.layout.fragment_location, null);
        }

        btnShowLocation = (Button) view.findViewById(R.id.button3);
        txtTitle = (TextView) view.findViewById(R.id.textView5);
        txtAds = (TextView) view.findViewById(R.id.textView6);
        Spinner s = (Spinner) view.findViewById(R.id.spinner2);

        geocoder = new Geocoder(getActivity(), Locale.KOREAN);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (!isPermission) {
                    callPermission();
                    return;
                }

                gps = new GpsInfo(getActivity());

                // GPS 사용유무 가져오기
                if (gps.isGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    try {
                        addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(addresses == null || addresses.size() == 0){
                        Toast.makeText(getActivity(), "주소없음", Toast.LENGTH_SHORT).show();
                    }else{
                        txtAds.setText("주소 = " + addresses.get(0).getAddressLine(0).toString());
                    }

                    Toast.makeText(
                            getActivity(),
                            "당신의 위치 - \n위도: " + latitude + "\n경도: " + longitude,
                            Toast.LENGTH_LONG).show();
                } else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }
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