/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.hadoop.hdds.scm;

import org.apache.hadoop.hdds.conf.Config;
import org.apache.hadoop.hdds.conf.ConfigGroup;
import org.apache.hadoop.hdds.conf.ConfigTag;
import org.apache.hadoop.hdds.conf.ConfigType;

/**
 * The configuration class for the SCM service.
 */
@ConfigGroup(prefix = "hdds.scm")
public class ScmConfig {

  @Config(key = "kerberos.principal",
      type = ConfigType.STRING,
      defaultValue = "",
      tags = { ConfigTag.SECURITY, ConfigTag.OZONE },
      description = "This Kerberos principal is used by the SCM service."
  )
  private String principal;

  @Config(key = "kerberos.keytab.file",
      type = ConfigType.STRING,
      defaultValue = "",
      tags = { ConfigTag.SECURITY, ConfigTag.OZONE },
      description = "The keytab file used by SCM daemon to login as "+
          "its service principal."
  )
  private String keytab;

  @Config(key = "unknown-container.action",
      type = ConfigType.STRING,
      defaultValue = "WARN",
      tags = { ConfigTag.SCM, ConfigTag.MANAGEMENT },
      description =
          "The action taken by SCM to process unknown "
          + "containers that reported by Datanodes. The default "
          + "action is just logging container not found warning, "
          + "another available action is DELETE action. "
          + "These unknown containers will be deleted under this "
          + "action way."
  )
  private String action;

  public void setKerberosPrincipal(String kerberosPrincipal) {
    this.principal = kerberosPrincipal;
  }


  public void setKerberosKeytab(String kerberosKeytab) {
    this.keytab = kerberosKeytab;
  }

  public void setUnknownContainerAction(String unknownContainerAction) {
    this.action = unknownContainerAction;
  }

  public String getKerberosPrincipal() {
    return this.principal;
  }

  public String getKerberosKeytab() {
    return this.keytab;
  }

  public String getUnknownContainerAction() {
    return this.action;
  }

  /**
   * Configuration strings class.
   * required for SCMSecurityProtocol where the KerberosInfo references
   * the old configuration with
   * the annotation shown below:-
   * @KerberosInfo(serverPrincipal = ScmConfigKeys
   *    .HDDS_SCM_KERBEROS_PRINCIPAL_KEY)
   */
  public static class ConfigStrings {
    public static final String HDDS_SCM_KERBEROS_PRINCIPAL_KEY =
          "hdds.scm.kerberos.principal";
    public static final String HDDS_SCM_KERBEROS_KEYTAB_FILE_KEY =
          "hdds.scm.kerberos.keytab.file";
  }

  public static final String HDDS_SCM_UNKNOWN_CONTAINER_ACTION =
      "hdds.scm.unknown-container.action";
}
