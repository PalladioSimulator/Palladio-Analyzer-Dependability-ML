import csv
import sys
import seaborn as sns
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from collections import OrderedDict
import os
from os import listdir
from dictances import bhattacharyya, bhattacharyya_coefficient

csvDelimiter = ';'

accRamboModel = 0.0
accChauffeurModel = 0.0
accNVersionModel = 0.0
accPerfectSteeringModel = 1.0
accWorstSteeringModel = 0.0

def generateResults():
    barplotSensitivityModel(ramboSensModelFile)
    barplotReliabilityPredictions(ramboPcmRelFile)
    comparePredictionResultsAndSensModel(ramboSensModelFile, ramboPcmRelFile, 'Rambo')

    #TODO: For all remaining AI models repeat the three lines from above

    compareAccuracyAndPrediction()
    compareConditionalSuccessProbsOfAllModels()

def barplotSensitivityModel(sensModelFile):
    sensModel = loadSensitivityModelData(sensModelFile)

    plot = sns.catplot(x="Uncertainty", y="Probability of success", hue='HueHelper', palette = "Greys", legend = False, kind="bar", data=sensModel)
    plot.set(ylim=(0.92,0.95))
    plt.show()

def barplotReliabilityPredictions(pcmRelFile):
    predictions = loadReliabilityPredictionsData(pcmRelFile)

    plot = sns.catplot(x="Uncertainty", y="Conditional probability of success", hue='HueHelper', palette = "Greys", legend = False, kind="bar", data=predictions)
    plot.set(ylim=(0.91,0.93))
    plt.show()

def comparePredictionResultsAndSensModel(sensModelFile, pcmRelFile, modelName):
    sensModel = loadSensitivityModel(sensModelFile)
    predictions = loadReliabilityPredictions(pcmRelFile)

    rows = []
    
    header = ('Uncertainty', 'Con prob of success AI model', 'Con prob of success PCM-Rel', 'Diff', 'BC', 'D_B', 'adjD_B')
    rows.append(header)

    for each in sensModel.keys():
        aiSuccessProb = sensModel[each]
        predSuccessProb = predictions[each]

        aiDist = {'Succ':aiSuccessProb, 'Fail':1 - aiSuccessProb}
        predDist = {'Succ':predSuccessProb, 'Fail':1 - predSuccessProb}
        bc = bhattacharyya_coefficient(aiDist, predDist)
        d = bhattacharyya(aiDist, predDist)

        diff = abs(aiSuccessProb - predSuccessProb)

        adjPredSuccessProb = predSuccessProb + diff
        adjPredDist = {'Succ':adjPredSuccessProb, 'Fail':1 - adjPredSuccessProb}
        adj_d = bhattacharyya(aiDist, adjPredDist)

        row = (each, aiSuccessProb, predSuccessProb, diff, bc, d, adj_d)
        rows.append(row)

    fileToWrite = 'ComparisonPcmRelAndSensModel_' + modelName + '.csv'

    createResultTable(rows, fileToWrite)

def compareAccuracyAndPrediction():
    rows = []

    header = ('Model', 'Model accuracy', 'PCM-Rel success probability', 'Model success probability')
    rows.append(header)

    successWS = computeSuccessProbOfAIModel(wsSensModelFile, wsPcmRelFile)
    successPcmRelWS = retrievePcmRelSuccessProbability(wsPcmRelFile)
    wsRow = ('WorstSteering', accWorstSteeringModel, successPcmRelWS, successWS)
    rows.append(wsRow)

    #successChauffeur = computeSuccessProbOfAIModel(chauffeurSensModelFile, chauffeurPcmRelFile)
    #successPcmRelChauffeur = retrievePcmRelSuccessProbability(chauffeurPcmRelFile)
    #chauffeurRow = ('Chauffeur', accChauffeurModel, successPcmRelChauffeur, successChauffeur)
    #rows.append(chauffeurRow)

    successRambo = computeSuccessProbOfAIModel(ramboSensModelFile, ramboPcmRelFile)
    successPcmRelRambo = retrievePcmRelSuccessProbability(ramboPcmRelFile)
    ramboRow = ('Rambo', accRamboModel, successPcmRelRambo, successRambo)
    rows.append(ramboRow)

    #successNV = computeSuccessProbOfAIModel(nvSensModelFile, nvPcmRelFile)
    #successPcmRelNV = retrievePcmRelSuccessProbability(nvPcmRelFile)
    #nvRow = ('NVersion', accNVersionModel, successPcmRelNV, successNV)
    #rows.append(nvRow)

    successPS = computeSuccessProbOfAIModel(psSensModelFile, psPcmRelFile)
    successPcmRelNV = retrievePcmRelSuccessProbability(psPcmRelFile)
    psRow = ('PerfectSteering', accPerfectSteeringModel, successPcmRelNV, successPS)
    rows.append(psRow)

    createResultTable(rows, 'ComparisonAccuracyAndPrediction.csv')


