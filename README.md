# Data Analysis Tools based on Intrinsic Time and Scaling Laws

This repository contains a set of data analysis tools mostly build on top of the Directional Change Intrinsic Time approach.
The majority of the tools equipped by comprehensive explanations and several examples.

<h3>The main folder contains several methods which could be directly used for data analysis and some special indicators:</h3>

<ul>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/LiquidityIndicator_Analysis.java">LiquidityIndicator_Analysis.java</a></strong> - computes liquidity of a real time series. It is a simple shell for the LiquidityIndicator class from the ievent package. It just needs a path to a file and several parameters.</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/InstantaneousVolatilityActivity_Analisys.java">InstantaneousVolatilityActivity_Analisys.java</a></strong> - this class is in essence a practical implementation of the "ievents/VolatilitySeasonality".</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/VolatilityEstimatorMovingWindow_Analysis.java">VolatilityEstimatorMovingWindow_Analysis.java</a></strong> - this class can be used to perform a complete analysis of a given price set</li>
</ul>


<h3>Short description of the methods included into the <em><a href="https://github.com/VladUZH/VlPetrov/tree/master/src/ievents">ievents</a></em> folder:</h3>

<ul>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/ievents/DcOS.java">DcOS.java</a></strong> - Directional Change dissection algorithm</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/ievents/IE.java">IE.java</a></strong> - an auxilarry class to hold information about each observed intrinsic event</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/ievents/IntrinsicNetwork.java">IntrinsicNetwork.java</a></strong> - is a realization of the Intrinsic Network introduced in the "Multi-scale Representation of High Frequency Market Liquidity" paper</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/ievents/LiquidityIndicator.java">LiquidityIndicator.java</a></strong> - is a realization of the "Multi-scale Representation of High Frequency Market Liquidity" paper working on real data</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/ievents/MovingWindowVolatilityEstimator.java">MovingWindowVolatilityEstimator.java</a></strong> - is a volatility estimator basen on the novel approach of the Directional Change Intrinsic Time</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/ievents/RealizedVolatility.java">RealizedVolatility.java</a></strong> - is practical realization of the theoretical work presented in the working paper "Bridging the gap between physical and intrinsic time" (volatility, computed using overshoot variability)</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/ievents/RealizedVolatilitySeasonality.java">RealizedVolatilitySeasonality.java</a></strong> - the class contains methods able to compute volatility seasonality based on the overshoot variability</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/ievents/InstantaneousVolatilitySeasonality.java">InatantaneousVolatilitySeasonality.java</a></strong> - the class contains methods able to compute volatility seasonality based on the number of directional change intrinsic events</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/ievents/InstantaneousVolatility.java">InstantaneousVolatility.java</a></strong> - is practical realization of the theoretical work presented in the paper "Waiting Times and Number of Directional Changes in an Intrinsic Time Framework" (<em>instantaneous</em> volatility, computed using number of observed directional changes)</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/ievents/DcOSmultD.java">DcOSmultiD.java</a></strong> - The multidimensional version of DcOS class. The description of the multidimensional approach is provided in the paper "Multidimensional Directional-Change Intrinsic Time Applied to FX data" (link soon)</li>
</ul>

