package com.github.kai9026.mysimplebank.archunit;

import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.github.kai9026.mysimplebank",
    importOptions = {
        ImportOption.DoNotIncludeTests.class,
        ImportOption.DoNotIncludeJars.class})
public class CommonRulesTest {

  @ArchTest
  static ArchRule no_generic_exceptions = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

  @ArchTest
  static ArchRule no_field_injections = NO_CLASSES_SHOULD_USE_FIELD_INJECTION;

  @ArchTest
  static ArchRule no_use_standard_streams = NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;
}
