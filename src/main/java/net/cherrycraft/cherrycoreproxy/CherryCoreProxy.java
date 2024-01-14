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

package net.cherrycraft.cherrycoreproxy;


import net.cherrycraft.cherrycoreproxy.utils.ConfigFileUtil;
import net.md_5.bungee.api.plugin.Plugin;

public class CherryCoreProxy extends Plugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        loadConfig();
    }

    void loadConfig() {
        ConfigFileUtil.createConfigIfNotExists(getDataFolder(), "proxy-config.yml");
        ConfigFileUtil.createConfigIfNotExists(getDataFolder(), "database.yml");
    }

    void saveConfig() {
        ConfigFileUtil.saveAllConfigs(getDataFolder());
    }


    @Override
    public void onDisable() {
        saveConfig();
    }


}
