package com.thomashan.game.sequence.command

import com.thomashan.game.sequence.GeneratedId
import com.thomashan.game.sequence.Timestamped
import com.thomashan.game.sequence.events.Event

trait Command<E extends Event> implements Timestamped, GeneratedId {
    abstract E execute()
}
