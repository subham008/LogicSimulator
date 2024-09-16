package com.logic.logicsimulator.Gates;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;

import com.logic.logicsimulator.BasicGateView;
import com.logic.logicsimulator.DummyGates.BasicDummyGate;
import com.logic.logicsimulator.DummyGates.DummyGates;
import com.logic.logicsimulator.EditorLayout;
import com.logic.logicsimulator.parsers.GateElement;
import com.logic.logicsimulator.parsers.IntegratedCircuitParser;
import com.logic.logicsimulator.parsers.LogicProject;
import com.logic.logicsimulator.parsers.TextLabelElement;
import java.util.HashMap;
import java.util.Map;

public class GateParser {

    private  Map<String,Integer> classList;

    private static Context context;

    public static void setContext(@NonNull Context con){
        context=con;
    }
    public GateParser(){
        classList=new HashMap<>();
        classList.put(AndGateView.GATE_TYPE,1);
        classList.put(NandGateView.GATE_TYPE,2);
        classList.put(OrGateView.GATE_TYPE , 3);
        classList.put(BufferGateView.GATE_TYPE , 4);
        classList.put(FrequencyGeneratorView.GATE_TYPE , 5);
        classList.put(PushButton.GATE_TYPE , 6);
        classList.put(BulbGate.GATE_TYPE , 7);
        classList.put(SwitchView.GATE_TYPE , 8);
        classList.put(NorGateView.GATE_TYPE , 9);
        classList.put(XnorGateView.GATE_TYPE , 10);
        classList.put(XorGateView.GATE_TYPE , 11);
        classList.put(HighConstantView.GATE_TYPE , 12);
        classList.put(LowConstantView.GATE_TYPE , 13);
        classList.put(NotGateView.GATE_TYPE , 14);
        classList.put(MultiplexerGateView.GATE_TYPE, 15);
        classList.put(DemultiplexerGateView.GATE_TYPE, 16);
        classList.put(TextLabel.GATE_TYPE, 17);
        classList.put(ICGate.IC_GATE_NAME ,18);
        classList.put(JKlatch.GATE_TYPE,19);
        classList.put(SRlatch.GATE_TYPE ,20);
        classList.put(Tlatch.GATE_TYPE , 21);
        classList.put(Dlatch.GATE_TYPE , 22);
        classList.put(RGBlight.GATE_TYPE , 23);
        classList.put(SRFlipFlop.GATE_TYPE,24);
        classList.put(JKFlipFlop.GATE_TYPE , 25);
        classList.put(TFlipFlop.GATE_TYPE,26);
        classList.put(DFlipFlop.GATE_TYPE , 27);
        classList.put(ByteGate.GATE_TYPE,28);
        classList.put(RamGate.GATE_TYPE,29);
        classList.put(Segment7Display.GATE_TYPE,30);
        classList.put(Segment14Display.GATE_TYPE,31);
        classList.put(DotPixelDisplay.GATE_TYPE,32);
        classList.put(CounterGate.GATE_TYPE,33);
        classList.put(TestGate.GATE_TYPE , 100);
    }
    public  BasicGateView getGate(Context context, EditorLayout main_layout, String projectName , String type , int l, int r, int t, int b ){
        int gateType=classList.get(type);
        BasicGateView gate=null;
        switch (gateType){
            case 1://AndGate
                    if(l<=0)l=2;
                    gate=new AndGateView(context ,main_layout,l);
                break;

            case 2://NandGate
                    if(l<=0)l=2;
                    gate=new  NandGateView(context , main_layout,l);

                break;

            case 3:
                if(l<=0)l=2;
                gate= new OrGateView(context , main_layout,l);
                break;

            case 4:
                gate= new BufferGateView(context , main_layout);
                break;

            case 5:
                gate=new FrequencyGeneratorView(context,main_layout);
                break;

            case 6:
                gate=new PushButton(context,main_layout);
                break;

            case 7:
                gate=new BulbGate(context , main_layout);
                break;
            case 8:
                gate=new SwitchView(context,main_layout);
                break;
            case 9:
                if(l<=0)l=2;
                gate=new NorGateView(context,main_layout, l);
                break;
            case 10:
                gate=new XnorGateView(context,main_layout);
                break;
            case 11:
                gate=new XorGateView(context,main_layout);
                break;
            case 12:
                gate=new HighConstantView(context,main_layout);
                break;
            case 13:
                gate=new LowConstantView(context,main_layout);
                break;
            case 14:
                    gate=new NotGateView(context,main_layout,1);
                break;
            case 15:
                    if(l<=0)l=2;
                    gate=new MultiplexerGateView(context,main_layout,b);
                break;
            case 16:
                    if(l<=0)l=2;
                    gate=new DemultiplexerGateView(context,main_layout,b);
                break;
            case 18:
                //System.err.println("ProjectName is "+projectName);
                IntegratedCircuitParser parser= new IntegratedCircuitParser(projectName , context.getFilesDir());//opening as IC
                if(parser.isIC())
                  gate=new ICGate(context , main_layout , parser ) ;
                else
                   gate=null;
                break;

            case 19:
                gate=new JKlatch(context , main_layout);
                break;
            case 20:
                gate=new SRlatch(context , main_layout);
                break;
            case 21:
                gate=new Tlatch(context , main_layout);
                break;
            case 22:
                gate=new Dlatch(context  , main_layout );
                break;
            case 23:
                gate=new RGBlight(context , main_layout);
                break;
            case 24:
                gate=new SRFlipFlop(context , main_layout);
                break;
            case 25:
                gate=new JKFlipFlop(context , main_layout);
                break;
            case 26:
                gate=new TFlipFlop(context ,main_layout);
                break;
            case 27:
                gate=new DFlipFlop(context , main_layout);
                break;
            case 28:
                gate=new ByteGate(context , main_layout);
                break;
            case 29:
                gate=new RamGate(context , main_layout , b);
                break;
            case 30:
                gate=new Segment7Display(context , main_layout );
                break;
            case 31:
                gate=new Segment14Display(context , main_layout );
                break;
            case 32:
                gate=new DotPixelDisplay(context , main_layout );
                break;
            case 33:
                gate=new CounterGate(context,main_layout,b);
                break;
            case 100:
                gate=new TestGate(context,main_layout);
                break;
        }

        return    gate;
    }//end of getGate

