package org.astdea.io.output.printer;

import org.apache.commons.csv.CSVPrinter;
import org.astdea.data.Project;

import java.io.IOException;

class ProjectMetricsPrinter implements PrinterCore
{
    private Project project;

    public ProjectMetricsPrinter(Project project) {this.project = project;}

    @Override
    public void print(String[] headers, CSVPrinter printer) throws IOException
    {
        for (String header : headers)
        {
            printer.print(project.get(header));
        }
        printer.println();
    }
}
