package skadistats.clarity.processor.modifiers;

import skadistats.clarity.model.StringTable;

public class OntableEntryObject {
    private final StringTable table;
    private final int index;
    private final String key;
    private final ByteString value;

    public OntableEntryObject(StringTable table, int index, String key, ByteString value) {
        this.table = table;
        this.index = index;
        this.key = key;
        this.value = value;
    }

    public StringTable getTable() {
        return table;
    }

    public int getIndex() {
        return index;
    }

    public String getKey() {
        return key;
    }

    public ByteString getValue() {
        return value;
    }
}
