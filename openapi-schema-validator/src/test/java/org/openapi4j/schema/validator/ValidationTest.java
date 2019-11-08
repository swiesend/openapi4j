package org.openapi4j.schema.validator;

import com.fasterxml.jackson.databind.JsonNode;

import org.junit.Assert;
import org.junit.Test;
import org.openapi4j.core.model.v3.OAI3;
import org.openapi4j.core.model.v3.OAI3Context;
import org.openapi4j.core.model.v3.OAI3SchemaKeywords;
import org.openapi4j.core.util.TreeUtil;
import org.openapi4j.schema.validator.v3.MaximumToleranceValidator;
import org.openapi4j.schema.validator.v3.MyEntityValidator;
import org.openapi4j.schema.validator.v3.SchemaValidator;
import org.openapi4j.schema.validator.v3.ValidatorInstance;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.openapi4j.schema.validator.v3.ValidationOptions.ADDITIONAL_PROPS_RESTRICT;

public class ValidationTest {
  @Test
  public void additionalPropertiesValidator() throws Exception {
    ValidationUtil.validate("/schema/additionalProperties.json");
  }

  @Test
  public void allOfValidator() throws Exception {
    ValidationUtil.validate("/schema/allOf.json");
  }

  @Test
  public void anyOfValidator() throws Exception {
    ValidationUtil.validate("/schema/anyOf.json");
  }

  @Test
  public void dependenciesValidator() throws Exception {
    ValidationUtil.validate("/schema/dependencies.json");
  }

  @Test
  public void enumValidator() throws Exception {
    ValidationUtil.validate("/schema/enum.json");
  }

  @Test
  public void formatValidator() throws Exception {
    ValidationUtil.validate("/schema/format.json");
  }

  @Test
  public void itemsValidator() throws Exception {
    ValidationUtil.validate("/schema/items.json");
  }

  @Test
  public void maximumValidator() throws Exception {
    ValidationUtil.validate("/schema/maximum.json");
  }

  @Test
  public void maxItemsValidator() throws Exception {
    ValidationUtil.validate("/schema/maxItems.json");
  }

  @Test
  public void maxLengthValidator() throws Exception {
    ValidationUtil.validate("/schema/maxLength.json");
  }

  @Test
  public void maxPropertiesValidator() throws Exception {
    ValidationUtil.validate("/schema/maxProperties.json");
  }

  @Test
  public void minimumValidator() throws Exception {
    ValidationUtil.validate("/schema/minimum.json");
  }

  @Test
  public void minItemsValidator() throws Exception {
    ValidationUtil.validate("/schema/minItems.json");
  }

  @Test
  public void minLengthValidator() throws Exception {
    ValidationUtil.validate("/schema/minLength.json");
  }

  @Test
  public void minPropertiesValidator() throws Exception {
    ValidationUtil.validate("/schema/minProperties.json");
  }

  @Test
  public void multipleOfValidator() throws Exception {
    ValidationUtil.validate("/schema/multipleOf.json");
  }

  @Test
  public void notValidator() throws Exception {
    ValidationUtil.validate("/schema/not.json");
  }

  @Test
  public void nullableValidator() throws Exception {
    ValidationUtil.validate("/schema/nullable.json");
  }

  @Test
  public void oneOfValidator() throws Exception {
    ValidationUtil.validate("/schema/oneOf.json");
  }

  @Test
  public void patternValidator() throws Exception {
    ValidationUtil.validate("/schema/pattern.json");
  }

  @Test
  public void patternPropertiesValidator() throws Exception {
    ValidationUtil.validate("/schema/patternProperties.json");
  }

  @Test
  public void propertiesValidator() throws Exception {
    ValidationUtil.validate("/schema/properties.json");
  }

  @Test
  public void requiredValidator() throws Exception {
    ValidationUtil.validate("/schema/required.json");
  }

  @Test
  public void refValidator() throws Exception {
    ValidationUtil.validate("/schema/reference.json");
  }

  @Test
  public void typeValidator() throws Exception {
    ValidationUtil.validate("/schema/type.json");
  }

  @Test
  public void uniqueItemsValidator() throws Exception {
    ValidationUtil.validate("/schema/uniqueItems.json");
  }

  @Test
  public void additionalPropsRestrictValidation() throws Exception {
    Map<Byte, Boolean> options = new HashMap<>();
    options.put(ADDITIONAL_PROPS_RESTRICT, true);
    ValidationUtil.validate("/schema/override/additionalPropsRestrictOption.json", options, null);
  }

  @Test
  public void overriddenValidation() throws Exception {
    Map<String, ValidatorInstance> validators = new HashMap<>();
    validators.put(OAI3SchemaKeywords.MAXIMUM, MaximumToleranceValidator::create);
    validators.put("x-myentity-val", MyEntityValidator::create);
    ValidationUtil.validate("/schema/override/maximumTolerance.json", null, validators);
  }

  @Test
  public void additionalValidation() throws Exception {
    Map<String, ValidatorInstance> validators = new HashMap<>();
    validators.put("x-myentity-val", MyEntityValidator::create);
    ValidationUtil.validate("/schema/override/myEntityValidation.json", null, validators);
  }

  @Test(expected = RuntimeException.class)
  public void schemaValidatorResolutionException() throws RuntimeException, IOException {
    new SchemaValidator(
      null,
      "my_schema",
      TreeUtil.json.readTree(ValidationTest.class.getResource("/schema/reference.json")));
  }

  @Test
  public void doNotChangeContextIfGiven() throws Exception {
    JsonNode schemaNode = TreeUtil.json.readTree("{\"not\": {\"type\": \"integer\"} }");

    OAI3Context apiContext = new OAI3Context(new URI("/"), schemaNode);
    ValidationContext<OAI3> validationContext = new ValidationContext<>(apiContext);
    SchemaValidator validator = new SchemaValidator(validationContext, "my_schema", schemaNode);

    Assert.assertEquals(validationContext, validator.getContext());
  }
}