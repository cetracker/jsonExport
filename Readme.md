# Tour (Statistic) Data Export

This is a POC for a suggested feature for exporting (statistic) tour data from MT.

## Filtering

The idea is that it should be possible to reduce the exported data to a user selectable set of properties, 
in order to restrict the volume of the exported data.   
E.g. If the user isn't interested to further process the time series, this can be omitted from the export.

To make this filtering dynamic, the `TourData` objects are first converted to key, value map via serializing 
it to a JSON string and reduce this map afterwards. Since a lot of properties are annoted with `@JsonProperty`
anyway, these preselected properties will be easily available for filtering.  
An alternative would probably be to work with refection.

Please see the [TourData::toJsonObject](./src/main/java/org/tour/domain/TourData.java) method for the implementation.

## Repository

The dummy data is provided by a [repository](./src/main/java/org/tour/persistence/TourRepository.java) faking a database repository with a couple of methods for filtering
the raw dummy data.   

## Service

The final export is implemented within the [ExportService::doExport](./src/main/java/org/tour/export/ExportService.java)
method in which three sorts of exports are demonstrated.

a) Exporting all tours one by one, including all `@JsonProperty` annotated properties.  
b) Exporting all tours but filtering for a set of properties as a JSON array  
c) Exporting all tours, starting from a certain date as a JSON array  
d) Exporting all tours, starting from a certain date plus filtering on the specified `TourType`s as a JSON array

## Building and Running

The only prerequisite is an installed Java 17 on the machine. If it's not the main java version, it can be 
pointed to via `export JAVA_HOME=<root of JDK 17 installation>`. It might be running with Java11 also, if adapted 
within the `build.gradle.kts` file.   

```shell
git clone git@github.com:cetracker/jsonExport.git
cd jsonExport
./gradlew build
./gradlew run

```

### Expected Result

```json
# ------ Unfiltered List ---------
{
  "id" : 1,
  "tourType" : 1,
  "startYear" : 2020,
  "startMonth" : 2,
  "startDay" : 3,
  "title" : "Tour 1",
  "tourDistance" : 13000.0,
  "tourComputedTime_Moving" : 7800,
  "startPulse" : 55
}
{
  "id" : 2,
  "tourType" : 2,
  "startYear" : 2020,
  "startMonth" : 3,
  "startDay" : 4,
  "title" : "Tour 2",
  "tourDistance" : 94000.0,
  "tourComputedTime_Moving" : 9800,
  "startPulse" : 55
}
{
  "id" : 3,
  "tourType" : 1,
  "startYear" : 2021,
  "startMonth" : 5,
  "startDay" : 15,
  "title" : "Tour 3",
  "tourDistance" : 13000.0,
  "tourComputedTime_Moving" : 9800,
  "startPulse" : 55
}
{
  "id" : 4,
  "tourType" : 2,
  "startYear" : 2022,
  "startMonth" : 6,
  "startDay" : 14,
  "title" : "Tour 4",
  "tourDistance" : 78000.0,
  "tourComputedTime_Moving" : 10800,
  "startPulse" : 55
}
{
  "id" : 5,
  "tourType" : 3,
  "startYear" : 2022,
  "startMonth" : 7,
  "startDay" : 20,
  "title" : "Tour 5",
  "tourDistance" : 85000.0,
  "tourComputedTime_Moving" : 12800,
  "startPulse" : 55
}
# ------ Complete list with selected properties ---------
[ {
  "id" : 1,
  "title" : "Tour 1"
}, {
  "id" : 2,
  "title" : "Tour 2"
}, {
  "id" : 3,
  "title" : "Tour 3"
}, {
  "id" : 4,
  "title" : "Tour 4"
}, {
  "id" : 5,
  "title" : "Tour 5"
} ]
# ------ List filtered by date with selected properties ---------
[ {
  "tourDistance" : 13000.0,
  "startYear" : 2021,
  "id" : 3,
  "title" : "Tour 3"
}, {
  "tourDistance" : 78000.0,
  "startYear" : 2022,
  "id" : 4,
  "title" : "Tour 4"
}, {
  "tourDistance" : 85000.0,
  "startYear" : 2022,
  "id" : 5,
  "title" : "Tour 5"
} ]
# ------ List filtered by date and TourType with selected properties ---------
[ {
  "tourType" : 1,
  "tourDistance" : 13000.0,
  "startYear" : 2021,
  "id" : 3,
  "title" : "Tour 3"
}, {
  "tourType" : 2,
  "tourDistance" : 78000.0,
  "startYear" : 2022,
  "id" : 4,
  "title" : "Tour 4"
} ]

```
