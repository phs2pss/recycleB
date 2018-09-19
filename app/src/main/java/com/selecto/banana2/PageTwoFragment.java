package com.selecto.banana2;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class PageTwoFragment extends Fragment {
    View view;
    TextView textView, textView2;
    String[] item={"종로구","중구","용산구","성동구","광진구","동대문구","중랑구","성북구","강북구","도봉구","노원구","노원구",
            "은평구","서대문구","마포구","양천구","강서구","구로구","금천구","영등포구","동작구","관악구","서초구","강남구",
            "송파구","강동구"};

    private long backKeyPressedTime = 0;
    private Toast toast;


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
        textView = (TextView) view.findViewById(R.id.textView);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        textView2.setMovementMethod(new ScrollingMovementMethod());

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        //아이템 선택 이벤트 처리
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if(item[position]=="강남구"){
                    textView2.setText("강남구 정보");
                }
                else if(item[position]=="중구"){
                    textView2.setText("배출 시간(재활용품, 음식물쓰레기 포함)\n" +
                            "월요일~토요일(※일요일은 배출 금지 및 미수거)\n" +
                            "주택지역 ⇒ 19:00~24:00\n" +
                            "2차로 이상 도로 및 관광특구지역 ⇒ 22:00~01:00 단, 22:00 이전 영업종료 사업장 ⇒ 18:00~24:00");
                }
                textView.setText(item[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                textView.setText("");
            }
        });

        return view;
    }

    public static PageTwoFragment newInstance() {
        Bundle args = new Bundle();

        PageTwoFragment fragment = new PageTwoFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
