package com.logic.logicsimulator;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.logic.logicsimulator.Gates.AndGateView;
import com.logic.logicsimulator.Gates.BufferGateView;
import com.logic.logicsimulator.Gates.BulbGate;
import com.logic.logicsimulator.Gates.ByteGate;
import com.logic.logicsimulator.Gates.CounterGate;
import com.logic.logicsimulator.Gates.DFlipFlop;
import com.logic.logicsimulator.Gates.DemultiplexerGateView;
import com.logic.logicsimulator.Gates.Dlatch;
import com.logic.logicsimulator.Gates.DotPixelDisplay;
import com.logic.logicsimulator.Gates.FrequencyGeneratorView;
import com.logic.logicsimulator.Gates.GateParser;
import com.logic.logicsimulator.Gates.HighConstantView;
import com.logic.logicsimulator.Gates.ICGate;
import com.logic.logicsimulator.Gates.JKFlipFlop;
import com.logic.logicsimulator.Gates.JKlatch;
import com.logic.logicsimulator.Gates.LowConstantView;
import com.logic.logicsimulator.Gates.MultiplexerGateView;
import com.logic.logicsimulator.Gates.NandGateView;
import com.logic.logicsimulator.Gates.NorGateView;
import com.logic.logicsimulator.Gates.NotGateView;
import com.logic.logicsimulator.Gates.OrGateView;
import com.logic.logicsimulator.Gates.PushButton;
import com.logic.logicsimulator.Gates.RGBlight;
import com.logic.logicsimulator.Gates.RamGate;
import com.logic.logicsimulator.Gates.SRFlipFlop;
import com.logic.logicsimulator.Gates.SRlatch;
import com.logic.logicsimulator.Gates.Segment14Display;
import com.logic.logicsimulator.Gates.Segment7Display;
import com.logic.logicsimulator.Gates.SwitchView;
import com.logic.logicsimulator.Gates.TFlipFlop;
import com.logic.logicsimulator.Gates.TextLabel;
import com.logic.logicsimulator.Gates.Tlatch;
import com.logic.logicsimulator.Gates.XnorGateView;
import com.logic.logicsimulator.Gates.XorGateView;
import com.logic.logicsimulator.parsers.IntegratedCircuitParser;
import com.logic.logicsimulator.parsers.circuitBuilder;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class GateBottomFragment extends BottomSheetDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditorLayout main_layout=null;
    String projectName="NO_NAME";
    public GateBottomFragment(EditorLayout editorLayout , String pname) {
        // Required empty public constructor
        this.main_layout=editorLayout;
        projectName=pname;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }


