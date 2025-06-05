package world.maryt.engraving.config;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigParser {
    public static ArrayList<String> getBypassDamageTypes() {
        try {
            return Stream.of(EngravingConfig.bypassDamageTypes).collect(Collectors.toCollection(ArrayList::new));
        } catch (Exception ignored) {
            return new ArrayList<>();
        }
    }

    public static boolean getBlessed() {
        try {
            return EngravingConfig.WorkingMode.equals("Blessed");
        } catch (Exception ignored) {
            return false;
        }
    }
}
