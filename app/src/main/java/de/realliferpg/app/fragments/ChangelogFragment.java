package de.realliferpg.app.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import de.realliferpg.app.R;
import de.realliferpg.app.helper.ApiHelper;
import de.realliferpg.app.interfaces.RequestCallbackInterface;
import de.realliferpg.app.objects.Changelog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChangelogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChangelogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangelogFragment extends Fragment implements RequestCallbackInterface {

    private ExpandableListView listView;
    private de.realliferpg.app.fragments.ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    private OnFragmentInteractionListener mListener;

    public ChangelogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ChangelogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangelogFragment newInstance() {
        ChangelogFragment fragment = new ChangelogFragment();
        Bundle args = new Bundle();
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

        View view = inflater.inflate(R.layout.fragment_changelog, container, false);

        ApiHelper apiHelper = new ApiHelper(this);
        apiHelper.getChangelog();


        return view;
    }



    @Override
    public void onResponse(JSONObject response, Class type) {
        if (type.equals(Changelog.Wrapper.class)) {

            Gson gson = new Gson();
            Changelog.Wrapper value = gson.fromJson(response.toString(), Changelog.Wrapper.class);

            ArrayList<Changelog> changelogs = new ArrayList<>(Arrays.asList(value.data));

            listView = this.getActivity().findViewById(R.id.lvExp);



            listDataHeader = new ArrayList<>();
            listHash = new HashMap<>();

            //List<String> edmtDev = new ArrayList<>();

            //listDataHeader.add(changelogs.get(1).toString());
            //listHash.put(listDataHeader.get(0), Arrays.asList(changelogs.get(1).change_map));


            for (de.realliferpg.app.objects.Changelog temp : changelogs) {



                List<String> changes = new ArrayList<>();

                listDataHeader.add(temp.toString());


                if(temp.change_map.length > 0){
                    changes.add("<b>Map</b>");
                    for (String tempmap : temp.change_map) {
                        changes.add("&bull; " + tempmap);
                    }
                    //Collections.addAll(changesMap, temp.change_map); //fügt alle elemente hinzu, jedoch ohne symbol am anfang
                }

                if(temp.change_mission.length > 0){
                    changes.add("<b>Mission</b>");
                    for (String tempmission : temp.change_mission) {
                        changes.add("&bull; " + tempmission);
                    }
                }


                if(temp.change_mod.length > 0){
                    changes.add("<b>Mod</b>");
                    for (String tempmod : temp.change_mod) {
                        changes.add("&bull; " + tempmod);
                    }
                }

                changes.add("<i><font color='red'>" + temp.note.toString() + "</font></i>");

                listHash.put(temp.toString(), changes);

            }



            listAdapter = new de.realliferpg.app.fragments.ExpandableListAdapter(this.getContext(),listDataHeader,listHash);
            listView.setAdapter(listAdapter);


        }
    }

    private void initData() {  //not used; only for code sample
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("EDMTDev");
        listDataHeader.add("Android");
        listDataHeader.add("Xamarin");
        listDataHeader.add("UWP");

        List<String> edmtDev = new ArrayList<>();
        edmtDev.add("This is Expandable ListView");

        List<String> androidStudio = new ArrayList<>();
        androidStudio.add("Expandable ListView");
        androidStudio.add("Google Map");
        androidStudio.add("Chat Application");
        androidStudio.add("Firebase ");

        List<String> xamarin = new ArrayList<>();
        xamarin.add("Xamarin Expandable ListView");
        xamarin.add("Xamarin Google Map");
        xamarin.add("Xamarin Chat Application");
        xamarin.add("Xamarin Firebase ");

        List<String> uwp = new ArrayList<>();
        uwp.add("UWP Expandable ListView");
        uwp.add("UWP Google Map");
        uwp.add("UWP Chat Application");
        uwp.add("UWP Firebase ");

        listHash.put(listDataHeader.get(0),edmtDev);
        listHash.put(listDataHeader.get(1),androidStudio);
        listHash.put(listDataHeader.get(2),xamarin);
        listHash.put(listDataHeader.get(3),uwp);
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
