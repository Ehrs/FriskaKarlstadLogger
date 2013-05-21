package se.ehrs.jonas.friskakarlstadlogger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                String text = decodedCode.getText();
                Matcher matcher = FRISKA_KARLSTAD_QR_PATTERN.matcher(text);
                if (matcher.matches()) {
                    int controlNumber = Integer.parseInt(matcher.group(1));
                    String controlCode = matcher.group(2);
                    long timestamp = decodedCode.getTimestamp();
                    cameraFragment.startCameraCampure();
                } else {
                    System.out.println("Could not parse '"+decodedCode.getText()+"'");
                }
            }
        };
        cameraFragment.setmCallBack(mCallBack );
        return view;
    }
}
