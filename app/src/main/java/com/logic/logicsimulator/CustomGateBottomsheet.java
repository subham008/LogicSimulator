package com.logic.logicsimulator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CustomGateBottomsheet extends BottomSheetDialogFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CustomGateBottomsheet() {
        // Required empty public constructor
    }

    public static CustomGateBottomsheet newInstance(String param1, String param2) {
       CustomGateBottomsheet fragment=new CustomGateBottomsheet();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

    public BasicGateView element=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.custom_gate_bottomsheet_layout, container, false);

        if (element==null)
        {
            Toast.makeText(getContext(), "ERROR:gate element passed is null", Toast.LENGTH_LONG).show();
            return v;
        }
        TextView gate_name = v.findViewById(R.id.gate_name);
        gate_name.setText(element.gateName);

        FrameLayout preview_layout = v.findViewById(R.id.preview_layout);
        BasicGateView gate=new BasicGateView(getContext(),element.parent_layout,element.pins.length,element.opin.length,element.tpins.length,element.bpins.length);
        gate.gateType=element.gateType;
         preview_layout.addView(gate);

        TextView gateType = v.findViewById(R.id.gate_type_name);
        gateType.setText(element.gateType);

        EditText edit_gate_name = v.findViewById(R.id.gate_name_edit);
        edit_gate_name.setText(element.gateName);

        Button save_title_button = v.findViewById(R.id.save_title_button);
        save_title_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                element.gateName=edit_gate_name.getText().toString();
                gate_name.setText(element.gateName);
            }
        });


        TextView custom_list_title=v.findViewById(R.id.custom_element_list_title);
        //setting customizable

        CustomListAdapter customListAdapter=null;
        ListView customizable_list_view = v.findViewById(R.id.customize_option_list);

        Customizable customarray[]=null;
        if(element!=null)
        customarray = element.customizableList;

        if (customarray != null && customarray.length > 0){
             customListAdapter = new CustomListAdapter(getContext(), customarray, getLayoutInflater());
            customizable_list_view.setAdapter(customListAdapter);
          }
        else
        {
           custom_list_title.setVisibility(View.GONE);
        }

        // Inflate the layout for this fragment
        return v;
    }

}
