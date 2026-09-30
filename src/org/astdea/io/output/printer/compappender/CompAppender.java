package org.astdea.io.output.printer.compappender;

import org.astdea.data.smells.interversionsmells.InterVersionSmell;

public interface CompAppender<InterType extends InterVersionSmell>
{
    void appendComps(InterType smell, StringBuilder compNames);
}
