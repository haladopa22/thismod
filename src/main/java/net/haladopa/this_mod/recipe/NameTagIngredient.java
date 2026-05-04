package net.haladopa.this_mod.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.AbstractIngredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class NameTagIngredient extends AbstractIngredient {
    public static final IIngredientSerializer<NameTagIngredient> SERIALIZER = new Serializer();

    private final String name;

    public NameTagIngredient(String name) {
        super(Stream.of(new Ingredient.ItemValue(new ItemStack(Items.NAME_TAG))));
        this.name = name;
    }

    @Override
    public boolean test(@Nullable ItemStack stack) {
        if (stack == null || stack.isEmpty()) return false;
        if (stack.getItem() != Items.NAME_TAG) return false;
        return stack.hasCustomHoverName() && stack.getHoverName().getString().equals(this.name);
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public JsonElement toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "this_mod:named_tag");
        json.addProperty("name", this.name);
        return json;
    }

    private static class Serializer implements IIngredientSerializer<NameTagIngredient> {
        @Override
        public NameTagIngredient parse(JsonObject json) {
            return new NameTagIngredient(json.get("name").getAsString());
        }

        @Override
        public NameTagIngredient parse(FriendlyByteBuf buffer) {
            return new NameTagIngredient(buffer.readUtf());
        }

        @Override
        public void write(FriendlyByteBuf buffer, NameTagIngredient ingredient) {
            buffer.writeUtf(ingredient.name);
        }
    }
}