<h3>Folder <em><a href="https://github.com/VladUZH/VlPetrov/tree/master/src/scalinglaws">scalinglaws</a></em> contains scaling laws from the "Patterns in high-frequency FX data: Discovery of 12 empirical scaling laws":</h3>
<ul>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/scalinglaws/MeanPriceMoveScalingLaw.java">MeanPriceMoveScalingLaw.java</a></strong> - "Mean price move scaling law", Law 0a, p=1</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/scalinglaws/QuadraticMeanPriceMoveScalingLaw.java">QuadraticMeanPriceMoveScalingLaw.java</a></strong> - "Quadratic mean price move scaling law", Law 0a, p=2</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/scalinglaws/DCcountScalingLaw.java">DCcountScalingLaw.java</a></strong> - "Number of Directional-Changes scaling law", Law 0b </li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/scalinglaws/PriceMoveCountScalingLaw.java">PriceMoveCountScalingLaw.java</a></strong> - "Price move count scaling law", Law 2</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/scalinglaws/MaxPriceMoveScalingLaw.java">MaxPriceMoveScalingLaw.java</a></strong> - "Maximal price move during scaling law", Law 3, p=1</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/scalinglaws/QuadraticMeanMaxPriceMoveScalingLaw.java">QuadraticMeanMaxPriceMoveScalingLaw.java</a></strong> - "Maximal price move during scaling law", Law 3, p=2</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/scalinglaws/MeanTimePriceMoveScalingLaw.java">MeanTimePriceMoveScalingLaw.java</a></strong> - "Mean time of price move scaling law", Law 4</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/scalinglaws/TimeDuringDCScalingLaw.java">TimeDuringDCScalingLaw.java</a></strong> - "Time during directional-change scaling law", Law 5</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/scalinglaws/TotalMoveScalingLaw.java">TotalMoveScalingLaw.java</a></strong> - "Total move scaling law", Law 9(tm)</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/scalinglaws/OSmoveScalingLaw.java">OSmoveScalingLaw.java</a></strong> - "Overshoot scaling law", Law 9(os)</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/scalinglaws/DCmoveScalingLaw.java">DCmoveScalingLaw.java</a></strong> - "Directional-change move scaling law", Law 9(dc)</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/scalinglaws/TimeTotMoveScalLaw.java">TimeTotMoveScalLaw.java</a></strong> - "Time total-move scaling law", Law 10(tm)</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/scalinglaws/TimeDCScalingLaw.java">TimeDCScalingLaw.java</a></strong> - "Time of directional change scaling law", Law 10(dc)</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/scalinglaws/TimeOSScalingLaw.java">TimeOSScalingLaw.java</a></strong> - "Time of overshoot scaling law", Law 10(os)</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/scalinglaws/TMtickCountScalingLaw.java">TMtickCountScalingLaw.java</a></strong> - "Total move tick count scaling law", Law 11(tm)</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/scalinglaws/DCtickCountScalingLaw.java">DCtickCountScalingLaw.java</a></strong> - "Directional change tick count scaling law", Law 11(dc)</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/scalinglaws/OStickCountScalingLaw.java">OStickCountScalingLaw.java</a></strong> - "Overshoot tick count scaling law", Law 11(os)</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/scalinglaws/CumulTMScalingLaw.java">CumulTMScalingLaw.java</a></strong> - "Cumulative total move scaling law" (coastline), Law 12(tm)</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/scalinglaws/CumulOSScalingLaw.java">CumulOSScalingLaw.java</a></strong> - "Cumulative overshoot scaling law", Law 12(os)</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/scalinglaws/CumulDCScalingLaw.java">CumulDCScalingLaw.java</a></strong> - "Cumulative directional change scaling law", Law 12(dc)</li>

</ul>

<h3>Folder <em><a href="https://github.com/VladUZH/VlPetrov/tree/master/src/market">market</a></em> contains:</h3>

<ul>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/market/Price.java">Price.java</a></strong> - is just a simple class for prices. Holds information about Bid, Ask, Time and some derivatives</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/market/SpreadInfo.java">SpreadInfo.java</a></strong> - computes Median, Mean, Min and Max spread of the given price time series</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/market/PriceMultiD.java">PriceMultiD.java</a></strong> - the multidimensional version of price. Sets of bids and asks are formed by individual components of several exchange rates</li>
</ul>

<h3>Folder <em><a href="https://github.com/VladUZH/VlPetrov/tree/master/src/tools">tools</a></em> (some additional traditional methods):</h3>

<ul>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/tools/BM.java">BM.java</a></strong> - this class uses Brownian Motion in order to generate a set of value with given number of elements.</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/tools/GBM.java">GBM.java</a></strong> - this class uses Geometrical Brownian Motion in order to generate a set of value with given number of elements.</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/tools/MovingWindowVolatilityEstimator.java">MovingWindowVolatilityEstimator.java</a></strong> - this class is an example of the classical volatility estimator based on the squared returns which could be used to run on the real data in real time</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/tools/Tools.java">Tools.java</a></strong> - holds a set of auxiliary functions which can be used in order to simplify work on the general ideas. The class is crucial for the stable work of almost all functions</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/tools/TraditionalVolatility.java">TraditionalVolatility.java</a></strong> - this class is traditional volatility estimator. Computes volatility of a given time series using squared returns</li>
<li><strong><a href="https://github.com/VladUZH/VlPetrov/blob/master/src/tools/ThetaTime.java">ThetaTime.java</a></strong> - realizes the concept of theta time described in the work of Docorogna et. al. 1993 "A geographical model for the daily and weekly seasonal volatility in the foreign exchange market".</li>
</ul>

<em>The project leading to this application has received funding from the European Union’s Horizon 2020 research and innovation programme under the Marie Skłodowska-Curie grant agreement No 675044</em>
