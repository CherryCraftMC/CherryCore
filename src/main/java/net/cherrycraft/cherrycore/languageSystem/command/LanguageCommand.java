package net.cherrycraft.cherrycore.languageSystem.command;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import net.cherrycraft.cherrycore.languageSystem.data.LanguageData;
import net.cherrycraft.cherrycore.manager.CommandManager;
import net.cherrycraft.cherrycore.manager.LanguageManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LanguageCommand extends CommandManager {

    MiniMessage miniMessage = MiniMessage.builder().build();
    LegacyComponentSerializer serializer = LegacyComponentSerializer.legacySection();

    public LanguageCommand(String commandName) {
        super("language");
        setDescription("Change your language");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            LanguageManager language = new LanguageManager();
            ChestGui gui = new ChestGui(4, serializer.serialize(miniMessage.deserialize(language.getMessage(player, "gui.translatorgui.title"))));
            StaticPane pane = new StaticPane(1, 1, 7, 1);
            int slot = 0;
            for (Map.Entry<String, LanguageData> lang : language.getLanguagesData().entrySet()) {
                ItemStack is = new ItemStack(Material.PAPER);
                ItemMeta meta = is.getItemMeta();
                meta.setDisplayName(serializer.serialize(miniMessage.deserialize(lang.getValue().getLanguageFile().get("language.fullname"))));
                if (lang.getValue().getLanguageName().equals(language.getPlayersLanguage(player))) {
                    meta.addEnchant(Enchantment.LUCK, 1, true);
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
                LanguageData lData = language.getLanguagesData().get(lang.getValue().getLanguageName());
                List<Component> lore = new ArrayList<>();
                lore.add(miniMessage.deserialize(lData.getLanguageFile().get("gui.translatorgui.changelanguageto")));
                lore.add(Component.empty());
                lore.add(miniMessage.deserialize(lData.getLanguageFile().get("gui.translatorgui.supported.games")));
                lore.add(miniMessage.deserialize(lData.getLanguageFile().get("gui.translatorgui.skyblock")));
                lore.add(miniMessage.deserialize(lData.getLanguageFile().get("gui.translatorgui.murdermystery")));
                lore.add(Component.empty());
                lore.add(miniMessage.deserialize(lData.getLanguageFile().get("gui.translatorgui.clicktochnage")));
                lore.set(0, lore.get(0).decoration(TextDecoration.ITALIC, false));
                lore.set(2, lore.get(2).decoration(TextDecoration.ITALIC, false));
                lore.set(3, lore.get(3).decoration(TextDecoration.ITALIC, false));
                lore.set(4, lore.get(4).decoration(TextDecoration.ITALIC, false));
                lore.set(6, lore.get(6).decoration(TextDecoration.ITALIC, false));
                meta.lore(lore);
                is.setItemMeta(meta);
                GuiItem book = new GuiItem(is, event -> {
                    language.setPlayersLanguage(player, lang.getValue().getLanguageName());
                    player.sendMessage(serializer.serialize(miniMessage.deserialize(language.getMessage(player, "gui.action.language.changed"))));
                    event.getInventory().close();
                    Player player1 = (Player) event.getWhoClicked();

                    player1.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1.0f, 1.0f);
                });
                is.setItemMeta(meta);
                pane.addItem(book, Slot.fromIndex(slot));
                slot++;
            }
            gui.addPane(pane);
            gui.show(player);
        } else {
            System.out.println("This command can only be executed by players.");
        }
    }
}
