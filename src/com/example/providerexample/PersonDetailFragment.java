package com.example.providerexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.providerexample.database.DatabaseHandler;
import com.example.providerexample.database.Person;

/**
 * A fragment representing a single Person detail screen.
 * This fragment is either contained in a {@link PersonListActivity}
 * in two-pane mode (on tablets) or a {@link PersonDetailActivity}
 * on handsets.
 */
public class PersonDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The person this fragment is presenting.
     */
    private Person mItem;
    
    /**
     * The UI elements showing the details of the Person
     */
    private TextView textFirstName;
    private TextView textLastName;
    private TextView textBio;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PersonDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
        	// Should use the contentprovider here ideally
            mItem = DatabaseHandler.getInstance(getActivity()).getPerson(getArguments().getLong(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_person_detail, container, false);

        if (mItem != null) {
            textFirstName = ((TextView) rootView.findViewById(R.id.textFirstName));
            textFirstName.setText(mItem.firstname);
            
            textLastName = ((TextView) rootView.findViewById(R.id.textLastName));
            textLastName.setText(mItem.lastname);
            
            textBio = ((TextView) rootView.findViewById(R.id.textBio));
            textBio.setText(mItem.bio);
        }

        return rootView;
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	updatePersonFromUI();
    }
    
    private void updatePersonFromUI() {
    	if (mItem != null) {
    		mItem.firstname = textFirstName.getText().toString();
    		mItem.lastname = textLastName.getText().toString();
    		mItem.bio = textBio.getText().toString();
    		
    		DatabaseHandler.getInstance(getActivity()).putPerson(mItem);
        }
    }
}
