/**
 * Copyright (c) 2010 Yahoo! Inc. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License. See accompanying LICENSE file.
 */
package org.apache.oozie.util.db;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.oozie.service.InstrumentationService;
import org.apache.oozie.service.Services;
import org.apache.oozie.util.Instrumentation;

public class InstrumentedBasicDataSource extends BasicDataSource {
    public static final String INSTR_GROUP = "jdbc";
    public static final String INSTR_NAME = "connections.active";

    /**
     * The created datasource instruments the active DB connections.
     */
    public InstrumentedBasicDataSource() {
        Instrumentation instr = Services.get().get(InstrumentationService.class).get();
        instr.addSampler(INSTR_GROUP, INSTR_NAME, 60, 1, new Instrumentation.Variable<Long>() {
            public Long getValue() {
                return (long) getNumActive();
            }
        });
    }

}