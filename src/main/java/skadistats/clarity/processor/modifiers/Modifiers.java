package skadistats.clarity.processor.modifiers;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import skadistats.clarity.event.Event;
import skadistats.clarity.event.InsertEvent;
import skadistats.clarity.event.Provides;
import skadistats.clarity.processor.stringtables.OnStringTableEntry;
import skadistats.clarity.wire.common.proto.DotaModifiers;

@Provides({OnModifierTableEntry.class})
public class Modifiers {

    @InsertEvent
    private Event<OnModifierTableEntry> evEntry;

    //EXTRACT PARAMETER OBJECT RESULTAAT
    @OnStringTableEntry("ActiveModifiers")
    public void onTableEntry(OntableEntryObject ontableEntryObject) throws InvalidProtocolBufferException {
        if (ontableEntryObject.getValue() != null) {
            DotaModifiers.CDOTAModifierBuffTableEntry message = DotaModifiers.CDOTAModifierBuffTableEntry.parseFrom(ontableEntryObject.getValue());
            evEntry.raise(message);
        }
    }

}
