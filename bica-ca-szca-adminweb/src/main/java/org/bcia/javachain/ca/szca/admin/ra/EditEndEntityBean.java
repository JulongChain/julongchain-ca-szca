
/*
 *
 * Copyright © 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 * Copyright © 2018  SZCA. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.bcia.javachain.ca.szca.admin.ra;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.cesecore.certificates.endentity.ExtendedInformation;


public class EditEndEntityBean {
    private ExtendedInformation extendedInformation;

    /**
     * Set the current end entity's ExtendedInformation.
     * @param extendedInformation 
     */
    public void setExtendedInformation(ExtendedInformation extendedInformation) {
        this.extendedInformation = extendedInformation;
    }

    /**
     * Parses certificate extension data from a String of properties in Java 
     * Properties format and store it in the extended information.
     *
     * @param extensionData properties to parse and store.
     * @throws IOException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void setExtensionData(String extensionData) {
        Properties properties = new Properties();
        try {
            properties.load(new StringReader(extensionData));
        } catch (IOException ex) {
            // Should not happen as we are only reading from a String.
            throw new RuntimeException(ex);
        }

        // Remove old extensiondata
        Map data = (Map) extendedInformation.getData();
        for (Object o : data.keySet()) {
            if (o instanceof String) {
                String key = (String) o;
                if (key.startsWith(ExtendedInformation.EXTENSIONDATA)) {
                    data.remove(key);
                }
            }
        }

        // Add new extensiondata
        for (Object o : properties.keySet()) {
            if (o instanceof String) {
                String key = (String) o;
                data.put(ExtendedInformation.EXTENSIONDATA + key, properties.getProperty(key));
            }
        }

        // Updated ExtendedInformation to use the new data
        extendedInformation.loadData(data);
    }

    /**
     * @return The extension data read from the extended information and 
     * formatted as in a Properties file.
     */
    public String getExtensionData() {
        final String result;
        if (extendedInformation == null) {
            result = "";
        } else {
            @SuppressWarnings("rawtypes")
            Map data = (Map) extendedInformation.getData();
            Properties properties = new Properties();

            for (Object o : data.keySet()) {
                if (o instanceof String) {
                    String key = (String) o;
                    if (key.startsWith(ExtendedInformation.EXTENSIONDATA)) {
                        String subKey = key.substring(ExtendedInformation.EXTENSIONDATA.length());
                        properties.put(subKey, data.get(key));
                    }
                }

            }

            // Render the properties and remove the first line created by the Properties class.
            StringWriter out = new StringWriter();
            try {
                properties.store(out, null);
            } catch (IOException ex) {
                // Should not happen as we are using a StringWriter
                throw new RuntimeException(ex);
            }

            StringBuffer buff = out.getBuffer();
            String lineSeparator = System.getProperty("line.separator");
            int firstLineSeparator = buff.indexOf(lineSeparator);

            result = firstLineSeparator >= 0 ? buff.substring(firstLineSeparator + lineSeparator.length()) : buff.toString();
        }
        return result;
    }

    /**
     * 
     * @return A Map view of the extension data.
     */
    public Map<String, String> getExtensionDataAsMap() {
        final Map<String, String> result = new HashMap<String, String>();
        if (extendedInformation != null) {
            @SuppressWarnings("rawtypes")
            Map data = (Map) extendedInformation.getData();
            for (Object o : data.keySet()) {
                String key = (String) o;
                if (key.startsWith(ExtendedInformation.EXTENSIONDATA)) {
                    String subKey = key.substring(ExtendedInformation.EXTENSIONDATA.length());
                    result.put(subKey, (String) data.get(key));
                }
            }
        }
        return result;
    }

}
