package skadistats.clarity.processor.sendtables;

import skadistats.clarity.decoder.Util;
import skadistats.clarity.decoder.s1.*;
import skadistats.clarity.event.Event;
import skadistats.clarity.event.Insert;
import skadistats.clarity.event.InsertEvent;
import skadistats.clarity.event.Provides;
import skadistats.clarity.model.DTClass;
import skadistats.clarity.model.EngineType;
import skadistats.clarity.model.s1.PropType;
import skadistats.clarity.processor.reader.OnMessage;
import skadistats.clarity.wire.common.proto.Demo;
import skadistats.clarity.wire.s1.proto.S1NetMessages;

import java.util.Iterator;
import java.util.LinkedList;

@Provides(value = {OnDTClass.class, OnDTClassesComplete.class}, engine = EngineType.SOURCE1)
public class S1DTClassEmitter {

    @Insert
    private DTClasses dtClasses;

    @InsertEvent
    private Event<OnDTClassesComplete> evClassesComplete;
    @InsertEvent
    private Event<OnDTClass> evDtClass;

    //Resultaat van make static
    @OnMessage(S1NetMessages.CSVCMsg_SendTable.class)
    public static void onSendTable(Event<OnDTClass> evDtClass, S1NetMessages.CSVCMsg_SendTable message) {

        LinkedList<SendProp> props = new LinkedList<SendProp>();
        SendTable st = new SendTable(
            message.getNetTableName(),
            props
        );

        for (S1NetMessages.CSVCMsg_SendTable.sendprop_t sp : message.getPropsList()) {
            props.add(
                    new SendPropBuilder().setTable(st).setTemplate(sp.getType() == PropType.ARRAY.ordinal() ? props.peekLast() : null).setType(sp.getType()).setVarName(sp.getVarName()).setFlags(sp.getFlags()).setPriority(sp.getPriority()).setDtName(sp.getDtName()).setNumElements(sp.getNumElements()).setLowValue(sp.getLowValue()).setHighValue(sp.getHighValue()).setNumBits(sp.getNumBits()).createSendProp()
            );
        }
        DTClass dtClass = S1DTClass.createS1DTClass(message.getNetTableName(), st);
        evDtClass.raise(dtClass);
    }

    @OnMessage(Demo.CDemoClassInfo.class)
    public void onClassInfo(Demo.CDemoClassInfo message) {
        for (Demo.CDemoClassInfo.class_t ct : message.getClassesList()) {
            //RESULTAAT VAN INLINE
            DTClass dt = dtClasses.byDtName.get(ct.getTableName());
            dt.setClassId(ct.getClassId());
            dtClasses.byClassId.put(ct.getClassId(), dt);
        }
        dtClasses.classBits = Util.calcBitsNeededFor(dtClasses.byClassId.size() - 1);
        evClassesComplete.raise();
    }

    @OnMessage(Demo.CDemoSyncTick.class)
    public void onSyncTick(Demo.CDemoSyncTick message) {
        Iterator<DTClass> iter = dtClasses.iterator();
        while (iter.hasNext()) {
            S1DTClass dtc = (S1DTClass) iter.next();
            SendTableFlattener.Result flattened = new SendTableFlattener(dtClasses, dtc.getSendTable()).flatten();
            dtc.setReceiveProps(flattened.receiveProps);
            dtc.setIndexMapping(flattened.indexMapping);
        }
        iter = dtClasses.iterator();
        while (iter.hasNext()) {
            S1DTClass dtc = (S1DTClass) iter.next();
            String superClassName = dtc.getSendTable().getBaseClass();
            if (superClassName != null) {
                dtc.setSuperClass((S1DTClass) dtClasses.byDtName.get(superClassName));
            }
        }
    }

}
