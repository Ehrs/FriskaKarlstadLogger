package se.ehrs.jonas.friskakarlstadlogger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.friskakarlstadlog.R;

public class LoggedControlsFragment extends Fragment {
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        ListView view = (ListView) inflater.inflate(R.layout.logged_controls, container, false);
	        Map<String, ?> allControls = getActivity().getPreferences(Context.MODE_PRIVATE).getAll();
	        List<String> controls = new ArrayList<String>();
	        for (Entry<String, ?> entry : allControls.entrySet()) {
	        	Timestamp timestamp = new Timestamp(Long.valueOf(entry.getKey()));
	        	controls.add(timestamp.toString()+" "+entry.getValue());
	        }
			ListAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, controls);
			view.setAdapter(adapter);
	        return view;
	 }
}
