package org.astdea.data.graphelements;


public enum TransitionType
{
    PURE("pure"),
    MERGE_ONLY("mergeOnly"),
    SPLIT_ONLY("splitOnly"),
    MERGE_AND_SPLIT("mergeAndSplit");

    private String text;
    public String get() { return this.text; }
    private TransitionType(String text) { this.text = text; }

    public static TransitionType getTransitionType(int oldCount, int newCount)
    {
        if (oldCount == 1 && newCount ==1) { return PURE; }
        else if (oldCount > 1 && newCount ==1) { return MERGE_ONLY; }
        else if (oldCount == 1 && newCount >1) { return SPLIT_ONLY; }
        else { return MERGE_AND_SPLIT; }
    }

}
