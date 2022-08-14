package com.github.kai9026.mysimplebank.archunit;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.github.kai9026.mysimplebank",
    importOptions = {
        ImportOption.DoNotIncludeTests.class,
        ImportOption.DoNotIncludeJars.class})
public class ArchitectureUnitTest {

  @ArchTest
  static ArchRule hexagonalArchitecture =
      layeredArchitecture()
          .layer("application").definedBy("..application..")
          .layer("domain").definedBy("..domain..")
          .layer("infrastructure").definedBy("..infrastructure..")
          .optionalLayer("adapters").definedBy("..adapters..")
          .optionalLayer("domain.api").definedBy("..domain.api..")
          .optionalLayer("domain.spi").definedBy("..domain.spi..")

          .whereLayer("infrastructure").mayNotBeAccessedByAnyLayer()
          .whereLayer("application").mayOnlyBeAccessedByLayers("infrastructure",
              "adapters")
          .whereLayer("domain").mayOnlyBeAccessedByLayers("domain", "domain.api",
              "domain.spi", "adapters", "application", "infrastructure");
}
