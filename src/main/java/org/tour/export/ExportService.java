package org.tour.export;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.tour.domain.TourData;
import org.tour.persistence.TourRepository;

/** Initially created on 14/04/2023 */
@Slf4j
public class ExportService {

  final ObjectMapper mapper = new ObjectMapper();
  private final TourRepository repository;

  public ExportService() {
    repository = new TourRepository();
  }

  public void doExport() {
    // Convert all tours without any filters applied
    // and print it to console
    final List<TourData> tourDataList = repository.findAll();
    LOGGER.info("# ------ Unfiltered List ---------");
    tourDataList.forEach(tour -> LOGGER.info(tour.toJson()));

    // Filter tour data properties
    final Set<String> exportFilter = Set.of("id", "title");
    // print this complete list as JSON array
    LOGGER.info("# ------ Complete list with selected properties ---------");
    printAsFormattedJSON(
        tourDataList.stream().map(tour -> tour.toJsonObject(exportFilter)).toList());

    // Filter for tours starting from (using the start year for simplicity here)
    final List<TourData> tourDataListYear =
        repository.findAllByStartYearGreaterOrEqual((short) 2021);
    // using a different filter for tour data properties
    final Set<String> exportFilterYear = Set.of("id", "title", "tourDistance", "startYear");
    // print this complete list as JSON array
    LOGGER.info("# ------ List filtered by date with selected properties ---------");
    printAsFormattedJSON(
        tourDataListYear.stream().map(tour -> tour.toJsonObject(exportFilterYear)).toList());

    // Filter tours on specified tour types too
    Set<Short> tourTypeFilter = Set.of((short) 1, (short) 2);
    final Set<String> exportFilterTourType = Set.of("id", "tourType", "title", "tourDistance", "startYear");
    final List<TourData> tourDataListTourType =
        repository.findAllByStartYearGreaterOrEqualAndTourtypeIsAnyOf((short) 2021, tourTypeFilter);
    // print this complete list as JSON array
    LOGGER.info("# ------ List filtered by date and TourType with selected properties ---------");
    printAsFormattedJSON(
        tourDataListTourType.stream().map(tour -> tour.toJsonObject(exportFilterTourType)).toList());
  }

  private void printAsFormattedJSON(final List<Map<String, Object>> filteredTourList) {
    String filteredString = "";
    try {
      filteredString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(filteredTourList);
    } catch (JsonProcessingException e) {
      LOGGER.warn("Conversion to JSON format failed", e);
    }
    LOGGER.info(filteredString);
  }
}
