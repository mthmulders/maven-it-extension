package org.apache.maven.jupiter.it;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.jupiter.extension.MavenIT;
import org.apache.maven.jupiter.extension.MavenRepository;
import org.apache.maven.jupiter.extension.MavenTest;
import org.apache.maven.jupiter.extension.maven.MavenExecutionResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import static org.apache.maven.jupiter.assertj.MavenExecutionResultAssert.assertThat;

/**
 * The following test cases are related together cause they are sharing a common cache {@link MavenRepository}.
 * Furthermore the {@code setup*} cases running in a given order (defined by {@link OrderAnnotation}).
 *
 * @author Karl Heinz Marbaise
 */
@MavenIT(goals = {"install"})
@MavenRepository
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("This is integration test Nr.1 with two setup projects.")
class MavenIntegrationIT {

  @MavenTest
  @Order(10)
  @DisplayName("where setup one is needed.")
  void setup(MavenExecutionResult result) {
    assertThat(result).isSuccessful();
  }

  @MavenTest
  @Order(20)
  @DisplayName("where setup two is needed.")
  void setup_2(MavenExecutionResult result) {
    assertThat(result).isSuccessful();
  }

  @MavenTest(debug = true, goals = {"clean", "verify"})
  @DisplayName("and the test case tries to check for resultion issue.")
  void first_integration_test(MavenExecutionResult result) {
    System.out.println("MavenIntegrationIT.first_integration_test rc:" + result.getReturnCode());
    assertThat(result).isSuccessful();
  }

}