def compareConditionalSuccessProbsOfAllModels():
    rows = []

    header = ('Uncertainties',
              'Con success prop perfect steering', 'Con success prop perfect steering PCM-Rel', 
              'Con success prop NVersion', 'Con success prop NVersion PCM-Rel',
              'Con success prop Rambo_Filter', 'Con success prop Rambo_Filter PCM-Rel',
              'Con success prop Rambo', 'Con success prop Rambo PCM-Rel',
              'Con success prop Chauffeur_Filter', 'Con success prop Chauffeur_Filter PCM-Rel',
              'Con success prop Chauffeur', 'Con success prop Chauffeur PCM-Rel',
              'Con success prop worst steering', 'Con success prop worst steering PCM-Rel')
    rows.append(header)    

    uncertainties = loadUncertaintyDist(psPcmRelFile).keys()
    psSensModel = loadSensitivityModel(psSensModelFile)
    psPcmRel = loadReliabilityPredictions(psPcmRelFile)
    #nvSensModel = loadSensitivityModel(nvSensModelFile)
    #nvPcmRel = loadReliabilityPredictions(nvPcmRelFile)
    #ramboFilterSensModel = loadSensitivityModel(ramboFilterSensModelFile)
    #ramboFilterPcmRel = loadReliabilityPredictions(ramboFilterPcmRelFile)
    ramboSensModel = loadSensitivityModel(ramboSensModelFile)
    ramboPcmRel = loadReliabilityPredictions(ramboPcmRelFile)
    #chauffeurFilterSensModel = loadSensitivityModel(chauffeurFilterSensModelFile)
    #chauffeurFilterPcmRel = loadReliabilityPredictions(chauffeurFilterPcmRelFile)
    #chauffeurSensModel = loadSensitivityModel(chauffeurSensModelFile)
    #chauffeurPcmRel = loadReliabilityPredictions(chauffeurPcmRelFile)
    wsSensModel = loadSensitivityModel(wsSensModelFile)
    wsPcmRel = loadReliabilityPredictions(wsPcmRelFile)

    for each in uncertainties:
        psSuccess = psSensModel[each]
        psSuccessPcmRel = psPcmRel[each]

        nvSuccess = 0.0#nvSensModel[each]
        nvSuccessPcmRel = 0.0#nvPcmRel[each]

        ramboFilterSuccess = 0.0#ramboFilterSensModel[each]
        ramboFilterSuccessPcmRel = 0.0#ramboFilterPcmRel[each]

        ramboSuccess = ramboSensModel[each]
        ramboSuccessPcmRel = ramboPcmRel[each]

        chauffeurFilterSuccess = 0.0#chauffeurFilterSensModel[each]
        chauffeurFilterSuccessPcmRel = 0.0#chauffeurFilterPcmRel[each]

        chauffeurSuccess = 0.0#chauffeurSensModel[each]
        chauffeurSuccessPcmRel = 0.0#chauffeurPcmRel[each]

        wsSuccess = wsSensModel[each]
        wsSuccessPcmRel = wsPcmRel[each]

        rows.append((each, 
                     psSuccess, psSuccessPcmRel, 
                     nvSuccess, nvSuccessPcmRel,
                     ramboFilterSuccess, ramboFilterSuccessPcmRel,
                     ramboSuccess, ramboSuccessPcmRel,
                     chauffeurFilterSuccess, chauffeurFilterSuccessPcmRel,
                     chauffeurSuccess, chauffeurSuccessPcmRel,
                     wsSuccess, wsSuccessPcmRel))

    createResultTable(rows, 'ComparisonConditionalSuccessProbsOfAllModels.csv')


def loadSensitivityModelData(sensModelFile):
    sensModel = loadSensitivityModel(sensModelFile)
    d = {'Uncertainty': sensModel.keys(), 'Probability of success': sensModel.values(), 'HueHelper': [1] * 6}
    return pd.DataFrame(data=d)

def loadSensitivityModel(sensModelFile):
    sensModel = {}
    with open(sensModelFile) as csv_file:
        rows = csv.reader(csv_file, delimiter=csvDelimiter)
        for each in rows:
            if each[0].startswith('Condition'):
                continue

            uncertainty = fromSensModelEntryToValueTuple(each[0])
            successProb = float(each[1])
            sensModel[uncertainty] = successProb

    return OrderedDict(sorted(sensModel.items()))

def loadReliabilityPredictionsData(pcmRelFile):
    predictions = loadReliabilityPredictions(pcmRelFile)
    d = {'Uncertainty': predictions.keys(), 'Conditional probability of success': predictions.values(), 'HueHelper': [1] * 6}
    return pd.DataFrame(data=d)

def loadReliabilityPredictions(pcmRelFile):
    predictions = {}
    with open(pcmRelFile) as csv_file:
        rows = csv.reader(csv_file, delimiter=csvDelimiter)
        for each in rows:
            if each[0].startswith('Uncertainty'):
                continue

            uncertainty = fromRelPredictionEntryToValueTuple(each[0])
            conSuccessProb = float(each[3])
            predictions[uncertainty] = conSuccessProb

    return OrderedDict(sorted(predictions.items()))

