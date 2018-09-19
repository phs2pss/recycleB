package com.selecto.banana2;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    private static int PAGE_NUMBER = 4;
    TextView textView;
    TabLayout mTabLayout;
    CustomViewPager mViewPager;
    TextView confirm;
    noticeDialog noticeDialog;
    ArrayList<Shoplistitem> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (CustomViewPager) findViewById(R.id.viewpager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(R.drawable.likeicon,"실천 10계명", new PageFourFragment());
        adapter.addFragment(R.drawable.recycleicon,"분리배출 요령", new PageOneFragment());
        adapter.addFragment(R.drawable.infoicon,"배출 정보", new PageTwoFragment());
        adapter.addFragment(R.drawable.mapicon,"봉투 판매소", new PageThreeFragment());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
            mTabLayout.getTabAt(i).setIcon(adapter.getFragmentInfo(i).getIconResId());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.MyAlertDialogStyle);
        TextView title = new TextView(this);
        title.setText("세계 제일의 재활용도시 서울");
        title.setPadding(10, 20, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(getResources().getColor(R.color.forest_green));
        title.setTextSize(30);
        builder.setCustomTitle(title);

        builder.setMessage("오늘날 인류는 역사상 유래 없는 물질적인 풍요를 누리고 있습니다. 하지만 우리가 이룩한 고도성장의 영광 뒤엔 환경파괴는 그림자처럼 따라왔고, 이제는 환경을 생각하지 않고서는 더 이상의 성장은 기대할 수 없는 상황입니다. 앞으로는 우리는 매립되는 폐기물을 더욱 줄여, 궁극적으로는 매립쓰레기를 제로(Zero)화 해야 하는 과제를 안고 있습니다.\n" +
                "\n" +
                "서울시는 1천만 인구가 집중된 대도시로서 여기서 소비하는 에너지와 발생하는 폐기물은 어마어마하고, 자원의 대부분은 수입에 의존하고 있습니다. 매립쓰레기 제로화와 자원선순환 도시 구현이라는 시대적 사명을 다하기 위해서는 재활용 가능 자원을 원천 분리 · 배출하는 것이 하나의 방법입니다. 재활용 분리배출은 시민들의 솔선참여가 꼭 필요합니다.\n" +
                "\n" +
                "본 '서울시 분리배출 길라잡이'는 재활용품 분리배출 기준에 대해 궁금했던 부분을 검색하여 분리배출 방법을 쉽게 알 수 있도록 제작되었습니다. 본 어플이 재활용품 분리배출에 대한 통일성 있는 기준으로 시민들이 분리배출을 실천하는 데에 조금이나마 기여하기를 기대합니다.");

        builder.setPositiveButton("확인", null);
        AlertDialog dialog = builder.show();
        TextView textView2 = (TextView) dialog.findViewById(android.R.id.message);
        textView2.setTextSize(20);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();

        noticeDialog = new noticeDialog(this);

        textView = findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "우리 동네 설정", Toast.LENGTH_LONG).show();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentBorC, new LocationFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    public String loadJSONFromAsset(String jsonName) {
        String json = null;
        try {

            InputStream is = getAssets().open(jsonName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    private long time = 0;

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            if(System.currentTimeMillis()-time>=2000){
                time=System.currentTimeMillis();
                Toast.makeText(getApplicationContext(),"\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.",Toast.LENGTH_SHORT).show();
            } else if(System.currentTimeMillis()-time<2000) {
                finish();
            }
        } else {
            super.onBackPressed();
        }
    }

    public void setData(ArrayList<Shoplistitem> data) {
        this.data = data;
    }

    public ArrayList<Shoplistitem> getData() {
        //Toast.makeText(this, "Data Count : " + data.size(), Toast.LENGTH_LONG).show();
        return data;
    }

    public void callShop(String tel) {

        if(tel == "null")
        {
            Toast.makeText(getApplicationContext(), "저장된 번호가 없습니다.", Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/" + tel));
            startActivity(intent);
        }
    }

}
