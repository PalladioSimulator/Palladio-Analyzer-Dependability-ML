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

probOfSuccessLabel = r'$P(X_b=Success \mid X_{\varphi_{B}},X_{\varphi_{Bl}})$'
pcmRelSuccessLabel = r'$rel(M_{C_b} \mid \varphi_{B},\varphi_{Bl})$'

accRamboModel = 0.05682
accChauffeurModel = 0.05768
accNVersionModel = 0.04221
accPerfectSteeringModel = 1.0
accWorstSteeringModel = 0.0

def generateResults():
    comparePredictionResultsAndSensModel(wsSensModelFile, wsPcmRelFile, 'WorstPossible')

    comparePredictionResultsAndSensModel(chauffeurSensModelFile, chauffeurPcmRelFile, 'Chauffeur')
    comparePredictionResultsAndSensModel(chauffeurSensModelFile, chauffeurFilterPcmRelFile, 'Chauffeur_Filter')

    comparePredictionResultsAndSensModel(ramboSensModelFile, ramboPcmRelFile, 'Rambo')
    comparePredictionResultsAndSensModel(ramboSensModelFile, ramboFilterPcmRelFile, 'Rambo_Filter')

    comparePredictionResultsAndSensModel(nvSensModelFile, nvPcmRelFile, 'NVersion')

    comparePredictionResultsAndSensModel(psSensModelFile, psPcmRelFile, 'Perfect')
    if True:
        return

    comparePredictionResultsAndSensModel(wsSensModelFile, wsPcmRelFile, 'WorstPossible')

    barplotSensitivityModel(chauffeurSensModelFile)
    barplotReliabilityPredictions(chauffeurPcmRelFile)
    comparePredictionResultsAndSensModel(chauffeurSensModelFile, chauffeurPcmRelFile, 'Chauffeur')
    comparePredictionResultsAndSensModel(chauffeurSensModelFile, chauffeurFilterPcmRelFile, 'Chauffeur_Filter')

    barplotSensitivityModel(ramboSensModelFile)
    barplotReliabilityPredictions(ramboPcmRelFile)
    comparePredictionResultsAndSensModel(ramboSensModelFile, ramboPcmRelFile, 'Rambo')
    comparePredictionResultsAndSensModel(ramboSensModelFile, ramboFilterPcmRelFile, 'Rambo_Filter')

    barplotSensitivityModel(nvSensModelFile, customYLims=(0.94,0.965))
    barplotReliabilityPredictions(nvPcmRelFile, customYLims=(0.92,0.95))
    comparePredictionResultsAndSensModel(nvSensModelFile, nvPcmRelFile, 'NVersion')

    barplotSensitivityModel(psSensModelFile, customYLims=(0.97,1.0))
    barplotReliabilityPredictions(psPcmRelFile, customYLims=(0.96,0.99))
    comparePredictionResultsAndSensModel(psSensModelFile, psPcmRelFile, 'Perfect')

    compareAccuracyAndPrediction()
    compareConditionalSuccessProbsOfAllModels()

    lineplotSuccessProbabilities()

def barplotSensitivityModel(sensModelFile, customYLims=(0.92,0.95)):
    sensModel = loadSensitivityModelData(sensModelFile)

    plot = sns.catplot(x="Uncertainty", y=probOfSuccessLabel, hue='Uncertainty', palette = "deep", legend = False, kind="bar", dodge=False, data=sensModel)
    plot.set(ylim=customYLims)
    for ax in plot.axes.flat:
        for label in ax.get_xticklabels():
            label.set_rotation(35)
            label.set_fontsize(7)
    plt.show()

def barplotReliabilityPredictions(pcmRelFile, customYLims=(0.90,0.93)):
    predictions = loadReliabilityPredictionsData(pcmRelFile)

    plot = sns.catplot(x="Uncertainty", y=pcmRelSuccessLabel, hue='Uncertainty', palette = "deep", legend = False, kind="bar", dodge=False, data=predictions)
    plot.set(ylim=customYLims)
    for ax in plot.axes.flat:
        for label in ax.get_xticklabels():
            label.set_rotation(35)
            label.set_fontsize(7)
    plt.show()

