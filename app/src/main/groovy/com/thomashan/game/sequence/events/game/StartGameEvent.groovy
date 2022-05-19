package com.thomashan.game.sequence.events.game

import com.thomashan.game.sequence.domain.game.Game
import com.thomashan.game.sequence.events.Event

trait StartGameEvent extends Event {
    final Optional<Game> game
}
