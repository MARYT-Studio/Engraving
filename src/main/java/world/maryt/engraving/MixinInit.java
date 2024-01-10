package world.maryt.engraving;

import zone.rong.mixinbooter.ILateMixinLoader;
import java.util.Collections;
import java.util.List;

public class MixinInit implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        return Collections.singletonList("mixins.engraving.json");
    }
}