def comparePredictionResultsAndSensModel(sensModelFile, pcmRelFile, modelName):
    sensModel = loadSensitivityModel(sensModelFile)
    predictions = loadReliabilityPredictions(pcmRelFile)
    uncertainties = loadUncertaintyDist(pcmRelFile)

    rows = []
    
    header = ('Uncertainty', 'Prob of Uncertainty', 'Con prob of success AI model', 'Con prob of success PCM-Rel', 'Diff', 'BC', 'D_B', 'adjD_B', 'k')
    rows.append(header)

    for each in uncertainties.keys():
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

        uncertaintyProb = uncertainties[each]

        if predSuccessProb == 0:
            k = 0
        else:
            k = predSuccessProb / aiSuccessProb

        row = (each, uncertaintyProb, aiSuccessProb, predSuccessProb, diff, bc, d, adj_d, k)
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

    successChauffeur = computeSuccessProbOfAIModel(chauffeurSensModelFile, chauffeurPcmRelFile)
    successPcmRelChauffeur = retrievePcmRelSuccessProbability(chauffeurPcmRelFile)
    chauffeurRow = ('Chauffeur', accChauffeurModel, successPcmRelChauffeur, successChauffeur)
    rows.append(chauffeurRow)

    successRambo = computeSuccessProbOfAIModel(ramboSensModelFile, ramboPcmRelFile)
    successPcmRelRambo = retrievePcmRelSuccessProbability(ramboPcmRelFile)
    ramboRow = ('Rambo', accRamboModel, successPcmRelRambo, successRambo)
    rows.append(ramboRow)

    successNV = computeSuccessProbOfAIModel(nvSensModelFile, nvPcmRelFile)
    successPcmRelNV = retrievePcmRelSuccessProbability(nvPcmRelFile)
    nvRow = ('NVersion', accNVersionModel, successPcmRelNV, successNV)
    rows.append(nvRow)

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
    nvSensModel = loadSensitivityModel(nvSensModelFile)
    nvPcmRel = loadReliabilityPredictions(nvPcmRelFile)
    ramboFilterPcmRel = loadReliabilityPredictions(ramboFilterPcmRelFile)
    ramboSensModel = loadSensitivityModel(ramboSensModelFile)
    ramboPcmRel = loadReliabilityPredictions(ramboPcmRelFile)
    chauffeurFilterPcmRel = loadReliabilityPredictions(chauffeurFilterPcmRelFile)
    chauffeurSensModel = loadSensitivityModel(chauffeurSensModelFile)
    chauffeurPcmRel = loadReliabilityPredictions(chauffeurPcmRelFile)
    wsSensModel = loadSensitivityModel(wsSensModelFile)
    wsPcmRel = loadReliabilityPredictions(wsPcmRelFile)

    for each in uncertainties:
        psSuccess = psSensModel[each]
        psSuccessPcmRel = psPcmRel[each]

        nvSuccess = nvSensModel[each]
        nvSuccessPcmRel = nvPcmRel[each]

        ramboFilterSuccess = ramboSensModel[each]
        ramboFilterSuccessPcmRel = ramboFilterPcmRel[each]

        ramboSuccess = ramboSensModel[each]
        ramboSuccessPcmRel = ramboPcmRel[each]

        chauffeurFilterSuccess = chauffeurSensModel[each]
        chauffeurFilterSuccessPcmRel = chauffeurFilterPcmRel[each]

        chauffeurSuccess = chauffeurSensModel[each]
        chauffeurSuccessPcmRel = chauffeurPcmRel[each]

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

