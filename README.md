# AsTdEA
Architecture Smell and Technical Debt Evolution Analyser

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
- Evolution Patterns of Software-Architecture Smells: An Empirical Study of Intra- and Inter-Version Smells (in review) (&#8594; [replication package](https://tinyurl.com/ArchSmellsEvoTOSEM))
