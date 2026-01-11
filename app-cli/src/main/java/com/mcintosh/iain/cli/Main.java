package com.mcintosh.iain.cli;

import com.mcintosh.iain.core.CoreProcessor;
import com.mcintosh.iain.core.task.ParseContext;
import java.io.IOException;

public class Main {

  public static void main(String[] args) {
    // Parse, validate and process the provided CLI arguments
    ParseContext parseContext = CommandLineParser.parse(args);

    /*
    For demonstration purposes I show a usage text if the ParseContext can not be created.
    To prevent polluting the console with exceptions, the ParseContext is null in such cases.
    Here we only continue to call the core processing logic if a ParseContext exists.
     */
    if (parseContext != null) {
      try {
        CoreProcessor.process(parseContext);
      } catch (IOException e) {
        Usage.showUsage(e.getMessage());
      }
    }
  }
}
