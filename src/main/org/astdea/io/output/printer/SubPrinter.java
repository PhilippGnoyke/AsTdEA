package org.astdea.io.output.printer;

import org.astdea.data.Project;

abstract class SubPrinter
{
    protected Project project;

    public SubPrinter(Project project) {this.project = project;}
}
