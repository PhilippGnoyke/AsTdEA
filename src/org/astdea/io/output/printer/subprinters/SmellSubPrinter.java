package org.astdea.io.output.printer.subprinters;

import org.astdea.data.smells.interversionsmells.InterVersionSmell;

import java.util.Set;

public abstract class SmellSubPrinter<InterType extends InterVersionSmell>
{
    protected Set<InterType> inters;

    public SmellSubPrinter(Set<InterType> inters) {this.inters = inters;}
}
