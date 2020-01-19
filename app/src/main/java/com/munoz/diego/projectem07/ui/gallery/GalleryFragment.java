package com.munoz.diego.projectem07.ui.gallery;

import android.os.Bundle;
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

public class GalleryFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private GalleryViewModel galleryViewModel;

    String[] m_animales;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        m_animales = getResources().getStringArray(R.array.animales);

        Spinner spin = root.findViewById(R.id.sp_animal);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this.getContext(),android.R.layout.simple_spinner_item, m_animales);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(aa);


        return root;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}