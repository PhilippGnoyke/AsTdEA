# AsTdEA
Architecture Smell and Technical Debt Evolution Analyzer

## About The Project
AsTdEA runs a modified version of Arcan to detect architecture smells and calculate technical debt in multiple versions of a project. Smells are tracked across versions. Many properties of smells and versions are calculated to enable statistical analyses. Among other changes, we optimized Arcan to a close-to-linear runtime complexity in relation to the input size (number of classes, packages, dependency edges).

## Installation
Download from [here](https://drive.google.com/uc?export=download&id=1AaFt1rw69C65n3UPqKcdswhXmGIS6bmg) and unzip.

Alternatively, download the [out/artifacts](https://github.com/PhilippGnoyke/AsTdEA/tree/master/out/artifacts/AsTdEA) folder from this repository and add metadata files according to the below documentation.
## Usage
### Overview
1. For each version to analyse, provide a jar or a folder of jars in the project's subdirectory in the input directory. The name of each jar or folder must contain the respective version number after a dash. AsTdEA can automatically retrieve the correct order of versions. If the versioning of the project to analyse deviates from simple patterns, follow the instructions in step 3.
2. For each project, provide two additional files with metadata: dates.csv and loc.csv. Both must contain one entry per version from the oldest to newest version. The former provides the release date of each version and the latter the number of lines of code (LOC) of each version. Here is an example for their layout:
```
date
2020-03-24
2020-08-01
2021-01-04
```
```
loc
32626
37805
47216
```
3. Optional: Provide an additional file with metadata: versions.csv in a similar format to the aforementioned two files. Provide the version number of each version, which must resemble the corresponding jar or folder name. Example:
```
date
yourProjectName-1.1
yourProjectName-1.2
yourProjectName-1.3
```
4. Optional: Provide an additional file with metadata for every version: SharedClasses.csv. You can whitelist classes in the binaries that represent the actual application to be analyzed, excluding third-party dependencies that were also compiled into the jar(s). Example structure:
```
fullyQualifiedName,srcPath
com.yourOrg.yourSystem.package.classname,yourSystemName\versions\yourSystemName-versionNumber\src\hereComesThePathWithInTheSrcRepo.java
```
AsTdEA currently does not use the srcPath field, but filling this field can yield additional information for further analyses.

5. Open a terminal in the unzipped folder and run the following:
```
java -jar AsTdEA.jar
```
6. After finishing the analysis, results are provided in an output directory as csv files.

### Arguments
By adding arguments after "java -jar AsTdEA.jar", you can customize AsTdEA:
```
-dontRunArcan, -noA
  Don't run Arcan but parse previously generated .csv files in the out folder.
  Default: false
-dontSuppressNonAsTdEvolution, -noSup
  Only output results of the modded features (TD, supercycle CDs, etc.)
  Default: false
-help, -h
  Print this help
  Default: false
-inputDir, -in
  Top level input dir. For each project to analyse, put a folder with the project's jars in it.
  Default: in
-outputDir, -out
  Top level output dir of results
  Default: out
```

### Input structure
We recommend that you arrange the input files in the following folder structure:
```
└───in
    ├───system1
    │   ├───stats
    │   │       dates.csv
    │   │       loc.csv
    │   │       versions.csv
    │   │
    │   └───versions
    │       ├───system0-version0
    │       │   ├───bin
    │       │   │       jarFile1.jar
    │       │   │       jarFile2.jar
    │       │   │       ...
    │       │   │
    │       │   └───stats
    │       │           SharedClasses.csv
    │       │
    │       ├───system0-version1
    │       ├───system0-version2
    │       └───system0-version3
    │       └───...
    ├───system2
    └───system3
    └───...
```

## Other Source Code
The source code of the modified version of Arcan is available [here](https://github.com/PhilippGnoyke/arcan-1.2.1-modded).

## Studies
The listed studies employed AsTdEA. You can take examples from them and their replication packages.
- [An Evolutionary Analysis of Software-Architecture Smells](https://ieeexplore.ieee.org/abstract/document/9609226) (&#8594; [replication package](https://figshare.com/s/fa17e81cf4f27c84d059))
- [On Developing and Improving Tools for Architecture-Smell Tracking in Java Systems](https://ieeexplore.ieee.org/abstract/document/10356402)
- [Evolution Patterns of Software-Architecture Smells: An Empirical Study of Intra- and Inter-Version Smells](https://www.sciencedirect.com/science/article/pii/S0164121224002152) (&#8594; [replication package](https://tinyurl.com/ArchSmellsEvoJSS))

## Output
### Structure

The generated file structure looks like this:
```
└───out
    ├───system1
    │   ├───interVersion
    │   │   │   ClassCDsComponents.csv
    │   │   │   ClassCDsEdges.csv
    │   │   │   ClassCDsProperties.csv
    │   │   │   ExTimeLogs.csv
    │   │   │   HDsComponents.csv
    │   │   │   HDsProperties.csv
    │   │   │   PackageCDsComponents.csv
    │   │   │   PackageCDsEdges.csv
    │   │   │   PackageCDsProperties.csv
    │   │   │   ProjectMetrics.csv
    │   │   │   UDsComponents.csv
    │   │   │   UDsProperties.csv
    │   │   │
    │   │   ├───ClassCdsMerges
    │   │   │       89.csv
    │   │   │       90.csv
    │   │   │       ...
    │   │   │
    │   │   ├───ClassCdsSplits
    │   │   │       89.csv
    │   │   │       90.csv
    │   │   │       ...
    │   │   │
    │   │   ├───ClassCdTransitions
    │   │   │       89.csv
    │   │   │       90.csv
    │   │   │       ...
    │   │   │
    │   │   ├───PackCdMerges
    │   │   │       181.csv
    │   │   │       182.csv
    │   │   │       ...
    │   │   │
    │   │   ├───PackCdSplits
    │   │   │       181.csv
    │   │   │       182.csv
    │   │   │       ...
    │   │   │
    │   │   └───PackCdTransitions
    │   │           181.csv
    │   │           182.csv
    │   │           ...
    │   │
    │   └───intraVersion
    │       │   VersionNames.csv
    │       │
    │       ├───0
    │       │   │   classCDmEFS.csv
    │       │   │   classCDmEFSWOTinys.csv
    │       │   │   ClassCDsComponents.csv
    │       │   │   ClassCDsProperties.csv
    │       │   │   ExTimeLogs.csv
    │       │   │   HDsComponents.csv
    │       │   │   HDsProperties.csv
    │       │   │   packageCDmEFS.csv
    │       │   │   packageCDmEFSWOTinys.csv
    │       │   │   PackageCDsComponents.csv
    │       │   │   PackageCDsProperties.csv
    │       │   │   ProjectMetrics.csv
    │       │   │   UDsComponents.csv
    │       │   │   UDsProperties.csv
    │       │   │
    │       │   ├───classCDEdges
    │       │   │       64021.csv
    │       │   │       64022.csv
    │       │   │       ...
    │       │   │
    │       │   └───packageCDEdges
    │       │           66613.csv
    │       │           66614.csv
    │       │           ...
    │       │
    │       ├───1
    │       ├───2
    │       └───3
    │       └───...
    ├───system2
    └───system3
    └───...
```
The default output folder is "out". Underneath, you can find a folder per system that was analyzed. One level down, we differentiate between intra-version and inter-version data.

Within the inter-version data, several .csv files are generated along with folders for merges, splits, and transitions - both on the class-level and package-level. Inside of these, a .csv file is generated per inter-version cyclic dependency that contains merges, splits, or transitions, respectively.

In the intra-version data, a folder per version is generated, counting up from 0. A mapping to the actual version designations is provided in VersionNames.csv. In every folder, you can find various .csv files and two folders: classCDEdges and packageCDEdges. They contain a .csv file per intra-version cyclic dependency.

### Metrics
In [Metrics.md](https://github.com/PhilippGnoyke/AsTdEA/blob/master/Metrics.md), we list and describe every column of every generated .csv file.
