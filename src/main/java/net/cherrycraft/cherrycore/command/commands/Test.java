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

package net.cherrycraft.cherrycore.command.commands;

import net.cherrycraft.cherrycore.manager.Command;
import org.bukkit.command.CommandSender;

public class Test extends Command {
    public Test(String commandName) {
        super("test");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        System.out.println("Test execute");
        return true;
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String getPermissionMessage() {
        return null;
    }
}
