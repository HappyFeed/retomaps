package app.moviles.retomaps;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SearchFragment extends Fragment {

    private Context context ;
    private RecyclerView listPlaces;
    private LinearLayoutManager linearLayoutManager;
    private SearchAdapter adapter;

    public SearchFragment() {

        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

        listPlaces = getActivity().findViewById(R.id.listPlaces);

        linearLayoutManager = new LinearLayoutManager(context);
        listPlaces.setLayoutManager(linearLayoutManager);
        adapter = new SearchAdapter();
        listPlaces.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}