def lineplotSuccessProbabilities():
    uncertainties = loadUncertaintyDist(psPcmRelFile).keys()
    psSensModel = loadSensitivityModel(psSensModelFile)
    psPcmRel = loadReliabilityPredictions(psPcmRelFile)
    nvSensModel = loadSensitivityModel(nvSensModelFile)
    nvPcmRel = loadReliabilityPredictions(nvPcmRelFile)
    ramboFilterPcmRel = loadReliabilityPredictions(ramboFilterPcmRelFile)
    ramboSensModel = loadSensitivityModel(ramboSensModelFile)
    ramboPcmRel = loadReliabilityPredictions(ramboPcmRelFile)
    chauffeurFilterPcmRel = loadReliabilityPredictions(chauffeurFilterPcmRelFile)
    chauffeurSensModel = loadSensitivityModel(chauffeurSensModelFile)
    chauffeurPcmRel = loadReliabilityPredictions(chauffeurPcmRelFile)
    wsSensModel = loadSensitivityModel(wsSensModelFile)
    wsPcmRel = loadReliabilityPredictions(wsPcmRelFile)

    expandedModels = []
    successProbsSens = []
    successProbsPcmRel = []
    expandedUncertainties = []
    for each in uncertainties:
        expanded = [each] * 7
        expandedUncertainties.extend(expanded)

        psSuccess = psSensModel[each]
        successProbsSens.append(psSuccess)
        psSuccessPcmRel = psPcmRel[each]
        successProbsPcmRel.append(psSuccessPcmRel)
        expandedModels.append(r'$b^+$')

        nvSuccess = nvSensModel[each]
        successProbsSens.append(nvSuccess)
        nvSuccessPcmRel = nvPcmRel[each]
        successProbsPcmRel.append(nvSuccessPcmRel)
        expandedModels.append(r'$b_{NV}$')

        ramboFilterSuccess = ramboSensModel[each]
        successProbsSens.append(ramboFilterSuccess)
        ramboFilterSuccessPcmRel = ramboFilterPcmRel[each]
        successProbsPcmRel.append(ramboFilterSuccessPcmRel)
        expandedModels.append(r'$b^f_R$')

        ramboSuccess = ramboSensModel[each]
        successProbsSens.append(ramboSuccess)
        ramboSuccessPcmRel = ramboPcmRel[each]
        successProbsPcmRel.append(ramboSuccessPcmRel)
        expandedModels.append(r'$b_R$')

        chauffeurFilterSuccess = chauffeurSensModel[each]
        successProbsSens.append(chauffeurFilterSuccess)
        chauffeurFilterSuccessPcmRel = chauffeurFilterPcmRel[each]
        successProbsPcmRel.append(chauffeurFilterSuccessPcmRel)
        expandedModels.append(r'$b^f_C$')

        chauffeurSuccess = chauffeurSensModel[each]
        successProbsSens.append(chauffeurSuccess)
        chauffeurSuccessPcmRel = chauffeurPcmRel[each]
        successProbsPcmRel.append(chauffeurSuccessPcmRel)
        expandedModels.append(r'$b_C$')


        wsSuccess = wsSensModel[each]
        successProbsSens.append(wsSuccess)
        wsSuccessPcmRel = wsPcmRel[each]
        successProbsPcmRel.append(wsSuccessPcmRel)
        expandedModels.append(r'$b^-$')

    d = {'Uncertainty': expandedUncertainties, probOfSuccessLabel: successProbsSens, pcmRelSuccessLabel: successProbsPcmRel, 'Steering angle prediction models': expandedModels}

    plot = sns.lineplot(data=d, x="Steering angle prediction models", y=probOfSuccessLabel, hue="Uncertainty", palette ="deep")
    plot.set(ylim=(0.9,1.01))
    plot.set_yticks(np.arange(0.9, 1.025, 0.025).tolist())
    plt.show()

    plot = sns.lineplot(data=d, x="Steering angle prediction models", y=pcmRelSuccessLabel, hue="Uncertainty", palette ="deep")
    plot.set(ylim=(0.9,1.01))
    plot.set_yticks(np.arange(0.9, 1.025, 0.025).tolist())
    plt.show()


def loadSensitivityModelData(sensModelFile):
    sensModel = loadSensitivityModel(sensModelFile)
    d = {'Uncertainty': sensModel.keys(), probOfSuccessLabel: sensModel.values(), 'HueHelper': [1] * 6}
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
    d = {'Uncertainty': predictions.keys(), pcmRelSuccessLabel: predictions.values(), 'HueHelper': [1] * 6}
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
    values = uncertaintyEntry.split(',')
    if "ImageBlurring" in values[0]:
        blur = values[0].replace("ImageBlurring=", "").replace(")", "")
        brightness = values[1].replace("(ImageBrightness=", "")
    else:
        blur = values[1].replace("ImageBlurring=", "").replace(")", "")
        brightness = values[0].replace("(ImageBrightness=", "")
    return blur + "," + brightness

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