def loadUncertaintyDist(pcmRelFile):
    uncertaintyDist = {}
    with open(pcmRelFile) as csv_file:
        rows = csv.reader(csv_file, delimiter=csvDelimiter)
        for each in rows:
            if each[0].startswith('Uncertainty'):
                continue

            uncertainty = fromRelPredictionEntryToValueTuple(each[0])
            uncertaintyProb = float(each[5])
            uncertaintyDist[uncertainty] = uncertaintyProb

    return OrderedDict(sorted(uncertaintyDist.items()))

def retrievePcmRelSuccessProbability(pcmRelFile):
    with open(pcmRelFile) as csv_file:
        rows = csv.reader(csv_file, delimiter=csvDelimiter)
        for each in rows:
            if each[0].startswith('Uncertainty'):
                continue

            return float(each[1])

def computeSuccessProbOfAIModel(sensModelFile, pcmRelFile):
    sensModel = loadSensitivityModel(sensModelFile)
    uncertaintyDist = loadUncertaintyDist(pcmRelFile)

    success = 0.0
    for each in sensModel.keys():
        success += sensModel[each] * uncertaintyDist[each]
    
    return success

def createResultTable(rows, fileToWrite):
    if os.path.exists(fileToWrite) == False:
        open(fileToWrite, "x")

    rowsToWrite = []
    for eachRow in rows:
        rowToWrite = []
        for eachValue in eachRow:
            rowToWrite.append(str(eachValue))
        rowsToWrite.append(rowToWrite)

    with open(fileToWrite, 'w', newline='') as f:
        writer = csv.writer(f, delimiter=csvDelimiter)
        writer.writerows(rowsToWrite)

def fromSensModelEntryToValueTuple(uncertaintyEntry):
    return uncertaintyEntry.replace("Blurring: ", "(").replace(", Brightness: ",",") + ")"

def fromRelPredictionEntryToValueTuple(uncertaintyEntry):
    return uncertaintyEntry.replace("ImageBlurring=", "").replace("),(",",").replace("ImageBrightness=", "")

if __name__ == "__main__":
    base = os.getcwd()
    if len(sys.argv) == 2:
        base = str(sys.argv[1])

    for dir in listdir(base):
        if os.path.isfile(dir):
            continue

        modelDir = os.path.join(base, dir)
        if dir.startswith("NVersion"):
            for file in listdir(modelDir):
                if file.startswith('NVersionSensitivityModel'):
                    global nvSensModelFile
                    nvSensModelFile = os.path.join(modelDir, file)
                if file.startswith('NVersionReliabilityPredictionResults'):
                    global nvPcmRelFile
                    nvPcmRelFile = os.path.join(modelDir, file)
        if dir.startswith("Chauffeur"):
            for file in listdir(modelDir):
                if file.startswith('ChauffeurSensitivityModel'):
                    global chauffeurSensModelFile
                    chauffeurSensModelFile = os.path.join(modelDir, file)
                if file.startswith('ChauffeurReliabilityPredictionResults'):
                    global chauffeurPcmRelFile
                    chauffeurPcmRelFile = os.path.join(modelDir, file)
        if dir.startswith("Chauffeur_Filter"):
            for file in listdir(modelDir):
                if file.startswith('Chauffeur_FilterSensitivityModel'):
                    global chauffeurFilterSensModelFile
                    chauffeurFilterSensModelFile = os.path.join(modelDir, file)
                if file.startswith('Chauffeur_FilterReliabilityPredictionResults'):
                    global chauffeurFilterPcmRelFile
                    chauffeurFilterPcmRelFile = os.path.join(modelDir, file)
        if dir.startswith("Rambo"):
            for file in listdir(modelDir):
                if file.startswith('RamboSensitivityModel'):
                    global ramboSensModelFile
                    ramboSensModelFile = os.path.join(modelDir, file)
                if file.startswith('RamboReliabilityPredictionResults'):
                    global ramboPcmRelFile
                    ramboPcmRelFile = os.path.join(modelDir, file)
        if dir.startswith("Rambo_Filter"):
            for file in listdir(modelDir):
                if file.startswith('Rambo_FilterSensitivityModel'):
                    global ramboFilterSensModelFile
                    ramboFilterSensModelFile = os.path.join(modelDir, file)
                if file.startswith('Rambo_FilterReliabilityPredictionResults'):
                    global ramboFilterPcmRelFile
                    ramboFilterPcmRelFile = os.path.join(modelDir, file)
        if dir.startswith("PerfectSteering"):
            for file in listdir(modelDir):
                if file.startswith('PerfectSteeringSensitivityModel'):
                    global psSensModelFile
                    psSensModelFile = os.path.join(modelDir, file)
                if file.startswith('PerfectSteeringReliabilityPredictionResults'):
                    global psPcmRelFile
                    psPcmRelFile = os.path.join(modelDir, file)
        if dir.startswith("WorstSteering"):
            for file in listdir(modelDir):
                if file.startswith('WorstSteeringSensitivityModel'):
                    global wsSensModelFile
                    wsSensModelFile = os.path.join(modelDir, file)
                if file.startswith('WorstSteeringReliabilityPredictionResults'):
                    global wsPcmRelFile
                    wsPcmRelFile = os.path.join(modelDir, file)
    
    generateResults()