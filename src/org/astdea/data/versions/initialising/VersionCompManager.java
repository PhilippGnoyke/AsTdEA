package org.astdea.data.versions.initialising;

import org.astdea.data.AffType;
import org.astdea.data.CompChanges;
import org.astdea.data.smells.Level;
import org.astdea.data.versions.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VersionCompManager {

    private final Map<String, Component> classes;
    private final Map<String, Component> packages;

    private final Map<AffType, Set<Component>> affectedByMap;

    public VersionCompManager() {
        this.classes = new HashMap<>();
        this.packages = new HashMap<>();
        this.affectedByMap = new HashMap<>();
        for (AffType type : AffType.values()) {
            affectedByMap.put(type, new HashSet<>());
        }
    }

    public Set<Component> getAffected(AffType affType) {
        return affectedByMap.get(affType);
    }

    public boolean isFqcnAffected(AffType affType, String fqcn) {
        Map<String, Component> map = getCompMap(affType);
        return map.containsKey(fqcn);
    }

    private Map<String, Component> getCompMap(AffType affType) {
        return AffType.isClassLevel(affType) ? classes : packages;
    }

    public Component addComponent(String fcqn, AffType affType) {
        Map<String, Component> map = getCompMap(affType);
        map.putIfAbsent(fcqn, new Component(fcqn));
        Component component = map.get(fcqn);
        affectedByMap.get(affType).add(component);
        return component;
    }

    public Set<Component> addComponents(Set<String> fcqns, AffType affType) {
        Set<Component> comps = new HashSet<>();
        for (String fcqn : fcqns) {
            comps.add(addComponent(fcqn, affType));
        }
        return comps;
    }

    public void addCompChanges(CompChanges compChanges, String versionName) {
        addCompChangesCore(Level.CLASS, classes, compChanges, versionName);
        addCompChangesCore(Level.PACK, packages, compChanges, versionName);
    }

    private void addCompChangesCore(Level level, Map<String, Component> comps, CompChanges compChanges, String versionName) {
        for (String fqcn : comps.keySet()) {
            Component component = comps.get(fqcn);
            Set<String> successors = compChanges.getSuccessorSet(component, level, versionName);
            if (successors != null) {
                component.addSuccessors(successors);
            }
            Set<String> predecessors = compChanges.getPredecessorSet(component, level, versionName);
            if (predecessors != null) {
                component.addPredecessors(predecessors);
            }
        }
    }
}
