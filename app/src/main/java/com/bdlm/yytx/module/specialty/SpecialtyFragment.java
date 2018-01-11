package com.bdlm.yytx.module.specialty;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bdlm.yytx.R;

/**
 * 特产
 */
public class SpecialtyFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout_scenic_note for this fragment
        return inflater.inflate(R.layout.fragment_specialty, container, false);
    }

}
