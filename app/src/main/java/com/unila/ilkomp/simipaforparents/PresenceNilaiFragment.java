package com.unila.ilkomp.simipaforparents;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unila.ilkomp.simipaforparents.adapter.PresenceSchoolAdapter;
import com.unila.ilkomp.simipaforparents.data.PresenceSchoolData;
import com.unila.ilkomp.simipaforparents.model.PresenceSchool;

import java.util.ArrayList;

public class PresenceNilaiFragment extends Fragment {

    RecyclerView recyclerView;
    private ArrayList<PresenceSchool> list = new ArrayList<>();

    private PresenceNilaiFragment.OnFragmentInteractionListener mListener;

    public PresenceNilaiFragment() {
        // Required empty public constructor
    }

    public static PresenceNilaiFragment newInstance(String param1, String param2) {
        PresenceNilaiFragment fragment = new PresenceNilaiFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_presence_nilai, container, false);

        recyclerView = root.findViewById(R.id.rv_presence_nilai);
        //progressBar = root.findViewById(R.id.progressBar);
        //dataEmpty = root.findViewById(R.id.data_empty);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list.clear();
        list.addAll(PresenceSchoolData.getListData());
        showRecyclerList();

        return root;
    }

    private void showRecyclerList(){
        PresenceSchoolAdapter presenceSchoolAdapter = new PresenceSchoolAdapter(list);
        recyclerView.setAdapter(presenceSchoolAdapter);
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PresenceNilaiFragment.OnFragmentInteractionListener) {
            mListener = (PresenceNilaiFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
