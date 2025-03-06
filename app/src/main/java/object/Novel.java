package object;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Novel {
    private String tenTruyen;
    private String tenChap;
    private String linkAnh;



    public Novel(@NonNull JSONObject object) throws JSONException {
        this.tenTruyen = object.getString("tenTruyen");
        this.tenChap = object.getString("tenChap");
        this.linkAnh = object.getString("linkAnh");
    }


    public Novel() {}
    public Novel(String tenTruyen, String tenChap, String linkAnh) {
        this.tenTruyen = tenTruyen;
        this.tenChap = tenChap;
        this.linkAnh = linkAnh;
    }

    public String getTenTruyen() {
        return tenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
    }

    public String getTenChap() {
        return tenChap;
    }

    public void setTenChap(String tenChap) {
        this.tenChap = tenChap;
    }

    public String getLinkAnh() {
        return linkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        this.linkAnh = linkAnh;
    }
}
