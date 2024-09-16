package com.logic.logicsimulator;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.logic.logicsimulator.parsers.IntegratedCircuitParser;
import com.logic.logicsimulator.parsers.LinkPinElement;
import com.logic.logicsimulator.parsers.LogicProject;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.logic.logicsimulator.parsers.PinElement;

import java.util.ArrayList;
import java.util.List;

public class make_integrated_bottom_sheet extends BottomSheetDialogFragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private IntegratedCircuitParser integratedParser;
    EditorLayout mainlayout=null;
    public make_integrated_bottom_sheet(@NonNull EditorLayout layout  , @NonNull IntegratedCircuitParser parser) {
        mainlayout=layout;
        this.integratedParser=parser;
        pins=new ArrayList<LinkPinElement>();
        PinList=new ArrayList<>();
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    MakeIcPinListAdapter pinListAdapter=null;
    ListView pinList=null;
    List<LinkPinElement> pins=null;

    List<Pin> PinList=null;
    BasicGateView gateView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

          View v=inflater.inflate(R.layout.fragment_make_integrated_bottom_sheet, container, false);

        FrameLayout preview=v.findViewById(R.id.ic_preview);
        gateView=new BasicGateView(getContext(),mainlayout,integratedParser.getLeftPinElements().size(), integratedParser.getRightPinElements().size(),integratedParser.getTopPinElements().size()
                ,integratedParser.getBottomPinElements().size());
        preview.addView(gateView);
        setPreviewGatePinNames();
        TextView nopinText=v.findViewById(R.id.no_pin_text);
        TextView lebelPinSuggest=v.findViewById(R.id.label_pin_suggestion_textview);

        if(pins.size()<=0)
            lebelPinSuggest.setVisibility(View.GONE);
        else if( lebelPinSuggest.getVisibility()==View.GONE)
            lebelPinSuggest.setVisibility(View.VISIBLE);

        if(pinListAdapter==null)
           pinListAdapter=new MakeIcPinListAdapter(getContext(),pins, integratedParser){
               @Override
               public void onChangeOrient(LinkPinElement pin,int o , int orientation,int position){
                   gateView=new BasicGateView(getContext(),mainlayout,integratedParser.getLeftPinElements().size(),integratedParser.getRightPinElements().size(),integratedParser.getTopPinElements().size(),integratedParser.getBottomPinElements().size());
                   integratedParser.changePinOrient(pin, orientation);
                   setPreviewGatePinNames();
               }

               @Override
               public void onRemove(LinkPinElement pin, int position){
                   integratedParser.removeLinkPin(pin);

               }
           };

        pinList=v.findViewById(R.id.pin_list);
        pinList.setAdapter(pinListAdapter);
        pinList.setEmptyView(nopinText);


        return v;
    }

    public boolean addPintoIC(@NonNull LinkPinElement pinElement  , @NonNull Pin pin){

        if(!PinList.contains(pin)) {
            pins.add(pinElement);
            PinList.add(pin);

            integratedParser.addPin( pin ,pinElement , pinElement.getLinkPinOrient()  );

            pin.setOnPinNameChange(new BasicTask(){
                @Override
                public void run(){
                    pinElement.setPinname(pin.getPinName());
                    pinList.invalidateViews();
                }
            });

            if(pinListAdapter!=null)
                pinListAdapter.notifyDataSetChanged();
            return true;
        }
        return false;
    }


    public boolean addPinToList(LinkPinElement pin){
        if(!pins.contains(pin)) {
            pins.add(pin);
            Pin p=pin.getPin(mainlayout);

            if(p!=null) {
                PinList.add(p);
                p.setDefaultColor(Color.YELLOW);
                p.setOnPinNameChange(new BasicTask(){
                    @Override
                    public void run(){
                        pin.setPinname(p.getPinName());
                        pinList.invalidateViews();
                    }
                });
            }


            if(pinListAdapter!=null)
                pinListAdapter.notifyDataSetChanged();
            return true;
        }

        return  false;
    }



    public boolean isPinBelongToIC(@NonNull Pin pin){
         return PinList.contains(pin);
    }




   public boolean removePin(@NonNull Pin pin , IntegratedCircuitParser parser){
        LinkPinElement element=parser.getLinkPin(pin);
        if(element!=null) {
            pins.remove(element);
            PinList.remove(pin);
            if(pinListAdapter!=null) {
                pinListAdapter.remove(element);
                pinListAdapter.notifyDataSetChanged();
            }
            else
                parser.removeLinkPin(element);


            return true;
        }

        return false;
   }



    public int removeAllPinFromGate(BasicGateView gateView , LogicProject logicProject){
        int count=0;
         for(Pin pin:gateView.pins){
             if(removePin(pin , logicProject.getIntegratedCircuitParser())) {
                 count++;
             }
         }
        for(Pin pin:gateView.opin){
            if(removePin(pin , logicProject.getIntegratedCircuitParser())) {
                count++;
            }
        }
        for(Pin pin:gateView.tpins){
            if(removePin(pin , logicProject.getIntegratedCircuitParser())) {
                count++;
            }
        }
        for(Pin pin:gateView.bpins){
            if(removePin(pin , logicProject.getIntegratedCircuitParser())) {
                count++;
            }
        }

        return count;
    }


    public void invadateItems(){

        //didnt work
        if(pinListAdapter!=null) {
            pinListAdapter.notifyDataSetChanged();
        }


    }


    private  void setPinName(Pin[] pin_array, List<LinkPinElement> list){
        if(pin_array.length != list.size())
            return;
        for(int i=0 ; i<pin_array.length;i++)
            pin_array[i].setPinName(list.get(i).getPinname());

    }
    private  void setPreviewGatePinNames(){
        if(gateView==null)
            return;
        setPinName(gateView.pins,integratedParser.getLeftPinElements());
        setPinName(gateView.opin,integratedParser.getRightPinElements());
        setPinName(gateView.tpins,integratedParser.getTopPinElements());
        setPinName(gateView.bpins,integratedParser.getBottomPinElements());
    }


}