int pins_count=2;
    public  void  askPinsCount(PinTask task){
        AlertDialog alertDialog;
        View dialogView = getLayoutInflater().inflate(R.layout.make_changeable_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext() );
        alertDialogBuilder.setView(dialogView);

        // Create and show the AlertDialog
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tag=dialogView.findViewById(R.id.make_change_tag);
        tag.setText("Input pins");

        TextView preview=dialogView.findViewById(R.id.make_change_preview);
        preview.setText(String.valueOf(pins_count));

        ImageButton inc=dialogView.findViewById(R.id.make_change_add);
        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  pins_count++;
                   preview.setText(String.valueOf(pins_count));
            }
        });

        ImageButton dec=dialogView.findViewById(R.id.make_change_sub);
        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pins_count--;
                preview.setText(String.valueOf(pins_count));
            }
        });

        Button save=dialogView.findViewById(R.id.make_change_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  task.run();
                 alertDialog.dismiss();
            }
        });

    }



    int textsize=40; //default color
    public void addText(){
        AlertDialog alertDialog;
        View dialogView = getLayoutInflater().inflate(R.layout.text_label_add_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext() );
        alertDialogBuilder.setView(dialogView);

        // Create and show the AlertDialog
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView textSizePreview=alertDialog.findViewById(R.id.text_size_preview);
        SeekBar textSize=alertDialog.findViewById(R.id.text_size_seekbar);
        textSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textsize=progress;
                textSizePreview.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        textSize.setProgress(textsize);//setting default color on seekbar

        EditText editText=dialogView.findViewById(R.id.text_label_text_editor);

        Button save=dialogView.findViewById(R.id.text_label_add);
        save.setText("ok");
        save.setTextColor(getResources().getColor(R.color.white));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextLabel view=new TextLabel(getContext() , editText.getText().toString() , textSize.getProgress() , 0xffffffff );
                main_layout.addTextLabel(view);
                view.setX(getEditorCentreX()+200);
                view.setY(getEditorCentreY()+200);
                view.setClickable(true);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        main_layout.setTextLabelFocus(view);
                    }
                });
                alertDialog.dismiss();
            }
        });
    }


    public int getEditorCentreX(){

        return Math.abs((int)main_layout.getX());
    }
    public int getEditorCentreY(){

        return Math.abs((int)main_layout.getY());
    }




    private void addGate(String type, boolean ask_count  ,@NotNull GateParser parser){
        BasicGateView gate=null;

        if(ask_count){
            askPinsCount(new PinTask(){
                @Override
                public void run(){
                    BasicGateView gate = parser.getGate(getContext() , main_layout,projectName , type,pins_count,pins_count, pins_count,pins_count);
                    gate.setX(getEditorCentreX());
                    gate.setY(getEditorCentreY());
                    main_layout.addGate(gate);
                }
            } );
        }
        else {
            gate=parser.getGate(getContext() , main_layout ,"Project",type,2,2,2,2 );
        }
        if(gate==null)
            return;

        gate.setX(getEditorCentreX());
        gate.setY(getEditorCentreY());
        main_layout.addGate(gate);
        Toast.makeText(getContext(), gate.gateType+" added", Toast.LENGTH_SHORT).show();
    }



    private   View createBottomView(LayoutInflater inflater , ViewGroup container){
       View   v=inflater.inflate(R.layout.fragment_gate_bottom, container, false);

         GateParser gateParser=new GateParser();

         ImageButton and_button=v.findViewById(R.id.and_gate_button);
         and_button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                addGate(AndGateView.GATE_TYPE,false ,gateParser );
             }
         });

         ImageButton nand_gate_button=v.findViewById(R.id.nand_gate_button);
         nand_gate_button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 addGate(NandGateView.GATE_TYPE,false , gateParser);
             }
         });

         ImageButton or_gate_button=v.findViewById(R.id.or_gate_button);
         or_gate_button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                addGate(OrGateView.GATE_TYPE,false , gateParser);
             }
         });

         ImageButton nor_gate=v.findViewById(R.id.nor_gate_button);
         nor_gate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 addGate(NorGateView.GATE_TYPE,false , gateParser);
             }
         });
        ImageButton xnor_gate=v.findViewById(R.id.xnor_gate_button);
        xnor_gate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGate(XnorGateView.GATE_TYPE,false , gateParser);
            }
        });
        ImageButton xor_gate=v.findViewById(R.id.xor_gate_button);
        xor_gate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGate(XorGateView.GATE_TYPE,false , gateParser);
            }
        });

        ImageButton not_gate=v.findViewById(R.id.not_gate_button);
        not_gate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGate(NotGateView.GATE_TYPE,false, gateParser);
            }
        });
        ImageButton buffer_gate_button=v.findViewById(R.id.buffer_gate_button);
        buffer_gate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGate(BufferGateView.GATE_TYPE,false , gateParser);
            }
        });

        ImageButton push_button=v.findViewById(R.id.pushbutton_gate_button);
        push_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGate(PushButton.GATE_TYPE,false , gateParser);
            }
        });

        ImageButton switch_button=v.findViewById(R.id.togglebutton_gate_button);
        switch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGate(SwitchView.GATE_TYPE,false , gateParser);
            }
        });
        ImageButton bulb_gate=v.findViewById(R.id.light_bulb_button);
        bulb_gate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGate(BulbGate.GATE_TYPE,false , gateParser);
            }
        });

        ImageButton multiplexer=v.findViewById(R.id.multiplexer_button);
        multiplexer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGate(MultiplexerGateView.GATE_TYPE,true , gateParser);
            }
        });

        ImageButton demultiplexer=v.findViewById(R.id.demultiplexer_button);
        demultiplexer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGate(DemultiplexerGateView.GATE_TYPE,true , gateParser);
            }
        });


        ImageButton rgb_light=v.findViewById(R.id.rgb_light_button);
        rgb_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGate(RGBlight.GATE_TYPE,false , gateParser);
            }
        });

        ImageButton add_text=v.findViewById(R.id.add_text_button);
        add_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addText();
            }
        });

        ImageButton icButton=v.findViewById(R.id.ic_button);
        icButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showICListDailog();
            }
        });


       ImageButton srButton=v.findViewById(R.id.sr_latch_button);
       srButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               addGate(SRlatch.GATE_TYPE , false , gateParser);
           }
       });

       ImageButton tButton=v.findViewById(R.id.t_latch_button);
       tButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               addGate(Tlatch.GATE_TYPE , false , gateParser);
           }
       });

       ImageButton dButton=v.findViewById(R.id.d_latch_button);
       dButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               addGate(Dlatch.GATE_TYPE , false  , gateParser);
           }
       });

       ImageButton jkButton=v.findViewById(R.id.jk_latch_button);
       jkButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               addGate(JKlatch.GATE_TYPE ,false ,gateParser);
           }
       });

       ImageButton srFFButton=v.findViewById(R.id.sr_flip_flop);
       srFFButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               addGate(SRFlipFlop.GATE_TYPE , false , gateParser);
           }
       });

        ImageButton jkFFButton=v.findViewById(R.id.jk_flip_flop);
        jkFFButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGate(JKFlipFlop.GATE_TYPE , false , gateParser);
            }
        });
        ImageButton dFFbutton = v.findViewById(R.id.d_flip_flop);
        dFFbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGate(DFlipFlop.GATE_TYPE , false, gateParser);
            }
        });

        ImageButton tFFbutton=v.findViewById(R.id.t_flip_flop);
        tFFbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGate(TFlipFlop.GATE_TYPE , false , gateParser);
            }
        });


         // n button gates
        ImageButton frequency_gen=v.findViewById(R.id.freq_gen);
        frequency_gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addGate(FrequencyGeneratorView.GATE_TYPE,true , gateParser);
            }
        });

        ImageButton n_and_gate=v.findViewById(R.id.and_n_gate_button);
        n_and_gate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 addGate(AndGateView.GATE_TYPE,true , gateParser);
            }
        });

        ImageButton n_nand_gate=v.findViewById(R.id.nand_n_gate_button);
        n_nand_gate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGate(NandGateView.GATE_TYPE,true , gateParser);
            }
        });

        ImageButton n_or_gate_button=v.findViewById(R.id.or_n_gate_button);
        n_or_gate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addGate(OrGateView.GATE_TYPE,true , gateParser);
            }
        });
        ImageButton n_nor_gate=v.findViewById(R.id.nor_n_gate_button);
        n_nor_gate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGate(NorGateView.GATE_TYPE,true , gateParser);
            }
        });
        ImageButton n_not_gate=v.findViewById(R.id.not_n_gate_button);
        n_not_gate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGate(NotGateView.GATE_TYPE,true , gateParser);
            }
        });
        ImageButton high_const=v.findViewById(R.id.high_constant_button);
        high_const.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGate(HighConstantView.GATE_TYPE,false , gateParser);
            }
        });

        ImageButton low_const=v.findViewById(R.id.low_constant_button);
        low_const.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addGate(LowConstantView.GATE_TYPE,false , gateParser);
            }
        });

        ImageButton byteButton=v.findViewById(R.id.byte_button);
        byteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGate(ByteGate.GATE_TYPE ,false,gateParser);
            }
        });

        ImageButton ramButton=v.findViewById(R.id.ram_button);
        ramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGate(RamGate.GATE_TYPE ,true,gateParser);
            }
        });

        ImageButton sg7dis=v.findViewById(R.id.seg_7_display);
        sg7dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGate(Segment7Display.GATE_TYPE ,false,gateParser);
            }
        });

        ImageButton sg14dis=v.findViewById(R.id.seg_14_display);
        sg14dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGate(Segment14Display.GATE_TYPE ,false,gateParser);
            }
        });

        ImageButton counterButon=v.findViewById(R.id.counter_button);
        counterButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGate(CounterGate.GATE_TYPE,false,gateParser);
            }
        });

        //HIDING 14 SEGMENT DISPLAY , UNABLE TO D DRAW DIAGNOL LINES
        sg14dis.setVisibility(View.GONE);

        ImageButton dotMatdis=v.findViewById(R.id.dot_matrix_display);
        dotMatdis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGate(DotPixelDisplay.GATE_TYPE ,false,gateParser);
            }
        });

        return  v;
    }


    IntegratedListAdapter listAdapter=null;
    public void showICListDailog(){
        AlertDialog alertDialog;
        View dialogView = getLayoutInflater().inflate(R.layout.integrated_list_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext() );
        alertDialogBuilder.setView(dialogView);

        // Create and show the AlertDialog
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//setting dailog background as trasparent

        ListView listView=dialogView.findViewById(R.id.integrated_list_view);

        if(listAdapter==null) {
            listAdapter = new IntegratedListAdapter(getContext() , getICList());
        }

        LinearLayout NoItemView=dialogView.findViewById(R.id.integrated_no_item_text);


        listView.setAdapter(listAdapter);
        listView.setEmptyView(NoItemView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntegratedCircuitParser parser=listAdapter.getItem(position);
                ICGate gate=new ICGate(getContext() , main_layout , parser);
                gate.setX(getEditorCentreX());
                gate.setY(getEditorCentreY());
                main_layout.addIC(gate);
                alertDialog.dismiss();
            }
        });
    }


    public List<IntegratedCircuitParser> getICList(){
        File filesDir = getContext().getFilesDir();

        List<IntegratedCircuitParser> itemList=new ArrayList<>();

        try {
            if (filesDir.exists()) {
                File[] files = filesDir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if(file.getName().compareTo(projectName)==0)
                            continue;
                        Document document = circuitBuilder.openProject(file);
                        if(document!=null)
                        {

                           if(IntegratedCircuitParser.isIntegrated(document) )
                               itemList.add(new IntegratedCircuitParser(file.getName() , getContext().getFilesDir()));
                        }
                    }
                }
            }
        }
        catch (Exception e){
            System.err.println("GateBottomGrafhment-->getIcList->ERROR : "+e.toString());
        }

        return itemList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =createBottomView(inflater, container);
        return v;
    }

    public class PinTask{
        public void run(){
              System.err.println("PinTask : METHOD NOT OVERRIDED");
        }
    }
}