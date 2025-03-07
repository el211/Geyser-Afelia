/*
 * Copyright (c) 2019-2022 GeyserMC. http://geysermc.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/Geyser
 */

package org.geysermc.geyser.translator.sound.entity;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.packet.LevelSoundEventPacket;
import org.geysermc.geyser.entity.type.Entity;
import org.geysermc.geyser.entity.type.living.animal.GoatEntity;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.geyser.translator.sound.EntitySoundInteractionTranslator;
import org.geysermc.geyser.translator.sound.SoundTranslator;

@SoundTranslator(entities = {"cow", "goat"}, items = "bucket")
public class MilkEntitySoundInteractionTranslator implements EntitySoundInteractionTranslator {

    @Override
    public void translate(GeyserSession session, Vector3f position, Entity value) {
        if (!session.getPlayerInventory().getItemInHand().getMapping(session).getJavaIdentifier().equals("minecraft:bucket")) {
            return;
        }
        if (value.getFlag(EntityFlag.BABY)) {
            return;
        }

        SoundEvent milkSound;
        if (value instanceof GoatEntity && ((GoatEntity) value).isScreamer()) {
            milkSound = SoundEvent.MILK_SCREAMER;
        } else {
            milkSound = SoundEvent.MILK;
        }
        LevelSoundEventPacket levelSoundEventPacket = new LevelSoundEventPacket();
        levelSoundEventPacket.setPosition(position);
        levelSoundEventPacket.setBabySound(false);
        levelSoundEventPacket.setRelativeVolumeDisabled(false);
        levelSoundEventPacket.setIdentifier(":");
        levelSoundEventPacket.setSound(milkSound);
        levelSoundEventPacket.setExtraData(-1);
        session.sendUpstreamPacket(levelSoundEventPacket);
    }
}
