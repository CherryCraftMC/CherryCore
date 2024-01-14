/*
 * (c) 2024 CherryCraft. All rights reserved.
 *
 *  This software is the confidential and proprietary information of CherryCraft
 *  ("Confidential Information"). You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you
 *  entered into with CherryCraft.
 *
 *  UNAUTHORIZED COPYING, DISTRIBUTION, OR REPRODUCTION OF THIS SOFTWARE, IN WHOLE OR
 *  IN PART, IS STRICTLY PROHIBITED. UNLESS OTHERWISE EXPRESSLY AGREED UPON IN A
 *  WRITTEN AGREEMENT, CHERRYCRAFT PROVIDES THIS SOFTWARE "AS IS" WITHOUT WARRANTY OF ANY
 *  KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 *  NON-INFRINGEMENT.
 *
 *  For inquiries, please contact CherryCraft at copyright@cherrycraft.net.
 */

package net.cherrycraft.cherrycoreproxy.utils;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class ConfigFileUtil {

    private static final Map<String, Configuration> configs = new HashMap<>();

    public static Configuration loadConfig(File dataFolder, String fileName) {
        try {
            File file = new File(dataFolder, fileName);
            Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            configs.put(fileName, config);
            return config;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration: " + fileName, e);
        }
    }

    public static void saveConfig(Configuration configuration, File dataFolder, String fileName) {
        try {
            File file = new File(dataFolder, fileName);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save configuration: " + fileName, e);
        }
    }

    public static void saveAllConfigs(File dataFolder) {
        for (Map.Entry<String, Configuration> entry : configs.entrySet()) {
            saveConfig(entry.getValue(), dataFolder, entry.getKey());
        }
    }

    public static void createConfigIfNotExists(File dataFolder, String fileName) {
        try {
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            File file = new File(dataFolder, fileName);

            if (!file.exists()) {
                try (InputStream in = ConfigFileUtil.class.getClassLoader().getResourceAsStream(fileName)) {
                    Files.copy(in, file.toPath());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create configuration: " + fileName, e);
        }
    }

    public static String getString(File dataFolder, String fileName, String path) throws IOException {
        Configuration configuration = loadConfig(dataFolder, fileName);
        return configuration.getString(path);
    }

    public static int getInt(File dataFolder, String fileName, String path) throws IOException {
        Configuration configuration = loadConfig(dataFolder, fileName);
        return configuration.getInt(path);
    }

    public static boolean getBoolean(File dataFolder, String fileName, String path) throws IOException {
        Configuration configuration = loadConfig(dataFolder, fileName);
        return configuration.getBoolean(path);
    }


}
