# Data Analysis Tools based on Intrinsic Time and Scaling Laws

This repository contains a set of data analysis tools mostly build on top of the Directional Change Intrinsic Time approach.
The majority of the tools equipped by comprehensive explanations and several examples.

<h3>The main folder contains several methods which could be directly used for data analysis and some special indicators:</h3>

<ul>
<li><strong>LiquidityIndicator_Analysis.java</strong> - computes liquidity of a real time series. It is a simple shell for the LiquidityIndicator class from the ievent package. It just needs a path to a file and several parameters.</li>
<li><strong>RealizedVolatilityActivity_Analisys.java</strong> - this class is in essence a practical implementation of the "ievents/VolatilitySeasonality".</li>
<li><strong>VolatilityEstimatorMovingWindow_Analysis.java</strong> - this class can be used to perform a complete analysis of a given price set</li>
</ul>


<h3>Short description of the methods included into the <em>ievents</em> folder:</h3>

<ul>
<li><strong>DcOS.java</strong> - Directional Change dissection algorithm</li>
<li><strong>IE.java</strong> - an auxilarry class to hold information about each observed intrinsic event</li>
<li><strong>IntrinsicNetwork.java</strong> - is a realization of the Intrinsic Network introduced in the "Multi-scale Representation of High Frequency Market Liquidity" paper</li>
<li><strong>LiquidityIndicator.java</strong> - is a realization of the "Multi-scale Representation of High Frequency Market Liquidity" paper working on real data</li>
<li><strong>MovingWindowVolatilityEstimator.java</strong> - is a volatility estimator basen on the novel approach of the Directional Change Intrinsic Time</li>
<li><strong>RealizedVolatility.java</strong> - is practical realization of the theoretical work presented in the working paper "Bridging the gap between physical and intrinsic time" (volatility, computed using overshoot variability)</li>
<li><strong>RealizedVolatilitySeasonality.java</strong> - the class contains methods able to compute volatility seasonality based on the overshoot variability</li>
<li><strong>InatantaneousVolatilitySeasonality.java</strong> - the class contains methods able to compute volatility seasonality based on the number of directional change intrinsic events</li>
<li><strong>InstantaneousVolatility.java</strong> - is practical realization of the theoretical work presented in the paper "Waiting Times and Number of Directional Changes in an Intrinsic Time Framework" (<em>instantaneous</em> volatility, computed using number of observed directional changes)</li>
</ul>

<h3>Folder <em>scalinglaws</em> contains scaling laws from the "Patterns in high-frequency FX data: Discovery of 12 empirical scaling laws":</h3>
<ul>
<li><strong>DCcountScalingLaw.java</strong> - Number of Directional-Changes scaling law, Law 0b </li>
<li><strong>OSmoveScalingLaw.java</strong> - this class builds the "Overshoot scaling law", Law 9</li>
<li><strong>TimeTotMoveScalLaw.java</strong> - this class builds the "Time total-move scaling law", Law 10</li>
<li><strong>MeanPriceMoveScalingLaw.java</strong> - this class builds the "Mean price move scaling law", Law 0a, p=1</li>
<li><strong>QuadraticMeanPriceMoveScalingLaw.java</strong> - this class builds the "Quadratic mean price move scaling law", Law 0a, p=2</li>
<li><strong>PriceMoveCountScalingLaw.java</strong> - this class builds the "Price move count scaling law", Law 2</li>
<li><strong>MaxPriceMoveScalingLaw.java</strong> - this class builds the "Maximal price move during scaling law", Law 3, p=1</li>
<li><strong>QuadraticMeanMaxPriceMoveScalingLaw.java</strong> - this class builds the "Maximal price move during scaling law", Law 3, p=2</li>
<li><strong>MeanTimePriceMoveScalingLaw.java</strong> - this class builds the "Mean time of price move scaling law", Law 4</li>

</ul>

<h3>Folder <em>market</em> contains:</h3>

<ul>
<li><strong>Price.java</strong> - is just a simple class for prices. Holds information about Bid, Ask, Time and some derivatives</li>
<li><strong>SpreadInfo.java</strong> - computes Median, Mean, Min and Max spread of the given price time series</li>
</ul>

<h3>Folder <em>tools</em> (some additional traditional methods):</h3>

<ul>
<li><strong>BM.java</strong> - this class uses Brownian Motion in order to generate a set of value with given number of elements.</li>
<li><strong>GBM.java</strong> - this class uses Geometrical Brownian Motion in order to generate a set of value with given number of elements.</li>
<li><strong>MovingWindowVolatilityEstimator.java</strong> - this class is an example of the classical volatility estimator based on the squared returns which could be used to run on the real data in real time</li>
<li><strong>Tools.java</strong> - holds a set of auxiliary functions which can be used in order to simplify work on the general ideas. The class is crucial for the stable work of almost all functions</li>
<li><strong>TraditionalVolatility.java</strong> - this class is traditional volatility estimator. Computes volatility of a given time series using squared returns</li>
<li><strong>ThetaTime.java</strong> - realizes the concept of theta time described in the work of Docorogna et. al. 1993 "A geographical model for the daily and weekly seasonal volatility in the foreign exchange market".</li>
</ul>

<em>The project leading to this application has received funding from the European Union’s Horizon 2020 research and innovation programme under the Marie Skłodowska-Curie grant agreement No 675044</em>
