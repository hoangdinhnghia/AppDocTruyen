package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.appdoctruyen.R;

import java.util.ArrayList;
import java.util.List;

import object.Novel;

public class NovelAdapter extends ArrayAdapter<Novel> {

    private Context ct;
    private ArrayList<Novel> arr;
    public NovelAdapter(@NonNull Context context, int resource, @NonNull List<Novel> objects) {
        super(context, resource, objects);
        this.ct = context;
        this.arr = new ArrayList<>(objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater)ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_truyen, null);
        }
        if (arr.size() > 0)
        {
            Novel novel = this.arr.get(position);

            TextView tenTruyen = (TextView) convertView.findViewById(R.id.txtTenTruyen);
            TextView tenChap = convertView.findViewById(R.id.txtTenChap);
            ImageView imgAnhTruyen = convertView.findViewById(R.id.imgAnhTruyen);

            tenTruyen.setText(novel.getTenTruyen());
            tenChap.setText(novel.getTenChap());
            Glide.with(this.ct).load(novel.getLinkAnh()).into(imgAnhTruyen);
        }

        return convertView;
    }
    public void updateData(List<Novel> newList) {
        this.arr.clear();
        this.arr.addAll(newList);
        notifyDataSetChanged();
    }

}
