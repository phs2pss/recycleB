package com.selecto.banana2;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import com.github.chrisbanes.photoview.PhotoView;
import java.util.ArrayList;

public class PageOneFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private SearchView search;
    private MyListAdapter listAdapter;
    private ExpandableListView myList;
    private ArrayList<Continent> continentList = new ArrayList<Continent>();

    View view;
    TextView textView;
    String nameMsg;

    public PageOneFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            return view;
        } else {
            view = inflater.inflate(R.layout.fragment_page_one, container, false);
        }

        textView = view.findViewById(R.id.textView4);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setScrollbarFadingEnabled(false);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        search = view.findViewById(R.id.search);
        search.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    textView.setText("");
                } else {
                    collapseAll();
                }
            }
        });
        loadData();
        myList = view.findViewById(R.id.expandableList);
        listAdapter = new MyListAdapter(getActivity(), continentList);
        myList.setAdapter(listAdapter);
        myList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.d("tag","onChildClick() start");
                TextView name = v.findViewById(R.id.name);
                nameMsg = name.getText().toString();
                Log.d("tag", "nameMsg : " + nameMsg);
                Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_dialog);
                PhotoView photoView = dialog.findViewById(R.id.childImage);
                photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);

                if (
                    nameMsg.equals("· 썩은 채소류") ||
                    nameMsg.equals("· 채소류 뿌리 또는 껍질") ||
                    nameMsg.equals("· 파 뿌리") ||
                    nameMsg.equals("· 미나리 뿌리") ||
                    nameMsg.equals("· 마늘 껍질") ||
                    nameMsg.equals("· 양파 껍질") ||
                    nameMsg.equals("· 고추 씨") ||
                    nameMsg.equals("· 고추대(대량)") ||
                    nameMsg.equals("· 옥수수대")) {
                        photoView.setImageResource(R.drawable.pa);
                } else if (
                    nameMsg.equals("· 과일류 껍질") ||
                    nameMsg.equals("· 코코넛 껍질") ||
                    nameMsg.equals("· 파인애플 껍질") ||
                    nameMsg.equals("· 핵과류의 씨") ||
                    nameMsg.equals("· 복숭아 씨") ||
                    nameMsg.equals("· 감 씨") ||
                    nameMsg.equals("· 체리 씨") ||
                    nameMsg.equals("· 망고 씨") ||
                    nameMsg.equals("· 견과류 껍질") ||
                    nameMsg.equals("· 호두 껍질") ||
                    nameMsg.equals("· 밤 껍질") ||
                    nameMsg.equals("· 땅콩 껍질") ||
                    nameMsg.equals("· 곡류 껍질") ||
                    nameMsg.equals("· 보리 겨(껍질)") ||
                    nameMsg.equals("· 쌀 겨(껍질)") ||
                    nameMsg.equals("· 콩 겨(껍질)")) {
                        photoView.setImageResource(R.drawable.fruit);
                } else if (
                    nameMsg.equals("· 육류 뼈") ||
                    nameMsg.equals("· 육류 비계") ||
                    nameMsg.equals("· 육류 내장") ||
                    nameMsg.equals("· 소 뼈") ||
                    nameMsg.equals("· 돼지 뼈") ||
                    nameMsg.equals("· 닭 뼈") ||
                    nameMsg.equals("· 어패류 껍데기") ||
                    nameMsg.equals("· 조개 껍데기") ||
                    nameMsg.equals("· 소라 껍데기") ||
                    nameMsg.equals("· 전복 껍데기") ||
                    nameMsg.equals("· 꼬막 껍데기") ||
                    nameMsg.equals("· 굴 껍데기") ||
                    nameMsg.equals("· 게 껍데기") ||
                    nameMsg.equals("· 가재 껍데기") ||
                    nameMsg.contains("· 생선 통가시") ||
                    nameMsg.contains("· 복어 내장")) {
                        photoView.setImageResource(R.drawable.meat);
                } else if (
                    nameMsg.equals("· 알 껍데기") ||
                    nameMsg.equals("· 계란 껍데기") ||
                    nameMsg.equals("· 메추리알 껍데기") ||
                    nameMsg.equals("· 차 찌꺼기") ||
                    nameMsg.equals("· 녹차 티백 찌꺼기") ||
                    nameMsg.equals("· 보리차 티백 찌꺼기") ||
                    nameMsg.equals("· 한약재 찌꺼기")) {
                        photoView.setImageResource(R.drawable.otherfood);
                } else if (
                    nameMsg.equals("· 통짜 큰 과일") ||
                    nameMsg.equals("· 통짜 큰 채소") ||
                    nameMsg.equals("· 통 수박") ||
                    nameMsg.equals("· 통 무") ||
                    nameMsg.equals("· 통 호박") ||
                    nameMsg.equals("· 통 배추") ||
                    nameMsg.equals("· 장류") ||
                    nameMsg.equals("· 된장") ||
                    nameMsg.equals("· 고추장") ||
                    nameMsg.equals("· 간장") ||
                    nameMsg.equals("· 양념된 음식") ||
                    nameMsg.equals("· 김치") ||
                    nameMsg.equals("· 젓갈")) {
                        photoView.setImageResource(R.drawable.tong);
                } else if (
                    nameMsg.equals("· 깐 마늘") ||
                    nameMsg.equals("· 깐 양파") ||
                    nameMsg.equals("· 고구마 껍질") ||
                    nameMsg.equals("· 감자 껍질") ||
                    nameMsg.equals("· 자른 수박 껍질") ||
                    nameMsg.equals("· 귤 껍질") ||
                    nameMsg.equals("· 바나나 껍질") ||
                    nameMsg.equals("· 물로 헹군 젓갈") ||
                    nameMsg.equals("· 물로 헹군 김치")) {
                        photoView.setImageResource(R.drawable.foodtrash);
                } else if (nameMsg.equals("· 신문지")) {
                        photoView.setImageResource(R.drawable.newspaper);
                } else if (
                    nameMsg.equals("· 책자") ||
                    nameMsg.equals("· 노트") ||
                    nameMsg.equals("· 전단지") ||
                    nameMsg.equals("· 쇼핑백") ||
                    nameMsg.equals("· 달력") ||
                    nameMsg.equals("· 포장지")) {
                        photoView.setImageResource(R.drawable.note);
                } else if (
                    nameMsg.equals("· 상자") ||
                    nameMsg.equals("· 골판지 상자")) {
                        photoView.setImageResource(R.drawable.box);
                } else if (
                    nameMsg.equals("· 종이 조각") ||
                    nameMsg.equals("· 영수증") ||
                    nameMsg.equals("· 고지서")) {
                        photoView.setImageResource(R.drawable.bill);
                } else if (nameMsg.equals("· 핸드타월")) {
                    photoView.setImageResource(R.drawable.hand);
                } else if (nameMsg.equals("· 휴지")) {
                    photoView.setImageResource(R.drawable.tissue);
                } else if (
                    nameMsg.equals("· 광고지") ||
                    nameMsg.equals("· 사진") ||
                    nameMsg.equals("· 코팅된 종이")) {
                        photoView.setImageResource(R.drawable.coting);
                } else if (nameMsg.equals("· 스프링 제본된 책자류")) {
                    photoView.setImageResource(R.drawable.spring);
                } else if (nameMsg.equals("· 종이컵")) {
                    photoView.setImageResource(R.drawable.papercup);
                } else if (nameMsg.equals("· 종이팩")) {
                    photoView.setImageResource(R.drawable.paperpack);
                } else if (nameMsg.equals("· 철 캔") || nameMsg.equals("· 알루미늄 캔")) {
                    photoView.setImageResource(R.drawable.fecan);
                } else if (nameMsg.equals("· 살충제 용기")) {
                    photoView.setImageResource(R.drawable.othercan);
                }  else if (nameMsg.equals("· 부탄가스 용기")) {
                    photoView.setImageResource(R.drawable.butan);
                } else if (nameMsg.equals("· 공구류") ||
                        nameMsg.equals("· 철사") ||
                        nameMsg.equals("· 못") ||
                        nameMsg.equals("· 전선") ||
                        nameMsg.equals("· 알루미늄 고철") ||
                        nameMsg.equals("· 스텐 고철")) {
                    photoView.setImageResource(R.drawable.fe);
                } else if (nameMsg.equals("· 우산")) {
                    photoView.setImageResource(R.drawable.umb);
                } else if (nameMsg.equals("· 음료수 병") ||
                        nameMsg.equals("· 소주 병") ||
                        nameMsg.equals("· 맥주 병") ||
                        nameMsg.equals("· 기타 병류")) {
                    photoView.setImageResource(R.drawable.bottle);
                } else if (nameMsg.equals("· 깨진 유리")) {
                    photoView.setImageResource(R.drawable.crackedglass);
                } else if (nameMsg.equals("· 유리잔") ||
                        nameMsg.equals("· 맥주컵") ||
                        nameMsg.equals("· 맥주컵") ||
                        nameMsg.equals("· 내열 유리 용기(밀폐 용기)") ||
                        nameMsg.equals("· 내열 유리 냄비")) {
                    photoView.setImageResource(R.drawable.otherglass);
                } else if (nameMsg.equals("· 페트병") ||
                        nameMsg.equals("· 플라스틱 용기류")) {
                    photoView.setImageResource(R.drawable.petbottle);
                } else if (nameMsg.equals("· 폐스티로폼")) {
                    photoView.setImageResource(R.drawable.stiro);
                } else if (nameMsg.equals("· 고무대야")) {
                    photoView.setImageResource(R.drawable.gomu);
                } else if (nameMsg.equals("· CD(콤팩트 디스크)")) {
                    photoView.setImageResource(R.drawable.cd);
                } else if (nameMsg.equals("· 비디오 테이프") ||
                        nameMsg.equals("· 오디오 테이프")) {
                    photoView.setImageResource(R.drawable.video);
                } else if (nameMsg.equals("· 화분")) {
                    photoView.setImageResource(R.drawable.flowerbottle);
                } else if (nameMsg.equals("· 유아용 볼풀공")) {
                    photoView.setImageResource(R.drawable.bollpull);
                } else if (nameMsg.equals("· 완구류") ||
                        nameMsg.equals("· 유모차") ||
                        nameMsg.equals("· 유아용 그네") ||
                        nameMsg.equals("· 유아용 자동차") ||
                        nameMsg.equals("· 유아용 목마")) {
                    photoView.setImageResource(R.drawable.toy);
                } else if (nameMsg.equals("· 송곳")) {
                    photoView.setImageResource(R.drawable.songgot);
                } else if (nameMsg.equals("· 노끈")) {
                    photoView.setImageResource(R.drawable.noggen);
                } else if (nameMsg.equals("· 알약 포장재")) {
                    photoView.setImageResource(R.drawable.pill);
                } else if (nameMsg.equals("· 부서진 스티로폼 조각")) {
                    photoView.setImageResource(R.drawable.crackedstiro);
                } else if (nameMsg.equals("· 1회용 그릇")) {
                    photoView.setImageResource(R.drawable.oneplate);
                } else if (nameMsg.equals("· 과자 봉지") ||
                        nameMsg.equals("· 라면 봉지") ||
                        nameMsg.equals("· 빵 봉지") ||
                        nameMsg.equals("· 1회용 비닐 봉투") ||
                        nameMsg.equals("· 각종 비닐류")) {
                    photoView.setImageResource(R.drawable.bong);
                } else if (nameMsg.contains("· 뽁뽁이")) {
                    photoView.setImageResource(R.drawable.aircapp);
                } else if (nameMsg.equals("· 형광등")) {
                    photoView.setImageResource(R.drawable.lmapp);
                } else if (nameMsg.equals("· 깨진 형광등")) {
                    photoView.setImageResource(R.drawable.crackedlamp);
                } else if (nameMsg.equals("· 백열전구")) {
                    photoView.setImageResource(R.drawable.back);
                } else if (nameMsg.equals("· LED 전구")) {
                    photoView.setImageResource(R.drawable.led);
                } else if (nameMsg.equals("· 건전지") ||
                        nameMsg.equals("· 충전용 전지")) {
                    photoView.setImageResource(R.drawable.batteryy);
                } else if (nameMsg.equals("· 헌옷") ||
                        nameMsg.equals("· 신발") ||
                        nameMsg.equals("· 가방") ||
                        nameMsg.equals("· 담요") ||
                        nameMsg.equals("· 누비 이불") ||
                        nameMsg.equals("· 커튼") ||
                        nameMsg.equals("· 카페트") ||
                        nameMsg.equals("· 여행용 가방") ||
                        nameMsg.equals("· 베개") ||
                        nameMsg.equals("· 방석")) {
                    photoView.setImageResource(R.drawable.clothee);
                } else if (nameMsg.equals("· 솜이불")) {
                    photoView.setImageResource(R.drawable.som);
                } else if (nameMsg.equals("· 걸레") ||
                        nameMsg.equals("· 수건")) {
                    photoView.setImageResource(R.drawable.towel);
                } else if (nameMsg.contains("· 소형 폐가전") ||
                        nameMsg.equals("· 컴퓨터") ||
                        nameMsg.equals("· 전기 밥솥") ||
                        nameMsg.equals("· 선풍기")) {
                    photoView.setImageResource(R.drawable.smallplug);
                } else if (nameMsg.contains("· 대형 폐가전") ||
                        nameMsg.equals("· 세탁기") ||
                        nameMsg.equals("· 에어컨") ||
                        nameMsg.equals("· 냉장고") ||
                        nameMsg.equals("· TV")) {
                    photoView.setImageResource(R.drawable.bigplug);
                } else if (nameMsg.equals("· 전기 담요") ||
                        nameMsg.equals("· 전기 방석")) {
                    photoView.setImageResource(R.drawable.elecdam);
                } else if (nameMsg.equals("· 폐식용유")) {
                    photoView.setImageResource(R.drawable.pyeoil);
                } else if (nameMsg.equals("· 폐목재류")) {
                    photoView.setImageResource(R.drawable.a1);
                } else if (nameMsg.equals("· 아이스팩")) {
                    photoView.setImageResource(R.drawable.a2);
                } else if (nameMsg.equals("· 연탄재")) {
                    photoView.setImageResource(R.drawable.a3);
                } else if (nameMsg.equals("· 페인트통")) {
                    photoView.setImageResource(R.drawable.a1t);
                } else if (nameMsg.equals("· 양초")) {
                    photoView.setImageResource(R.drawable.a2t);
                } else if (nameMsg.equals("· 스펀지")) {
                    photoView.setImageResource(R.drawable.a3t);
                } else if (nameMsg.equals("· 폐의약품")) {
                    photoView.setImageResource(R.drawable.pyepill);
                }

                dialog.show();
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                Window window = dialog.getWindow();
                int x = (int) (size.x * 0.85f);
                int y = (int) (size.y * 0.5f);
                window.setLayout(x, y);

                return false;
            }
        });
        return view;
    }

    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            myList.expandGroup(i);
        }
        Log.d("tag", "expandAll()");
    }

    public void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            myList.collapseGroup(i);
        }
        Log.d("tag", "collapseAll()");
    }

    @Override
    public boolean onClose() {
        listAdapter.filterData("");
        Log.d("tag", "onClose()");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (query.length() > 0) {
            listAdapter.filterData(query);
            expandAll();
        } else if (query.length() == 0) {
            listAdapter.filterData(query);
            collapseAll();
        }
        Log.d("tag", "onQueryTextChange()");
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query.length() > 0) {
            listAdapter.filterData(query);
            expandAll();
        }
        Log.d("tag", "onQueryTextSubmit()");
        return false;
    }

    public void loadData() {
        ArrayList<Country> countryList;
        Country country;
        Continent continent;

        countryList = new ArrayList<Country>();
        country = new Country("일반종량제봉투", "· 썩은 채소류", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 채소류 뿌리 또는 껍질", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 파 뿌리", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 미나리 뿌리", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 마늘 껍질", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 양파 껍질", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 고추 씨", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 고추대(대량)", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 옥수수대", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 과일류 껍질", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 코코넛 껍질", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 파인애플 껍질", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 핵과류의 씨", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 복숭아 씨", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 감 씨", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 체리 씨", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 망고 씨", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 견과류 껍질", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 호두 껍질", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 밤 껍질", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 땅콩 껍질", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 곡류 껍질", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 보리 겨(껍질)", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 쌀 겨(껍질)", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 콩 겨(껍질)", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 육류 뼈", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 육류 비계", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 육류 내장", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 소 뼈", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 돼지 뼈", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 닭 뼈", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 어패류 껍데기", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 조개 껍데기", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 소라 껍데기", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 전복 껍데기", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 꼬막 껍데기", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 굴 껍데기", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 게 껍데기", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 가재 껍데기", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 생선 통가시", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 복어 내장", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 알 껍데기", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 계란 껍데기", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 메추리알 껍데기", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 차 찌꺼기", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 녹차 티백 찌꺼기", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 보리차 티백 찌꺼기", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 한약재 찌꺼기", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 통짜 큰 과일", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 통짜 큰 채소", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 통 수박", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 통 무", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 통 호박", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 통 배추", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 장류", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 된장", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 고추장", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 간장", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 양념된 음식", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 김치", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 젓갈", "");
        countryList.add(country);
        country = new Country("음식물쓰레기", "· 깐 마늘", "");
        countryList.add(country);
        country = new Country("음식물쓰레기", "· 깐 양파", "");
        countryList.add(country);
        country = new Country("음식물쓰레기", "· 고구마 껍질", "");
        countryList.add(country);
        country = new Country("음식물쓰레기", "· 감자 껍질", "");
        countryList.add(country);
        country = new Country("음식물쓰레기", "· 자른 수박 껍질", "");
        countryList.add(country);
        country = new Country("음식물쓰레기", "· 귤 껍질", "");
        countryList.add(country);
        country = new Country("음식물쓰레기", "· 바나나 껍질", "");
        countryList.add(country);
        country = new Country("음식물쓰레기", "· 물로 헹군 젓갈", "");
        countryList.add(country);
        country = new Country("음식물쓰레기", "· 물로 헹군 김치", "");
        countryList.add(country);
        continent = new Continent("음식물류", "", countryList);
        continentList.add(continent);

        countryList = new ArrayList<Country>();
        country = new Country("종이류","· 신문지", "");
        countryList.add(country);
        country = new Country("종이류","· 책자", "");
        countryList.add(country);
        country = new Country("종이류","· 노트", "");
        countryList.add(country);
        country = new Country("종이류","· 전단지", "");
        countryList.add(country);
        country = new Country("종이류","· 쇼핑백", "");
        countryList.add(country);
        country = new Country("종이류","· 달력", "");
        countryList.add(country);
        country = new Country("종이류","· 포장지", "");
        countryList.add(country);
        country = new Country("종이류","· 상자", "");
        countryList.add(country);
        country = new Country("종이류","· 골판지 상자", "");
        countryList.add(country);
        country = new Country("종이류","· 종이 조각", "");
        countryList.add(country);
        country = new Country("종이류","· 영수증", "");
        countryList.add(country);
        country = new Country("종이류","· 고지서", "");
        countryList.add(country);
        country = new Country("일반종량제봉투/종이류","· 핸드타월", "");
        countryList.add(country);
        country = new Country("일반종량제봉투","· 휴지", "");
        countryList.add(country);
        country = new Country("일반종량제봉투","· 광고지", "");
        countryList.add(country);
        country = new Country("일반종량제봉투","· 사진", "");
        countryList.add(country);
        country = new Country("일반종량제봉투","· 코팅된 종이", "");
        countryList.add(country);
        country = new Country("종이류","· 스프링 제본된 책자류", "");
        countryList.add(country);
        continent = new Continent("종이류", "", countryList);
        continentList.add(continent);

        countryList = new ArrayList<Country>();
        country = new Country("종이팩","· 종이컵", "");
        countryList.add(country);
        country = new Country("종이팩","· 종이팩", "");
        countryList.add(country);
        continent = new Continent("종이컵 · 종이팩", "", countryList);
        continentList.add(continent);

        countryList = new ArrayList<Country>();
        country = new Country("캔류","· 철 캔", "");
        countryList.add(country);
        country = new Country("캔류","· 알루미늄 캔", "");
        countryList.add(country);
        country = new Country("캔류","· 부탄가스 용기", "");
        countryList.add(country);
        country = new Country("캔류","· 살충제 용기", "");
        countryList.add(country);
        country = new Country("고철류","· 공구류", "");
        countryList.add(country);
        country = new Country("고철류","· 철사", "");
        countryList.add(country);
        country = new Country("고철류","· 못", "");
        countryList.add(country);
        country = new Country("고철류","· 전선", "");
        countryList.add(country);
        country = new Country("고철류","· 알루미늄 고철", "");
        countryList.add(country);
        country = new Country("고철류","· 스텐 고철", "");
        countryList.add(country);
        country = new Country("일반종량제봉투/고철류","· 우산", "");
        countryList.add(country);
        continent = new Continent("캔류 · 고철류", "", countryList);
        continentList.add(continent);

        countryList = new ArrayList<Country>();
        country = new Country("유리병류","· 음료수 병", "");
        countryList.add(country);
        country = new Country("유리병류","· 소주 병", "");
        countryList.add(country);
        country = new Country("유리병류","· 맥주 병", "");
        countryList.add(country);
        country = new Country("유리병류","· 기타 병류", "");
        countryList.add(country);
        country = new Country("일반종량제봉투","· 깨진 유리", "");
        countryList.add(country);
        country = new Country("유리병류","· 유리잔", "");
        countryList.add(country);
        country = new Country("유리병류","· 맥주컵", "");
        countryList.add(country);
        country = new Country("특수규격마대","· 내열 유리 용기(밀폐 용기)", "");
        countryList.add(country);
        country = new Country("특수규격마대","· 내열 유리 냄비", "");
        countryList.add(country);
        continent = new Continent("유리병류", "", countryList);
        continentList.add(continent);

        countryList = new ArrayList<Country>();
        country = new Country("플라스틱류", "· 페트병", "");
        countryList.add(country);
        country = new Country("플라스틱류", "· 플라스틱 용기류", "");
        countryList.add(country);
        country = new Country("일반종량제봉투/플라스틱류", "· 폐스티로폼", "");
        countryList.add(country);
        country = new Country("일반종량제봉투/대형폐기물", "· 고무대야", "");
        countryList.add(country);
        country = new Country("플라스틱류", "· CD(콤팩트 디스크)", "");
        countryList.add(country);
        country = new Country("일반종량제봉투/플라스틱류", "· 비디오 테이프", "");
        countryList.add(country);
        country = new Country("일반종량제봉투/플라스틱류", "· 오디오 테이프", "");
        countryList.add(country);
        country = new Country("일반종량제봉투/대형폐기물", "· 화분", "");
        countryList.add(country);
        country = new Country("플라스틱류", "· 유아용 볼풀공", "");
        countryList.add(country);
        country = new Country("재질별배출", "· 완구류", "");
        countryList.add(country);
        country = new Country("대형폐기물", "· 유모차", "");
        countryList.add(country);
        country = new Country("대형폐기물", "· 유아용 그네", "");
        countryList.add(country);
        country = new Country("대형폐기물", "· 유아용 자동차", "");
        countryList.add(country);
        country = new Country("대형폐기물", "· 유아용 목마", "");
        countryList.add(country);
        country = new Country("고철류/플라스틱류", "· 송곳", "");
        countryList.add(country);
        country = new Country("비닐류/플라스틱류", "· 노끈", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 알약 포장재", "");
        countryList.add(country);
        country = new Country("플라스틱류", "· 부서진 스티로폼 조각", "");
        countryList.add(country);
        country = new Country("재질별배출", "· 1회용 그릇", "");
        countryList.add(country);
        continent = new Continent("플라스틱류", "", countryList);
        continentList.add(continent);

        countryList = new ArrayList<Country>();
        country = new Country("비닐류", "· 과자 봉지", "");
        countryList.add(country);
        country = new Country("비닐류", "· 라면 봉지", "");
        countryList.add(country);
        country = new Country("비닐류", "· 빵 봉지", "");
        countryList.add(country);
        country = new Country("비닐류", "· 1회용 비닐 봉투", "");
        countryList.add(country);
        country = new Country("비닐류", "· 각종 비닐류", "");
        countryList.add(country);
        country = new Country("비닐류", "· 뽁뽁이(버블랩/에어캡)", "");
        countryList.add(country);
        continent = new Continent("비닐류", "", countryList);
        continentList.add(continent);

        countryList = new ArrayList<Country>();
        country = new Country("전용수거함", "· 형광등", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 깨진 형광등", "");
        countryList.add(country);
        country = new Country("일반종량제봉투/특수규격마대", "· 백열전구", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· LED 전구", "");
        countryList.add(country);
        continent = new Continent("형광등", "", countryList);
        continentList.add(continent);

        countryList = new ArrayList<Country>();
        country = new Country("전용수거함", "· 건전지", "");
        countryList.add(country);
        country = new Country("전용수거함", "· 충전용 전지", "");
        countryList.add(country);
        continent = new Continent("폐전지", "", countryList);
        continentList.add(continent);

        countryList = new ArrayList<Country>();
        country = new Country("의류수거함", "· 헌옷", "");
        countryList.add(country);
        country = new Country("의류수거함", "· 신발", "");
        countryList.add(country);
        country = new Country("의류수거함", "· 가방", "");
        countryList.add(country);
        country = new Country("의류수거함", "· 담요", "");
        countryList.add(country);
        country = new Country("의류수거함", "· 누비 이불", "");
        countryList.add(country);
        country = new Country("의류수거함", "· 커튼", "");
        countryList.add(country);
        country = new Country("의류수거함", "· 카페트", "");
        countryList.add(country);
        country = new Country("대형폐기물", "· 여행용 가방", "");
        countryList.add(country);
        country = new Country("대형폐기물", "· 솜이불", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 베개", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 방석", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 걸레", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 수건", "");
        countryList.add(country);
        continent = new Continent("섬유류", "", countryList);
        continentList.add(continent);

        countryList = new ArrayList<Country>();
        country = new Country("전용수거함", "· 소형 폐가전(1m 미만 가전 제품)", "");
        countryList.add(country);
        country = new Country("전용수거함", "· 컴퓨터", "");
        countryList.add(country);
        country = new Country("전용수거함", "· 전기 밥솥", "");
        countryList.add(country);
        country = new Country("전용수거함", "· 선풍기", "");
        countryList.add(country);
        country = new Country("방문수거", "· 대형 폐가전(1m 이상 가전 제품)", "");
        countryList.add(country);
        country = new Country("방문수거", "· 세탁기", "");
        countryList.add(country);
        country = new Country("방문수거", "· 에어컨", "");
        countryList.add(country);
        country = new Country("방문수거", "· 냉장고", "");
        countryList.add(country);
        country = new Country("방문수거", "· TV", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 전기 담요", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 전기 방석", "");
        countryList.add(country);
        continent = new Continent("폐전자제품", "", countryList);
        continentList.add(continent);

        countryList = new ArrayList<Country>();
        country = new Country("폐유수거함", "· 폐식용유", "");
        countryList.add(country);
        continent = new Continent("폐식용유", "", countryList);
        continentList.add(continent);

        countryList = new ArrayList<Country>();
        country = new Country("일반종량제봉투", "· 폐목재류", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 아이스팩", "");
        countryList.add(country);
        country = new Country("투명비닐봉투", "· 연탄재", "");
        countryList.add(country);
        country = new Country("특수규격마대", "· 페인트통", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 양초", "");
        countryList.add(country);
        country = new Country("일반종량제봉투", "· 스펀지", "");
        countryList.add(country);
        country = new Country("약국수거함", "· 폐의약품", "");
        countryList.add(country);
        continent = new Continent("기타", "", countryList);
        continentList.add(continent);
    }
}
