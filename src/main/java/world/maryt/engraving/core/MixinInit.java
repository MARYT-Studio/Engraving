package world.maryt.engraving.core;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class MixinInit implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs()
    {
        List<String> configs = new ArrayList<>();
        configs.add("mixins.engraving.json");
        return configs;
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig)
    {
        switch (mixinConfig)
        {
            case "mixins.engraving.json":
                return true;
            case "mixins.engraving_old_version.json": {
                for (ModContainer mod: Loader.instance().getModList()) {
                    if (!Objects.equals(mod.getModId(), "slashblade_addon")) continue;
                    String version = mod.getVersion();
                    return isNewSjap(version);
                }
            }
        }
        return true;
    }

    private boolean isNewSjap(String version) {
        @SuppressWarnings("SuspiciousRegexArgument")
        String[] versionNumbers = version.split(".");
        if (versionNumbers.length != 3) return false;
        try {
            int major = Integer.parseInt(versionNumbers[0]);
            int minor = Integer.parseInt(versionNumbers[1]);
            int fix = Integer.parseInt(versionNumbers[2]);

            return major >= 1 && minor >= 7 && fix >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
