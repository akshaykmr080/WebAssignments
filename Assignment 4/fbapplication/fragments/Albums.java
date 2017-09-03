package com.example.akshaykumars.fbapplication.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akshaykumars.fbapplication.R;
import com.example.akshaykumars.fbapplication.detailsItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Albums.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Albums#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Albums extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static ArrayList<detailsItems> albumData = new ArrayList<detailsItems>();
    private OnFragmentInteractionListener mListener;

    public Albums() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Albums.
     */
    // TODO: Rename and change types and number of parameters
    public static Albums newInstance(String param1, String param2) {
        Albums fragment = new Albums();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        String albumName = null;
        ArrayList<String> urls = null;
        ArrayList<String> id = null;
        if(param2 != null){
            try {
                JSONObject jsonobject = new JSONObject(param2);
                JSONArray jsonArray = jsonobject.getJSONArray("data");
                for (int i =0; i< jsonArray.length(); i++){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    albumName = obj.get("name").toString();
                    JSONArray photos = ((JSONObject) obj.get("photos")).getJSONArray("data");
                    urls = new ArrayList<String>();
                    id = new ArrayList<String>();
                    for(int j=0; j<photos.length();j++){
                        urls.add(photos.getJSONObject(j).get("picture").toString());
                        id.add(photos.getJSONObject(j).get("id").toString());
                    }
                    detailsItems item = new detailsItems(albumName,urls,id);
                    albumData.add(item);
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
        return inflater.inflate(R.layout.fragment_albums, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
