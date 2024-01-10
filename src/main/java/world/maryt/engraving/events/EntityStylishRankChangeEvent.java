package world.maryt.engraving.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.event.IEntityEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.Entity;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("mods.engraving.event.EntityStylishChangeEvent")
@ZenRegister
public class EntityStylishRankChangeEvent implements IEntityEvent {
    private final Entity entity;
    private int amount;

    public EntityStylishRankChangeEvent(Entity entity, int amount) {
        this.entity = entity;
        this.amount = amount;
    }

    @ZenGetter("amount")
    @ZenMethod
    public int getAmount() {
        return this.amount;
    }

    @ZenSetter("amount")
    @ZenMethod
    public void setAmount(int amount) { this.amount = amount; }

    @Override
    public IEntity getEntity() {
        return CraftTweakerMC.getIEntity(this.entity);
    }
}
