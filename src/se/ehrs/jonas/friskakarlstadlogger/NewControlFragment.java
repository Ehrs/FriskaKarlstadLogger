package se.ehrs.jonas.friskakarlstadlogger;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abhi.barcode.fragment.interfaces.IResultCallback;
import com.abhi.barcode.fragment.library.BarCodeFragment;
import com.example.friskakarlstadlog.R;
import com.google.zxing.Result;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */
public class NewControlFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";
    private static final Pattern FRISKA_KARLSTAD_QR_PATTERN = Pattern.compile("^(\\d+)(\\S+)$");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.new_control_fragment, container, false);
        final BarCodeFragment cameraFragment = (BarCodeFragment) getFragmentManager().findFragmentById(R.id.camera_fragment);
        IResultCallback mCallBack = new IResultCallback() {
            
            @Override
            public void result(Result decodedCode) {
            	System.out.println("DEBUG 1: We have a code!");
                String text = decodedCode.getText();
                Matcher matcher = FRISKA_KARLSTAD_QR_PATTERN.matcher(text);
                if (matcher.matches()) {
                	String timestamp = String.valueOf(decodedCode.getTimestamp());
                    Editor editor = getActivity().getPreferences(Context.MODE_PRIVATE).edit();
                    editor.putString(timestamp, text).apply();
                } else {
                    System.out.println("DEBUG 3: This cannot be a Friska Karlstad Code: '"+decodedCode.getText()+"'");
                }
                Map<String, ?> allPrefs = getActivity().getPreferences(Context.MODE_PRIVATE).getAll();
                System.out.println(String.format("DEBUG 4: You have %d prefs", allPrefs.size()));
                for (Entry<String, ?> entry : allPrefs.entrySet()) {
                	System.out.println("DEBUG 5: "+new Timestamp(Long.parseLong(entry.getKey())).toString());
                	Object control = entry.getValue();
                	if (control instanceof String) {
                		System.out.println("DEBUG 5b: "+control.toString());
                	}
                }
            }
        };
        cameraFragment.setmCallBack(mCallBack );
        return view;
    }
}
