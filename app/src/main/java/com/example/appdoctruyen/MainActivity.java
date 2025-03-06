package com.example.appdoctruyen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import android.os.Handler;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import API.ApiLayTruyen;
import adapter.NovelAdapter;
import interfaces.LayTruyen;
import object.Novel;

public class MainActivity extends AppCompatActivity {

    GridView gdvDSTruyen;
    NovelAdapter novelAdapter;
    ArrayList<Novel> novelArrayList;
    private Button btnNextPage, btnPrevPage;
    private int currentPage = 0;
    private static final int ITEMS_PER_PAGE = 12; // 4 hàng x 3 cột




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        init();
        anhXa();
        setUp();
        setClick();

        imgNen = findViewById(R.id.Nen);
        startImageSlideshow();



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void init()
    {
        novelArrayList = new ArrayList<>();

        novelAdapter = new NovelAdapter(this, 0, novelArrayList);
    }
    private void anhXa()
    {
        gdvDSTruyen = findViewById(R.id.gdvDSTruyen);
        btnNextPage = findViewById(R.id.btnNextPage);
        btnPrevPage = findViewById(R.id.btnPrevPage);
    }
    private void setUp()
    {
        gdvDSTruyen.setAdapter(novelAdapter);
        int start = currentPage * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, novelArrayList.size());
        ArrayList<Novel> pageData = new ArrayList<>(novelArrayList.subList(start, end)); // Sửa lỗi subList()

        // Gán dữ liệu vào adapter
        novelAdapter = new NovelAdapter(this, 0, pageData);
        gdvDSTruyen.setAdapter(novelAdapter);
    }
    private void setClick(){

        btnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });
        btnPrevPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevPage();
            }
        });
    }


    // chuyen anh lieen tuc
    private int[] imageArray = {
            R.mipmap.test1,
            R.mipmap.test2,
            R.mipmap.test3
    };
    private int currentIndex = 0;
    private ImageView imgNen;
    private void startImageSlideshow() {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                currentIndex++;
                if (currentIndex >= imageArray.length) {
                    currentIndex = 0;
                }
                imgNen.setImageResource(imageArray[currentIndex]);
                handler.postDelayed(this, 3000); // Đổi ảnh mỗi 3 giây
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    // next trang
    private void nextPage() {
        if ((currentPage + 1) * ITEMS_PER_PAGE < novelArrayList.size()) {
            currentPage++;
            setUp();
            gdvDSTruyen.post(() -> gdvDSTruyen.setSelection(0)); // Cuộn lên đầu trang

        }
    }

    // Quay lại trang trước
    private void prevPage() {
        if (currentPage > 0) {
            currentPage--;
            setUp();
            gdvDSTruyen.post(() -> gdvDSTruyen.setSelection(0)); // Cuộn lên đầu trang

        }
    }

    ApiLayTruyen apiLayTruyen = new ApiLayTruyen(new LayTruyen() {

        @Override
        public void start() {

        }

        @Override
        public void complete(String data) {
            try {
                novelArrayList.clear();  // Xóa danh sách cũ

                JSONObject jsonObject = new JSONObject(data);  // Chuyển `data` thành JSONObject
                JSONArray arr = jsonObject.getJSONObject("data").getJSONArray("novels");  // Lấy danh sách truyện từ key "novels"

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject object = arr.getJSONObject(i);
                    novelArrayList.add(new Novel(object));  // Thêm truyện vào danh sách
                }
                currentPage =0;
                setUp();

            } catch (Exception e) {
                Log.e("API", "Lỗi khi xử lý JSON: " + e.getMessage());
            }
        }

        @Override
        public void errol() {

        }
    });


}