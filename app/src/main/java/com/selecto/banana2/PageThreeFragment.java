package com.selecto.banana2;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PageThreeFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    View view;
    GoogleMap mMap;
    MapView mapView;
    Button button;
    StringBuilder sb;
    double latitude;
    double longitude;
    String NM = null, ADDR_OLD = null, ADDR = null, TEL = null, XCODE = null, YCODE = null;
    Marker mypos;
    CameraPosition cp;
    private GpsInfo gps;


    public PageThreeFragment() {

        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            return view;
        } else {
            view = inflater.inflate(R.layout.fragment_page_three, null);
        }

        gps = new GpsInfo(getActivity());

        this.latitude = gps.getLatitude();
        this.longitude = gps.getLongitude();

        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.hide(PageThreeFragment.this);
                fragmentTransaction.add(R.id.fragment_shop_list, new ShopListFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    /*통합데이터 불러오기 및 필요데이터 추출, 추출데이터 JSON생성*/
    public void jsonTest() throws JSONException {

        GeoPoint gp;
        double distance;
        sb = new StringBuilder();
        String str = ((MainActivity)getActivity()).loadJSONFromAsset("Shoplist.json");
        JSONObject dataObj = new JSONObject(str);
        MarkerOptions markerOptions = new MarkerOptions();
        ArrayList<Shoplistitem> data = new ArrayList<>();
        Shoplistitem shoplistitem;

        try {
            JSONArray jsonArray = dataObj.getJSONArray("DATA");

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                XCODE = jsonObject.getString("xcode");
                YCODE = jsonObject.getString("ycode");

                if (("".equals(XCODE))||("".equals(YCODE))||(XCODE == "null")||(YCODE == "null")){
                    //Toast.makeText(getContext(), "걸러냈다.", Toast.LENGTH_LONG).show();
                    continue;
                }
                else {
                    gp = new GeoPoint(Double.parseDouble(XCODE), Double.parseDouble(YCODE));
                    gp = GeoTrans.convert(3, 0, gp);
                    distance = calDistance(latitude, longitude, gp.y, gp.x);

                    if (distance < 1000) {
                        NM = jsonObject.getString("nm");
                        ADDR_OLD = jsonObject.getString("addr_old");
                        ADDR = jsonObject.getString("addr");
                        TEL = jsonObject.getString("tel");

                        shoplistitem = new Shoplistitem(NM, ADDR_OLD, ADDR, TEL);
                        data.add(shoplistitem);

                        markerOptions.title(NM);
                        markerOptions.position(new LatLng(gp.y, gp.x));
                        if((ADDR_OLD == "null")&&(ADDR == "null"))
                            markerOptions.snippet("주소정보없음");
                        else if(ADDR_OLD == "null")
                            markerOptions.snippet(ADDR);
                        else
                            markerOptions.snippet(ADDR_OLD);

                        mMap.addMarker(markerOptions);
                        //                  mMap.setOnInfoWindowClickListener(infoWindowClickListener);
                    }
                }
            }
            ((MainActivity)getActivity()).setData(data);

        }catch(JSONException e){
            Toast.makeText(getContext(), "파싱에러(2)", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    /*
    GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            String markerId = marker.getId();
            Toast.makeText(getContext(), "정보창 클릭 Marker ID : "+markerId, Toast.LENGTH_SHORT).show();
        }
    };
    */

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onMapReady(final GoogleMap googleMap)
    {
        LatLng latLng;
        MapsInitializer.initialize(this.getActivity());
        mMap = googleMap;

        /*카메라 초기위치*/
        latLng = new LatLng(latitude, longitude);   //기준좌표
        cp = new CameraPosition.Builder().target(latLng).zoom(15).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
        mMap.addCircle(new CircleOptions().center(latLng).radius(1000).strokeColor(Color.parseColor("#884169e1")).fillColor(Color.parseColor("#5587cefa")));
        mMap.setOnMapClickListener(this);

        BitmapDrawable bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.myposition2);
        Bitmap b=bitmapDrawable.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);

        mypos = mMap.addMarker(new MarkerOptions().position(latLng).title("사용자 지정위치")
                //.snippet("위도 : " + String.valueOf(latLng.latitude) + "\n경도 : " + String.valueOf(latLng.longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

        try {
            jsonTest();
        } catch (JSONException e) {
            Toast.makeText(getContext(), "파싱에러(1)", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        /*
        Point screenPt = mMap.getProjection().
                toScreenLocation(latLng);

        // 현재 화면에 찍힌 포인트로 부터 위도와 경도를 알려준다.
        LatLng point = mMap.getProjection().
                fromScreenLocation(screenPt);
*/
        mMap.clear();
        mypos.remove();

        BitmapDrawable bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.myposition2);
        Bitmap b=bitmapDrawable.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);

        mypos = mMap.addMarker(new MarkerOptions().position(latLng).title("사용자 지정위치")
                //  .snippet("위도 : " + String.valueOf(latLng.latitude) + "\n경도 : " + String.valueOf(latLng.longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        mMap.addCircle(new CircleOptions().center(latLng).radius(1000).strokeColor(Color.parseColor("#884169e1")).fillColor(Color.parseColor("#5587cefa")));

        latitude = latLng.latitude;
        longitude = latLng.longitude;

        try {
            jsonTest();
        } catch (JSONException e) {
            Toast.makeText(getContext(), "파싱에러(1)", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public double calDistance(double lat1, double lon1, double lat2, double lon2){

        double theta, dist;
        theta = lon1 - lon2;
        dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);

        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;    // 단위 mile 에서 km 변환.
        dist = dist * 1000.0;      // 단위  km 에서 m 로 변환

        return dist;
    }

    // 주어진 도(degree) 값을 라디언으로 변환
    private double deg2rad(double deg){
        return (double)(deg * Math.PI / (double)180d);
    }

    // 주어진 라디언(radian) 값을 도(degree) 값으로 변환
    private double rad2deg(double rad){
        return (double)(rad * (double)180d / Math.PI);
    }
/*
    public void refreshMap(int position1)
    {
        //강남구
        if(position1 == 0)
        {
            latitude = 37.4959854;
            longitude = 127.0664091;
        }
        //강동구
        else if(position1 == 1)
        {
            latitude = 37.5492077;
            longitude = 127.1464824;
        }
        //강북구
        else if(position1 == 2)
        {
            latitude = 37.6469954;
            longitude = 127.0147158;
        }
        //강서구
        else if(position1 == 3)
        {
            latitude = 37.5657617;
            longitude = 126.8226561;
        }
        //관악구
        else if(position1 == 4)
        {
            latitude = 37.494394;
            longitude = 126.854607;
        }
        //광진구
        else if(position1 == 5)
        {
            latitude = 37.468031;
            longitude = 126.944764;
        }
        //구로구
        else if(position1 == 6)
        {
            latitude = 37.545717;
            longitude = 127.085870;
        }
        //금천구
        else if(position1 == 7)
        {
            latitude = 37.460388;
            longitude = 126.900752;
        }
        //노원구
        else if(position1 == 8)
        {
            latitude = 37.653068;
            longitude = 127.075595;
        }
        //도봉구
        else if(position1 == 9)
        {
            latitude = 37.668396;
            longitude = 127.032449;
        }
        //동대문구
        else if(position1 == 10)
        {
            latitude = 37.582068;
            longitude = 127.053985;
        }
        //동작구
        else if(position1 == 11)
        {
            latitude = 37.499278;
            longitude = 126.951933;
        }
        //마포구
        else if(position1 == 12)
        {
            latitude = 37.559000;
            longitude = 126.909157;
        }
        //서대문구
        else if(position1 == 13)
        {
            latitude = 37.577995;
            longitude = 126.938404;
        }
        //서초구
        else if(position1 == 14)
        {
            latitude = 37.472991;
            longitude = 127.030332;
        }
        //성동구
        else if(position1 == 15)
        {
            latitude = 37.551193;
            longitude = 127.040889;
        }
        //성북구
        else if(position1 == 16)
        {
            latitude = 37.605993;
            longitude = 127.016482;
        }
        //송파구
        else if(position1 == 17)
        {
            latitude = 37.504971;
            longitude = 127.116525;
        }
        //양천구
        else if(position1 == 18)
        {
            latitude = 37.524699;
            longitude = 126.855190;
        }
        //영등포구
        else if(position1 == 19)
        {
            latitude = 37.522187;
            longitude = 126.909035;
        }
        //용산구
        else if(position1 == 20)
        {
            latitude = 37.531600;
            longitude = 126.980453;
        }
        //은평구
        else if(position1 == 21)
        {
            latitude = 37.618556;
            longitude = 126.926634;
        }
        //종로구
        else if(position1 == 22)
        {
            latitude = 37.594646;
            longitude = 126.977228;
        }
        //중구
        else if(position1 == 23)
        {
            latitude = 37.559977;
            longitude = 126.995883;
        }
        //중랑구
        else if(position1 == 24)
        {
            latitude = 37.598168;
            longitude = 127.093304;
        }
    }

*/

    public static PageThreeFragment newInstance() {
        Bundle args = new Bundle();

        PageThreeFragment fragment = new PageThreeFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
