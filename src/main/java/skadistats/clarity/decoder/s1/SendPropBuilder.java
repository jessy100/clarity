package skadistats.clarity.decoder.s1;

//Resultaat van introduce builder
public class SendPropBuilder {
    private SendTable table;
    private SendProp template;
    private int type;
    private String varName;
    private int flags;
    private int priority;
    private String dtName;
    private int numElements;
    private float lowValue;
    private float highValue;
    private int numBits;

    public SendPropBuilder setTable(SendTable table) {
        this.table = table;
        return this;
    }

    public SendPropBuilder setTemplate(SendProp template) {
        this.template = template;
        return this;
    }

    public SendPropBuilder setType(int type) {
        this.type = type;
        return this;
    }

    public SendPropBuilder setVarName(String varName) {
        this.varName = varName;
        return this;
    }

    public SendPropBuilder setFlags(int flags) {
        this.flags = flags;
        return this;
    }

    public SendPropBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public SendPropBuilder setDtName(String dtName) {
        this.dtName = dtName;
        return this;
    }

    public SendPropBuilder setNumElements(int numElements) {
        this.numElements = numElements;
        return this;
    }

    public SendPropBuilder setLowValue(float lowValue) {
        this.lowValue = lowValue;
        return this;
    }

    public SendPropBuilder setHighValue(float highValue) {
        this.highValue = highValue;
        return this;
    }

    public SendPropBuilder setNumBits(int numBits) {
        this.numBits = numBits;
        return this;
    }

    public SendProp createSendProp() {
        return new SendProp(table, template, type, varName, flags, priority, dtName, numElements, lowValue, highValue, numBits);
    }
}