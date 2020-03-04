package com.munoz.diego.projectem07.ui.configuracio;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.munoz.diego.projectem07.R;

import java.util.ArrayList;
import java.util.List;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_configuracio, container, false);

        Spinner spinner_posts = root.findViewById(R.id.sp_num_posts);
        Spinner spinner_map = root.findViewById(R.id.sp_num_map);

        String[] post = getResources().getStringArray(R.array.config_choose_posts);
        String[] map = getResources().getStringArray(R.array.config_choose_map);

        ArrayAdapter<Integer> adapterPost = new ArrayAdapter<Integer>(getActivity(),
                android.R.layout.simple_spinner_item,
                stringArrayToIntegerList(post));
        ArrayAdapter<Integer> adapterMap = new ArrayAdapter<Integer>(getActivity(),
                android.R.layout.simple_spinner_item,
                stringArrayToIntegerList(map));

        adapterPost.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterMap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SharedPreferences prefs = mContext.getSharedPreferences("com.munoz.diego.projectem07", Context.MODE_PRIVATE);

        spinner_posts.setAdapter(adapterPost);
        spinner_map.setAdapter(adapterMap);

        spinner_posts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Integer val = (Integer)adapterView.getItemAtPosition(i);
                SharedPreferences prefs = mContext.getSharedPreferences("com.munoz.diego.projectem07", Context.MODE_PRIVATE);

                prefs.edit().putInt("num_posts", val).apply();
                prefs.edit().putInt("num_postsi", i).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_map.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Integer val = (Integer)adapterView.getItemAtPosition(i);
                SharedPreferences prefs = mContext.getSharedPreferences("com.munoz.diego.projectem07", Context.MODE_PRIVATE);

                prefs.edit().putInt("num_maps", val).apply();
                prefs.edit().putInt("num_mapsi", i).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_posts.setSelection(prefs.getInt("num_postsi", 0));
        spinner_map.setSelection(prefs.getInt("num_mapsi", 0));

        return root;
    }

    public List<Integer> stringArrayToIntegerList(String[] a){
        List<Integer> aux = new ArrayList<>();

        for(int i = 0; i < a.length; i++){
            aux.add(Integer.valueOf(a[i]));
        }
        return aux;
    }
}