    public BasicDummyGate getGate( String type , int l, int r, int t, int b  , String projectName){
        int gateType=classList.get(type);
        BasicDummyGate gate=null;

        switch (gateType){
            case 1://AndGate
                gate=new  DummyGates.AndGateView(l );
                break;

            case 2://NandGate
                gate=new DummyGates.NandGateView( l);
                break;

            case 3:
                gate= new DummyGates.OrGateView( l);
                break;

            case 4:
                gate=new DummyGates.BufferGateView();
                break;
            case 5:
                gate=new DummyGates.FrequencyGeneratorView();
                break;
            case 9:
                gate=new DummyGates.NorGateView( l);
                break;

            case 10:
                gate=new DummyGates.XnorGateView();
                break;
            case 11:
                gate=new DummyGates.XorGateView();
                break;

            case 12:
                gate=new DummyGates.HighConstantView();
                break;

            case 13:
                gate=new DummyGates.LowConstantView();
                break;

            case 14:
                gate=new DummyGates.NotGateView();
                break;

            case 15:
                gate=new DummyGates.MultiplexerGateView( l);
                break;

            case 16:
                gate=new DummyGates.DemultiplexerGateView(l);
                break;

          /* case 18:
                IntegratedCircuitParser parser= new IntegratedCircuitParser(projectName , context.getFilesDir());//opening as IC
                if(parser!=null)
                   gate=new DummyGates.ICGateDummy(parser);
                else
                    gate=null;
                break;
            */
            case 19:
                gate=new DummyGates.JKlatchGateView();
                break;

            case 20:
                gate=new DummyGates.SRlatchGateView();
                break;

            case 21:
                gate=new DummyGates.TlatchGateView();
                break;

            case 22:
                gate=new DummyGates.DlatchGateView();
                break;
            case 24:
                gate=new DummyGates.SRFlipFlop();
                break;
            case 25:
                gate=new DummyGates.JKFlipFlop();
                break;
            case 26:
                gate=new DummyGates.TFlipFlop();
                break;
            case 27:
                gate=new DummyGates.DFlipFlop();
                break;
            case 28:
                gate=new DummyGates.ByteGate();
                break;
            case 29:
                gate=new DummyGates.RamGate( b);
                break;
            case 33:
                gate=new DummyGates.CouneterDummyGate(b);
                break;
            case 100:
                gate=new DummyGates.TestGate( l);
                break;
        }


        return gate;
    }
    public boolean isTextLabel(String type){
        if(classList.get(type)==17)
            return true;
        return false;
    }


