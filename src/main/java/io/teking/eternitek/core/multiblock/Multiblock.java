package io.teking.eternitek.core.multiblock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class Multiblock {

    private final char[][][] pattern;
    private final Map<Character, Predicate<BlockState>> stateCheckers;
    private final int width;
    private final int height;
    private final int length;

    public Multiblock(char[][][] pattern, Map<Character, Predicate<BlockState>> stateCheckers) {
        this.pattern = pattern;
        this.stateCheckers = stateCheckers;
        this.height = pattern.length;
        this.width = pattern[0].length;
        this.length = pattern[0][0].length;
    }

    public boolean isValid(BlockPos pos, World world) {
        return (
                checkDirection(pos, world, Direction.NORTH) ||
                checkDirection(pos, world, Direction.EAST)  ||
                checkDirection(pos, world, Direction.SOUTH) ||
                checkDirection(pos, world, Direction.WEST)
        );
    }

    public boolean checkDirection(BlockPos pos, World world, Direction direction) {

        if(direction == Direction.UP || direction == Direction.DOWN) return false;

        Vec3i core = getCorePos();
        int centerX = core.getX();
        int centerY = core.getY();
        int centerZ = core.getZ();

        BlockPos origin;
        switch(direction) {
            case NORTH:
                origin = pos.add(-centerX, -centerY, -centerZ);
                break;
            case EAST:
                origin = pos.add(-centerZ, -centerY, centerX - (pattern[0][0].length - 1));
                break;
            case SOUTH:
                origin = pos.add(centerX - (pattern[0][0].length - 1), -centerY, centerZ - (pattern[0].length - 1));
                break;
            case WEST:
                origin = pos.add(centerZ - (pattern[0].length - 1), -centerY, -centerX);
                break;
            default:
                return false;
        }

        for(int y = 0; y < height; y++) {
            for(int z = 0; z < length; z++) {
                for(int x = 0; x < width; x++) {

                    char expected = pattern[y][z][x];
                    if(expected == '*') continue;

                    BlockPos check;
                    switch(direction) {
                        case NORTH:
                            check = origin.add(x, y, z);
                            break;
                        case EAST:
                            check = origin.add(z, y, (pattern[0][0].length - 1) - x);
                            break;
                        case SOUTH:
                            check = origin.add((pattern[0][0].length - 1) - x, y, (pattern[0].length - 1) - z);
                            break;
                        case WEST:
                            check = origin.add((pattern[0].length - 1) - z, y, x);
                            break;
                        default:
                            return false;
                    }

                    BlockState state = world.getBlockState(check);

                    if(!checkBlock(expected, state, check)) return false;

                }
            }
        }

        return true;
        
    }

    public boolean checkBlock(char expected, BlockState state, BlockPos pos) {
        if (expected == ' ') {
            if (!state.isAir()) {
                return false;
            }
            return true;
        }

        Predicate<BlockState> checker = stateCheckers.get(expected);
        if (checker == null || !checker.test(state)) {
            return false;
        }
        return true;
    }

    public Vec3i getCorePos() {
        for(int y = 0; y < height; y++) {
            for(int z = 0; z < length; z++) {
                for(int x = 0; x < width; x++) {
                    if(pattern[y][z][x] == '*') {
                        return new Vec3i(x, y, z);
                    }
                }
            }
        }
        return Vec3i.ZERO;
    }

    public boolean canUse(PlayerEntity player, BlockPos pos, World world) {
        if(isValid(pos, world)) {
            return true;
        }
        player.sendMessage(Text.translatable("texts.eternitek.invalid_structure"), true);
        return false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    public record Data(Identifier id, Character controller, Map<Character, RegistryEntryList<Block>> key, List<List<String>> pattern) {

        private static final Codec<Character> CONTROLLER_CODEC = Codec.STRING.comapFlatMap(Data::validateKey, String::valueOf);

        private static final Codec<Character> KEY_ENTRY_CODEC = Codec.STRING.comapFlatMap(Data::validateKey, String::valueOf);

        private static final Codec<List<List<String>>> PATTERN_CODEC = Codec.STRING.listOf().listOf().comapFlatMap(Data::validatePattern, Function.identity());

        public static final Codec<Data> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Identifier.CODEC.fieldOf("id").forGetter(Data::id),
                CONTROLLER_CODEC.fieldOf("controller").forGetter(Data::controller),
                Codecs.strictUnboundedMap(KEY_ENTRY_CODEC, RegistryCodecs.entryList(RegistryKeys.BLOCK)).fieldOf("key").forGetter(Data::key),
                PATTERN_CODEC.fieldOf("pattern").forGetter(Data::pattern)
        ).apply(instance, Data::new));

        private static DataResult<Character> validateKey(String keyEntry) {
            if(keyEntry.length() != 1) {
                return DataResult.error(() -> "Invalid key entry: '" + keyEntry + "' is an invalid symbol (must be 1 character only).");
            }
            if(keyEntry.charAt(0) == '*' || keyEntry.charAt(0) == ' ') {
                return DataResult.error(() -> "Invalid key entry: '" + keyEntry + "' is a reserved symbol.");
            }
            return DataResult.success(keyEntry.charAt(0));
        }

        private static DataResult<List<List<String>>> validatePattern(List<List<String>> pattern) {
            for(List<String> stringList : pattern) {
                if(stringList.size() > 16) {
                    return DataResult.error(() -> "Multiblock cannot be larger than 16 blocks.");
                } else if(stringList.isEmpty()) {
                    return DataResult.error(() -> "Multiblock cannot be empty.");
                }
            }
            return DataResult.success(pattern);
        }

    }

}
