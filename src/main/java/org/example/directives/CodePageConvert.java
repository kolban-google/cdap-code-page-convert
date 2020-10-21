/*
 *  Copyright © 2017 Cask Data, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */

package org.example.directives;

import java.util.ArrayList;
import java.util.List;

import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.api.annotation.Plugin;
import io.cdap.wrangler.api.Arguments;
import io.cdap.wrangler.api.Directive;
import io.cdap.wrangler.api.ExecutorContext;
import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.parser.ColumnName;
import io.cdap.wrangler.api.parser.Text;
import io.cdap.wrangler.api.parser.TokenType;
import io.cdap.wrangler.api.parser.UsageDefinition;

/**
 * This class <code>TextReverse</code>implements a <code>Directive</code> interface
 * for reversing the text specified by the value of the <code>column</code>.
 */
@Plugin(type = Directive.TYPE)
@Name("code-page-convert")
@Description("Converts from one code page to another.")
public final class CodePageConvert implements Directive {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private String sourceColumn;
  private String targetColumn;
  private String sourceCp;
  //private String targetCp;

  @Override
  public UsageDefinition define() {
    //System.out.println("CodePageConvert::define");
    UsageDefinition.Builder builder = UsageDefinition.builder("code-page-convert");
    builder.define("source-column", TokenType.COLUMN_NAME);
    builder.define("target-column", TokenType.COLUMN_NAME);
    builder.define("source-cp", TokenType.TEXT);
    //builder.define("target-cp", TokenType.TEXT);
    return builder.build();
  }

  @Override
  public void initialize(Arguments args) {
    //System.out.println("CodePageConvert::initialize");
    sourceColumn = ((ColumnName) args.value("source-column")).value();
    targetColumn = ((ColumnName) args.value("target-column")).value();
    sourceCp = ((Text) args.value("source-cp")).value();
    //targetCp = ((Text) args.value("target-cp")).value();
    System.out.println(String.format("sourceColumn: %s, targetColumn: %s, sourceCp: %s", sourceColumn, targetColumn, sourceCp));
  }

  @Override
  public List<Row> execute(List<Row> rows, ExecutorContext context) {
    //System.out.println("CodePageConvert::execute");
    //System.out.println("About to read from " + sourceColumn);
    ArrayList<Row> newRows = new ArrayList<>();
    for (Row row: rows) {
      Object o = row.getValue(sourceColumn);
      /*

      if (o == null) {
        System.out.println("Could not get value");
        continue;
      }
      System.out.println("Type of o is " + o.getClass().toString());
      */
      
      byte dat[] = (byte[])o;
      try {
        String newValue = new String(dat, sourceCp);
        System.out.println("Value: " + newValue);
        Row newRow = new Row(row);
        newRow.addOrSet(targetColumn, newValue);
        newRows.add(newRow);
      }
      catch(Exception e) {
        e.printStackTrace();
      }
    } // For each row
    return newRows;
  }

  @Override
  public void destroy() {
    // no-op
  }
}
