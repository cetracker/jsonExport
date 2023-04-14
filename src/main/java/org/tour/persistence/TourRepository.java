package org.tour.persistence;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.tour.domain.TourData;

/**
 * Dummy in memory repository.
 * Initially created on 4/14/23.
 * */
public class TourRepository {

  private final List<TourData> tours;
  public TourRepository() {
    tours = initializeSampleData();
  }
  public List<TourData> findAll() {
    // return a copy instead of reference!
    return tours.stream().toList();
  }
  public List<TourData> findAllByStartYearGreaterOrEqual(short startYear) {
    return tours.stream()
        .filter(tourData -> tourData.getStartYear() >= startYear)
        .toList();
  }
  public List<TourData> findAllByStartYearGreaterOrEqualAndTourtypeIsAnyOf(short startYear, Set<Short> tourTypes) {
    return tours.stream()
        .filter(tourData -> tourData.getStartYear() >= startYear && tourTypes.contains(tourData.getTourType()))
        .toList();
  }

  private List<TourData> initializeSampleData() {
    String[][] tourData = {
        {"01","2020","02","03","Tour 1","13000","7800","55","1"},
        {"02","2020","03","04","Tour 2","94000","9800","55","2"},
        {"03","2021","05","15","Tour 3","13000","9800","55","1"},
        {"04","2022","06","14","Tour 4","78000","10800","55","2"},
        {"05","2022","07","20","Tour 5","85000","12800","55","3"},
        };
    return Arrays.stream(tourData).map(tour ->
        TourData.builder()
            .id(Integer.parseInt(tour[0]))
            .startYear(Short.parseShort(tour[1]))
            .startMonth(Short.parseShort(tour[2]))
            .startDay(Short.parseShort(tour[3]))
            .title(tour[4])
            .tourDistance(Float.parseFloat(tour[5]))
            .tourComputedTime_Moving(Long.parseLong(tour[6]))
            .startPulse(Short.parseShort(tour[7]))
            .tourType(Short.parseShort(tour[8]))
            .build()
    ).toList();
  }
}
