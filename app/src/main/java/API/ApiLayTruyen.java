package API;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import interfaces.LayTruyen;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ApiLayTruyen {
    private String data;
    private LayTruyen layTruyen;
    private static final String API_URL = "https://api.myjson.online/v1/records/f68b176a-e7cd-4a3c-aeb8-3874906a7a41";

    public ApiLayTruyen(LayTruyen layTruyen) {
        this.layTruyen = layTruyen;
        this.layTruyen.start();

        fetchDataFromApi();
    }

    private void fetchDataFromApi() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(API_URL).build();
            data = null;

            try {
                Response response = client.newCall(request).execute();
                ResponseBody body = response.body();
                if (body != null) {
                    data = body.string();
                    Log.d("API_DATA", "Dữ liệu nhận được: " + data);

                } else {
                    data = "Lỗi: Không có dữ liệu!";
                }
            } catch (IOException e) {
                data = "Lỗi khi tải dữ liệu: " + e.getMessage();
            }

            // Cập nhật UI sau khi lấy dữ liệu
            new Handler(Looper.getMainLooper()).post(() -> {
                Log.d("API", "Dữ liệu nhận được: " + data);
                if (data.startsWith("Lỗi")) {
                    layTruyen.errol();  // Gọi hàm xử lý lỗi
                } else {
                    layTruyen.complete(data); // Trả về dữ liệu API
                }
                Log.d("API_DATA", "Dữ liệu nhận được: " + data);
            });
        });
    }
}
