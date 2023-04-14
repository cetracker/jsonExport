package org.tour;

import org.tour.export.ExportService;

/**
 * Initially created on 2023-04-14
 */
public class Main {

  public static void main(String[] args) {

/*
    Logger logger = LogManager.getRootLogger();
    logger.trace("Configuration File Defined To Be :: "+System.getProperty("log4j.configurationFile"));
*/

    ExportService service = new ExportService();
    service.doExport();

  }
}
