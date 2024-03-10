# AsTdEA
Architecture Smell and Technical Debt Evolution Analyzer

## About The Project
AsTdEA runs a modified version of Arcan to detect architecture smells and calculate technical debt in multiple versions of a project. Smells are tracked across versions. Many properties of smells and versions are calculated to enable statistical analyses.

## Installation
Download from [here](https://drive.google.com/uc?export=download&id=1NArqsJyah7NhCcYacjxoOnzcypt-On08) and unzip.

## Usage
1. For each version to analyse, provide a jar or a folder of jars in the project's subdirectory in the input directory. The name of each jar or folder must contain the respective version number after a dash. AsTdEA can automatically retrieve the correct order of versions. If the versioning of the project to analyse deviates from simple patterns, follow the instructions in 4.
2. For each project, provide two additional files with metadata: dates.csv and loc.csv. Both must contain one entry per version from the oldest to newest version. The former provides the release date of each version and the latter the number of lines of code (LOC) of each version. Here is an example for their layout:
```sh
date
2020-03-24
2020-08-01
2021-01-04
```
```sh
loc
32626
37805
47216
```
3. Optional: Provide an addition file with metadata: versions.csv in a similar format to the aforementioned two files. Provide the version number of each version, which must resemble the corresponding jar or folder name. Example:
```sh
date
yourProjectName-1.1
yourProjectName-1.2
yourProjectName-1.3
```
4. Open a terminal in the unzipped folder and run the following:
```sh
java -jar AsTdEA.jar
```
5. After finishing the analysis, results are provided in an output directory as csv files.

## Other Source Code
The source code of the modified version of Arcan is available [here](https://github.com/PhilippGnoyke/arcan-1.2.1-modded).

## Studies
The listed studies employed AsTdEA. You can take examples from them and their replication packages.
- [An Evolutionary Analysis of Software-Architecture Smells](https://ieeexplore.ieee.org/abstract/document/9609226) (&#8594; [replication package](https://figshare.com/s/fa17e81cf4f27c84d059))
- [On Developing and Improving Tools for Architecture-Smell Tracking in Java Systems](https://ieeexplore.ieee.org/abstract/document/10356402)
- Evolution Patterns of Software-Architecture Smells: An Empirical Study of Intra- and Inter-Version Smells (in review) (&#8594; [replication package](https://tinyurl.com/ArchSmellsEvoJSS))

## Output
### Structure

The generated file structure looks like this:
```sh
└───out
    ├───system0
    │   ├───interVersion
    │   │   │   ClassCDsComponents.csv
    │   │   │   ClassCDsEdges.csv
    │   │   │   ClassCDsProperties.csv
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
    ├───system1
    └───system2
    └───...
```
The default output folder is "out". Underneath, you can find a folder per system that was analyzed. One level down, we differentiate between intra-version and inter-version data.

Within the inter-version data, several .csv files are generated along with folders for merges, splits, and transitions - both on the class-level and package-level. Inside of these, a .csv file is generated per inter-version cyclic dependency that contains merges, splits, or transitions, respectively.

In the intra-version data, a folder per version is generated, counting up from 0. A mapping to the actual version designations is provided in VersionNames.csv. In every folder, you can find various .csv files and two folders: classCDEdges and packageCDEdges. They contain a .csv file per intra-version cyclic dependency.

### Metrics
In this section, we list and describe every column of every generated .csv file.

#### interVersion\ClassCDsComponents.csv<br>interVersion\PackageCDsComponents.csv
| Column index | Column name in the .csv                             | Column description                                                                                                                                                                                                                                                                                                                                                                   |
|--------------|-----------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0            | id                                                  | ID of the inter-version smell in this row (across all analyzed systems)                                                                                                                                                                                                                                                                                                              |
| 1            | intraVersionSmells(versionId.smellId-removalWeight) | List of all intra-version smell IDs in this inter-version smell, separated by commas. Each intra-version smell ID consists of its version ID and its smell ID within the version. Specifically for cyclic dependencies, the ID is followed by a hyphen and the removal weight, which is relevant for calculating the number of smell removals in cyclic-dependency evolution graphs. |

#### interVersion\HDsComponents.csv<br>interVersion\UDsComponents.csv
| Column index | Column name in the .csv               | Column description                                                                                                                                                                |
|--------------|---------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0            | id                                    | ID of the inter-version smell in this row (across all analyzed systems)                                                                                               |
| 1            | intraVersionSmells(versionId.smellId) | List of all intra-version smell IDs in this inter-version smell, separated by commas. Each intra-version smell ID consists of its version ID and its smell ID within the version. |

#### interVersion\ClassCDsEdges.csv<br>interVersion\PackageCDsEdges.csv
| Column index | Column name in the .csv | Column description                                                                                                                                                                                                                                                                                                            |
|--------------|-------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0            | id                      | ID of the inter-version smell in this row (across all analyzed systems)                                                                                                                                                                                                                                           |
| 1            | edges                   | List of all edges between adjacent intra-version cyclic dependencies in the inter-version cyclic dependency, seperated by commas. Each edge is represented by the intra-version smell ID of the outgoing vertex (version ID + smell ID in the version), an arrow "->", and the intra-version smell ID of the incoming vertex. |

#### interVersion\ClassCDsProperties.csv<br>interVersion\PackageCDsProperties.csv<br>interVersion\HDsProperties.csv (Column 0 – 10)<br>interVersion\UDsProperties.csv (Column 0 – 10)
| Column index | Column name in the .csv         | Data type      | Range  | Column description                                                                                                                |
|--------------|---------------------------------|----------------|--------|-----------------------------------------------------------------------------------------------------------------------------------|
| 0            | id                              | Integer        | [0..∞) | ID of the inter-version smell in this row (across all analyzed systems)                                               |
| 1            | versionOfIntroduction           | Integer        | [0..∞) | Version ID of the first appearance of this inter-version smell                                                                    |
| 2            | timeOfIntroduction              | Date           | [0..∞) | Date of the first appearance of this inter-version smell (YYYY-MM-DD)                                                             |
| 3            | versionOfRemoval                | Integer        | [1..∞) | Version ID of the first version this inter-version smell is not present in                                                        |
| 4            | timeOfRemoval                   | Date           | [1..∞) | Date of the first version this inter-version smell is not present in                                                              |
| 5            | lifeSpanInVersions              | Integer        | [1..∞) | Number of versions in which this inter-version smell was observed                                                                 |
| 6            | lifeSpanInDays                  | Integer        | [1..∞) | Number of days between the first and the last version in which this inter-version smell was observed                              |
| 7            | versionOrientedRelativeLifespan | Floating point | (0,1]  | Share of analyzed versions of this system in which this inter-version smell was observed                                          |
| 8            | timeOrientedRelativeLifespan    | Floating point | (0,1]  | Share of analyzed days of this system in which this inter-version smell was observed                                              |
| 9            | presentInFirstVersion           | Boolean        | [0..1] | Was this inter-version smell observed in the first analyzed version?                                                              |
| 10           | presentInLastVersion            | Boolean        | [0..1] | Was this inter-version smell observed in the last analyzed version?                                                               |
| 11           | familyOrder                     | Integer        | [1..∞) | Number of intra-version smells within this inter-version smell                                                                    |
| 12           | medianFamilyWidth               | Floating point | [1,∞)  | Median number of co-existing related intra-version smells in the same version within this inter-version smell                     |
| 13           | minFamilyWidth                  | Integer        | [1..∞) | Lowest number of co-existing related intra-version smells in the same version within this inter-version smell                     |
| 14           | maxFamilyWidth                  | Integer        | [1..∞) | Highest number of co-existing related intra-version smells in the same version within this inter-version smell                    |
| 15           | familySize                      | Integer        | [0..∞) | Number of transitions between intra-version smells in this inter-version smell                                                    |
| 16           | pureTransitions                 | Integer        | [0..∞) | Number of pure transitions between intra-version smells in this inter-version smell                                               |
| 17           | mergeOnlyTransitions            | Integer        | [0..∞) | Number of merge only transitions between intra-version smells in this inter-version smell                                         |
| 18           | splitOnlyTransitions            | Integer        | [0..∞) | Number of split only transitions between intra-version smells in this inter-version smell                                         |
| 19           | mergeAndSplitTransitions        | Integer        | [0..∞) | Number of transitions that are both a merge and split transition between intra-version smells in this inter-version smell         |
| 20           | mergesCount                     | Integer        | [0..∞) | Number of merges of intra-version smells in this inter-version smell                                                              |
| 21           | splitsCount                     | Integer        | [0..∞) | Number of splits of intra-version smells in this inter-version smell                                                              |
| 22           | simpleMergesShareUnweighted     | Floating point | [0,1]  | Share of simple merges (2&#8594;1) of intra-version smells in this inter-version smell among all merges                           |
| 23           | simpleSplitsShareUnweighted     | Floating point | [0,1]  | Share of simple splits (1&#8594;2) of intra-version smells in this inter-version smell among all splits                           |
| 24           | simpleMergesShareWeighted       | Floating point | [0,1]  | Share of transitions in simple merges (2&#8594;1) of intra-version smells in this inter-version smell among all merge transitions |
| 25           | simpleSplitsShareWeighted       | Floating point | [0,1]  | Share of transitions in simple splits (1&#8594;2) of intra-version smells in this inter-version smell among all split transitions |
| 26           | incomingEdgeCountLargestMerge   | Integer        | [0..∞) | Number of merge transitions in the largest merge in this inter-version smell                                                      |
| 27           | outgoingEdgeCountLargestSplit   | Integer        | [0..∞) | Number of split transitions in the largest split in this inter-version smell                                                      |

#### interVersion\ProjectMetrics.csv
| Column index | Column name in the .csv   | Data type | Range  | Column description                                                                     |
|--------------|---------------------------|-----------|--------|----------------------------------------------------------------------------------------|
| 0            | analysedVersions          | Integer   | [1..∞) | Number of analyzed versions                                                            |
| 1            | analysedTimeSpanInDays    | Integer   | [1..∞) | Number of days between the first and last analyzed versions                            |
| 2            | numOfIntraVersionSmells   | Integer   | [0..∞) | Number of detected intra-version smells across all versions                            |
| 3            | numOfIntraVersionClassCDs | Integer   | [0..∞) | Number of detected intra-version class-level cyclic dependencies across all versions   |
| 4            | numOfIntraVersionPackCDs  | Integer   | [0..∞) | Number of detected intra-version package-level cyclic dependencies across all versions |
| 5            | numOfIntraVersionHDs      | Integer   | [0..∞) | Number of detected intra-version hub-like dependencies across all versions             |
| 6            | numOfIntraVersionUDs      | Integer   | [0..∞) | Number of detected intra-version unstable dependencies across all versions             |
| 7            | numOfInterVersionSmells   | Integer   | [0..∞) | Number of detected inter-version smells                                                |
| 8            | numOfInterVersionClassCDs | Integer   | [0..∞) | Number of detected inter-version class-level cyclic dependencies                       |
| 9            | numOfInterVersionPackCDs  | Integer   | [0..∞) | Number of detected inter-version package-level cyclic dependencies                     |
| 10           | numOfInterVersionHDs      | Integer   | [0..∞) | Number of detected inter-version hub-like dependencies                                 |
| 11           | numOfInterVersionUDs      | Integer   | [0..∞) | Number of detected inter-version unstable dependencies                                 |

#### interVersion\ClassCdsMerges\\&lt;Inter-version smell ID&gt;.csv<br>interVersion\PackageCdsMerges\\&lt;Inter-version smell ID&gt;.csv
| Column index | Column name in the .csv | Data type             | Range  | Column description                                                                                                                     |
|--------------|-------------------------|-----------------------|--------|----------------------------------------------------------------------------------------------------------------------------------------|
| 0            | sourceVersion           | Integer               | [0..∞) | ID of the version before the merge occurred                                                                                            |
| 1            | targetVersion           | Integer               | [1..∞) | ID of the version after the merge occurred                                                                                             |
| 2            | targetIntraVersionCd    | Integer.Integer       |        | Intra-version smell ID of the merge result (version ID and smell ID within the version)                                                |
| 3            | incomingEdgesCount      | Integer               | [2..∞) | Number of merge transitions, i.e. number of intra-version smells that were merged                                                      |
| 4            | overallOrderBefore      | Integer               | [4..∞) | Sum of the order of all intra-version smells that were merged                                                                          |
| 5            | overallOrderAfter       | Integer               | [4..∞) | Order of the resulting merged intra-version smell                                                                                      |
| 6            | overallOrderChange      | Floating point        | (-1,∞) | Rate of change of the order                                                                                                            |
| 7            | overallSizeBefore       | Integer               | [4..∞) | Sum of the size of all intra-version smells that were merged                                                                           |
| 8            | overallSizeAfter        | Integer               | [4..∞) | Size of the resulting merged intra-version smell                                                                                       |
| 9            | overallSizeChange       | Floating point        | (-1,∞) | Rate of change of the size                                                                                                             |
| 10           | overallSubcyclesBefore  | Integer               | [2..∞) | Sum of the number of subcycles of all intra-version smells that were merged                                                            |
| 11           | overallSubcyclesAfter   | Integer               | [2..∞) | Number of subcycles of the resulting merged intra-version smell                                                                        |
| 12           | overallSubcyclesChange  | Floating point        | (-1,∞) | Rate of change of the number of subcycles                                                                                              |
| 13           | sourceIntraVersionCds   | List<Integer.Integer> |        | Intra-version smell IDs of all intra-version smells that were merged, separated by commas (version ID and smell ID within the version) |

#### interVersion\ClassCdsSplits\\&lt;Inter-version smell ID&gt;.csv<br>interVersion\PackageCdsSplits\\&lt;Inter-version smell ID&gt;.csv
| Column index | Column name in the .csv | Data type             | Range  | Column description                                                                                                                    |
|--------------|-------------------------|-----------------------|--------|---------------------------------------------------------------------------------------------------------------------------------------|
| 0            | sourceVersion           | Integer               | [0..∞) | ID of the version before the split occurred                                                                                           |
| 1            | targetVersion           | Integer               | [1..∞) | ID of the version after the split occurred                                                                                            |
| 2            | sourceIntraVersionCd    | Integer.Integer       |        | Intra-version smell ID of the split result (version ID and smell ID within the version)                                               |
| 3            | incomingEdgesCount      | Integer               | [2..∞) | Number of split transitions, i.e. number of intra-version smells that resulted from the split                                         |
| 4            | overallOrderBefore      | Integer               | [4..∞) | Order of the split intra-version smell                                                                                                |
| 5            | overallOrderAfter       | Integer               | [4..∞) | Sum of the order of all intra-version smells that resulted from the split                                                             |
| 6            | overallOrderChange      | Floating point        | (-1,∞) | Rate of change of the order                                                                                                           |
| 7            | overallSizeBefore       | Integer               | [4..∞) | Size of the split intra-version smell                                                                                                 |
| 8            | overallSizeAfter        | Integer               | [4..∞) | Sum of the size of all intra-version smells that resulted from the split                                                              |
| 9            | overallSizeChange       | Floating point        | (-1,∞) | Rate of change of the size                                                                                                            |
| 10           | overallSubcyclesBefore  | Integer               | [2..∞) | Number of subcycles of the split intra-version smell                                                                                  |
| 11           | overallSubcyclesAfter   | Integer               | [2..∞) | Sum of the number of subcycles of all intra-version smells that resulted from the split                                               |
| 12           | overallSubcyclesChange  | Floating point        | (-1,∞) | Rate of change of the number of subcycles                                                                                             |
| 13           | targetIntraVersionCds   | List<Integer.Integer> |        | Intra-version smell IDs of all intra-version smells that were split, separated by commas (version ID and smell ID within the version) |

#### interVersion\ClassCdsTransitions\\&lt;Inter-version smell ID&gt;.csv<br>interVersion\PackageCdsTransitions\\&lt;Inter-version smell ID&gt;.csv
| Column index | Column name in the .csv | Data type       | Range                                                              | Column description                                                                                                           |
|--------------|-------------------------|-----------------|--------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|
| 0            | sourceVersion           | Integer         | [0..∞)                                                             | ID of the version at the tail of the transition                                                                              |
| 1            | targetVersion           | Integer         | [1..∞)                                                             | ID of the version at the head of the transition                                                                              |
| 2            | sourceIntraVersionCd    | Integer.Integer |                                                                    | Intra-version smell ID (version ID and smell ID within the version) of the intra-version smell at the tail of the transition |
| 3            | targetIntraVersionCd    | Integer.Integer |                                                                    | Intra-version smell ID (version ID and smell ID within the version) of the intra-version smell at the head of the transition |
| 4            | transitionType          | String          | {pure, mergeOnly, splitOnly, mergeAndSplit}                        | Is this transition involved in a merge, split, both, or neither?                                                             |
| 5            | shapeSource             | String          | {tiny, chain, star, circle, clique, multiHub, semiClique, unknown} | Shape of the intra-version smell at the tail of the transition                                                               |
| 6            | shapeTarget             | String          | {tiny, chain, star, circle, clique, multiHub, semiClique, unknown} | Shape of the intra-version smell at the head of the transition                                                               |

