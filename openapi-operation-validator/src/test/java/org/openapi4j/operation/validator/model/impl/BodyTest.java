package org.openapi4j.operation.validator.model.impl;

import com.fasterxml.jackson.databind.JsonNode;

import org.junit.Test;
import org.openapi4j.core.util.TreeUtil;
import org.openapi4j.parser.model.v3.MediaType;
import org.openapi4j.parser.model.v3.Schema;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BodyTest {
  @Test
  public void fromMap() throws Exception {
    Map<String, Object> values = new HashMap<>();
    values.put("key", "value");
    Body body = Body.from(values);

    checkBody(body, TreeUtil.toJsonNode(values));
  }

  @Test
  public void fromList() throws Exception {
    List<Object> values = new ArrayList<>();
    values.add(0, "value0");
    values.add(1, "value1");
    Body body = Body.from(values);

    checkBody(body, TreeUtil.toJsonNode(values));
  }

  @Test
  public void fromBoolean() throws Exception {
    Boolean value = Boolean.TRUE;
    Body body = Body.from(value);

    checkBody(body, TreeUtil.toJsonNode(value));
  }

  @Test
  public void fromString() throws Exception {
    String values = "{\"key\":\"value\"}";
    Body body = Body.from(values);

    checkBody(body, TreeUtil.json.readTree(values));
  }

  @Test
  public void fromJsonNode() throws Exception {
    String values = "{\"key\":\"value\"}";
    Body body = Body.from(TreeUtil.json.readTree(values));

    checkBody(body, TreeUtil.json.readTree(values));
  }

  @Test
  public void fromInputStream() throws Exception {
    String values = "{\"key\":\"value\"}";
    Body body = Body.from(new ByteArrayInputStream(values.getBytes()));

    checkBody(body, TreeUtil.json.readTree(new ByteArrayInputStream(values.getBytes())));
  }

  private void checkBody(Body body, JsonNode values) throws IOException {
    Schema schema = new Schema();
    schema.setProperty("key", new Schema().setType("string"));

    assertEquals(
      values,
      body.getContentAsNode(new MediaType().setSchema(schema), "application/json"));
  }
}