    public boolean isLogicGate(String type){
        return classList.get(type) != null;
    }

    public boolean isInputGate(String type){
        int type_number=classList.get(type);

        return type_number == 6 || type_number == 8;
    }

    public boolean isOutputGate(String type){
        int type_number=classList.get(type);


        return type_number == 7;
    }

public TextLabel createTextLabel(@NonNull  TextLabelElement element , @NonNull Context context , @NonNull EditorLayout layout){
        TextLabel label=new TextLabel(context , element.getText() , Integer.parseInt(element.getFontSize()) , Color.WHITE);
        label.setX( (float) Integer.parseInt(element.getXloc()));
        label.setY( (float) Integer.parseInt(element.getYloc()));
        label.setClickable(true);
        label.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            layout.setTextLabelFocus(label);
        }
    });
    return label;
}

    public int addGates(@NonNull LogicProject logicProject ,@NonNull EditorLayout layout , @NonNull Context context){
        int success_gates=0;
        int loop=0;
        System.out.println("Gate List size:"+logicProject.getGateElements().size());

        GateParser.setContext(context);

        for(GateElement element:logicProject.getGateElements()){
            loop++;

            try{
                 BasicGateView gateView=getGate(context ,
                         layout,element.getProjectName(),
                         element.getType(),element.getLeftPinsCount() ,
                         element.getRightPinsCount() ,
                         element.getTopPinsCount(),
                         element.getBottomPinsCount());


                 if(gateView!=null){
                     layout.addGate(gateView, element);
                     success_gates++;
                 }
                 else {
                     System.out.println("GateParser->Failed to create BasicGateView of  GateElement");
                     element.tostring();
                 }
            }
            catch (Exception e){
                System.out.println("GateParser->addGates->Error:"+e);
            }
        }// end of adding gates

        System.out.println("GATES SUCCESS ADDED :"+success_gates+" GATES FAILED TO ADD :"+Math.abs(loop-success_gates));


        //adding textLabel
        int  success_label=0;
             loop=0;
        System.out.println("Label List size:"+logicProject.getTextLabelElementList().size());
        for(TextLabelElement element:logicProject.getTextLabelElementList()){
            loop++;
             try{
                  TextLabel label=createTextLabel(element , context , layout);
                  layout.addTextLabelOnLayout(label, element);
                  label.setX( (float) Integer.parseInt(element.getXloc()));
                  label.setY( (float) Integer.parseInt(element.getYloc()));
                  success_label++;
             }
             catch (Exception e){
                 System.out.println("GateParser->Label adding->Error:"+e);
                 logicProject.removeTextLabel(element);
             }
        }

        System.out.println("LABEL SUCCESS ADDED :"+success_label+" GATES FAILED TO ADD :"+Math.abs(loop-success_label));


        int connection_count=layout.startMakingConnection();  layout.invalidate();
        System.out.println("STARTING MAKING CONNECTION ESTIMATED CONNECTION: "+layout.estimatedConnection()+"CONNECTION MADE SUCCESS : "+connection_count);


        return success_gates+success_label;
    }

}
