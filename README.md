# Explanation Generation Extension for Palladios Slingshot Simulator

Part of the masters thesis "Integrating Explanation Generation into the Palladio Tool Chain".  
Please check there for further documentation.

## Usage

With the default configuration:  
Follow the setup instructions below.  
Start an Eclipse application.  
Run a simulation in the target Eclipse instance.  
Switch to the Explanations console of the target Eclipse instance to view the outputs.  
Note: This console is created on first simulation start.

The default case definition file is located in the *analyzer* bundle.  
The *AbstractMABEXAnalyzer* currently points to it with *casesDefaultLocation*.  
The default explanation text file is located in the *interpreter* bundle.  
The *ConsoleBasedMABEXInterpreter* points to it with *explanationFileLocation*.  

The *ExplanationGeneration* class in the *core* bundle sets up the component and can be used to change/add interpreters, register new monitors or replace the analyzer/builder.

## Setup

Follow https://palladiosimulator.github.io/Palladio-Documentation-Slingshot/tutorial/  
Import the projects in this repository into Eclipse in the same way.  
Install the M2E - PDE Integration Eclipse plugin.  
Set the target platform located at *releng/org.palladiosimulator.addons.explanationgeneration.targetplatform*.  
This component introduces SnakeYaml as an additional dependency pulled via Maven compared to Slingshot at the time of development.  
With the target platform set, Eclipse should show no errors anymore.

## Used Slingshot and SPD versions:

### Developed against git commits:

Palladio-Analyzer-Slingshot: commit 451d99ee95986a781691882ca1006e9b7b766704  
Palladio-Analyzer-Slingshot-Extension-Monitoring: commit 0cd78bfc2211255a3304d510aebd22ef41c95e09  
Palladio-Analyzer-Slingshot-Extension-PCM-Core: commit ece437320647aed428c5294aaac1fa5299ccd89a  

### Evaluated using git commits:

Palladio-Analyzer-Slingshot: commit ff6c9845b179a1d8e8c94076610273cf0fab124d  
Palladio-Analyzer-Slingshot-Extension-Monitoring: commit d04ff34e16dcd4b7646a3b24fa4d0d6253812b51  
Palladio-Analyzer-Slingshot-Extension-PCM-Core: commit 3b4de1454ae90691fc8e0c90f542fbdf52d73d1b  
Palladio-Addons-SPD-Metamodel: commit 7d50fef29dd847af98a5711089143fdacdd501c6  
Palladio-Analyzer-Slingshot-Extension-SPD-Interpreter: commit 0fd63641107fe0575a7357b6149ea30a63228113  

### Tested with git commits:

Palladio-Analyzer-Slingshot: commit 77584407a874151a3d38270344aad36ee79e1a6a  
Palladio-Analyzer-Slingshot-Extension-Monitoring: commit 199876e2d43c93b0d16a5132c0415be93b6fc175  
Palladio-Analyzer-Slingshot-Extension-PCM-Core: commit ee57a5fd447f3ec1c90879baeb49c7838a676bbf
