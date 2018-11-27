# Analysis-and-Prediction-of-Energy-Consumption    
    Link to Dataset:https://www.kaggle.com/jeanmidev/smart-meters-in-london
Technologies/Tools Used: Spark,Tableau.

Algorithms Used: Kmeans, Bisecting Kmeans(Hierarchical Kmeans),Random Forest, ARIMA.

The project analyses the electricity consumption data of more than 2 years and forecasts the energy demand of households in a part of London city in UK. 
Using a smart meters data set from Kaggle, the project analysed the Seasonal consumption and effect of weather. 
It also provides insight into consumers categorization, and predict future consumption.

-Question1(Cluster the Data) in code folder, contains the model for clustering the consumers on their energy consumption pattern for this purpose we used Kmeans, Bisecting Kmeans(Hierarchical Kmeans) ML algorithms.
We applied Clustering on two levels: first was to clusters the blocks(each block contained a few hundred customers) for this we aggregated the consumption of consumers residing in each block,
secondly we aggregated the consumption of each user for the underlying time period  and passed these dataset to the clustering algo.
We formed 5 clusters i.e K=5 while clustering at block level and 4 clusters i.e k=4 while clustring at consumer level.

-Question2(Analysis of Data using ML Algos), we used diffirent ML algos for Predicting the future demand of energy in the region. As the data was time dependent our default choice was ARIMA model, after that we tried Randomforest.
ARIMA performed best in this scenario. 

-Question3(Finding correlation between engergy consumption pattern and weather condition), we aggregated the total energy consumed by all the customers per day and correlated it with various weather factors like temperature, pressure, wind speed etc.
we found that there exist a negative correlation i.e as the temperature decreases we see a shoot in consumption of electricity, but as the temperature increases there is a drop in electricity consumption.

-Additional Task: we had Bank Holiday data with us using that we tried to find if there was any relation between energy consumption on holidays and energy consumption on a working day, we concluded there was no relation.
    




Team Members:

Rochan Mehrotra (180240125037)

Tejas S. Lotankar (180240125024) 

Anup Kumar Jain (180240125006)

