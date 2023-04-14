package org.tour.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

/** Initially created on 14/04/2023 */
@Slf4j
@Builder
@Getter
public class TourData {
  @JsonProperty private final int id;

  @JsonProperty private final short tourType;

  @JsonProperty private short startYear;

  @JsonProperty private short startMonth;

  @JsonProperty private short startDay;

  @JsonProperty private String title;

  @JsonProperty private float tourDistance;

  @JsonProperty private long tourComputedTime_Moving;

  @JsonProperty private short startPulse;

  /**
   * Serialize this object to JSON format.
   * @return A formated JSON string representation of this object
   */
  public String toJson() {
    final ObjectMapper mapper = new ObjectMapper();
    String jsonString = "";
    try {
      jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      LOGGER.warn("Failed to export tour to Json", e);
    }
    return jsonString;
  }

  /**
   * Reduce this object to a list of wanted properties
   *
   * @param exportFilter Set of properties to be included
   * @return This object reduced to filtered properties as JSONObject
   */
  public Map<String, Object> toJsonObject(Set<String> exportFilter) {
    final ObjectMapper mapper = new ObjectMapper();
    String jsonString = "";
    try {
      jsonString = mapper.writer().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      LOGGER.warn("Failed to export tour to Json", e);
    }
    JSONObject jsonObj = new JSONObject(jsonString);
    return jsonObj.keySet().stream()
        .filter(exportFilter::contains)
        .collect(Collectors.toMap(k -> k, jsonObj::get));
  }
}
