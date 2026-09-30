package org.astdea.data.smells.intraversionsmells;

import it.unimib.disco.essere.main.graphmanager.GraphBuilder;

public enum Shape
{
    TINY(GraphBuilder.TINY),
    CHAIN(GraphBuilder.CHAIN),
    STAR(GraphBuilder.STAR),
    CIRCLE(GraphBuilder.CIRCLE),
    CLIQUE(GraphBuilder.CLIQUE),
    MULTI_HUB(GraphBuilder.MULTI_HUB),
    SEMI_CLIQUE(GraphBuilder.SEMI_CLIQUE),
    UNKNOWN(GraphBuilder.UNKNOWN);

    private String text;

    public String get() { return this.text; }

    private Shape(String text) { this.text = text; }

    public static Shape parseString(String string)
    {
        return switch (string)
            {
                case GraphBuilder.TINY -> TINY;
                case GraphBuilder.CHAIN -> CHAIN;
                case GraphBuilder.STAR -> STAR;
                case GraphBuilder.CIRCLE -> CIRCLE;
                case GraphBuilder.CLIQUE -> CLIQUE;
                case GraphBuilder.MULTI_HUB -> MULTI_HUB;
                case GraphBuilder.SEMI_CLIQUE -> SEMI_CLIQUE;
                case GraphBuilder.UNKNOWN -> UNKNOWN;
                default -> null;
            };
    }
}
