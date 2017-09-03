package com.example.akshaykumars.fbapplication.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.akshaykumars.fbapplication.Items;
import com.example.akshaykumars.fbapplication.ListAdaptor;
import com.example.akshaykumars.fbapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Pages.OnPagesFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Pages#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pages extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static ArrayList<Items> pageItems = new ArrayList<Items>();
    private OnPagesFragmentInteractionListener mListener;

    public Pages() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Pages.
     */
    // TODO: Rename and change types and number of parameters
    public static Pages newInstance(String param1, String param2) {
        Pages fragment = new Pages();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        if(param2 != null){
            try {
                JSONObject jsonobject = new JSONObject(param2);
                JSONArray jsonArray = jsonobject.getJSONArray("data");
                for (int i =0; i< jsonArray.length(); i++){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Items item = new Items(obj.get("id").toString(),obj.get("name").toString(),((JSONObject)obj.get("picture")).get("url").toString());
                    pageItems.add(item);
                }

            } catch(Exception e){

            }
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pages, container, false);
        // Inflate the layout for this fragment
        ListView yourListView = (ListView) rootView.findViewById(R.id.listView2);

        /*Items item1 = new Items("1234567","Akshay","https://google.com");
        Items item2 = new Items("1234567","Akshay","https://google.com");
        userItems.add(item1);
        userItems.add(item2);*/
        // get data from the table by the ListAdapter


        ListAdaptor customAdapter = new ListAdaptor(this.getContext(), R.layout.row, pageItems);


        yourListView .setAdapter(customAdapter);
        return rootView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onPagesFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPagesFragmentInteractionListener) {
            mListener = (OnPagesFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPagesFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPagesFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPagesFragmentInteraction(Uri uri);
    }
}
