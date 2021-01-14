package org.astdea.data.smells.interversionsmells;

import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.io.output.OPN;
import org.astdea.utils.MathUtils;

import java.util.List;
import java.util.Set;

public class InterVersionCd extends InterVersionSmell<List<Set<IntraVersionCd>>>
{
    private int familyOrder;
    private double medianFamilyWidth;
    private int maxFamilyWidth = 0;

    @Override
    public List<Set<IntraVersionCd>> getIntraVersionSmells() {return intraVersionSmells;}

    public InterVersionCd(int versionOfIntroduction, List<Set<IntraVersionCd>> intraVersionSmells)
    {
        super(versionOfIntroduction, intraVersionSmells);

        double[] widths = new double[lifeSpanInVersions];
        for (int i = 0; i < lifeSpanInVersions; i++)
        {
            int width = intraVersionSmells.get(i).size();
            familyOrder += width;
            maxFamilyWidth = Math.max(maxFamilyWidth, width);
            widths[i] = width;
        }
        medianFamilyWidth = MathUtils.median(widths);
    }

    @Override
    public Object get(String fieldName)
    {
        return switch (fieldName)
            {
                case OPN.FAMILY_ORDER -> familyOrder;
                case OPN.MEDIAN_FAMILY_WIDTH -> medianFamilyWidth;
                case OPN.MAX_FAMILY_WIDTH -> maxFamilyWidth;
                default -> super.get(fieldName);
            };
    }
}
