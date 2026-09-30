package org.astdea.data;

import org.astdea.data.smells.Level;
import org.astdea.data.versions.Component;
import org.astdea.io.IOUtils;
import org.astdea.io.input.CsvReadingUtils;
import org.astdea.io.input.IFN;
import org.astdea.io.input.IPN;

import java.io.IOException;
import java.util.*;

public class CompChanges {

    private Map<String, Map<String, Set<String>>> outgoingClassChanges;
    private Map<String, Map<String, Set<String>>> outgoingPackChanges;
    private Map<String, Map<String, Set<String>>> incomingClassChanges;
    private Map<String, Map<String, Set<String>>> incomingPackChanges;

    public CompChanges(String projectName, String compChangeDir) throws IOException {
        if (compChangeDir.isEmpty()) {
            return;
        }
        String classChangeFile = IOUtils.makeFilePath(compChangeDir, IFN.CLASS_CHANGES, projectName + ".csv");
        String packChangeFile = IOUtils.makeFilePath(compChangeDir, IFN.PACK_CHANGES, projectName + ".csv");
        outgoingClassChanges = new HashMap<>();
        outgoingPackChanges = new HashMap<>();
        incomingClassChanges = new HashMap<>();
        incomingPackChanges = new HashMap<>();

        fillChangeMap(classChangeFile, outgoingClassChanges, incomingClassChanges);
        fillChangeMap(packChangeFile, outgoingPackChanges, incomingPackChanges);
    }

    private void fillChangeMap(String file, Map<String, Map<String, Set<String>>> outgoingMap, Map<String, Map<String, Set<String>>> incomingMap) throws IOException {
        List<Map<String, String>> data = CsvReadingUtils.readCsvRows(file, new String[]{IPN.VERSION_OLD, IPN.FQCN_OLD, IPN.FQCN_NEW});
        for (Map<String, String> row : data) {
            String versionOld = row.get(IPN.VERSION_OLD);
            String versionNew = row.get(IPN.VERSION_NEW);
            String fqcnOld = row.get(IPN.FQCN_OLD);
            String fqcnNew = row.get(IPN.FQCN_NEW);
            outgoingMap.putIfAbsent(versionOld, new HashMap<>());
            incomingMap.putIfAbsent(versionNew, new HashMap<>());
            Map<String, Set<String>> versionMapOutgoing = outgoingMap.get(versionOld);
            Map<String, Set<String>> versionMapIncoming = incomingMap.get(versionNew);
            versionMapOutgoing.putIfAbsent(fqcnOld, new HashSet<>());
            versionMapOutgoing.get(fqcnOld).add(fqcnNew);
            versionMapIncoming.putIfAbsent(fqcnNew, new HashSet<>());
            versionMapIncoming.get(fqcnNew).add(fqcnOld);
        }
    }

    public Set<String> getSuccessorSet(Component component, Level level, String versionName) {
        Map<String, Map<String, Set<String>>> map = level == Level.CLASS ? outgoingClassChanges : outgoingPackChanges;
        return getSetCore(map, component, versionName);
    }

    public Set<String> getPredecessorSet(Component component, Level level, String versionName) {
        Map<String, Map<String, Set<String>>> map = level == Level.CLASS ? incomingClassChanges : incomingPackChanges;
        return getSetCore(map, component, versionName);
    }

    private Set<String> getSetCore(Map<String, Map<String, Set<String>>> map, Component component, String versionName) {
        if (!map.containsKey(versionName) || !map.get(versionName).containsKey(component.getFqcn())) {
            return null;
        }
        return map.get(versionName).get(component.getFqcn());
    }
}
