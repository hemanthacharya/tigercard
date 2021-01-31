# tigercard app to determine the total cost of travel

To build - 

./gradlew clean build

To run - 

./gradlew run

It will run the main method in the TigerCardClient

Inputs : 

1. tariff.csv containing the following - 

zones,peak_hours,off_peak_hours,daily_cap,weekly_cap

2. travelData containing the following - 

Day,Time,FromZone,ToZone

Output :

outputs total cost in the below format

Total cost : #
