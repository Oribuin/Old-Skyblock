package xyz.oribuin.skyblock.schematics;

import org.bukkit.DyeColor;
import org.bukkit.block.banner.PatternType;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class BannerBlock {
    private DyeColor bannerBaseColor;
    private List<Pattern> bannerPattern;
    private static HashMap<String, PatternType> patternKey;

    static {
        patternKey = new HashMap<>();
        patternKey.put("", PatternType.BASE);
        patternKey.put("bo", PatternType.BORDER);
        patternKey.put("bri", PatternType.BRICKS);
        patternKey.put("cm", PatternType.CIRCLE_MIDDLE);
        patternKey.put("cre", PatternType.CREEPER);
        patternKey.put("cro", PatternType.CROSS);
        patternKey.put("cb", PatternType.CURLY_BORDER);
        patternKey.put("dl", PatternType.DIAGONAL_LEFT);
        patternKey.put("dlm", PatternType.DIAGONAL_LEFT_MIRROR);
        patternKey.put("dr", PatternType.DIAGONAL_RIGHT);
        patternKey.put("drm", PatternType.DIAGONAL_RIGHT_MIRROR);
        patternKey.put("flo", PatternType.FLOWER);
        patternKey.put("gl", PatternType.GLOBE);
        patternKey.put("gr", PatternType.GRADIENT);
        patternKey.put("gru", PatternType.GRADIENT_UP);
        patternKey.put("hh", PatternType.HALF_HORIZONTAL);
        patternKey.put("hhm", PatternType.HALF_HORIZONTAL_MIRROR);
    }
}
