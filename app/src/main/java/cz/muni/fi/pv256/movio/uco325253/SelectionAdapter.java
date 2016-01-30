package cz.muni.fi.pv256.movio.uco325253;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import java.util.List;

/**
 * Created by Marek on 02.12.2015.
 */
public class SelectionAdapter extends ArrayAdapter<String> {

    public SelectionAdapter(Context context, int resource) {
        super(context, resource);
    }

    public SelectionAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public SelectionAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    public SelectionAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public SelectionAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    public SelectionAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }



}
