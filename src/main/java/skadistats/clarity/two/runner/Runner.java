package skadistats.clarity.two.runner;

import skadistats.clarity.two.framework.annotation.Provides;
import skadistats.clarity.two.processor.reader.OnInputStream;
import skadistats.clarity.two.processor.stringtables.StringTables;

import java.io.InputStream;

@Provides({OnInputStream.class})
public class Runner {

    public void runWith(InputStream is, Object processor) {
        Context c = new Context();
        c.addProcessor(this);
        c.addProcessor(processor);
        c.addProcessor(new StringTables());
        c.initialize();
        c.raise(OnInputStream.class, is);
    }

}
